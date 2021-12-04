package com.codegym.project.cart.cartDetail;

import com.codegym.project.cart.cart.Cart;
import com.codegym.project.dish.Dish;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class CartDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Dish dish;

    private int quantity;

    private double price;

    public CartDetail() {
    }

    public CartDetail(Dish dish,  int quantity, double price) {
        this.dish = dish;
        this.quantity = quantity;
        this.price = price;
    }
}
