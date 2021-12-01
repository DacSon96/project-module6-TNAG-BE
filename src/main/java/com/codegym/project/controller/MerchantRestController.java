package com.codegym.project.controller;

import com.codegym.project.role.IRoleService;
import com.codegym.project.role.Role;
import com.codegym.project.users.users.User;
import com.codegym.project.users.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/merchantslist")
public class MerchantRestController {
    @Autowired
    private UserService userService;

    @Autowired
    private IRoleService roleService;

    @GetMapping
    public ResponseEntity<Page<User>> findAllByRoleMerchants(@PageableDefault(sort = "username", size = 5) Pageable pageable) {
        Role role = roleService.findByName("ROLE_MERCHANT");
        Page<User> userPage;
        userPage = userService.findAllByRolesContaining(role, pageable);
        return new ResponseEntity<>(userPage, HttpStatus.OK);
    }
}
