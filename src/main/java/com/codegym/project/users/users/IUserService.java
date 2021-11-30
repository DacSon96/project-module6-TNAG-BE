package com.codegym.project.users.users;

import com.codegym.project.IGeneralService;
import com.codegym.project.role.Role;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends IGeneralService<User>, UserDetailsService {
    User findByUsername(String username);

    Iterable<User> findAllByRoles(Role role);

}
