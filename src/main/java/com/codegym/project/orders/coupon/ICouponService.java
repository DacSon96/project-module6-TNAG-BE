package com.codegym.project.orders.coupon;

import com.codegym.project.IGeneralService;
import com.codegym.project.users.merchantProfile.MerchantProfile;

import java.util.List;

public interface ICouponService extends IGeneralService<Coupon> {
    List<Coupon> findAll();

    Iterable<Coupon> findAllByMerchantProfile(MerchantProfile merchantProfile);

    Coupon findByInputCode(String inputCode);

}
