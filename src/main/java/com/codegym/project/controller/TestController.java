package com.codegym.project.controller;

import com.codegym.project.orders.order.OrderFindBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    private OrderFindBy orderFindBy;

    @GetMapping("/test")
    public ResponseEntity<?> test(){
        orderFindBy.findOrderDto("3", 2l) ;
        return new ResponseEntity<String>(HttpStatus.OK);
    }
}
