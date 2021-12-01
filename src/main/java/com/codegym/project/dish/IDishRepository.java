package com.codegym.project.dish;

import com.codegym.project.users.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IDishRepository extends JpaRepository<Dish, Long> {
    Page<Dish> findAllByNameContaining(String name, Pageable pageable);
    Optional<Dish> findByName(String name);
    Page<Dish> findDishByMerchant(User user, Pageable pageable);
}
