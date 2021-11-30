package com.codegym.project.orders.orderDetail;

import com.codegym.project.food.Food;
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
    private Food food;

    @Column(nullable = false)
    private double price;

    @Column(columnDefinition = "1")
    private int quantity;

    private double total;

    public OrderDetail() {
    }

    public OrderDetail(Long id, Food food, double price, int quantity) {
        this.id = id;
        this.food = food;
        this.price = price;
        this.quantity = quantity;
        this.total = price*quantity;
    }
}
