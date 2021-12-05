package com.codegym.project.controller;


import com.codegym.project.role.IRoleService;
import com.codegym.project.role.Role;
import com.codegym.project.users.userAddress.IUserAddressService;
import com.codegym.project.users.userAddress.UserDeliverAddress;
import com.codegym.project.users.userForm.UserForm;
import com.codegym.project.users.userProfile.IUserProfileService;
import com.codegym.project.users.userProfile.UserProfile;
import com.codegym.project.users.userStatus.IUserStatusService;
import com.codegym.project.users.userStatus.UserStatus;
import com.codegym.project.users.userStatus.UserStatusConst;
import com.codegym.project.users.users.IUserService;
import com.codegym.project.users.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.codegym.project.security.JwtService;
import com.codegym.project.security.model.JwtResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

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

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IUserStatusService userStatusService;

    @Value("${file-upload}")
    private String fileUpload;

    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody UserForm userForm) throws IOException {
        UserProfile userProfile = new UserProfile(
                userForm.getFullName(),
                userForm.getPhone(),
                userForm.getSex()
        );
        userProfile = userProfileService.save(userProfile);
        List<Role> roles = new ArrayList<>();
        Role role = roleService.findByName("ROLE_USER");
        UserStatus userStatus = userStatusService.findByName(UserStatusConst.approved);
        roles.add(role);
        User user = new User(
                userForm.getName(),
                userForm.getPassword(),
                userForm.getEmail(),
                roles,
                userProfile,
                userStatus
        );
        user = userService.save(user);

        UserDeliverAddress userDeliverAddress = new UserDeliverAddress(
                userForm.getName(),
                userForm.getPhone(),
                user,
                userForm.getAddress()
        );
        userAddressService.save(userDeliverAddress);


        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtService.generateTokenLogin(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User currentUser = userService.findByUsername(user.getUsername());
        return new ResponseEntity<>(new JwtResponse(jwt, currentUser.getId(), userDetails.getUsername(), currentUser.getUserProfile().getFullName(), userDetails.getAuthorities(), currentUser.getEmail(), currentUser.getUserProfile().getPhone(), currentUser.getUserProfile().getAvatar()), HttpStatus.OK);
    }

    @PostMapping("/username/check")
    public ResponseEntity<User> checkDuplicate(@RequestBody User user) {
        if (userService.isUserDuplicated(user.getUsername())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }
}