package com.codegym.project.notification;

import com.codegym.project.users.users.User;
import org.aspectj.weaver.ast.Not;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface INotificationRepository extends JpaRepository<Notification, Long> {
    Iterable<Notification> findAllByReceiverOrderByTimeDesc(User receiver);
}
