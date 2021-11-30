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

    private String address;

    @ManyToMany(fetch = FetchType.EAGER)
    @Column(nullable = false)
    private List<Category> categories;

    @Column(nullable = false)
    @Size(min = 10, max = 11)
    private String hotline;

    @Column(nullable = false)
    private String openHours;

}
