package com.codegym.project.controller;

import com.codegym.project.role.IRoleService;
import com.codegym.project.role.Role;
import com.codegym.project.role.RoleConst;
import com.codegym.project.users.merchantProfile.IMerchantProfileService;
import com.codegym.project.users.merchantProfile.MerchantProfile;
import com.codegym.project.users.userStatus.IUserStatusService;
import com.codegym.project.users.userStatus.UserStatus;
import com.codegym.project.users.userStatus.UserStatusConst;
import com.codegym.project.users.users.User;
import com.codegym.project.users.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/merchants")
public class MerchantRestController {
    @Autowired
    private UserService userService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IMerchantProfileService merchantProfileService;

    @Autowired
    private IUserStatusService userStatusService;

    @GetMapping
    public ResponseEntity<Page<User>> findAllByRoleMerchants(@PageableDefault(sort = "username", size = 5) Pageable pageable) {
        Role role = roleService.findByName(RoleConst.MERCHANT);
        Page<User> userPage;
        userPage = userService.findAllByRolesContaining(role, pageable);
        return new ResponseEntity<>(userPage, HttpStatus.OK);
    }

    @Secured({RoleConst.ADMIN, RoleConst.USER})
    @PostMapping("/{id}")
    public ResponseEntity<User> registerMerchant(@RequestBody MerchantProfile merchantProfile, @PathVariable("id") Long id){
        Optional<User> optionalUser = userService.findById(id);
        if (!optionalUser.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            UserStatus userStatus = userStatusService.findByName(UserStatusConst.PENDING);
            Role role = roleService.findByName(RoleConst.MERCHANT);
            User user = optionalUser.get();
            List<Role> roleList = user.getRoles();
            roleList.add(role);
            user.setRoles(roleList);
            user.setUserStatus(userStatus);
            user.setMerchantProfile(merchantProfile);
            merchantProfileService.save(merchantProfile);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }
    }
}
