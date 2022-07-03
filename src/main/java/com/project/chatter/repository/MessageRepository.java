package com.project.chatter.repository;

import com.project.chatter.model.entity.ChatRoom;
import com.project.chatter.model.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    Optional<Message> findFirstByChatRoomOrderByAddedDateDesc(ChatRoom chatRoom);
}