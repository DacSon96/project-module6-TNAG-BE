package com.codegym.project.users.merchantProfile;

import com.codegym.project.category.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MerchantProfileForm {

    private Long id;

    @Column(nullable = false)
    @Size(min = 10, max = 100)
    private String name;

    @Column(nullable = false)
    @Size(min = 10, max = 100)
    private String address;

    private Set<Category> categories;

    @Column(nullable = false)
    @Size(min = 10, max = 11)
    private String hotline;

    @Column(nullable = false)
    private String openHours;

    @Column(nullable = false)
    @Size(min = 50)
    private String description;

    //    @Column(nullable = false)
    @Column(columnDefinition = "text")
    private MultipartFile avatar;

    //    @Column(nullable = false)
    @Column(columnDefinition = "text")
    private MultipartFile cover;

    //    @Column(nullable = false)
    @Column(columnDefinition = "text")
    private MultipartFile thumbnail;
}
