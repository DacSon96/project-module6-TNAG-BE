package com.codegym.project.users.users;

import com.codegym.project.IGeneralService;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends IGeneralService<User>, UserDetailsService {
    User findByUsername(String username);

}
