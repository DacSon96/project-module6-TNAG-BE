package com.codegym.project.food;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IFoodRepository extends JpaRepository<Food, Long> {
    Page<Food> findAllByNameContaining(String name, Pageable pageable);
    Optional<Food> findByName(String name);
}
