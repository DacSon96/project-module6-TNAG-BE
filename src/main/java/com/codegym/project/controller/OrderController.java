package com.codegym.project.controller;

import com.codegym.project.cart.cart.Cart;
import com.codegym.project.cart.cart.ICartService;
import com.codegym.project.helper.Timer;
import com.codegym.project.orders.coupon.Coupon;
import com.codegym.project.orders.order.IOrdersService;
import com.codegym.project.orders.order.Orders;
import com.codegym.project.orders.order.OrdersForm;
import com.codegym.project.orders.orderDetail.IOrderDetailService;
import com.codegym.project.orders.orderDetail.OrdersDetail;
import com.codegym.project.orders.orderStatus.IOrderStatusService;
import com.codegym.project.orders.orderStatus.OrderStatus;
import com.codegym.project.orders.orderStatus.OrderStatusConst;
import com.codegym.project.orders.payment.PaymentMethod;
import com.codegym.project.role.IRoleService;
import com.codegym.project.role.Role;
import com.codegym.project.role.RoleConst;
import com.codegym.project.users.userAddress.UserDeliverAddress;
import com.codegym.project.users.users.IUserService;
import com.codegym.project.users.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@RestController
@CrossOrigin("*")
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private IOrdersService ordersService;
    @Autowired
    private ICartService cartService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IOrderStatusService orderStatusService;
    @Autowired
    private IOrderDetailService orderDetailService;

    @GetMapping("/user")
    public ResponseEntity<Page<Orders>> getOrderByUser(Authentication authentication, @RequestParam(name = "sort", required = false) String sort, Pageable pageable) {
        User user = userService.getUserFromAuthentication(authentication);
        Page<Orders> orders = ordersService.findAllByUserOrderByOrderTimeDesc(user, pageable);
        return new ResponseEntity<>(orders, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Orders> findById(@PathVariable Long id) {
        Optional<Orders> optionalOrders = ordersService.findById(id);
        if (!optionalOrders.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(optionalOrders.get(), HttpStatus.OK );
        }
    }

    @Secured({"ROLE_USER"})
    @PostMapping("/{merchantId}")
    public ResponseEntity<Orders> newOrder(@RequestBody OrdersForm ordersForm,
                                           @PathVariable("merchantId") Long merchantId,
                                           Authentication authentication){
        if (ordersForm.getAddress().getId() == null || ordersForm.getPaymentMethod().getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Orders orders = ordersService.saveNewOrder(ordersForm, merchantId, authentication);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
