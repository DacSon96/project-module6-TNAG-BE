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
        UserDeliverAddress userDeliverAddress = ordersForm.getAddress();
        PaymentMethod paymentMethod = ordersForm.getPaymentMethod();
        if (userDeliverAddress.getId() == null || paymentMethod.getId() == null) {
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
                ordersDetailSet,
                merchant
                );
        ordersService.save(orders);
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
                                                                      Pageable pageable){
           if(!q.isPresent()) {
               Optional<User> merchant = userService.findById(id);
               Page<Orders> orders = ordersService.findOrdersByMerchant(merchant.get(), pageable);
               return new ResponseEntity<>(orders, HttpStatus.OK);
           }else {
              List<Orders> orders = orderFindBy.getOrders("q",id);
              return new ResponseEntity<>(orders,HttpStatus.OK);
           }

    }

}
