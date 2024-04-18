package model;

import static org.junit.Assert.*;
import org.junit.Test;
import com.google.firebase.Timestamp;

public class ChatMessageTest {

    @Test
    public void testDefaultConstructor() {
        // Arrange
        ChatMessage chatMessage = new ChatMessage();

        // Assert
        assertEquals(null, chatMessage.getMessage());
        assertEquals(null, chatMessage.getSenderId());
        assertEquals(null, chatMessage.getTimestamp());
    }

    @Test
    public void testConstructorWithMessageSenderIdAndTimestamp() {
        // Arrange
        String message = "Hello, world!";
        String senderId = "123456";
        Timestamp timestamp = new Timestamp(1234567890, 0);
        ChatMessage chatMessage = new ChatMessage(message, senderId, timestamp);

        // Assert
        assertEquals(message, chatMessage.getMessage());
        assertEquals(senderId, chatMessage.getSenderId());
        assertEquals(timestamp, chatMessage.getTimestamp());
    }

    // Test case for getting message
    @Test
    public void getMessage() {
        // Initialize test data
        String message = "Hello!";
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setMessage(message);

        // Assert that the getter method retrieves the correct message
        assertEquals(message, chatMessage.getMessage());
    }

    // Test case for setting message
    @Test
    public void setMessage() {
        // Initialize test data
        String message = "How are you?";
        ChatMessage chatMessage = new ChatMessage();

        // Set message using the setter method
        chatMessage.setMessage(message);

        // Assert that the setter method correctly sets message
        assertEquals(message, chatMessage.getMessage());
    }

    // Test case for getting senderId
    @Test
    public void getSenderId() {
        // Initialize test data
        String senderId = "123";
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSenderId(senderId);

        // Assert that the getter method retrieves the correct senderId
        assertEquals(senderId, chatMessage.getSenderId());
    }

    // Test case for setting senderId
    @Test
    public void setSenderId() {
        // Initialize test data
        String senderId = "456";
        ChatMessage chatMessage = new ChatMessage();

        // Set senderId using the setter method
        chatMessage.setSenderId(senderId);

        // Assert that the setter method correctly sets senderId
        assertEquals(senderId, chatMessage.getSenderId());
    }

    // Test case for getting timestamp
    @Test
    public void getTimestamp() {
        // Initialize test data
        Timestamp timestamp = Timestamp.now();
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setTimestamp(timestamp);

        // Assert that the getter method retrieves the correct timestamp
        assertEquals(timestamp, chatMessage.getTimestamp());
    }

    // Test case for setting timestamp
    @Test
    public void setTimestamp() {
        // Initialize test data
        Timestamp timestamp = Timestamp.now();
        ChatMessage chatMessage = new ChatMessage();

        // Set timestamp using the setter method
        chatMessage.setTimestamp(timestamp);

        // Assert that the setter method correctly sets timestamp
        assertEquals(timestamp, chatMessage.getTimestamp());
    }
}
