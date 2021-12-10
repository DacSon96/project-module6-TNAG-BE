package com.codegym.project.users.merchantProfile;

import com.codegym.project.category.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Size;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MerchantProfileForm {

    private Long id;

    @Size(min = 10, max = 100)
    private String name;

    @Size(min = 10, max = 100)
    private String address;

    private Set<Category> categories;

    @Size(min = 10, max = 11)
    private String hotline;

    private String openHours;

    @Size(min = 10)
    private String description;

    //    @Column(nullable = false)
    private MultipartFile avatar;

    //    @Column(nullable = false)
    private MultipartFile cover;

    //    @Column(nullable = false)
    private MultipartFile thumbnail;
}
