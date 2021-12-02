package com.codegym.project.users.userForm;

import com.codegym.project.users.merchantProfile.MerchantProfile;
import com.codegym.project.users.userStatus.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserForm {

    private Long id;
    private String name;
    private String email;
    private String password;
    private String fullName;
    private String phone;
    private String address;
    private String sex;



}