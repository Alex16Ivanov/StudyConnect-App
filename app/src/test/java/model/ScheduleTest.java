package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class ScheduleTest {

    // Test case for the constructor with parameters
    @Test
    public void testScheduleConstructor() {
        // Initialize test data
        String scheduleId = "SCH001";
        String userId = "USR001";

        // Create a new Schedule object using the constructor
        Schedule schedule = new Schedule(scheduleId, userId);

        // Assert that the constructor correctly sets scheduleId
        assertEquals(scheduleId, schedule.getScheduleId());

        // Assert that the constructor correctly sets userId
        assertEquals(userId, schedule.getUserId());
    }

    // Test case for getting scheduleId
    @Test
    public void getScheduleId() {
        // Initialize test data
        String scheduleId = "SCH001";
        Schedule schedule = new Schedule(scheduleId, "");

        // Assert that the getter method retrieves the correct scheduleId
        assertEquals(scheduleId, schedule.getScheduleId());
    }

    // Test case for setting scheduleId
    @Test
    public void setScheduleId() {
        // Initialize test data
        String scheduleId = "SCH002";
        Schedule schedule = new Schedule();

        // Set scheduleId using the setter method
        schedule.setScheduleId(scheduleId);

        // Assert that the setter method correctly sets scheduleId
        assertEquals(scheduleId, schedule.getScheduleId());
    }

    // Test case for getting userId
    @Test
    public void getUserId() {
        // Initialize test data
        String userId = "USR001";
        Schedule schedule = new Schedule("", userId);

        // Assert that the getter method retrieves the correct userId
        assertEquals(userId, schedule.getUserId());
    }

    // Test case for setting userId
    @Test
    public void setUserId() {
        // Initialize test data
        String userId = "USR002";
        Schedule schedule = new Schedule();

        // Set userId using the setter method
        schedule.setUserId(userId);

        // Assert that the setter method correctly sets userId
        assertEquals(userId, schedule.getUserId());
    }
}