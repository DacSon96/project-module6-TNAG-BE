package com.codegym.project.controller;

import com.codegym.project.role.IRoleService;
import com.codegym.project.role.Role;
import com.codegym.project.role.RoleConst;
import com.codegym.project.users.merchantProfile.MerchantProfileForm;
import com.codegym.project.users.userStatus.IUserStatusService;
import com.codegym.project.users.userStatus.UserStatus;
import com.codegym.project.users.userStatus.UserStatusConst;
import com.codegym.project.users.users.IUserService;
import com.codegym.project.users.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.codegym.project.users.merchantProfile.MerchantProfile;
import com.codegym.project.users.merchantProfile.IMerchantProfileService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/merchants")
@CrossOrigin("*")
public class MerchantProfileController {
    @Value("${file-upload}")
    private String fileUpload;

    @Autowired
    private IMerchantProfileService merchantProfileService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IUserStatusService userStatusService;

    @Secured("ROLE_ADMIN")
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
        UserStatus status = userStatusService.findByName(UserStatusConst.pending);
        Role merchantRole = roleService.findByName(RoleConst.USER);
        Page<User> merchantPending = userService.findAllByRolesContainingAndUserStatus(merchantRole, status, pageable);
        return new ResponseEntity<>(merchantPending, HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/updateStatus/{id}/{statusName}")
    public ResponseEntity<User> approvalById(@PathVariable Long id, @PathVariable String statusName) {
        Optional<User> optionalUser = userService.findById(id);
        String status;
        if (statusName.equals("pending")) {
            status = UserStatusConst.pending;
        } else if (statusName.equals("approved")) {
            status = UserStatusConst.approved;
        } else if (statusName.equals("blocked")) {
            status = UserStatusConst.blocked;
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        UserStatus approvalStatus = userStatusService.findByName(status);

        if (!optionalUser.isPresent() || approvalStatus == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        User user = optionalUser.get();
        Role role = roleService.findByName(RoleConst.MERCHANT);
        List<Role> roleList = user.getRoles();
        roleList.add(role);
        user.setRoles(roleList);
        user.setUserStatus(approvalStatus);
        userService.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> getById(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        if (!user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Secured({"ROLE_ADMIN", "ROLE_MERCHANT"})
    @PutMapping("/{id}")
    public ResponseEntity<MerchantProfile> update(@PathVariable Long id, MerchantProfileForm merchantProfileForm) throws IOException {
        Optional<MerchantProfile> merchantProfileOptional = merchantProfileService.findById(id);
        if (!merchantProfileOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        String filenameAvatar;
        if (merchantProfileForm.getAvatar() != null) {
            MultipartFile multipartFileAvatar = merchantProfileForm.getAvatar();
            filenameAvatar = multipartFileAvatar.getOriginalFilename();
            FileCopyUtils.copy(merchantProfileForm.getAvatar().getBytes(), new File(fileUpload + filenameAvatar));
        } else {
            filenameAvatar = merchantProfileOptional.get().getAvatar();
        }
        String filenameCover;
        if (merchantProfileForm.getCover() != null) {
            MultipartFile multipartFileCover = merchantProfileForm.getCover();
            filenameCover = multipartFileCover.getOriginalFilename();
            FileCopyUtils.copy(merchantProfileForm.getCover().getBytes(), new File(fileUpload + filenameCover));
        } else {
            filenameCover = merchantProfileOptional.get().getCover();
        }
        String filenameThumbnail;
        if (merchantProfileForm.getThumbnail() != null) {
            MultipartFile multipartFileThumbnail = merchantProfileForm.getThumbnail();
            filenameThumbnail = multipartFileThumbnail.getOriginalFilename();
            FileCopyUtils.copy(merchantProfileForm.getThumbnail().getBytes(), new File(fileUpload + filenameThumbnail));
        } else {
            filenameThumbnail = merchantProfileOptional.get().getThumbnail();
        }
        merchantProfileForm.setId(id);
        MerchantProfile merchantProfile = new MerchantProfile(
                merchantProfileForm.getId(),
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
        merchantProfileService.save(merchantProfile);
        return new ResponseEntity<>(merchantProfile, HttpStatus.OK);
    }

    @GetMapping("/search/{searchValue}")
    public ResponseEntity<Page<User>> searchMerchantByName(
            @PathVariable String searchValue,
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
        Role role = roleService.findByName("ROLE_MERCHANT");
        Page<User> merchantPage = userService.findAllByRolesAndMerchantProfileNameContaining(role, searchValue, pageable);
        return new ResponseEntity<>(merchantPage, HttpStatus.OK);
    }
}
