package com.codegym.project.controller;

import com.codegym.project.role.IRoleService;
import com.codegym.project.role.Role;
import com.codegym.project.role.RoleConst;
import com.codegym.project.users.merchantProfile.IMerchantProfileService;
import com.codegym.project.users.merchantProfile.MerchantProfile;
import com.codegym.project.users.merchantProfile.MerchantProfileForm;
import com.codegym.project.users.userStatus.IUserStatusService;
import com.codegym.project.users.userStatus.UserStatus;
import com.codegym.project.users.userStatus.UserStatusConst;
import com.codegym.project.users.users.User;
import com.codegym.project.users.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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

    @Value("${file-upload}")
    private String fileUpload;

    @GetMapping
    public ResponseEntity<Page<User>> findAllByRoleMerchants(@PageableDefault(sort = "username", size = 5) Pageable pageable) {
        Role role = roleService.findByName(RoleConst.MERCHANT);
        Page<User> userPage;
        userPage = userService.findAllByRolesContaining(role, pageable);
        if (userPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userPage, HttpStatus.OK);
    }

    @Secured("ROLE_USER")
    @PostMapping("/{id}/register")
    public ResponseEntity<User> registerMerchant(MerchantProfileForm merchantProfileForm, @PathVariable("id") Long id) throws IOException {
        Optional<User> optionalUser = userService.findById(id);
        if (!optionalUser.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            String newHotline = "0" + merchantProfileForm.getHotline();
            merchantProfileForm.setHotline(newHotline);
            UserStatus userStatus = userStatusService.findByName(UserStatusConst.pending);
            Role role = roleService.findByName(RoleConst.MERCHANT);
            User user = optionalUser.get();
            List<Role> roleList = user.getRoles();
            roleList.add(role);
            user.setRoles(roleList);
            user.setUserStatus(userStatus);

            MultipartFile multipartFileAvatar = merchantProfileForm.getAvatar();
            String filenameAvatar = multipartFileAvatar.getOriginalFilename();
            FileCopyUtils.copy(merchantProfileForm.getAvatar().getBytes(), new File(fileUpload + filenameAvatar));

            MultipartFile multipartFileCover = merchantProfileForm.getCover();
            String filenameCover = multipartFileCover.getOriginalFilename();
            FileCopyUtils.copy(merchantProfileForm.getCover().getBytes(), new File(fileUpload + filenameCover));

            MultipartFile multipartFileThumbnail = merchantProfileForm.getThumbnail();
            String filenameThumbnail = multipartFileThumbnail.getOriginalFilename();
            FileCopyUtils.copy(merchantProfileForm.getThumbnail().getBytes(), new File(fileUpload + filenameThumbnail));

            MerchantProfile merchantProfile = new MerchantProfile(
                    null,
                    merchantProfileForm.getName(),
                    merchantProfileForm.getAddress(),
                    merchantProfileForm.getCategories(),
                    merchantProfileForm.getHotline(),
                    merchantProfileForm.getOpenHours(),
                    merchantProfileForm.getDescription(),
                    filenameAvatar,
                    filenameCover,
                    filenameThumbnail
            );
            user.setMerchantProfile(merchantProfile);
            merchantProfileService.save(merchantProfile);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }
    }
}
