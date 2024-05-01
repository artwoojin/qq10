package com.project.qq10.domain.repository;

import com.project.qq10.domain.dto.ProductSearchDto;
import com.project.qq10.domain.entity.Product;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;

import static com.project.qq10.domain.entity.QProduct.product;

@RequiredArgsConstructor
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Slice<Product> findBySearchOption(Long cursorId, ProductSearchDto search, Pageable pageable) {
        List<Long> ids = jpaQueryFactory
                .select(product.productId)
                .from(product)
                .where(containProductName(search), eqCursorId(cursorId))
                .limit(pageable.getPageSize() + 1)
                .fetch();

        List<Product> products = jpaQueryFactory.selectFrom(product)
                .where(product.productId.in(ids))
                .fetch();

        boolean hasNext = false;
        if (products.size() > pageable.getPageSize()) {
            products.remove(pageable.getPageSize());

            hasNext = true;
        }

        return new SliceImpl<>(products, pageable, hasNext);
    }


    private BooleanExpression containProductName(ProductSearchDto search) {
        if (search == null || search.searchName().isEmpty()) {
            return null;
        }

        return product.name.containsIgnoreCase(search.searchName());
    }

    private BooleanExpression eqCursorId(Long cursorId) {
        if (cursorId == null) {
            return null;
        }

        return product.productId.gt(cursorId);
    }

}
