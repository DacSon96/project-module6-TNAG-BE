package com.codegym.project.review;

import com.codegym.project.users.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findAllByUserContaining(User user, Pageable pageable);
    Optional<Review> findByUser( User user);
}
