package com.codegym.project.orders.orderStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderStatusService implements IOrderStatusService{
    @Autowired
    private IOrderStatusRepository orderStatusRepository;

    @Override
    public Page<OrderStatus> findAll(Pageable pageable) {
        return orderStatusRepository.findAll(pageable);
    }

    @Override
    public Optional<OrderStatus> findById(Long id) {
        return orderStatusRepository.findById(id);
    }

    @Override
    public OrderStatus save(OrderStatus orderStatus) {
        return orderStatusRepository.save(orderStatus);
    }

    @Override
    public void deleteById(Long id) {
        orderStatusRepository.deleteById(id);
    }
}
