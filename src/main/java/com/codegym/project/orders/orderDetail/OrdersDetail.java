package com.codegym.project.orders.orderDetail;

import com.codegym.project.dish.Dish;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class OrdersDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Dish dish;

    @Column(nullable = false)
    private double price;

    private int quantity;

    private double total;

    public OrdersDetail() {
    }

    public OrdersDetail(Long id, Dish dish, double price, int quantity) {
        this.id = id;
        this.dish = dish;
        this.price = price;
        this.quantity = quantity;
        this.total = price * quantity;
    }

    public OrdersDetail(Dish dish, double price, int quantity) {
        this.dish = dish;
        this.price = price;
        this.quantity = quantity;
        this.total = quantity * price;
    }
}
