package com.codegym.project.food;

import com.codegym.project.users.users.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishForm {
    private Long id;

    @Size(min = 4, max = 200)
    private String name;

    @NotEmpty
    private MultipartFile image;

    @Size(max = 255)
    private String description;

    private User merchant;

    private Boolean status;
}
