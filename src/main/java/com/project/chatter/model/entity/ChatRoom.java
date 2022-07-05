package com.project.chatter.model.entity;

import com.project.chatter.model.enums.ChatRoomType;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "chat_rooms")
public class ChatRoom extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private ChatRoomType type;

    @Column
    private String name;

    @ManyToMany(mappedBy = "chatRooms")
    private List<User> members;

    @OneToMany(mappedBy = "chatRoom")
    private List<Message> messages;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "chat_rooms_seen_users",
            joinColumns = @JoinColumn(name = "chat_room_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> seenUsers;

    public ChatRoom() {
    }

    public ChatRoom(ChatRoomType type, String name, List<User> members, List<Message> messages) {
        this.type = type;
        this.name = name;
        this.members = members;
        this.messages = messages;
    }

    public ChatRoom(ChatRoomType type, List<User> members, List<Message> messages) {
        this.type = type;
        this.members = members;
        this.messages = messages;
    }

    public ChatRoomType getType() {
        return type;
    }

    public ChatRoom setType(ChatRoomType type) {
        this.type = type;
        return this;
    }

    public String getName() {
        return name;
    }

    public ChatRoom setName(String name) {
        this.name = name;
        return this;
    }

    public List<User> getMembers() {
        return members;
    }

    public ChatRoom setMembers(List<User> members) {
        this.members = members;
        return this;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public ChatRoom setMessages(List<Message> messages) {
        this.messages = messages;
        return this;
    }

    public Set<User> getSeenUsers() {
        return seenUsers;
    }

    public ChatRoom setSeenUsers(Set<User> seenUsers) {
        this.seenUsers = seenUsers;
        return this;
    }
}
