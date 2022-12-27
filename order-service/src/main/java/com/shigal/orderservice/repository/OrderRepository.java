package com.shigal.orderservice.repository;/*
 *
 * @author Lawshiga
 *
 */

import com.shigal.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
