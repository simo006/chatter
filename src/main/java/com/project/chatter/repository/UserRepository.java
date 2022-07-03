package com.project.chatter.repository;

import com.project.chatter.model.entity.ChatRoom;
import com.project.chatter.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    @Query("SELECT c FROM User u JOIN u.chatRooms c WHERE u.email = :userEmail")
    Optional<List<ChatRoom>> findAllChatRoomsByUserEmail(String userEmail);
}