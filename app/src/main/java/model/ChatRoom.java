package model;

import com.google.firebase.Timestamp;

import java.util.List;

/**
 * Represents a chat room entity.
 */
public class ChatRoom {
    // Unique identifier for the chat room
    private String chatroomId;
    // List of user IDs participating in the chat room
    private List<String> userIds;
    // Timestamp of the last message sent in the chat room
    private Timestamp lastMessageTimestamp;
    // ID of the user who sent the last message
    private String lastMessageSenderId;
    // Content of the last message sent in the chat room
    private String lastMessage;
    // URL associated with the last message (if applicable)
    private String lastUrl;

    // Default constructor required for Firebase
    public ChatRoom() {
    }

    // Constructor with parameters
    public ChatRoom(String chatroomId, List<String> userIds, Timestamp lastMessageTimestamp, String lastMessageSenderId, String lastUrl) {
        this.chatroomId = chatroomId;
        this.userIds = userIds;
        this.lastMessageTimestamp = lastMessageTimestamp;
        this.lastMessageSenderId = lastMessageSenderId;
        this.lastUrl = lastUrl;
    }

    // Getter and setter methods for all fields

    public String getLastUrl() {
        return lastUrl;
    }

    public void setLastUrl(String lastUrl) {
        this.lastUrl = lastUrl;
    }

    public String getChatroomId() {
        return chatroomId;
    }

    public void setChatroomId(String chatroomId) {
        this.chatroomId = chatroomId;
    }

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    public Timestamp getLastMessageTimestamp() {
        return lastMessageTimestamp;
    }

    public void setLastMessageTimestamp(Timestamp lastMessageTimestamp) {
        this.lastMessageTimestamp = lastMessageTimestamp;
    }

    public String getLastMessageSenderId() {
        return lastMessageSenderId;
    }

    public void setLastMessageSenderId(String lastMessageSenderId) {
        this.lastMessageSenderId = lastMessageSenderId;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

}