package com.codegym.project.orders.order;

import com.codegym.project.dish.Dish;
import com.codegym.project.orders.coupon.Coupon;
import com.codegym.project.orders.payment.PaymentMethod;
import com.codegym.project.users.userAddress.UserDeliverAddress;
import com.codegym.project.users.users.User;
import lombok.Data;

@Data
public class OrdersForm {
    private Long id;
    private UserDeliverAddress address;
    private Coupon coupon;
    private User shipper;
    private PaymentMethod paymentMethod;

    public OrdersForm() {
    }
}
