package com.codegym.project.orders.coupon;

import com.codegym.project.IGeneralService;

import java.util.List;

public interface ICouponService extends IGeneralService<Coupon> {
    List<Coupon> findAll();
}
