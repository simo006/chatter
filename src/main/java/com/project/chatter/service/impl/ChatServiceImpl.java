package com.project.chatter.service.impl;

import com.project.chatter.model.entity.ChatRoom;
import com.project.chatter.model.entity.Message;
import com.project.chatter.model.entity.User;
import com.project.chatter.model.enums.ChatRoomType;
import com.project.chatter.model.view.chat.ChatDetailsView;
import com.project.chatter.model.view.chat.ChatView;
import com.project.chatter.model.view.chat.MessageView;
import com.project.chatter.model.view.chat.SeenChatView;
import com.project.chatter.repository.ChatRoomRepository;
import com.project.chatter.repository.MessageRepository;
import com.project.chatter.repository.UserRepository;
import com.project.chatter.service.ChatService;
import com.project.chatter.web.exception.ChatNotFoundError;
import com.project.chatter.web.exception.NotFoundError;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class ChatServiceImpl extends BaseServiceImpl implements ChatService {

    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MessageRepository messageRepository;

    public ChatServiceImpl(UserRepository userRepository, ChatRoomRepository chatRoomRepository, MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.chatRoomRepository = chatRoomRepository;
        this.messageRepository = messageRepository;
    }

    @Override
    @Transactional
    public void populateChatRoomAndMessages() {
        if (chatRoomRepository.count() == 0) {
            List<User> users = userRepository.findAllById(List.of(1L, 2L, 3L));

            List<ChatRoom> chatRooms = List.of(
                    new ChatRoom(ChatRoomType.PERSONAL, List.of(users.get(0), users.get(1)), new ArrayList<>()),
                    new ChatRoom(ChatRoomType.PERSONAL, List.of(users.get(0), users.get(2)), new ArrayList<>())
            );

            users.get(0).setChatRooms(List.of(chatRooms.get(0), chatRooms.get(1)));
            users.get(1).setChatRooms(List.of(chatRooms.get(0)));
            users.get(2).setChatRooms(List.of(chatRooms.get(1)));

            List<Message> messages = List.of(
                    new Message("Lorem Ipsum is simply dummy text of the printing and typesetting industry.", users.get(0), chatRooms.get(0), timestamp()),
                    new Message("Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.", users.get(0), chatRooms.get(0), timestamp()),
                    new Message("It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.", users.get(1), chatRooms.get(0), timestamp()),
                    new Message("It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.", users.get(2), chatRooms.get(1), timestamp()),
                    new Message("It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages.", users.get(0), chatRooms.get(0), timestamp()),
                    new Message("It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages.", users.get(1), chatRooms.get(0), timestamp()),
                    new Message("It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages.", users.get(2), chatRooms.get(1), timestamp())
            );
            messageRepository.saveAll(messages);
        }
    }

    private static LocalDateTime timestamp() {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(ThreadLocalRandom.current().nextInt()), ZoneOffset.UTC);
    }

    @Override
    @Transactional
    public List<ChatView> getChats() {
        User currentUser = getCurrentUser(userRepository);

        return currentUser.getChatRooms().stream()
                .map(chatRoom -> mapToChatView(chatRoom, currentUser))
                .collect(Collectors.toList());
    }

    private ChatView mapToChatView(ChatRoom chatRoom, User currentUser) {
        boolean isUserSeenChat = chatRoom.getSeenUsers().stream().anyMatch(user -> user.getEmail().equals(currentUser.getEmail()));
        
        ChatView chatView = new ChatView(chatRoom.getId(), getChatRoomName(chatRoom, currentUser.getEmail()), isUserSeenChat);

        // to check if the other user is online on the client side
        List<String> otherUsersEmails = getOtherUsersEmails(chatRoom, currentUser);
        if (otherUsersEmails.size() == 1) {
            chatView.setOtherUserEmail(otherUsersEmails.get(0));
        }

        if (chatRoom.getMessages() != null && !chatRoom.getMessages().isEmpty()) {
            Message lastMessage = messageRepository.findFirstByChatRoomOrderByIdDesc(chatRoom).orElseThrow(ChatNotFoundError::new);
            User lastMessageSender = lastMessage.getAddedUser();

            if (lastMessageSender.getEmail().equals(currentUser.getEmail())) {
                chatView.setSender("You");
            } else {
                chatView.setSender(lastMessage.getAddedUser().getFirstName());
            }

            chatView.setLastMessage(lastMessage.getMessage());
            chatView.setDateTimeSent(lastMessage.getAddedDate());
        }

        return chatView;
    }

    private String getChatRoomName(ChatRoom chatRoom, String currentUserEmail) {
        // Group chats have specific names
        if (chatRoom.getType() == ChatRoomType.GROUP) {
            return chatRoom.getName();
        }

        // User chats have the name of the other user
        User otherUser = chatRoom.getMembers().stream()
                .filter(user -> !user.getEmail().equals(currentUserEmail))
                .findAny()
                .orElseThrow(ChatNotFoundError::new);

        return getNames(otherUser.getFirstName(), otherUser.getLastName());
    }

    @Override
    @Transactional
    public ChatDetailsView getChat(Long chatId) {
        User currentUser = getCurrentUser(userRepository);

        ChatRoom chatRoom = chatRoomRepository.findById(chatId).orElseThrow(ChatNotFoundError::new);

        List<MessageView> messages = new ArrayList<>(chatRoom.getMessages().stream()
                .map(this::mapMessageToMessageView)
                .toList());
        // Sort messages in descending order
        Collections.reverse(messages);

        List<String> members = chatRoom.getMembers().stream()
                .map(user -> getNames(user.getFirstName(), user.getLastName()))
                .toList();

        List<String> seenByNames = chatRoom.getSeenUsers().stream()
                .filter(user -> !user.getEmail().equals(currentUser.getEmail()))
                .map(user -> getNames(user.getFirstName(), user.getLastName()))
                .toList();

        ChatDetailsView chatDetailsView = new ChatDetailsView(chatId, members,
                getChatRoomName(chatRoom, currentUser.getEmail()), messages, seenByNames);
        
        // to check if the other user is online on the client side
        List<String> otherUsersEmails = getOtherUsersEmails(chatRoom, currentUser);
        if (otherUsersEmails.size() == 1) {
            chatDetailsView.setOtherUserEmail(otherUsersEmails.get(0));
        }

        return chatDetailsView;
    }

    private List<String> getOtherUsersEmails(ChatRoom chatRoom, User currentUser) {
        return chatRoom.getMembers().stream()
                .map(User::getEmail)
                .filter(email -> !email.equals(currentUser.getEmail()))
                .toList();
    }

    @Override
    @Transactional
    public MessageView sendMessage(Long chatId, String messageText, String senderEmail) {
        User currentUser = getCurrentUser(senderEmail, userRepository);

        ChatRoom chatRoom = chatRoomRepository.findById(chatId).orElseThrow(ChatNotFoundError::new);
        // Unseen the chat from all users
        chatRoom.setSeenUsers(null);
        chatRoomRepository.saveAndFlush(chatRoom);

        Message message = new Message(messageText, currentUser, chatRoom);
        messageRepository.save(message);

        return mapMessageToMessageView(message);
    }

    @Override
    public boolean isSubscriptionValid(long chatId, String currentUserEmail) {
        List<ChatRoom> chatRooms = userRepository.findAllChatRoomsByUserEmail(currentUserEmail)
                .orElseThrow(ChatNotFoundError::new);

        return chatRooms.stream().anyMatch(c -> c.getId() == chatId);
    }

    @Override
    public String getChatRoomSubscriptionName(long chatId) {
        return String.format("/chat-room/%d/messages", chatId);
    }

    private MessageView mapMessageToMessageView(Message message) {
        User sender = message.getAddedUser();

        return new MessageView(message.getId(), message.getChatRoom().getId(), getNames(sender.getFirstName(),
                sender.getLastName()), sender.getEmail(), message.getMessage(), message.getAddedDate());
    }

    @Override
    @Transactional
    public SeenChatView seenChat(Long chatId, String userEmail) {
        User user = getCurrentUser(userEmail, userRepository);
        ChatRoom chatRoom = chatRoomRepository.findById(chatId).orElseThrow(ChatNotFoundError::new);

        chatRoom.getSeenUsers().add(user);

        chatRoomRepository.save(chatRoom);

        return new SeenChatView(userEmail, getNames(user.getFirstName(), user.getLastName()), chatId);
    }
}
