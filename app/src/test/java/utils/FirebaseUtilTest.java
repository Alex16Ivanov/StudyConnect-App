package utils;

import org.junit.Test;
import static junit.framework.TestCase.assertEquals;
import com.google.firebase.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.*;
import org.junit.*;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.SeekEvent;
import model.User;

public class FirebaseUtilTest {

    // verifies that the method returns the correct chatroom ID based on two user IDs
    @Test
    public void testGetChatroomId() {
        // Test with userId1 < userId2
        assertEquals("userId1_userId2", FirebaseUtil.getChatroomId("userId1", "userId2"));

        // Test with userId1 > userId2
        assertEquals("userId1_userId2", FirebaseUtil.getChatroomId("userId2", "userId1"));
    }

    // verifies that the method returns the correct schedule ID based on a user ID
    @Test
    public void testGetScheduleId() {
        assertEquals("userIdschedule", FirebaseUtil.getScheduleId("userId"));
    }

    // verifies that the method correctly converts a Timestamp object to a formatted string
    @Test
    public void testTimestampToString() {
        // create a timestamp
        long currentTimeMillis = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(currentTimeMillis / 1000, 0);

        // get current time
        LocalDateTime currentTime = LocalDateTime.now();

        // format current time in HH:mm
        String formattedTime = currentTime.format(DateTimeFormatter.ofPattern("HH:mm"));

        assertEquals(formattedTime, FirebaseUtil.timestampToString(timestamp));
    }

}