package com.codegym.project.notification;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NotificationService implements INotificationService{


    @Override
    public Page<Notification> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<Notification> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Notification save(Notification notification) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
