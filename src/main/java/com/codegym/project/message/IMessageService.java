package com.codegym.project.message;

import com.codegym.project.IGeneralService;
import com.codegym.project.users.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IMessageService extends IGeneralService<Message> {
    Page<Message> findAllBySenderAndReceiver(User sender, User receiver, Pageable pageable);

    Page<Message> findAllBySenderContainingOrReceiverContaining(User sender, User receiver, Pageable pageable);

    Page<Message> findAllBySenderContainsOrReceiverContains(User sender, User receiver, Pageable pageable);

    Iterable<Message> getAllHistoryChat(Long userId1, Long userId2, int size);


}
