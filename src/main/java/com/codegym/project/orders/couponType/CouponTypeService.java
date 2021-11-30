package com.codegym.project.orders.couponType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CouponTypeService implements ICouponTypeService {
    @Autowired
    private ICouponTypeRepository couponTypeRepository;

    @Override
    public Page<CouponType> findAll(Pageable pageable) {
        return couponTypeRepository.findAll(pageable);
    }

    @Override
    public Optional<CouponType> findById(Long id) {
        return couponTypeRepository.findById(id);
    }

    @Override
    public CouponType save(CouponType couponType) {
        return couponTypeRepository.save(couponType);
    }

    @Override
    public void deleteById(Long id) {
        couponTypeRepository.deleteById(id);
    }
}
