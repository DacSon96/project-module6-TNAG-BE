package com.codegym.project.orders.couponType;

import com.codegym.project.IGeneralService;

public interface ICouponTypeService extends IGeneralService<CouponType> {

    Iterable<CouponType> findAll();
}
