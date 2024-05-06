package com.project.qq10.domain.service;

import com.project.qq10.domain.dto.OrderRequestDto;
import com.project.qq10.domain.dto.OrderResponseDto;
import com.project.qq10.domain.entity.*;
import com.project.qq10.domain.exception.BusinessException;
import com.project.qq10.domain.exception.ErrorMessage;
import com.project.qq10.domain.exception.NotFoundException;
import com.project.qq10.domain.repository.OrderRepository;
import com.project.qq10.domain.repository.OrderedProductRepository;
import com.project.qq10.domain.repository.ProductRepository;
//import com.project.qq10.domain.repository.WishlistRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderedProductRepository orderedProductRepository;
    private final ProductRepository productRepository;
    //private final WishlistRepository wishlistRepository;

    @Transactional
    public void orderedProduct(OrderRequestDto requestDto, User user) {
        Product product = productRepository.findById(requestDto.productId()).orElseThrow(
                () -> new NotFoundException(ErrorMessage.PRODUCT_NOT_FOUND)
        );

        OrderedProduct orderedProduct = createOrderedProduct(product);

        int quantity = requestDto.quantity();
        product.decreaseQuantity(quantity);

        Order order = Order.builder()
                .totalPrice(product.getPrice() * requestDto.quantity())
                .userId(user.getUserId())
                .orderedProductList(List.of(orderedProduct))
                .status(OrderStatus.CREATED)
                .build();

        orderRepository.save(order);
    }

    public List<OrderResponseDto> getUserOrderInfo(User user) {
        List<Order> orderList = orderRepository.findAllByUserId(user.getUserId());

        return orderList.stream().map(OrderResponseDto::of).toList();
    }

    private OrderedProduct createOrderedProduct(Product product) {
        OrderedProduct orderProduct = OrderedProduct.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .build();

        orderedProductRepository.save(orderProduct);

        return orderProduct;
    }

//    private OrderResponseDto createOrder(User user, List<WishlistProduct> wishlistProducts) {
//        List<OrderedProduct> orderedProductList = new ArrayList<>();
//        int totalPrice = 0;
//        for (WishlistProduct wishlistProduct : wishlistProducts) {
//            // 위시리스트 상품
//            Product product = productRepository.findById(wishlistProduct.getProductId()).orElseThrow(
//                    () -> new NotFoundException(ErrorMessage.PRODUCT_NOT_FOUND));
//
//            totalPrice += wishlistProduct.getQuantity() * product.getPrice();
//
//            // 주문에 따른 상품 수량 감소
//            product.decreaseQuantity(wishlistProduct.getQuantity());
//
//            orderedProductList.add(createOrderedProduct(product));
//
//            // 위시리스트 상품 삭제
//            wishlistProduct.delete();
//        }
//
//        Order order = Order.builder()
//                .totalPrice(totalPrice)
//                .userId(user.getUserId())
//                .orderedProductList(orderedProductList)
//                .status(OrderStatus.CREATED)
//                .build();
//
//        return OrderResponseDto.of(order);
//    }
//
//    @Transactional
//    public OrderResponseDto orderWishlist(User user) {
//        Wishlist wishlist = wishlistRepository.findById(user.getWishListId()).orElseThrow(
//                () -> new NotFoundException(ErrorMessage.WISHLIST_NOT_FOUND)
//        );
//
//        List<WishlistProduct> wishlistProducts = wishlist.getWishlistProducts();
//
//        // 주문 생성
//        return createOrder(user, wishlistProducts);
//    }

    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new NotFoundException(ErrorMessage.ORDER_NOT_FOUND)
        );

        if (order.isNotCancelableStatus()) {
            throw new BusinessException(ErrorMessage.CANNOT_CANCEL_ORDER);
        }

        order.cancelOrder();

        // 상품 재고 복구
        List<OrderedProduct> orderedProductList = order.getOrderedProductList();
        for (OrderedProduct orderedProduct : orderedProductList) {
            Product product = productRepository.findById(orderedProduct.getProductId()).orElseThrow(
                    () -> new NotFoundException(ErrorMessage.PRODUCT_NOT_FOUND)
            );

            product.increaseQuantity(orderedProduct.getQuantity());
        }
    }

    @Transactional
    public void returnOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new NotFoundException(ErrorMessage.ORDER_NOT_FOUND)
        );

        if (order.isNotReturnableStatus()) {
            throw new BusinessException(ErrorMessage.CANNOT_RETURN_ORDER_STATUS);
        }
        if (order.isNotReturnableDate()) {
            throw new BusinessException(ErrorMessage.CANNOT_RETURN_ORDER_DATE);
        }

        order.returnOrder();
    }

    // 주문 상태 변경
    @Transactional
    public void updateOrderStatus() {
        List<Order> createdOrders = orderRepository.findAllByStatusAndModifiedAtBefore(OrderStatus.CREATED, LocalDateTime.now().minusDays(1));

        // 생성된지 하루 지난 주문 배송 진행중 처리
        for (Order order : createdOrders) {
            order.updateStatus(OrderStatus.SHIPPING);
        }

        List<Order> shippingOrders = orderRepository.findAllByStatusAndModifiedAtBefore(OrderStatus.SHIPPING, LocalDateTime.now().minusDays(1));

        // 배송 시작된지 하루 지난 주문 배송 완료 처리
        for (Order order : shippingOrders) {
            order.updateStatus(OrderStatus.COMPLETED);
        }

        List<Order> returnedOrders = orderRepository.findAllByStatusAndModifiedAtBefore(OrderStatus.RETURNING, LocalDateTime.now().minusDays(1));

        // 반품 완료된지 하루 지난 상품 재고 복구
        for (Order order : returnedOrders) {
            List<OrderedProduct> orderProductList = order.getOrderedProductList();
            for (OrderedProduct orderProduct : orderProductList) {
                Product product = productRepository.findById(orderProduct.getProductId()).orElseThrow(
                        () -> new NotFoundException(ErrorMessage.PRODUCT_NOT_FOUND)
                );

                product.increaseQuantity(orderProduct.getQuantity());
            }

            order.updateStatus(OrderStatus.RETURNED);
        }
    }


}
