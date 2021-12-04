package com.codegym.project.users.userAddress;

import com.codegym.project.users.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserAddressRepository extends JpaRepository<UserDeliverAddress, Long> {
    Iterable<UserDeliverAddress> findAllByUser(User user);
}
