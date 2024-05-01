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

}
