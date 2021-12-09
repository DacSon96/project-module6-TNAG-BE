package com.codegym.project.controller;

import com.codegym.project.users.request.IShipperRequestService;
import com.codegym.project.users.request.ShipperRegisterRequest;
import com.codegym.project.users.shipperProfile.IShipperProfileService;
import com.codegym.project.users.shipperProfile.ShipperProfile;
import com.codegym.project.users.shipperProfile.ShipperRegisterForm;
import com.codegym.project.dish.Dish;
import com.codegym.project.dish.DishForm;
import com.codegym.project.role.IRoleService;
import com.codegym.project.role.Role;
import com.codegym.project.users.userAddress.IUserAddressService;
import com.codegym.project.users.userAddress.UserDeliverAddress;
import com.codegym.project.users.userForm.UserForm;
import com.codegym.project.users.userProfile.AvatarUploadForm;
import com.codegym.project.users.userProfile.IUserProfileService;
import com.codegym.project.users.userProfile.UserProfile;
import com.codegym.project.users.userStatus.IUserStatusService;
import com.codegym.project.users.userStatus.UserStatus;
import com.codegym.project.users.users.IUserService;
import com.codegym.project.users.users.User;
import com.codegym.project.users.users.UserDto;
import com.codegym.project.users.users.UserFindBy;
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
import java.util.List;
import java.util.ArrayList;
import java.util.List;
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

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IUserStatusService userStatusService;

    @Autowired
    private IShipperProfileService shipperProfileService;

    @Autowired
    private IShipperRequestService shipperRequestService;

    @Autowired
    private UserFindBy userFindBy;

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

    @Secured("ROLE_USER")
    @PostMapping("/address")
    public ResponseEntity<UserDeliverAddress> userDeliverAddressResponseEntity(@RequestBody UserDeliverAddress userDeliverAddress, Authentication authentication) {
        User user = userService.getUserFromAuthentication(authentication);
        userDeliverAddress.setUser(user);
        return new ResponseEntity<>(userAddressService.save(userDeliverAddress), HttpStatus.CREATED);
    }

    @Secured("ROLE_USER")
    @DeleteMapping("/address/{id}")
    public ResponseEntity<UserDeliverAddress> deleteUserAddress(@PathVariable Long id) {
        Optional<UserDeliverAddress> optionalUserDeliverAddress = userAddressService.findById(id);
        if (!optionalUserDeliverAddress.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            userAddressService.deleteById(id);
            return new ResponseEntity<>(optionalUserDeliverAddress.get(), HttpStatus.OK);
        }
    }
    @GetMapping("/findUserByCategory/{id}")
    public ResponseEntity<List<?>>findUserByCateory(@PathVariable("id") Long id){
        List<UserDto> userDtoList = userFindBy.findUserDto(id);
        if(userDtoList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(userDtoList,HttpStatus.OK);
        }
    }

    @PostMapping("/register/shipper")
    public ResponseEntity<ShipperRegisterRequest> registerShipper(ShipperRegisterForm shipperRegisterForm, Authentication authentication) throws IOException {
        User user = userService.getUserFromAuthentication(authentication);
        String driverLicense = saveFileUpload(shipperRegisterForm.getDriverLicense()) ;
        String idCardFront = saveFileUpload(shipperRegisterForm.getIdCardFront());
        String idCardBack = saveFileUpload(shipperRegisterForm.getIdCardBack());
        String vehicleOwner = saveFileUpload(shipperRegisterForm.getVehicleOwnershipCertificate());
        String profession = shipperRegisterForm.getProfession();
        ShipperProfile shipperProfile = new ShipperProfile(
                driverLicense,
                idCardFront,
                idCardBack,
                vehicleOwner,
                profession
        );

        user.setShipperProfile(shipperProfile);
        shipperProfile = shipperProfileService.save(shipperProfile);
        ShipperRegisterRequest shipperRegisterRequest = new ShipperRegisterRequest(
                user,
                shipperProfile,
                null
        );
        shipperRegisterRequest = shipperRequestService.save(shipperRegisterRequest);
        return new ResponseEntity<>(shipperRegisterRequest, HttpStatus.OK);
    }

    private String saveFileUpload(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        FileCopyUtils.copy(file.getBytes(), new File(fileUpload, fileName));
        return fileName;
    }
}
