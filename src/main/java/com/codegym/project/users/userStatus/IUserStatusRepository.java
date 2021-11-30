package com.codegym.project.users.userStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserStatusRepository extends JpaRepository<UserStatus, Long> {
    UserStatus findByName(String name);
}
