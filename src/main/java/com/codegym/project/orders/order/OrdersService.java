package com.codegym.project.orders.order;

import com.codegym.project.cart.cart.Cart;
import com.codegym.project.cart.cartDetail.CartDetail;
import com.codegym.project.cart.cartDetail.ICartDetailService;
import com.codegym.project.orders.orderDetail.OrdersDetail;
import com.codegym.project.users.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class OrdersService implements IOrdersService{
    @Autowired
    private IOrdersRepository ordersRepository;

    @Autowired
    private ICartDetailService cartDetailService;

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
    public Page<Orders> findAllByUserOrderByOrderTimeDesc(User user, Pageable pageable) {
        return ordersRepository.findAllByUserOrderByOrderTimeDesc(user, pageable);
    }

    @Override
    public Page<Orders> findOrdersByMerchant(User merchant, Pageable pageable) {
        return ordersRepository.findOrdersByMerchant(merchant,pageable);
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
