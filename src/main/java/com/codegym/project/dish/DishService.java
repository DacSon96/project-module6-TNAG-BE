package com.codegym.project.dish;

import com.codegym.project.users.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DishService implements IDishService {
    @Autowired
    private IDishRepository dishRepository;

    @Override
    public Page<Dish> findAll(Pageable pageable) {
        return dishRepository.findAll(pageable);
    }

    @Override
    public Optional<Dish> findById(Long id) {
        return dishRepository.findById(id);
    }

    @Override
    public Dish save(Dish dish) {
        return dishRepository.save(dish);
    }

    @Override
    public void deleteById(Long id) {
        dishRepository.deleteById(id);
    }

    @Override
    public Page<Dish> findAllByNameContaining(String name, Pageable pageable) {
        return dishRepository.findAllByNameContaining(name,pageable);
    }

    @Override
    public Optional<Dish> findByName(String name) {
        return dishRepository.findByName(name);
    }

    @Override
    public Page<Dish> findDishByMerchant(User user, Pageable pageable){
        return dishRepository.findDishByMerchant(user,pageable);
    }
}
