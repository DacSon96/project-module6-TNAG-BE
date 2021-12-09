package com.codegym.project.orders.order;

import com.codegym.project.cart.cart.Cart;
import com.codegym.project.cart.cart.ICartService;
import com.codegym.project.cart.cartDetail.CartDetail;
import com.codegym.project.cart.cartDetail.ICartDetailService;
import com.codegym.project.helper.Timer;
import com.codegym.project.orders.coupon.Coupon;
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
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class OrdersService implements IOrdersService{
    @Autowired
    private IOrdersRepository ordersRepository;

    @Autowired
    private ICartDetailService cartDetailService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IOrderDetailService orderDetailService;

    @Autowired
    private ICartService cartService;

    @Autowired
    private IOrderStatusService orderStatusService;

    @Override
    public Page<Orders> findAll(Pageable pageable) {
        return ordersRepository.findAll(pageable);
    }

    @Override
    public Optional<Orders> findById(Long id) {
        return ordersRepository.findById(id);
    }

    @Override
    public Orders save(Orders orders) {
        return ordersRepository.save(orders);
    }

    @Override
    public void deleteById(Long id) {
        ordersRepository.deleteById(id);
    }

    @Override
    public Set<OrdersDetail> convertCartDetailToOrderDetail(Cart cart) {
        Set<CartDetail> cartDetails = cart.getCartDetails();
        Set<OrdersDetail> ordersDetails = new HashSet<>();
        for (CartDetail c: cartDetails) {
            OrdersDetail ordersDetail = new OrdersDetail(
                    c.getDish(),
                    c.getPrice(),
                    c.getQuantity()
            );
            ordersDetails.add(ordersDetail);
            cartDetailService.deleteById(c.getId());
        }
        return ordersDetails;
    }

    @Override
    public Page<Orders> findAllByMerchantOrderByOrderTime(User user, Pageable pageable) {
        return ordersRepository.findAllByMerchantOrderByOrderTimeDesc(user,pageable);
    }

    @Override
    public Page<Orders> findAllByUserOrderByOrderTimeDesc(User user, Pageable pageable) {
        return ordersRepository.findAllByUserOrderByOrderTimeDesc(user, pageable);
    }

    @Override
    public Page<Orders> findAllByMerchantAndOrderStatusNameOrderByOrderTime(User user, String name, Pageable pageable) {
        return ordersRepository.findAllByMerchantAndOrderStatusNameOrderByOrderTimeDesc(user,name,pageable);
    }

    @Override
    public Orders saveNewOrder(OrdersForm ordersForm, Long merchantId, Authentication authentication) {
        UserDeliverAddress userDeliverAddress = ordersForm.getAddress();
        PaymentMethod paymentMethod = ordersForm.getPaymentMethod();
        User user = userService.getUserFromAuthentication(authentication);
        Role merchantRole = roleService.findByName(RoleConst.MERCHANT);
        User merchant = userService.findByRolesContainingAndId(merchantRole, merchantId);
        Coupon coupon = ordersForm.getCoupon();
        String note = ordersForm.getNote();
        OrderStatus orderStatus = orderStatusService.findByName(OrderStatusConst.CREATED);
        LocalDateTime orderTime = Timer.getCurrentTime();
        double totalPayment = 0;
        double discount = 0;
        Cart cart = cartService.findByMerchantAndUser(merchant, user);
        Set<OrdersDetail> ordersDetailSet = convertCartDetailToOrderDetail(cart);
        for (OrdersDetail o: ordersDetailSet) {
            orderDetailService.save(o);
            totalPayment += o.getPrice() * o.getQuantity();
        }
        cartService.deleteById(cart.getId());
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
        orders = save(orders);
        return orders;
    }

    @Override
    public Page<Orders> findOrdersByMerchant(User merchant, Pageable pageable) {
        return ordersRepository.findOrdersByMerchant(merchant,pageable);
    }

    @Override
    public Orders findOrdersByAddress_CustomerName(String customerName) {
        return ordersRepository.findOrdersByAddress_CustomerName(customerName);
    }

    @Override
    public Page<Orders> findAllByOrderStatusOrderByOrderTimeDesc(OrderStatus orderStatus, Pageable pageable) {
        return ordersRepository.findAllByOrderStatusOrderByOrderTimeDesc(orderStatus,pageable);
    }

    @Override
    public Page<Orders> findAllByShipperOrderByOrderTimeDesc(User shipper, Pageable pageable) {
        return ordersRepository.findAllByShipperOrderByOrderTimeDesc(shipper,pageable);
    }


//    @Override
//    public Page<Orders> findOrdersByMerchantAndId(Long id, User merchant) {
//        return ordersRepository.findOrdersByMerchantAndId(id,merchant);
//    }

//    @Override
//    public Page<Orders> findOrdersByIdPhoneName(Long id, String fullName, String phone, Pageable pageable) {
//        return ordersRepository.findOrdersByIdPhoneName(id,fullName,phone,pageable);
//    }

//    @Override
//    public Page<orderDto> findByOrderFull(Long id, String name, String phone,Pageable pageable) {
//        return ordersRepository.findByOrderFull(id,name,phone,pageable);
//    }


}
