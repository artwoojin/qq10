package com.project.qq10.domain.service;

import com.project.qq10.domain.dto.ProductRequestDto;
import com.project.qq10.domain.dto.ProductResponseDto;
import com.project.qq10.domain.dto.ProductSearchDto;
import com.project.qq10.domain.entity.Product;
import com.project.qq10.domain.exception.ErrorMessage;
import com.project.qq10.domain.exception.NotFoundException;
import com.project.qq10.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductResponseDto> getProductList(ProductSearchDto search, int size, Long cursorId) {
        Slice<Product> products = productRepository.findBySearchOption(cursorId, search, PageRequest.ofSize(size));
        List<Product> productList = products.stream().toList();

        return productList.stream().map(ProductResponseDto::toListResponseDto).toList();
    }

    public ProductResponseDto getProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new NotFoundException(ErrorMessage.PRODUCT_NOT_FOUND));

        return ProductResponseDto.of(product);
    }

    public ProductResponseDto createProduct(ProductRequestDto requestDto) {
        Product product = Product.builder()
                .name(requestDto.name())
                .description(requestDto.description())
                .price(requestDto.price())
                .quantity(requestDto.quantity())
                .build();

        productRepository.save(product);

        return ProductResponseDto.of(product);
    }
}
