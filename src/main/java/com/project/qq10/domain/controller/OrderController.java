package com.project.qq10.domain.controller;

import com.project.qq10.domain.dto.OrderRequestDto;
import com.project.qq10.domain.dto.OrderResponseDto;
import com.project.qq10.domain.service.OrderService;
import com.project.qq10.global.dto.ApiResponse;
import com.project.qq10.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "order", description = "주문")
@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "상품 주문")
    @PostMapping("/order")
    public ApiResponse<Boolean> orderProduct(@RequestBody OrderRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        orderService.orderedProduct(requestDto, userDetails.getUser());

        return ApiResponse.success();
    }

    @Operation(summary = "주문 정보 조회")
    @GetMapping("/order")
    public ApiResponse<List<OrderResponseDto>> getUserOrderInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<OrderResponseDto> userOrderInfo = orderService.getUserOrderInfo(userDetails.getUser());

        return ApiResponse.success(userOrderInfo);
    }

//    @Operation(summary = "위시리스트 일괄 주문")
//    @PostMapping("/order-wishlist")
//    public ApiResponse<OrderResponseDto> orderWishlist(@AuthenticationPrincipal UserDetailsImpl userDetails) {
//        OrderResponseDto orderResponseDto = orderService.orderWishlist(userDetails.getUser());
//
//        return ApiResponse.success(orderResponseDto);
//    }

    @Operation(summary = "주문 취소")
    @DeleteMapping("/order/{orderId}")
    public ApiResponse<Boolean> cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);

        return ApiResponse.success();
    }

    @Operation(summary = "반품")
    @PutMapping("/order/{orderId}")
    public ApiResponse<Boolean> returnOrder(@PathVariable Long orderId) {
        orderService.returnOrder(orderId);

        return ApiResponse.success();
    }

}