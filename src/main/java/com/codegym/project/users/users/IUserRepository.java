package com.codegym.project.users.users;

import com.codegym.project.role.Role;
import com.codegym.project.users.merchantProfile.MerchantProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    Iterable<User> findAllByRolesContaining(Role role);
}
