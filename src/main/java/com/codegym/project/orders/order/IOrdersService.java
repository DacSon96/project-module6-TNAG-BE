package com.codegym.project.orders.order;

import com.codegym.project.IGeneralService;
import com.codegym.project.cart.cart.Cart;
import com.codegym.project.orders.orderDetail.OrdersDetail;
import com.codegym.project.users.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface IOrdersService extends IGeneralService<Orders> {
    Set<OrdersDetail> convertCartDetailToOrderDetail(Cart cart);

    Page<Orders> findAllByUserOrderByOrderTimeDesc(User user, Pageable pageable);

}
