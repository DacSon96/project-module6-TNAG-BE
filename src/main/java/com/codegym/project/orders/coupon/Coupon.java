package com.codegym.project.orders.coupon;

import com.codegym.project.orders.couponType.CouponType;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double discount;

    private String inputCode;

    private double discountCondition;

    private String description;

    @ManyToOne
    private CouponType type;
}
