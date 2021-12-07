package com.codegym.project.orders.order;

import com.codegym.project.orders.coupon.Coupon;
import com.codegym.project.orders.payment.PaymentMethod;
import com.codegym.project.users.userAddress.UserDeliverAddress;
import lombok.Data;

@Data
public class OrdersForm {
    private Long id;
    private UserDeliverAddress address;
    private Coupon coupon;
    private PaymentMethod paymentMethod;
    private String note;
    public OrdersForm() {
    }
}
