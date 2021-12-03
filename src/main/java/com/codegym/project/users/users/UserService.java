package com.codegym.project.users.users;

import com.codegym.project.role.Role;
import com.codegym.project.security.model.UserPrinciple;
import com.codegym.project.users.userStatus.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    @Override
    public User findByRolesContainingAndId(Role role, Long id) {
        return userRepository.findByRolesContainingAndId(role, id);
    }

    @Override
    public Page<User> findAllByRolesContaining(Role role, Pageable pageable) {
        return userRepository.findAllByRolesContaining(role, pageable);
    }

    @Override
    public Page<User> findAllByRolesContainingAndUserStatus(Role role, UserStatus status, Pageable pageable) {
        return userRepository.findAllByRolesContainingAndUserStatus(role, status, pageable);
    }

    @Override
    public boolean isUserDuplicated(String username) {
        User optionalUser = userRepository.findByUsername(username);
        if (optionalUser == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public User getUserFromAuthentication(Authentication authentication) {
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        String username = userPrinciple.getUsername();
        User user = userRepository.findByUsername(username);
        return user;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        return UserPrinciple.build(user);
    }

    @Autowired
    private IUserRepository userRepository;

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
