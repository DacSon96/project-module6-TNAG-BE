package com.codegym.project.food;

import com.codegym.project.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IFoodService extends IGeneralService<Food> {
    Page<Food> findAllByNameContaining(String name, Pageable pageable);
    Optional<Food> findByName(String name);
}
