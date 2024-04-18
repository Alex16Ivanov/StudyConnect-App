package model;

import static org.junit.Assert.*;
import org.junit.Test;
import com.google.firebase.Timestamp;

public class ChatImageMessageTest {

    @Test
    public void testDefaultConstructor() {
        // Arrange
        ChatImageMessage chatImageMessage = new ChatImageMessage();

        // Assert
        assertEquals(null, chatImageMessage.getUrl());
        assertEquals(null, chatImageMessage.getSenderId());
        assertEquals(null, chatImageMessage.getTimestamp());
    }

    @Test
    public void testConstructorWithUrlSenderIdAndTimestamp() {
        // Arrange
        String url = "https://example.com/image.png";
        String senderId = "123456";
        Timestamp timestamp = new Timestamp(1234567890, 0);
        ChatImageMessage chatImageMessage = new ChatImageMessage(url, senderId, timestamp);

        // Assert
        assertEquals(url, chatImageMessage.getUrl());
        assertEquals(senderId, chatImageMessage.getSenderId());
        assertEquals(timestamp, chatImageMessage.getTimestamp());
    }

    // Test case for getting url
    @Test
    public void getUrl() {
        // Initialize test data
        String url = "https://example.com/image.jpg";
        ChatImageMessage chatImageMessage = new ChatImageMessage();
        chatImageMessage.setUrl(url);

        // Assert that the getter method retrieves the correct url
        assertEquals(url, chatImageMessage.getUrl());
    }

    // Test case for setting url
    @Test
    public void setUrl() {
        // Initialize test data
        String url = "https://example.com/image2.jpg";
        ChatImageMessage chatImageMessage = new ChatImageMessage();

        // Set url using the setter method
        chatImageMessage.setUrl(url);

        // Assert that the setter method correctly sets url
        assertEquals(url, chatImageMessage.getUrl());
    }

    // Test case for getting senderId
    @Test
    public void getSenderId() {
        // Initialize test data
        String senderId = "user1";
        ChatImageMessage chatImageMessage = new ChatImageMessage();
        chatImageMessage.setSenderId(senderId);

        // Assert that the getter method retrieves the correct senderId
        assertEquals(senderId, chatImageMessage.getSenderId());
    }

    // Test case for setting senderId
    @Test
    public void setSenderId() {
        // Initialize test data
        String senderId = "user2";
        ChatImageMessage chatImageMessage = new ChatImageMessage();

        // Set senderId using the setter method
        chatImageMessage.setSenderId(senderId);

        // Assert that the setter method correctly sets senderId
        assertEquals(senderId, chatImageMessage.getSenderId());
    }

    // Test case for getting timestamp
    @Test
    public void getTimestamp() {
        // Initialize test data
        Timestamp timestamp = Timestamp.now();
        ChatImageMessage chatImageMessage = new ChatImageMessage();
        chatImageMessage.setTimestamp(timestamp);

        // Assert that the getter method retrieves the correct timestamp
        assertEquals(timestamp, chatImageMessage.getTimestamp());
    }

    // Test case for setting timestamp
    @Test
    public void setTimestamp() {
        // Initialize test data
        Timestamp timestamp = Timestamp.now();
        ChatImageMessage chatImageMessage = new ChatImageMessage();

        // Set timestamp using the setter method
        chatImageMessage.setTimestamp(timestamp);

        // Assert that the setter method correctly sets timestamp
        assertEquals(timestamp, chatImageMessage.getTimestamp());
    }
}
