package com.codegym.project.orders.order;

import com.codegym.project.cart.cart.Cart;
import com.codegym.project.cart.cartDetail.CartDetail;
import com.codegym.project.orders.orderDetail.OrdersDetail;
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
        }
        return ordersDetails;
    }
}
