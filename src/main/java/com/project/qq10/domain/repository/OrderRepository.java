package com.project.qq10.domain.repository;

import com.project.qq10.domain.entity.Order;
import com.project.qq10.domain.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUserId(long userId);

    List<Order> findAllByStatusAndModifiedAtBefore(OrderStatus status, LocalDateTime dateTime);
}
