package com.codegym.project.message;

import com.codegym.project.users.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IMessageRepository extends JpaRepository<Message, Long> {
    Page<Message> findAllBySenderAndReceiver(User sender, User receiver, Pageable pageable);

    Page<Message> findAllBySenderContainingOrReceiverContaining(User sender, User receiver, Pageable pageable);

    Page<Message> findAllBySenderContainsOrReceiverContains(User sender, User receiver, Pageable pageable);

    @Query(value = "call getAllChatBetweenTwoUser(?1,?2, ?3)", nativeQuery = true)
    Iterable<Message> getAllHistoryChat(Long userId1, Long userId2, int size);
}
