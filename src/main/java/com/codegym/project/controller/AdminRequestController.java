package com.codegym.project.controller;

import com.codegym.project.role.IRoleService;
import com.codegym.project.role.Role;
import com.codegym.project.role.RoleConst;
import com.codegym.project.users.request.IShipperRequestService;
import com.codegym.project.users.request.ShipperRegisterRequest;
import com.codegym.project.users.shipperProfile.ShipperProfile;
import com.codegym.project.users.users.IUserService;
import com.codegym.project.users.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/request")
@CrossOrigin("*")
public class AdminRequestController {
    @Autowired
    private IShipperRequestService shipperRequestService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IUserService userService;

    @GetMapping
    public ResponseEntity<Iterable<ShipperRegisterRequest>> getAllPendingShipperRequest() {
        Iterable<ShipperRegisterRequest> requests = shipperRequestService.findAll();
        List<ShipperRegisterRequest> result = new ArrayList<>();
        for (ShipperRegisterRequest request: requests) {
            if (request.getStatus() == null) {
                result.add(request);
            }
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/{id}/{status}")
    private ResponseEntity<ShipperRegisterRequest> updateStatus(@PathVariable("status") String status,
                                                                @PathVariable("id") Long id) {
        Optional<ShipperRegisterRequest> optional = shipperRequestService.findById(id);
        if (!optional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ShipperRegisterRequest request = optional.get();
        if (status.equals("true")) {
            shipperRequestService.updateStatus(optional.get(), true );
            User user = request.getUser();
            ShipperProfile shipperProfile = request.getShipperProfile();
            Role shipperRole = roleService.findByName(RoleConst.SHIPPER);
            List<Role> roles = user.getRoles();
            roles.add(shipperRole);
            user.setRoles(roles);
            user.setShipperProfile(shipperProfile);
            userService.save(user);
        } else {
            shipperRequestService.updateStatus(request, false);
        }
        return new ResponseEntity<>(request, HttpStatus.OK);
    }
}
