package com.codegym.project.review;

import com.codegym.project.users.users.User;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Data
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private String content;

    @Column(columnDefinition = "1")
    @Size(min = 1, max = 5)
    private int rate;
}
