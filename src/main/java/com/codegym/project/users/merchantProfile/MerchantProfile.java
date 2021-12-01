package com.codegym.project.users.merchantProfile;

import com.codegym.project.category.Category;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Data
public class MerchantProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String address;

    private String image;

    @ManyToMany
    private List<Category> categories;

    @Column(nullable = false)
    @Size(min = 10, max = 11)
    private String hotline;

    @Column(nullable = false)
    private String openHours;

}
