package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class TimeofDayTest {

    // Test case for constructor with parameter
    @Test
    public void testConstructorWithParameter() {
        // Initialize test data
        String timeString = "12:30";

        // Create a TimeofDay object using the constructor with parameter
        TimeofDay time = new TimeofDay(timeString);

        // Assert that the constructor correctly sets hours and minutes
        assertEquals(12, time.getHours());
        assertEquals(30, time.getMinutes());
    }

    // Test case for getting hours
    @Test
    public void getHours() {
        // Initialize test data
        int hours = 10;
        TimeofDay time = new TimeofDay();
        time.setHours(hours);

        // Assert that the getter method retrieves the correct hours
        assertEquals(hours, time.getHours());
    }

    // Test case for getting minutes
    @Test
    public void getMinutes() {
        // Initialize test data
        int minutes = 30;
        TimeofDay time = new TimeofDay();
        time.setMinutes(minutes);

        // Assert that the getter method retrieves the correct minutes
        assertEquals(minutes, time.getMinutes());
    }

    // Test case for setting hours
    @Test
    public void setHours() {
        // Initialize test data
        int hours = 5;
        TimeofDay time = new TimeofDay();

        // Set hours using the setter method
        time.setHours(hours);

        // Assert that the setter method correctly sets hours
        assertEquals(hours, time.getHours());
    }

    // Test case for setting minutes
    @Test
    public void setMinutes() {
        // Initialize test data
        int minutes = 45;
        TimeofDay time = new TimeofDay();

        // Set minutes using the setter method
        time.setMinutes(minutes);

        // Assert that the setter method correctly sets minutes
        assertEquals(minutes, time.getMinutes());
    }

    // Test case for toString method
    @Test
    public void testToString() {
        // Initialize test data
        int hours = 8;
        int minutes = 15;
        TimeofDay time = new TimeofDay();
        time.setHours(hours);
        time.setMinutes(minutes);

        // Assert that the toString method formats the time correctly
        assertEquals("08:15", time.toString());
    }
}
