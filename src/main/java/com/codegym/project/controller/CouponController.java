package com.codegym.project.controller;

import com.codegym.project.orders.coupon.Coupon;
import com.codegym.project.orders.coupon.ICouponService;
import com.codegym.project.users.merchantProfile.IMerchantProfileService;
import com.codegym.project.users.merchantProfile.MerchantProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/coupons")
@CrossOrigin("*")
public class CouponController {
    @Autowired
    private IMerchantProfileService merchantProfileService;
    @Autowired
    private ICouponService couponService;

    @GetMapping("/merchants/{id}")
    public ResponseEntity<Iterable<Coupon>> findByMerchant(@PathVariable Long id) {
        Optional<MerchantProfile> merchantProfile = merchantProfileService.findById(id);
        if (!merchantProfile.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Iterable<Coupon> coupons = couponService.findAllByMerchantProfile(merchantProfile.get());
        return new ResponseEntity<>(coupons, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Coupon> findById(@PathVariable Long id) {
        Optional<Coupon> couponOptional = couponService.findById(id);
        if (!couponOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(couponOptional.get(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Coupon> findByCode(@RequestParam(name = "code") Optional<String> code) {
        Coupon coupon = couponService.findByInputCode(code.get());
        if (coupon == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(coupon, HttpStatus.OK);
    }
}
