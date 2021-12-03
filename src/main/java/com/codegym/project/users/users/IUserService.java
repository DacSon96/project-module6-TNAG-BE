package com.codegym.project.users.users;

import com.codegym.project.IGeneralService;
import com.codegym.project.role.Role;
import com.codegym.project.role.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.codegym.project.users.userStatus.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface IUserService extends IGeneralService<User>, UserDetailsService {
    User findByUsername(String username);
    Page<User> findAllByRolesContaining (Role role, Pageable pageable);

    User findByRolesContainingAndId(Role role, Long id);

    Page<User> findAllByRolesContainingAndUserStatus(Role role, UserStatus status,Pageable pageable);

    boolean isUserDuplicated(String username);

    User getUserFromAuthentication(Authentication authentication);
}
