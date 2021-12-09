package com.codegym.project.notification;

import com.codegym.project.users.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NotificationService implements INotificationService{
    @Autowired
    private INotificationRepository notificationRepository;


    @Override
    public Page<Notification> findAll(Pageable pageable) {
        return notificationRepository.findAll(pageable);
    }

    @Override
    public Optional<Notification> findById(Long id) {
        return notificationRepository.findById(id);
    }

    @Override
    public Notification save(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public void deleteById(Long id) {
        notificationRepository.deleteById(id);
    }

    @Override
    public Iterable<Notification> findAllByReceiverOrderByTimeDesc(User receiver) {
        return notificationRepository.findAllByReceiverOrderByTimeDesc(receiver);
    }

    @Override
    public Notification createNewNotification(User sender, User receiver, String content) {

        return null;
    }
}
