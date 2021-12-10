package com.codegym.project.controller;

import com.codegym.project.orders.order.IOrdersService;
import com.codegym.project.orders.order.Orders;
import com.codegym.project.orders.orderStatus.IOrderStatusService;
import com.codegym.project.orders.orderStatus.OrderStatus;
import com.codegym.project.orders.orderStatus.OrderStatusConst;
import com.codegym.project.role.Role;
import com.codegym.project.role.RoleConst;
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

import java.util.Optional;


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
    public ResponseEntity<Page<OrderStatus>> getAll(@PageableDefault(sort = "id", size = 10) Pageable pageable) {
        Page<OrderStatus> orderStatusPage = orderStatusService.findAll(pageable);
        return new ResponseEntity<>(orderStatusPage, HttpStatus.OK);
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<Orders> cancelStatus(@PathVariable Long id) {
        Optional<Orders> orders = ordersService.findById(id);
        if (!orders.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        OrderStatus orderStatus = orderStatusService.findByName(OrderStatusConst.CANCELED);
        orders.get().setOrderStatus(orderStatus);
        orders.get().setId(id);
        return new ResponseEntity<>(ordersService.save(orders.get()), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> cancellationOrder(Authentication authentication, @RequestBody Orders order) {
        User user = userService.getUserFromAuthentication(authentication);
        if ((user.getId().equals(order.getMerchant().getId()))||((user.getId().equals(order.getUser().getId())))) {
            OrderStatus orderStatus = orderStatusService.findByName(OrderStatusConst.CANCELED);
            order.setOrderStatus(orderStatus);
            ordersService.save(order);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/confirmShipping/{id}")
    public ResponseEntity<Orders> confirmShippingOrder(Authentication authentication,
                                                       @RequestBody OrderStatus orderStatus,
                                                       @PathVariable Long id) {
        User shipper = userService.getUserFromAuthentication(authentication);
        Boolean checkShipper = false;
        for (Role role : shipper.getRoles()) {
            if (role.getName().equals(RoleConst.SHIPPER)) {
                checkShipper = true;
            }
        }
        if (!checkShipper) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else {
            Optional<Orders> ordersOptional = ordersService.findById(id);
            if (!ordersOptional.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            Orders order = ordersOptional.get();
            order.setOrderStatus(orderStatus);
            order.setShipper(shipper);
            ordersService.save(order);
            return new ResponseEntity<>(order, HttpStatus.OK);
        }
    }

}
