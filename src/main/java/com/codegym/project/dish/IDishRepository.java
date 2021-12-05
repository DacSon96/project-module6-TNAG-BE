package com.codegym.project.dish;

import com.codegym.project.users.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IDishRepository extends JpaRepository<Dish, Long> {
    Page<Dish> findAllByNameContaining(String name, Pageable pageable);
    Optional<Dish> findByName(String name);
    Page<Dish> findDishByMerchant(User user, Pageable pageable);
    Page<Dish> findAllByNameContainingAndMerchant(String name, User user, Pageable pageable);
    Page<Dish> findDishByNameContainingAndIdAndMerchant(String name, User user, Pageable pageable, Long id);
    //Tìm kiếm nhiều trường trong 1 ô input
    @Query("select n from Dish n where (:name is null or lower(concat(n.name,' ',n.description)) " +
            "like %:name% or lower(n.name) like %:name% or lower(n.description) like %:name%)")
    Page<Dish> findByfullname(@Param("name")String name ,Pageable pageable);
}
