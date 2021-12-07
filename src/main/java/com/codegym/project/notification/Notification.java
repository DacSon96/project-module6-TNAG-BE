package com.codegym.project.notification;

import com.codegym.project.users.users.User;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User receiver;

    private String content;

    private String routerLink;
}
