package com.codegym.project.orders.coupon;

import com.codegym.project.users.merchantProfile.MerchantProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICouponRepository extends JpaRepository<Coupon, Long> {
    List<Coupon> findAll();

    Iterable<Coupon> findAllByMerchantProfile(MerchantProfile merchantProfile);

    Coupon findByInputCode(String inputCode);

}
