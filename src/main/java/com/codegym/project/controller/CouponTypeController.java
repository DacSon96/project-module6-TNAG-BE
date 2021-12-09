package com.codegym.project.controller;

import com.codegym.project.orders.couponType.CouponType;
import com.codegym.project.orders.couponType.ICouponTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/couponType")
public class CouponTypeController {
    @Autowired
    private ICouponTypeService couponTypeService;

    @GetMapping
    public ResponseEntity<Iterable<CouponType>> findAll() {
        Iterable<CouponType> couponTypes = couponTypeService.findAll();
        if (couponTypes == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(couponTypes,HttpStatus.OK);
    }
}
