package com.codegym.project.users.merchantProfile;

import com.codegym.project.category.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MerchantProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Category> categories;

    private String hotline;

    private String openHours;

    private String description;

    private String avatar;

    private String cover;

    private String thumbnail;
}
