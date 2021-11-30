package com.codegym.project.food;

import com.codegym.project.IGeneralService;
import com.codegym.project.users.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IDishService extends IGeneralService<Dish> {
    Page<Dish> findAllByNameContaining(String name, Pageable pageable);
    Optional<Dish> findByName(String name);
    Page<Dish> findDishByMerchant(User user, Pageable pageable);
}
