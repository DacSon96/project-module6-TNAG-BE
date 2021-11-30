package com.codegym.project.users.users;

import com.codegym.project.role.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    Page<User> findByRolesContainingAndId(Role role, Long id, Pageable pageable);

}
