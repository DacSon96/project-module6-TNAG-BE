package com.codegym.project.controller;

import com.codegym.project.orders.coupon.Coupon;
import com.codegym.project.orders.coupon.ICouponService;
import com.codegym.project.users.merchantProfile.IMerchantProfileService;
import com.codegym.project.users.merchantProfile.MerchantProfile;
import com.codegym.project.users.users.IUserService;
import com.codegym.project.users.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    @Autowired
    private IUserService userService;

    @GetMapping("/merchants/{id}")
    public ResponseEntity<Iterable<Coupon>> findByMerchant(@PathVariable Long id) {
        Optional<MerchantProfile> merchantProfile = merchantProfileService.findById(id);
        if (!merchantProfile.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Iterable<Coupon> coupons = couponService.findAllByMerchantProfile(merchantProfile.get());
        return new ResponseEntity<>(coupons, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Coupon> create(@RequestBody Coupon coupon, Authentication authentication) {
        User user = userService.getUserFromAuthentication(authentication);
        if (user.getMerchantProfile() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        coupon.setMerchantProfile(user.getMerchantProfile());
        return new ResponseEntity<>(couponService.save(coupon), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Coupon> update(@PathVariable Long id, @RequestBody Coupon coupon, Authentication authentication) {
        User user = userService.getUserFromAuthentication(authentication);
        if (user.getMerchantProfile() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<Coupon> couponOptional = couponService.findById(id);
        if (!couponOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        coupon.setId(id);
        coupon.setMerchantProfile(user.getMerchantProfile());
        couponService.save(coupon);
        return new ResponseEntity<>(coupon, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Coupon> delete(@PathVariable Long id) {
        Optional<Coupon> couponOptional = couponService.findById(id);
        if (!couponOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        couponService.deleteById(id);
        return new ResponseEntity<>(couponOptional.get(), HttpStatus.OK);
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
