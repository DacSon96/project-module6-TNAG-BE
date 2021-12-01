package com.codegym.project.dish;

import lombok.Data;
import com.codegym.project.users.users.User;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Data
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

    @ManyToOne
    private User merchant;

    private Boolean status;
}
