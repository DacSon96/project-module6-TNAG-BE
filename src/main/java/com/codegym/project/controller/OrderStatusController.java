package com.codegym.project.controller;

import com.codegym.project.orders.order.IOrdersService;
import com.codegym.project.orders.order.Orders;
import com.codegym.project.orders.orderStatus.IOrderStatusService;
import com.codegym.project.orders.orderStatus.OrderStatus;
import com.codegym.project.orders.orderStatus.OrderStatusConst;
import com.codegym.project.users.users.IUserService;
import com.codegym.project.users.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;



@CrossOrigin("*")
@RestController
@RequestMapping("/orderStatus")
public class OrderStatusController {
    @Autowired
    private IOrderStatusService orderStatusService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IOrdersService ordersService;

    @GetMapping
    public ResponseEntity<Page<OrderStatus>> getAll(@PageableDefault(sort = "id", size = 5) Pageable pageable) {
        Page<OrderStatus> orderStatusPage = orderStatusService.findAll(pageable);
        return new ResponseEntity<>(orderStatusPage, HttpStatus.OK);
    }
    @PutMapping
    public ResponseEntity<?> cancellationOrder(Authentication authentication,@RequestBody Orders order) {
        User user = userService.getUserFromAuthentication(authentication);
        if (!user.getId().equals(order.getMerchant().getId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        OrderStatus orderStatus = orderStatusService.findByName(OrderStatusConst.CANCELED);
        order.setOrderStatus(orderStatus);
        ordersService.save(order);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
