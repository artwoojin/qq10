package com.project.qq10.domain.repository;

import com.project.qq10.domain.dto.ProductSearchDto;
import com.project.qq10.domain.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ProductRepositoryCustom {
    Slice<Product> findBySearchOption(Long cursorId, ProductSearchDto search, Pageable pageable);
}
