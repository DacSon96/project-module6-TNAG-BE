package com.codegym.project.users.users;

import com.codegym.project.role.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.codegym.project.users.userStatus.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    Page<User> findAllByRolesContaining (Role role, Pageable pageable);

    User findByRolesContainingAndId(Role role, Long id);

    Page<User> findAllByRolesContainingAndUserStatus(Role role, UserStatus status,Pageable pageable);

    Page<User> findAllByRolesAndMerchantProfileNameContaining(Role role, String name, Pageable pageable);

}
