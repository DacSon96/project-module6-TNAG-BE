package com.codegym.project.dish;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.codegym.project.users.users.User;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Size(min = 4, max = 200)
    private String name;

    @Column(nullable = false)
    @NotEmpty
    private String image;

    @Size(max = 255)
    private String description;

    @Column(nullable = false)
    private double price;

    @ManyToOne
    private User merchant;

    private Boolean status;

    public Dish(String name, String image, String description, double price, User merchant, Boolean status) {
        this.name = name;
        this.image = image;
        this.description = description;
        this.price = price;
        this.merchant = merchant;
        this.status = status;
    }

    public Dish(Long id, String name, String image, String description, double price, User merchant, Boolean status) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.description = description;
        this.price = price;
        this.merchant = merchant;
        this.status = status;
    }
}
