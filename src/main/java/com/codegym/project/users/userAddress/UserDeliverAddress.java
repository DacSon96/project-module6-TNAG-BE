package com.codegym.project.users.userAddress;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.codegym.project.users.users.User;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDeliverAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotEmpty
    private String customerName;

    @Column(nullable = false)
    @NotEmpty
    private String phone;

    @ManyToOne
    private User user;

    public UserDeliverAddress(String customerName, String phone, User user) {
        this.customerName = customerName;
        this.phone = phone;
        this.user = user;
    }
}
