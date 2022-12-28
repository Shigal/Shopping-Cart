package com.shigal.orderservice.service;/*
 *
 * @author Lawshiga
 *
 */

import com.shigal.orderservice.dto.OrderRequest;

public interface OrderService {
    public void placeOrder(OrderRequest orderRequest);
}
