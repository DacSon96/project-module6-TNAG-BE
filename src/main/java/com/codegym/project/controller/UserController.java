package com.codegym.project.controller;

import com.codegym.project.users.userAddress.IUserAddressService;
import com.codegym.project.users.userAddress.UserDeliverAddress;
import com.codegym.project.users.users.IUserService;
import com.codegym.project.users.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin("*")
public class UserController {
    @Autowired
    private IUserAddressService userAddressService;

    @Autowired
    private IUserService userService;

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/address")
    public ResponseEntity<Iterable<UserDeliverAddress>> getUserAddresses(Authentication authentication) {
        User user = userService.getUserFromAuthentication(authentication);
        Iterable<UserDeliverAddress> userDeliverAddresses = userAddressService.findAllByUser(user);
        return new ResponseEntity<>(userDeliverAddresses, HttpStatus.OK);
    }

}
