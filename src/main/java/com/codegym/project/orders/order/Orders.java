package com.codegym.project.orders.order;

import com.codegym.project.orders.coupon.Coupon;
import com.codegym.project.orders.orderDetail.OrdersDetail;
import com.codegym.project.orders.orderStatus.OrderStatus;
import com.codegym.project.orders.payment.PaymentMethod;
import com.codegym.project.users.userAddress.UserDeliverAddress;
import com.codegym.project.users.users.User;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Data
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @Column(nullable = false)
    private Date orderTime;

    @ManyToOne
    private UserDeliverAddress address;

    @Column(nullable = false)
    private double totalPayment;

    @ManyToOne
    private OrderStatus orderStatus;

    @ManyToOne
    private Coupon coupon;

    @ManyToOne
    private User shipper;

    @ManyToOne
    private PaymentMethod paymentMethod;

    @ManyToMany
    private Set<OrdersDetail> ordersDetails;

}
