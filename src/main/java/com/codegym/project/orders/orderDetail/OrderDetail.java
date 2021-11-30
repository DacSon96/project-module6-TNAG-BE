package com.codegym.project.orders.orderDetail;

import com.codegym.project.food.Dish;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class
OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Dish dish;

    @Column(nullable = false)
    private double price;

    @Column(columnDefinition = "1")
    private int quantity;

    private double total;

    public OrderDetail() {
    }

    public OrderDetail(Long id, Dish dish, double price, int quantity) {
        this.id = id;
        this.dish = dish;
        this.price = price;
        this.quantity = quantity;
        this.total = price*quantity;
    }
}
