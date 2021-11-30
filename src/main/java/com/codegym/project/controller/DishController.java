package com.codegym.project.controller;

import com.codegym.project.food.Dish;
import com.codegym.project.food.DishForm;
import com.codegym.project.food.IDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private IDishService dishService;

    @Value("${file-upload}")
    private String fileUpload;

    @GetMapping("/{id}")
    public ResponseEntity<Dish> findById(@PathVariable Long id) {
        Optional<Dish> food = dishService.findById(id);
        if (!food.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(food.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Dish> createDish(DishForm dishForm) throws IOException {
        MultipartFile multipartFile = dishForm.getImage();
        String fileName = multipartFile.getOriginalFilename();
        FileCopyUtils.copy(dishForm.getImage().getBytes(), new File(fileUpload, fileName));
        Dish dish = new Dish(
                dishForm.getName(),
                fileName,
                dishForm.getDescription(),
                dishForm.getMerchant(),
                dishForm.getStatus()
        );
        return new ResponseEntity<>(dishService.save(dish), HttpStatus.CREATED);
    }
}
