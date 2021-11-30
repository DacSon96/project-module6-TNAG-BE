package com.codegym.project.cart.cartDetail;

import com.codegym.project.cart.cart.Cart;
import com.codegym.project.food.Food;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class CartDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Food food;

    @ManyToOne
    private Cart cart;

    private int quantity;

    private double price;

}
