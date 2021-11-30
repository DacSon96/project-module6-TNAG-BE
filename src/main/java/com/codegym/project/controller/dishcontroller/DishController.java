package com.codegym.project.controller.dishcontroller;

import com.codegym.project.food.Dish;
import com.codegym.project.food.DishService;
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
public class DishController {
    @Autowired
    private DishService dishService;

    @Autowired
    private UserService userService;

    @GetMapping("/dishes/merchant/{id}")
    public ResponseEntity<Page<Dish>> findAllDishesByMechant(@PathVariable("id") Long id, Pageable pageable){
        Optional<User> userOptional = userService.findById(id);
        if (!userOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else if (!userOptional.get().getRoles().equals("ROLE_MERCHANT")){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else {
            Page<Dish> dishPage= dishService.findDishByMerchant(userOptional.get(),pageable);
            return new ResponseEntity<>( dishPage,HttpStatus.OK);
        }

    }
}
