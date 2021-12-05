package com.codegym.project.orders.order;

import com.codegym.project.IGeneralService;
import com.codegym.project.cart.cart.Cart;
import com.codegym.project.orders.orderDetail.OrdersDetail;

import java.util.Set;

public interface IOrdersService extends IGeneralService<Orders> {
    Set<OrdersDetail> convertCartDetailToOrderDetail(Cart cart);
}
