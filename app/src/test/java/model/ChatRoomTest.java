package model;

import static org.junit.Assert.*;
import org.junit.Test;
import com.google.firebase.Timestamp;
import java.util.Arrays;
import java.util.List;

public class ChatRoomTest {

    @Test
    public void testDefaultConstructor() {
        // Create a ChatRoom object using the default constructor
        ChatRoom chatRoom = new ChatRoom();

        // Check if the object is not null
        assertNotNull(chatRoom);

        // Check if the fields are initialized with default values
        assertNull(chatRoom.getChatroomId());
        assertNull(chatRoom.getUserIds());
        assertNull(chatRoom.getLastMessageTimestamp());
        assertNull(chatRoom.getLastMessageSenderId());
        assertNull(chatRoom.getLastMessage());
        assertNull(chatRoom.getLastUrl());
    }

    @Test
    public void testParameterizedConstructor() {
        // Create sample data for testing
        String chatroomId = "chatroom123";
        List<String> userIds = Arrays.asList("user1", "user2", "user3");
        Timestamp lastMessageTimestamp = new Timestamp(123456789, 0);
        String lastMessageSenderId = "user1";
        String lastUrl = "https://example.com";

        // Create a ChatRoom object using the parameterized constructor
        ChatRoom chatRoom = new ChatRoom(chatroomId, userIds, lastMessageTimestamp, lastMessageSenderId, lastUrl);

        // Check if the object is not null
        assertNotNull(chatRoom);

        // Check if the fields are initialized correctly
        assertEquals(chatroomId, chatRoom.getChatroomId());
        assertEquals(userIds, chatRoom.getUserIds());
        assertEquals(lastMessageTimestamp, chatRoom.getLastMessageTimestamp());
        assertEquals(lastMessageSenderId, chatRoom.getLastMessageSenderId());
        assertNull(chatRoom.getLastMessage()); // Not set in constructor
        assertEquals(lastUrl, chatRoom.getLastUrl());
    }

    // Test case for getting chatroomId
    @Test
    public void getChatroomId() {
        // Initialize test data
        String chatroomId = "123456";
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setChatroomId(chatroomId);

        // Assert that the getter method retrieves the correct chatroomId
        assertEquals(chatroomId, chatRoom.getChatroomId());
    }

    // Test case for setting chatroomId
    @Test
    public void setChatroomId() {
        // Initialize test data
        String chatroomId = "654321";
        ChatRoom chatRoom = new ChatRoom();

        // Set chatroomId using the setter method
        chatRoom.setChatroomId(chatroomId);

        // Assert that the setter method correctly sets chatroomId
        assertEquals(chatroomId, chatRoom.getChatroomId());
    }

    // Test case for getting userIds
    @Test
    public void getUserIds() {
        // Initialize test data
        List<String> userIds = Arrays.asList("user1", "user2");
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setUserIds(userIds);

        // Assert that the getter method retrieves the correct userIds
        assertEquals(userIds, chatRoom.getUserIds());
    }

    // Test case for setting userIds
    @Test
    public void setUserIds() {
        // Initialize test data
        List<String> userIds = Arrays.asList("user3", "user4");
        ChatRoom chatRoom = new ChatRoom();

        // Set userIds using the setter method
        chatRoom.setUserIds(userIds);

        // Assert that the setter method correctly sets userIds
        assertEquals(userIds, chatRoom.getUserIds());
    }

    // Test case for getting lastMessageTimestamp
    @Test
    public void getLastMessageTimestamp() {
        // Initialize test data
        Timestamp lastMessageTimestamp = Timestamp.now();
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setLastMessageTimestamp(lastMessageTimestamp);

        // Assert that the getter method retrieves the correct lastMessageTimestamp
        assertEquals(lastMessageTimestamp, chatRoom.getLastMessageTimestamp());
    }

    // Test case for setting lastMessageTimestamp
    @Test
    public void setLastMessageTimestamp() {
        // Initialize test data
        Timestamp lastMessageTimestamp = Timestamp.now();
        ChatRoom chatRoom = new ChatRoom();

        // Set lastMessageTimestamp using the setter method
        chatRoom.setLastMessageTimestamp(lastMessageTimestamp);

        // Assert that the setter method correctly sets lastMessageTimestamp
        assertEquals(lastMessageTimestamp, chatRoom.getLastMessageTimestamp());
    }

    // Test case for getting lastMessageSenderId
    @Test
    public void getLastMessageSenderId() {
        // Initialize test data
        String lastMessageSenderId = "user5";
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setLastMessageSenderId(lastMessageSenderId);

        // Assert that the getter method retrieves the correct lastMessageSenderId
        assertEquals(lastMessageSenderId, chatRoom.getLastMessageSenderId());
    }

    // Test case for setting lastMessageSenderId
    @Test
    public void setLastMessageSenderId() {
        // Initialize test data
        String lastMessageSenderId = "user6";
        ChatRoom chatRoom = new ChatRoom();

        // Set lastMessageSenderId using the setter method
        chatRoom.setLastMessageSenderId(lastMessageSenderId);

        // Assert that the setter method correctly sets lastMessageSenderId
        assertEquals(lastMessageSenderId, chatRoom.getLastMessageSenderId());
    }

    // Test case for getting lastMessage
    @Test
    public void getLastMessage() {
        // Initialize test data
        String lastMessage = "Hello, how are you?";
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setLastMessage(lastMessage);

        // Assert that the getter method retrieves the correct lastMessage
        assertEquals(lastMessage, chatRoom.getLastMessage());
    }

    // Test case for setting lastMessage
    @Test
    public void setLastMessage() {
        // Initialize test data
        String lastMessage = "I'm fine, thank you!";
        ChatRoom chatRoom = new ChatRoom();

        // Set lastMessage using the setter method
        chatRoom.setLastMessage(lastMessage);

        // Assert that the setter method correctly sets lastMessage
        assertEquals(lastMessage, chatRoom.getLastMessage());
    }

    // Test case for getting lastUrl
    @Test
    public void getLastUrl() {
        // Initialize test data
        String lastUrl = "https://example.com/image.jpg";
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setLastUrl(lastUrl);

        // Assert that the getter method retrieves the correct lastUrl
        assertEquals(lastUrl, chatRoom.getLastUrl());
    }

    // Test case for setting lastUrl
    @Test
    public void setLastUrl() {
        // Initialize test data
        String lastUrl = "https://example.com/image2.jpg";
        ChatRoom chatRoom = new ChatRoom();

        // Set lastUrl using the setter method
        chatRoom.setLastUrl(lastUrl);

        // Assert that the setter method correctly sets lastUrl
        assertEquals(lastUrl, chatRoom.getLastUrl());
    }
}
