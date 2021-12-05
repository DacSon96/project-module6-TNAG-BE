package com.codegym.project.cart.cart;

import com.codegym.project.cart.cartDetail.CartDetail;
import com.codegym.project.dish.Dish;
import com.codegym.project.users.users.User;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User merchant;

    @ManyToOne
    private User user;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<CartDetail> cartDetails;

    public Cart() {
    }

    public Cart(User merchant, User user, Set<CartDetail> cartDetails) {
        this.merchant = merchant;
        this.user = user;
        this.cartDetails = cartDetails;
    }
}


