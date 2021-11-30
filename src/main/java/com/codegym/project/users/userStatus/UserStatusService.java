package com.codegym.project.users.userStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserStatusService implements IUserStatusService{
    @Autowired
    private IUserStatusRepository userStatusRepository;

    @Override
    public Page<UserStatus> findAll(Pageable pageable) {
        return userStatusRepository.findAll(pageable);
    }

    @Override
    public Optional<UserStatus> findById(Long id) {
        return userStatusRepository.findById(id);
    }

    @Override
    public UserStatus save(UserStatus userStatus) {
        return userStatusRepository.save(userStatus);
    }

    @Override
    public void deleteById(Long id) {
        userStatusRepository.deleteById(id);
    }

    @Override
    public UserStatus findByName(String name) {
        return userStatusRepository.findByName(name);
    }
}
