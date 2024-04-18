package model;

import static org.junit.Assert.*;
import org.junit.Test;
import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Map;

public class SeekEventTest {

    @Test
    public void testDefaultConstructor() {
        // Arrange
        SeekEvent seekEvent = new SeekEvent();

        // Assert
        assertEquals(null, seekEvent.getEventDetails());
        assertEquals(null, seekEvent.getCourseName());
        assertEquals(null, seekEvent.getCreatorId());
        assertEquals(null, seekEvent.getStartTimeString());
        assertEquals(null, seekEvent.getEndTimeString());
        assertEquals(null, seekEvent.getDayString());
    }

    @Test
    public void testConstructorWithTitleAndTimeParameters() {
        // Arrange
        String title = "Study Session";
        Map<String, String> startTime = new HashMap<>();
        startTime.put("hours", "10");
        startTime.put("minutes", "30");
        Map<String, String> endTime = new HashMap<>();
        endTime.put("hours", "12");
        endTime.put("minutes", "00");
        String day = "Monday";
        String courseName = "Math";
        String creatorId = "123456789";

        // Act
        SeekEvent seekEvent = new SeekEvent(title, startTime, endTime, day, courseName, creatorId);

        // Assert
        assertEquals(title, seekEvent.getEventDetails().getTitle());
        assertEquals("10:30", seekEvent.getEventDetails().getStartTime().toString());
        assertEquals("12:00", seekEvent.getEventDetails().getEndTime().toString());
        assertEquals(DayOfWeek.MONDAY, seekEvent.getEventDetails().getDay());
        assertEquals(startTime, seekEvent.getStartTimeString());
        assertEquals(endTime, seekEvent.getEndTimeString());
        assertEquals(day, seekEvent.getDayString());
        assertEquals(courseName, seekEvent.getCourseName());
        assertEquals(creatorId, seekEvent.getCreatorId());
    }

    // Test case for getting eventDetails
    @Test
    public void getEventDetails() {
        // Initialize test data
        Event event = new Event("Meeting", "09:00", "10:00", DayOfWeek.MONDAY);
        SeekEvent seekEvent = new SeekEvent();
        seekEvent.setEventDetails(event);

        // Assert that the getter method retrieves the correct eventDetails
        assertEquals(event, seekEvent.getEventDetails());
    }

    // Test case for setting eventDetails
    @Test
    public void setEventDetails() {
        // Initialize test data
        Event event = new Event("Meeting", "09:00", "10:00", DayOfWeek.MONDAY);
        SeekEvent seekEvent = new SeekEvent();

        // Set eventDetails using the setter method
        seekEvent.setEventDetails(event);

        // Assert that the setter method correctly sets eventDetails
        assertEquals(event, seekEvent.getEventDetails());
    }

    // Test case for getting courseName
    @Test
    public void getCourseName() {
        // Initialize test data
        String courseName = "Math";
        SeekEvent seekEvent = new SeekEvent();
        seekEvent.setCourseName(courseName);

        // Assert that the getter method retrieves the correct courseName
        assertEquals(courseName, seekEvent.getCourseName());
    }

    // Test case for setting courseName
    @Test
    public void setCourseName() {
        // Initialize test data
        String courseName = "Biology";
        SeekEvent seekEvent = new SeekEvent();

        // Set courseName using the setter method
        seekEvent.setCourseName(courseName);

        // Assert that the setter method correctly sets courseName
        assertEquals(courseName, seekEvent.getCourseName());
    }

    // Test case for getting creatorId
    @Test
    public void getCreatorId() {
        // Initialize test data
        String creatorId = "123";
        SeekEvent seekEvent = new SeekEvent();
        seekEvent.setCreatorId(creatorId);

        // Assert that the getter method retrieves the correct creatorId
        assertEquals(creatorId, seekEvent.getCreatorId());
    }

    // Test case for setting creatorId
    @Test
    public void setCreatorId() {
        // Initialize test data
        String creatorId = "456";
        SeekEvent seekEvent = new SeekEvent();

        // Set creatorId using the setter method
        seekEvent.setCreatorId(creatorId);

        // Assert that the setter method correctly sets creatorId
        assertEquals(creatorId, seekEvent.getCreatorId());
    }

    // Test case for getting startTimeString
    @Test
    public void getStartTimeString() {
        // Initialize test data
        Map<String, String> startTime = new HashMap<>();
        startTime.put("hours", "09");
        startTime.put("minutes", "00");
        SeekEvent seekEvent = new SeekEvent();
        seekEvent.setStartTimeString(startTime);

        // Assert that the getter method retrieves the correct startTimeString
        assertEquals(startTime, seekEvent.getStartTimeString());
    }

    // Test case for setting startTimeString
    @Test
    public void setStartTimeString() {
        // Initialize test data
        Map<String, String> startTime = new HashMap<>();
        startTime.put("hours", "10");
        startTime.put("minutes", "30");
        SeekEvent seekEvent = new SeekEvent();

        // Set startTimeString using the setter method
        seekEvent.setStartTimeString(startTime);

        // Assert that the setter method correctly sets startTimeString
        assertEquals(startTime, seekEvent.getStartTimeString());
    }

    // Test case for getting endTimeString
    @Test
    public void getEndTimeString() {
        // Initialize test data
        Map<String, String> endTime = new HashMap<>();
        endTime.put("hours", "11");
        endTime.put("minutes", "00");
        SeekEvent seekEvent = new SeekEvent();
        seekEvent.setEndTimeString(endTime);

        // Assert that the getter method retrieves the correct endTimeString
        assertEquals(endTime, seekEvent.getEndTimeString());
    }

    // Test case for setting endTimeString
    @Test
    public void setEndTimeString() {
        // Initialize test data
        Map<String, String> endTime = new HashMap<>();
        endTime.put("hours", "12");
        endTime.put("minutes", "30");
        SeekEvent seekEvent = new SeekEvent();

        // Set endTimeString using the setter method
        seekEvent.setEndTimeString(endTime);

        // Assert that the setter method correctly sets endTimeString
        assertEquals(endTime, seekEvent.getEndTimeString());
    }

    // Test case for getting dayString
    @Test
    public void getDayString() {
        // Initialize test data
        String dayString = "MONDAY";
        SeekEvent seekEvent = new SeekEvent();
        seekEvent.setDayString(dayString);

        // Assert that the getter method retrieves the correct dayString
        assertEquals(dayString, seekEvent.getDayString());
    }

    // Test case for setting dayString
    @Test
    public void setDayString() {
        // Initialize test data
        String dayString = "FRIDAY";
        SeekEvent seekEvent = new SeekEvent();

        // Set dayString using the setter method
        seekEvent.setDayString(dayString);

        // Assert that the setter method correctly sets dayString
        assertEquals(dayString, seekEvent.getDayString());
    }
}
