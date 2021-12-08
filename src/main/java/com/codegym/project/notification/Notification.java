package com.codegym.project.notification;

import com.codegym.project.users.users.User;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime time;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User receiver;

    private String content;

    private String routerLink;

    public Notification() {
    }

    public Notification(LocalDateTime time, User sender, User receiver, String content) {
        this.time = time;
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
    }

    public Notification(LocalDateTime time, User sender, User receiver, String content, String routerLink) {
        this.time = time;
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.routerLink = routerLink;
    }
}
