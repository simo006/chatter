package com.project.chatter.model.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
public class Message extends BaseEntity {

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String message;

    @ManyToOne(fetch = FetchType.EAGER)
    private User addedUser;

    @ManyToOne
    private ChatRoom chatRoom;

    @Column(name = "added_dt", nullable = false)
    private LocalDateTime addedDate;

    public Message() {
    }

    public Message(String message, User addedUser, ChatRoom chatRoom, LocalDateTime addedDate) {
        this.message = message;
        this.addedUser = addedUser;
        this.chatRoom = chatRoom;
        this.addedDate = addedDate;
    }

    public String getMessage() {
        return message;
    }

    public Message setMessage(String message) {
        this.message = message;
        return this;
    }

    public LocalDateTime getAddedDate() {
        return addedDate;
    }

    public Message setAddedDate(LocalDateTime addedDate) {
        this.addedDate = addedDate;
        return this;
    }

    public User getAddedUser() {
        return addedUser;
    }

    public Message setAddedUser(User addedUser) {
        this.addedUser = addedUser;
        return this;
    }

    public ChatRoom getChatRoom() {
        return chatRoom;
    }

    public Message setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
        return this;
    }
}
