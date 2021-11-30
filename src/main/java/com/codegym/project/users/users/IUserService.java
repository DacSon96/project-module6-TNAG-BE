package com.codegym.project.users.users;

import com.codegym.project.IGeneralService;
import com.codegym.project.role.Role;
import com.codegym.project.users.userStatus.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends IGeneralService<User>, UserDetailsService {
    User findByUsername(String username);

    User findByRolesContainingAndId(Role role, Long id);

    Page<User> findAllByRolesContainingAndUserStatus(Role role, UserStatus status,Pageable pageable);
}
