package com.codegym.project.users;

import com.codegym.project.role.Role;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;

@Data
public class UserDto {
    private Long id;

    @Size(min = 6, max = 18)
    private String username;

    @Size(min = 6, max = 12)
    private String password;

    private List<Role> roles;


    private String address;

    private String phone;
}
