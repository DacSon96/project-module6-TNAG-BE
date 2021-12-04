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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
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

    @Secured({"ROLE_USER"})
    @PostMapping("/{merchantId}")
    public ResponseEntity<Orders> newOrder(@RequestBody OrdersForm ordersForm,
                                           @PathVariable("merchantId") Long merchantId,
                                           Authentication authentication){
        UserDeliverAddress userDeliverAddress = ordersForm.getAddress();
        PaymentMethod paymentMethod = ordersForm.getPaymentMethod();
        if (userDeliverAddress == null || paymentMethod == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User user = userService.getUserFromAuthentication(authentication);
        Role merchantRole = roleService.findByName(RoleConst.MERCHANT);
        User merchant = userService.findByRolesContainingAndId(merchantRole, merchantId);
        Coupon coupon = ordersForm.getCoupon();
        String note = ordersForm.getNote();
        OrderStatus orderStatus = orderStatusService.findByName(OrderStatusConst.CREATED);
        LocalDateTime orderTime = Timer.getCurrentTime();
        double totalPayment = 0;
        double discount = 0;
        if (merchant == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Cart cart = cartService.findByMerchantAndUser(merchant, user);
        Set<OrdersDetail> ordersDetailSet = ordersService.convertCartDetailToOrderDetail(cart);
        for (OrdersDetail o: ordersDetailSet) {
            orderDetailService.save(o);
            totalPayment += o.getPrice() * o.getQuantity();
        }
        if (coupon != null) {
            discount = coupon.getDiscount();
        }

        Orders orders = new Orders(
                user,
                orderTime,
                userDeliverAddress,
                totalPayment - discount,
                note,
                orderStatus,
                coupon,
                paymentMethod,
                ordersDetailSet
                );
        ordersService.save(orders);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
