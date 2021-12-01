package com.codegym.project.controller;

import com.codegym.project.role.IRoleService;
import com.codegym.project.role.Role;
import com.codegym.project.users.userStatus.IUserStatusService;
import com.codegym.project.users.userStatus.UserStatus;
import com.codegym.project.users.users.IUserService;
import com.codegym.project.users.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/merchant")
@CrossOrigin("*")
public class MerchantProfileController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IUserStatusService userStatusService;

//    @Secured("ROLE_ADMIN")
    @GetMapping("/pending")
    public ResponseEntity<Page<User>> getAllMerchantPendingApproval(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
            @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort) {
        Sort sortable = null;
        if (sort.equals("ASC")) {
            sortable = Sort.by("id").ascending();
        }
        if (sort.equals("DESC")) {
            sortable = Sort.by("id").descending();
        }
        Pageable pageable = PageRequest.of(page, size, sortable);
        UserStatus status = userStatusService.findByName("pendingApproval");
        Role merchantRole = roleService.findByName("merchant");
        Page<User> merchantPending = userService.findAllByRolesContainingAndUserStatus(merchantRole, status, pageable);
        return new ResponseEntity<>(merchantPending, HttpStatus.OK);
    }

    @PutMapping("/updateStatus/{id}/{statusName}")
    public ResponseEntity<User> approvalById(@PathVariable Long id, @PathVariable String statusName) {
        Optional<User> optionalUser = userService.findById(id);
        UserStatus approvalStatus = userStatusService.findByName(statusName);
        if (!optionalUser.isPresent() || approvalStatus == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        User user = optionalUser.get();
        user.setUserStatus(approvalStatus);
        userService.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
