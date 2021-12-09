package com.codegym.project.orders.order;

import com.codegym.project.IGeneralService;
import com.codegym.project.cart.cart.Cart;
import com.codegym.project.orders.orderDetail.OrdersDetail;
import com.codegym.project.orders.orderStatus.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import com.codegym.project.users.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.util.Set;

public interface IOrdersService extends IGeneralService<Orders> {
    Set<OrdersDetail> convertCartDetailToOrderDetail(Cart cart);

    Page<Orders> findAllByMerchantOrderByOrderTime(User user, Pageable pageable);

    Page<Orders> findAllByUserOrderByOrderTimeDesc(User user, Pageable pageable);

    Page<Orders> findAllByMerchantAndOrderStatusNameOrderByOrderTime(User user, String name, Pageable pageable);
    Orders saveNewOrder(OrdersForm ordersForm, Long merchantId, Authentication authentication);

//    Page<orderDto> findByOrderFull(Long id, String name, String phone, Pageable pageable);
//Page<Orders> findOrdersByIdPhoneName(Long id, String fullName, String phone, Pageable pageable);

    Page<Orders> findOrdersByMerchant(User merchant,Pageable pageable);


//    Page<Orders> findOrdersByMerchantAndId(Long id, User merchant);

    Orders findOrdersByAddress_CustomerName(String customerName);

    Page<Orders> findAllByOrderStatusOrderByOrderTimeDesc(OrderStatus orderStatus,Pageable pageable);

    Page<Orders> findAllByShipperOrderByOrderTimeDesc(User shipper,Pageable pageable);

}
