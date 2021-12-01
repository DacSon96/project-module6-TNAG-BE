package com.codegym.project.controller;

import com.codegym.project.dish.Dish;
import com.codegym.project.dish.DishService;
import com.codegym.project.role.IRoleService;
import com.codegym.project.role.Role;
import com.codegym.project.users.users.User;
import com.codegym.project.users.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/dishes")
public class DishController {
    @Autowired
    private DishService dishService;

    @Autowired
    private UserService userService;

    @Autowired
    private IRoleService roleService;

    @GetMapping("/dishes/merchant/{id}")
    public ResponseEntity<Page<Dish>> findAllDishesByMechant(@PathVariable("id") Long id, Pageable pageable) {
        Role role = roleService.findByName("ROLE_MERCHANT");
        User user = userService.findByRolesContainingAndId(role, id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            Page<Dish> dishPage = dishService.findDishByMerchant(user, pageable);
            return new ResponseEntity<>(dishPage, HttpStatus.OK);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<Dish> findById(@PathVariable Long id) {
        Optional<Dish> food = dishService.findById(id);
        if (!food.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(food.get(), HttpStatus.OK);
    }
}
