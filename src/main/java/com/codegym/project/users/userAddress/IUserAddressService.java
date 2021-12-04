package com.codegym.project.users.userAddress;

import com.codegym.project.IGeneralService;
import com.codegym.project.users.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserAddressService extends IGeneralService<UserDeliverAddress> {
    Iterable<UserDeliverAddress> findAllByUser(User user);
}
