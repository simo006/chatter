package com.project.chatter.model.entity;

import com.project.chatter.model.enums.ChatRoomType;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "chat_rooms")
public class ChatRoom extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private ChatRoomType type;

    @ManyToMany(mappedBy = "chatRooms")
    private List<User> members;

    public ChatRoomType getType() {
        return type;
    }

    public ChatRoom setType(ChatRoomType type) {
        this.type = type;
        return this;
    }

    public List<User> getMembers() {
        return members;
    }

    public ChatRoom setMembers(List<User> members) {
        this.members = members;
        return this;
    }
}
