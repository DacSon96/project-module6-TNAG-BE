package com.codegym.project.users.userAddress;

import com.codegym.project.users.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserAddressService implements IUserAddressService{
    @Autowired
    IUserAddressRepository userAddressRepository;
    @Override
    public Page<UserDeliverAddress> findAll(Pageable pageable) {
        return userAddressRepository.findAll(pageable);
    }

    @Override
    public Optional<UserDeliverAddress> findById(Long id) {
        return userAddressRepository.findById(id);
    }

    @Override
    public UserDeliverAddress save(UserDeliverAddress userDeliverAddress) {
        return userAddressRepository.save(userDeliverAddress);
    }

    @Override
    public void deleteById(Long id) {
    userAddressRepository.deleteById(id);
    }

    @Override
    public Iterable<UserDeliverAddress> findAllByUser(User user) {
        return userAddressRepository.findAllByUser(user);
    }
}
