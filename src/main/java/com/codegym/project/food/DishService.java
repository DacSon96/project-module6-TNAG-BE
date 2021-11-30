package com.codegym.project.food;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public class DishService implements IDishService {
    @Autowired
    private IDishRepository foodRepository;

    @Override
    public Page<Dish> findAll(Pageable pageable) {
        return foodRepository.findAll(pageable);
    }

    @Override
    public Optional<Dish> findById(Long id) {
        return foodRepository.findById(id);
    }

    @Override
    public Dish save(Dish dish) {
        return foodRepository.save(dish);
    }

    @Override
    public void deleteById(Long id) {
        foodRepository.deleteById(id);
    }

    @Override
    public Page<Dish> findAllByNameContaining(String name, Pageable pageable) {
        return foodRepository.findAllByNameContaining(name,pageable);
    }

    @Override
    public Optional<Dish> findByName(String name) {
        return foodRepository.findByName(name);
    }
}
