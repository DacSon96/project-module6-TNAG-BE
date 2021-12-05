package com.codegym.project.controller;

import com.codegym.project.users.userAddress.IUserAddressService;
import com.codegym.project.users.userAddress.UserDeliverAddress;
import com.codegym.project.users.userProfile.AvatarUploadForm;
import com.codegym.project.users.userProfile.IUserProfileService;
import com.codegym.project.users.userProfile.UserProfile;
import com.codegym.project.users.users.IUserService;
import com.codegym.project.users.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@CrossOrigin("*")
public class UserController {
    @Autowired
    private IUserAddressService userAddressService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IUserProfileService userProfileService;

    @Value("${file-upload}")
    private String fileUpload;

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/address")
    public ResponseEntity<Iterable<UserDeliverAddress>> getUserAddresses(Authentication authentication) {
        User user = userService.getUserFromAuthentication(authentication);
        Iterable<UserDeliverAddress> userDeliverAddresses = userAddressService.findAllByUser(user);
        return new ResponseEntity<>(userDeliverAddresses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findUserById(@PathVariable Long id) {
        Optional<User> optionalUser = userService.findById(id);
        if (!optionalUser.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(optionalUser.get(), HttpStatus.OK);
        }
    }

    @Secured("ROLE_USER")
    @PutMapping("/profile")
    public ResponseEntity<UserProfile> updateProfile(@RequestBody UserProfile userProfile,
                                                     Authentication authentication){
        User user = userService.getUserFromAuthentication(authentication);
        userProfile.setAvatar(user.getUserProfile().getAvatar());
        userProfile.setId(user.getUserProfile().getId());
        userProfileService.save(userProfile);
        return new ResponseEntity<>(userProfile, HttpStatus.OK);
    }

    @PutMapping("/profile/avatar")
    public ResponseEntity<UserProfile> updateAvatar(AvatarUploadForm form, Authentication authentication) throws IOException {
        User user = userService.getUserFromAuthentication(authentication);
        MultipartFile avatar = form.getAvatar();
        String fileName = avatar.getOriginalFilename();
        FileCopyUtils.copy(avatar.getBytes(), new File(fileUpload, fileName));
        UserProfile userProfile = user.getUserProfile();
        userProfile.setAvatar(fileName);
        userProfileService.save(userProfile);
        return new ResponseEntity<>(userProfile, HttpStatus.OK);

    }

}
