package com.codegym.project.review;

import com.codegym.project.IGeneralService;
import com.codegym.project.users.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IReviewService extends IGeneralService<Review> {
    Page<Review> findAllByUserContaining(User user, Pageable pageable);
    Optional<Review> findByUser(User user);
}
