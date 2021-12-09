package com.codegym.project.controller;

import com.codegym.project.cart.cart.Cart;
import com.codegym.project.cart.cart.ICartService;
import com.codegym.project.helper.Timer;
import com.codegym.project.orders.coupon.Coupon;
import com.codegym.project.orders.order.*;
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
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@CrossOrigin("*")
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderFindBy orderFindBy;
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
    public ResponseEntity<Optional<Orders>> findById(@PathVariable Long id) {
        Optional<Orders> optionalOrders = ordersService.findById(id);
        if (!optionalOrders.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(optionalOrders, HttpStatus.OK );
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

//    @GetMapping("/search")
//    public ResponseEntity<Page<orderDto>> find(@RequestParam(name = "id",required = false)Long id,
//                                               @RequestParam(name = "name",required = false)String name,
//                                               @RequestParam(name = "phone",required = false)String phone,
//                                               Pageable pageable){
//        return ResponseEntity.ok(ordersService.findByOrderFull(id,name,phone,pageable));
//    }

//    @GetMapping("/search")
//    public ResponseEntity<Page<Orders>> find(@RequestParam(name = "id",required = false) Long id,
//                                             @RequestParam(name = "name", required= false) String name,
//                                             @RequestParam(name = "phone", required = false) String phone,
//                                             Pageable pageable){
//        return new ResponseEntity<>(ordersService.findOrdersByIdPhoneName(id, name, phone, pageable), HttpStatus.OK);
//    }

    @GetMapping("/merchant/{merchantId}")
    public ResponseEntity<?> findOrdersByMerchant( @PathVariable("merchantId") Long id,
                                                              @RequestParam(name = "q")Optional<String> q,
                                                                      Pageable pageable) {
//        if (!q.isPresent()) {
//            Optional<User> merchant = userService.findById(id);
//            Page<Orders> orders = ordersService.findOrdersByMerchant(merchant.get(), pageable);
//            return new ResponseEntity<>(orders, HttpStatus.OK);
//        } else {
            List<Orders> orders = orderFindBy.getOrders(q.get(), id);
            return new ResponseEntity<>(orders, HttpStatus.OK);
//        }
    }
    @GetMapping("/merchant")
    public  ResponseEntity<Page<Orders>> getOrderByMerchantId(
            Authentication authentication,
            @RequestParam(name = "q") Optional<String> q,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
            @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort) {
        Sort sortable = null;
        if (sort.equals("ASC")) {
            sortable = Sort.by("id").ascending();
        }
        if (sort.equals("DESC")) {
            sortable = Sort.by("id").descending();
        }
        Pageable pageable = PageRequest.of(page, size, sortable);
        User user = userService.getUserFromAuthentication(authentication);
        if (user == null || user.getMerchantProfile() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (!q.isPresent()) {
            Page<Orders> orders = ordersService.findAllByMerchantOrderByOrderTime(user,pageable);
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } else {
            Page<Orders> orders = ordersService.findAllByMerchantAndOrderStatusNameOrderByOrderTime(user,q.get(),pageable);
            return new ResponseEntity<>(orders, HttpStatus.OK);
        }

    }

}
