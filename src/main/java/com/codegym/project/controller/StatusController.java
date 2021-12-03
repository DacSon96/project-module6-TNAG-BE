package com.codegym.project.controller;

import com.codegym.project.users.userStatus.IUserStatusService;
import com.codegym.project.users.userStatus.UserStatus;
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
@RequestMapping("/status")
@CrossOrigin("*")
public class StatusController {
    @Autowired
    private IUserStatusService userStatusService;

    @GetMapping("")
    private ResponseEntity<Page<UserStatus>> getAllStatus(@PageableDefault(sort = "name", size = 5) Pageable pageable){
        Page<UserStatus> userStatus = userStatusService.findAll(pageable);
        return new ResponseEntity<>(userStatus, HttpStatus.OK);
    }

}
