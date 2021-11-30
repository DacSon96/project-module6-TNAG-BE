package com.codegym.project.food;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FoodService implements IFoodService{
    @Autowired
    private IFoodRepository foodRepository;

    @Override
    public Page<Food> findAll(Pageable pageable) {
        return foodRepository.findAll(pageable);
    }

    @Override
    public Optional<Food> findById(Long id) {
        return foodRepository.findById(id);
    }

    @Override
    public Food save(Food food) {
        return foodRepository.save(food);
    }

    @Override
    public void deleteById(Long id) {
        foodRepository.deleteById(id);
    }

    @Override
    public Page<Food> findAllByNameContaining(String name, Pageable pageable) {
        return foodRepository.findAllByNameContaining(name,pageable);
    }

    @Override
    public Optional<Food> findByName(String name) {
        return foodRepository.findByName(name);
    }
}
