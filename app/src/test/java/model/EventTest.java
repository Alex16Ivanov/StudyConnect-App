package model;

import static org.junit.Assert.*;

import org.junit.Test;

import java.time.DayOfWeek;

public class EventTest {

    // Test case for getting title
    @Test
    public void getTitle() {
        // Initialize test data
        String title = "Meeting";
        Event event = new Event();
        event.setTitle(title);

        // Assert that the getter method retrieves the correct title
        assertEquals(title, event.getTitle());
    }

    // Test case for setting title
    @Test
    public void setTitle() {
        // Initialize test data
        String title = "Appointment";
        Event event = new Event();

        // Set title using the setter method
        event.setTitle(title);

        // Assert that the setter method correctly sets title
        assertEquals(title, event.getTitle());
    }

    // Test case for getting start time
    @Test
    public void getStartTime() {
        // Initialize test data
        String startTime = "09:00";
        Event event = new Event("", startTime, "10:00", DayOfWeek.MONDAY);

        // Assert that the getter method retrieves the correct start time
        assertEquals(startTime, event.getStartTime().toString());
    }

    // Test case for setting start time
    @Test
    public void setStartTime() {
        // Initialize test data
        String startTime = "10:30";
        Event event = new Event();

        // Create TimeofDay object for start time
        TimeofDay time = new TimeofDay(startTime);

        // Set start time using the setter method
        event.setStartTime(time);

        // Assert that the setter method correctly sets start time
        assertEquals(startTime, event.getStartTime().toString());
    }

    // Test case for getting end time
    @Test
    public void getEndTime() {
        // Initialize test data
        String endTime = "11:30";
        Event event = new Event("", "10:00", endTime, DayOfWeek.MONDAY);

        // Assert that the getter method retrieves the correct end time
        assertEquals(endTime, event.getEndTime().toString());
    }

    // Test case for setting end time
    @Test
    public void setEndTime() {
        // Initialize test data
        String endTime = "12:30";
        Event event = new Event();

        // Create TimeofDay object for end time
        TimeofDay time = new TimeofDay(endTime);

        // Set end time using the setter method
        event.setEndTime(time);

        // Assert that the setter method correctly sets end time
        assertEquals(endTime, event.getEndTime().toString());
    }

    // Test case for getting day
    @Test
    public void getDay() {
        // Initialize test data
        DayOfWeek day = DayOfWeek.WEDNESDAY;
        Event event = new Event("", "10:00", "11:00", day);

        // Assert that the getter method retrieves the correct day
        assertEquals(day, event.getDay());
    }

    // Test case for setting day
    @Test
    public void setDay() {
        // Initialize test data
        DayOfWeek day = DayOfWeek.FRIDAY;
        Event event = new Event();

        // Set day using the setter method
        event.setDay(day);

        // Assert that the setter method correctly sets day
        assertEquals(day, event.getDay());
    }
}