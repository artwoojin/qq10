package com.project.qq10.domain.controller;

import com.project.qq10.domain.dto.ProductRequestDto;
import com.project.qq10.domain.dto.ProductResponseDto;
import com.project.qq10.domain.dto.ProductSearchDto;
import com.project.qq10.domain.service.ProductService;
import com.project.qq10.global.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "product", description = "상품")
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "상품 리스트 조회")
    @GetMapping("/product")
    public ApiResponse<List<ProductResponseDto>> getProductList(ProductSearchDto search, @RequestParam int size, Long cursorId) {
        List<ProductResponseDto> productResponseDtoList = productService.getProductList(search, size, cursorId);

        return ApiResponse.success(productResponseDtoList);
    }

    @Operation(summary = "상품 상세 조회")
    @GetMapping("/product/{productId}")
    public ApiResponse<ProductResponseDto> getProduct(@PathVariable Long productId) {
        ProductResponseDto productResponseDto = productService.getProduct(productId);

        return ApiResponse.success(productResponseDto);
    }

    @Operation(summary = "상품 등록")
    @PostMapping("/product")
    public ApiResponse<ProductResponseDto> createProduct(@RequestBody ProductRequestDto requestDto) {
        ProductResponseDto productResponseDto = productService.createProduct(requestDto);

        return ApiResponse.success(productResponseDto);
    }
}
