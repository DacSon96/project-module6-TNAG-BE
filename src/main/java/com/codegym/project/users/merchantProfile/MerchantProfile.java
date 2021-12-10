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

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)

    private String address;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Category> categories;

    @Column(nullable = false)
    private String hotline;

    @Column(nullable = false)
    private String openHours;

    @Column(nullable = false)
    private String description;

    @Column(columnDefinition = "text")
    private String avatar;

    @Column(columnDefinition = "text")
    private String cover;

    @Column(columnDefinition = "text")
    private String thumbnail;
}
