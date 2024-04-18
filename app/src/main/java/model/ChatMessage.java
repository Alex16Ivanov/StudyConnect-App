package model;

import com.google.firebase.Timestamp;

/**
 * Represents a chat message in the application.
 * This class includes the message content, the sender's user ID, and the timestamp when the message was sent.
 */
public class ChatMessage {
    // The content of the chat message
    private String message;
    // The ID of the user who sent this message
    private String senderId;
    // The timestamp when the message was sent
    private Timestamp timestamp;

    /**
     * Default constructor required for Firebase's automatic data mapping.
     */
    public ChatMessage() {
        // Default constructor
    }

    /**
     * Constructs a new ChatMessage with the specified message content, sender's user ID, and timestamp.
     *
     * @param message   The content of the chat message.
     * @param senderId  The ID of the user who sent the message.
     * @param timestamp The timestamp when the message was sent.
     */
    public ChatMessage(String message, String senderId, Timestamp timestamp) {
        this.message = message;
        this.senderId = senderId;
        this.timestamp = timestamp;
    }

    // Getter and setter for the message content
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // Getter and setter for the sender's user ID
    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    // Getter and setter for the timestamp of when the message was sent
    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}