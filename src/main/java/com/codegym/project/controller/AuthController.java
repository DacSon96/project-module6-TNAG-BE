package com.codegym.project.controller;


import com.codegym.project.role.Role;
import com.codegym.project.users.merchantProfile.MerchantProfile;
import com.codegym.project.users.merchantProfile.service.IMerchantProfileService;
import com.codegym.project.users.userAddress.IUserAddressService;
import com.codegym.project.users.userAddress.UserDeliverAddress;
import com.codegym.project.users.userForm.UserForm;
import com.codegym.project.users.userProfile.IUserProfileService;
import com.codegym.project.users.userProfile.UserProfile;
import com.codegym.project.users.userStatus.IUserStatusService;
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
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/userForms")
public class AuthController {
    @Autowired
    private IUserStatusService userStatusService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IMerchantProfileService merchantProfileService;
    @Autowired
    private IUserProfileService userProfileService;
    @Autowired
    private IUserAddressService userAddressService;

    @Value("${file-upload}")
    private String fileUpload;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserForm userForm) throws IOException {
        MultipartFile multipartFile = userForm.getAvatar();
        String fileName = multipartFile.getOriginalFilename();
        FileCopyUtils.copy(userForm.getAvatar().getBytes(), new File(fileUpload,fileName));

        UserProfile userProfile = new UserProfile(
                userForm.getFullName(),
                userForm.getPhone(),
                fileName,
                userForm.getSex()
        );
        userProfileService.save(userProfile);
        MerchantProfile merchantProfile =null;
        List<Role> roles=null;
        User user = new User(
                userForm.getName(),
                userForm.getPassword(),
                userForm.getEmail(),
                roles,
                userProfile,
                merchantProfile,
        );


        UserDeliverAddress userDeliverAddress = new UserDeliverAddress(
                userForm.getName(),
                userForm.getPhone(),
                user
        );


        return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
    }

}
