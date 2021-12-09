package com.codegym.project.message;

import com.codegym.project.users.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MessageService implements IMessageService{
    @Autowired
    private IMessageRepository messageRepository;

    @Override
    public Page<Message> findAllBySenderContainsOrReceiverContains(User sender, User receiver, Pageable pageable) {
        return messageRepository.findAllBySenderContainsOrReceiverContains(sender, receiver, pageable);
    }

    @Override
    public Iterable<Message> getAllHistoryChat(Long userId1, Long userId2, int size) {
        return messageRepository.getAllHistoryChat(userId1, userId2, size);
    }

    @Override
    public Page<Message> findAll(Pageable pageable) {
        return messageRepository.findAll(pageable);
    }

    @Override
    public Optional<Message> findById(Long id) {
        return messageRepository.findById(id);
    }

    @Override
    public Message save(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public void deleteById(Long id) {
        messageRepository.deleteById(id);
    }

    @Override
    public Page<Message> findAllBySenderAndReceiver(User sender, User receiver, Pageable pageable) {
        return messageRepository.findAllBySenderAndReceiver(sender, receiver, pageable);
    }

    @Override
    public Page<Message> findAllBySenderContainingOrReceiverContaining(User sender, User receiver, Pageable pageable) {
        return messageRepository.findAllBySenderContainingOrReceiverContaining(sender, receiver, pageable);
    }
}
