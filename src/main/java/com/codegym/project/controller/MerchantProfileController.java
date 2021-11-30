package com.codegym.project.controller;

import com.codegym.project.role.IRoleService;
import com.codegym.project.role.Role;
import com.codegym.project.users.merchantProfile.MerchantProfile;
import com.codegym.project.users.merchantProfile.IMerchantProfileService;
import com.codegym.project.users.users.IUserService;
import com.codegym.project.users.users.User;
import com.codegym.project.users.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/merchant")
@CrossOrigin("*")
public class MerchantProfileController {
    @Autowired
    private IMerchantProfileService merchantProfileService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @GetMapping("/list")
    public ResponseEntity<Iterable<User>> getAllMerchant(){
        Optional<Role> merchantRole = roleService.findById(Long.valueOf(1));
        Iterable<User> userIterable = userService.findAllByRoles(merchantRole.get());
        return new ResponseEntity<>(userIterable, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<MerchantProfile>> getMerchantById(@PathVariable Long id){
        Optional<MerchantProfile> merchantProfileOptional = merchantProfileService.findById(id);
        return new ResponseEntity<>(merchantProfileOptional, HttpStatus.OK);
    }
}
