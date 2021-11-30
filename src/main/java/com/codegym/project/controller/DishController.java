package com.codegym.project.controller;

import com.codegym.project.food.Dish;
import com.codegym.project.food.IDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/dishes")
public class DishController {
    @Autowired
    private IDishService dishService;

    @GetMapping("/{id}")
    public ResponseEntity<Dish> findById(@PathVariable Long id) {
        Optional<Dish> food = dishService.findById(id);
        if (!food.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(food.get(),HttpStatus.OK);
    }
}
