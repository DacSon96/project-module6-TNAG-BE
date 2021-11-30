package com.codegym.project.cart;

import com.codegym.project.users.users.User;
import lombok.Data;

import javax.persistence.*;

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
}


