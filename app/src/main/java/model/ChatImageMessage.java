package model;

import com.google.firebase.Timestamp;

/**
 * Represents an image message in a chat, including the image URL, sender's ID, and timestamp.
 */
public class ChatImageMessage {

    // URL of the image
    private String url;
    // ID of the user who sent the message
    private String senderId;
    // Timestamp when the message was sent
    private Timestamp timestamp;

    /**
     * Default constructor required for calls to DataSnapshot.getValue(ChatImageMessage.class)
     */
    public ChatImageMessage() {
        // Default constructor for Firebase deserialization
    }

    /**
     * Constructs a ChatImageMessage with specified URL, senderId, and timestamp.
     *
     * @param url The URL of the image message.
     * @param senderId The ID of the user who sent the message.
     * @param timestamp The timestamp when the message was sent.
     */
    public ChatImageMessage(String url, String senderId, Timestamp timestamp) {
        this.url = url;
        this.senderId = senderId;
        this.timestamp = timestamp;
    }

    /**
     * Gets the URL of the image message.
     *
     * @return The URL of the image.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the URL of the image message.
     *
     * @param url The URL to set for the image message.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Gets the ID of the user who sent the message.
     *
     * @return The sender's user ID.
     */
    public String getSenderId() {
        return senderId;
    }

    /**
     * Sets the ID of the user who sent the message.
     *
     * @param senderId The sender's user ID to set.
     */
    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    /**
     * Gets the timestamp when the message was sent.
     *
     * @return The timestamp of the message.
     */
    public Timestamp getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp when the message was sent.
     *
     * @param timestamp The timestamp to set for the message.
     */
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}