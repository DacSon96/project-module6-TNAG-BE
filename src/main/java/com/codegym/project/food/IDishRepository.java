package com.codegym.project.food;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IDishRepository extends JpaRepository<Dish, Long> {
    Page<Dish> findAllByNameContaining(String name, Pageable pageable);
    Optional<Dish> findByName(String name);
}
