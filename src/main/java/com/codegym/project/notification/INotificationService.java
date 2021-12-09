package com.codegym.project.notification;

import com.codegym.project.IGeneralService;
import com.codegym.project.users.users.User;

public interface INotificationService extends IGeneralService<Notification> {
    Notification createNewNotification(User sender, User receiver, String content);
    Iterable<Notification> findAllByReceiverOrderByTimeDesc(User receiver);

}
