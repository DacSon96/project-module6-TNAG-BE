package com.codegym.project.controller;

import com.codegym.project.dish.Dish;
import com.codegym.project.dish.DishService;
import com.codegym.project.role.IRoleService;
import com.codegym.project.role.Role;
import com.codegym.project.role.RoleConst;
import com.codegym.project.security.model.UserPrinciple;
import com.codegym.project.users.users.User;
import com.codegym.project.users.users.UserService;
import com.codegym.project.dish.DishForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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

    @Value("${file-upload}")
    private String fileUpload;

    @GetMapping("/{id}/merchant")
    public ResponseEntity<Page<Dish>> findAllDishesByMerchant(@RequestParam(name = "q") Optional<String> q,
                                                              @PathVariable("id") Long id, Pageable pageable) {
        Role role = roleService.findByName(RoleConst.MERCHANT);
        User user = userService.findByRolesContainingAndId(role, id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (!q.isPresent()) {
            Page<Dish> dishPage = dishService.findDishByMerchant(user, pageable);
            return new ResponseEntity<>(dishPage, HttpStatus.OK);
        } else {
            Page<Dish> dishPage = dishService.findAllByNameContainingAndMerchant(q.get(), user, pageable);
            return new ResponseEntity<>(dishPage, HttpStatus.OK);
        }

    }

    @GetMapping
    public ResponseEntity<Page<Dish>> findAll(@RequestParam(name = "q") Optional<String> q,
                                              @PageableDefault(sort = "name", size = 5) Pageable pageable) {
        Page<Dish> dishPage;
        if (!q.isPresent()) {
            dishPage = dishService.findAll(pageable);
        } else {
            dishPage = dishService.findAllByNameContaining(q.get(), pageable);
        }
        return new ResponseEntity<>(dishPage, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Dish> findById(@PathVariable Long id) {
        Optional<Dish> food = dishService.findById(id);
        if (!food.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(food.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Dish> createDish(DishForm dishForm, Authentication authentication) throws IOException {
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        User user = userService.findByUsername(userPrinciple.getUsername());
        MultipartFile multipartFile = dishForm.getImage();
        String fileName = multipartFile.getOriginalFilename();
        FileCopyUtils.copy(dishForm.getImage().getBytes(), new File(fileUpload + fileName));
        Dish dish = new Dish(
                dishForm.getPrice(),
                fileName,
                dishForm.getName(),
                dishForm.getDescription(),
                user,
                true
        );
        return new ResponseEntity<>(dishService.save(dish), HttpStatus.CREATED);
    }
}
