package com.codegym.project.users.userAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserAddressService implements IUserAddressService {
    @Autowired
    private IUserAddressRepository userAddressRepository;

    @Override
    public Page<UserDeliverAddress> findAll(Pageable pageable) {
        return userAddressRepository.findAll(pageable);
    }

    @Override
    public Optional<UserDeliverAddress> findById(Long id) {
        return userAddressRepository.findById(id);
    }

    @Override
    public UserDeliverAddress save(UserDeliverAddress userAddress) {
        return userAddressRepository.save(userAddress);
    }

    @Override
    public void deleteById(Long id) {
        userAddressRepository.deleteById(id);
    }
}
