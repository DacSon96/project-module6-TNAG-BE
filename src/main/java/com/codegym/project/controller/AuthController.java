package com.codegym.project.controller;


import com.codegym.project.role.IRoleService;
import com.codegym.project.role.Role;
import com.codegym.project.users.userAddress.IUserAddressService;
import com.codegym.project.users.userAddress.UserDeliverAddress;
import com.codegym.project.users.userForm.UserForm;
import com.codegym.project.users.userProfile.IUserProfileService;
import com.codegym.project.users.userProfile.UserProfile;
import com.codegym.project.users.users.IUserService;
import com.codegym.project.users.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
public class AuthController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IUserProfileService userProfileService;
    @Autowired
    private IUserAddressService userAddressService;

    @Autowired
    private IRoleService roleService;

    @Value("${file-upload}")
    private String fileUpload;

    @PostMapping("/register")
    public ResponseEntity<User> createUser( UserForm userForm) throws IOException {
        MultipartFile multipartFile = userForm.getAvatar();
        String fileName = multipartFile.getOriginalFilename();
        FileCopyUtils.copy(userForm.getAvatar().getBytes(), new File(fileUpload + fileName));

        UserProfile userProfile = new UserProfile(
                userForm.getFullName(),
                userForm.getPhone(),
                fileName,
                userForm.getSex()
        );
        userProfile = userProfileService.save(userProfile);
        List<Role> roles = new ArrayList<>();
        Role role = roleService.findByName("ROLE_USER");
        roles.add(role);

        User user = new User(
                userForm.getName(),
                userForm.getPassword(),
                userForm.getEmail(),
                roles,
                userProfile
        );

        user = userService.save(user);

        UserDeliverAddress userDeliverAddress = new UserDeliverAddress(
                userForm.getName(),
                userForm.getPhone(),
                user
        );
        userAddressService.save(userDeliverAddress);


        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
