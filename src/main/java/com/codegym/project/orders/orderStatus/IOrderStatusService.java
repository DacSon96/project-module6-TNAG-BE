package com.codegym.project.orders.orderStatus;

import com.codegym.project.IGeneralService;

public interface IOrderStatusService extends IGeneralService<OrderStatus> {
    OrderStatus findByName(String name);
}
