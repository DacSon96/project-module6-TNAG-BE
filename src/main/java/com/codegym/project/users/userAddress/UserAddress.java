package com.codegym.project.users.userAddress;

import lombok.Data;
import com.codegym.project.users.users.User;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Data
public class UserAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotEmpty
    private String name;

    @Column(nullable = false)
    @NotEmpty
    private String phone;

    @ManyToOne
    private User user;
}
