package com.codegym.project.users.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    Page<User> findAllByRolesContaining (Role role, Pageable pageable);

    User findByRolesContainingAndId(Role role, Long id);
}
