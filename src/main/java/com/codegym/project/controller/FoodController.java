package com.codegym.project.controller;

import com.codegym.project.food.Food;
import com.codegym.project.food.IFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/foods")
public class FoodController {
    @Autowired
    private IFoodService foodService;

    @GetMapping("/{id}")
    public ResponseEntity<Food> findById(@PathVariable Long id) {
        Optional<Food> food = foodService.findById(id);

        if (!food.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(food.get(),HttpStatus.OK);
    }
}
