package com.codegym.project.orders.coupon;

import com.codegym.project.users.merchantProfile.MerchantProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CouponService implements ICouponService {
    @Autowired
    private ICouponRepository couponRepository;

    @Override
    public Page<Coupon> findAll(Pageable pageable) {
        return couponRepository.findAll(pageable);
    }

    @Override
    public Optional<Coupon> findById(Long id) {
        return couponRepository.findById(id);
    }

    @Override
    public Coupon save(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    @Override
    public void deleteById(Long id) {
        couponRepository.deleteById(id);
    }

    @Override
    public List<Coupon> findAll() {
        return couponRepository.findAll();
    }

    @Override
    public Iterable<Coupon> findAllByMerchantProfile(MerchantProfile merchantProfile) {
        return couponRepository.findAllByMerchantProfile(merchantProfile);
    }

    @Override
    public Coupon findByInputCode(String inputCode) {
        return couponRepository.findByInputCode(inputCode);
    }
}
