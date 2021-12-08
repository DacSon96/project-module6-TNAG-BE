package com.codegym.project.controller.socket;

import com.codegym.project.orders.order.IOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class OrderSocketController {
    @Autowired
    private IOrdersService ordersService;
}
