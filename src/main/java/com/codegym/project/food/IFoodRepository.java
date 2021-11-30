package com.codegym.project.food;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IFoodRepository extends JpaRepository<Food, Long> {
    Page<Food> findAllByNameContaining(String name, Pageable pageable);
    Optional<Food> findByName(String name);
}
