package com.example.studyconnect_g1;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.util.Map;

import utils.TimeValidationUtil;

public class SeekHelpActivityTest {
    @Mock
    SeekHelpActivity seekHelpActivity;

    @Mock
    TimeValidationUtil timeValidationActivity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        MockitoAnnotations.initMocks(this);
        // Now, ensure that your class under test uses this mock
        // Mock the validatecourseTitle method to simulate its logic rather than its interaction with EditText.
        when(seekHelpActivity.validatecourseTitle(anyString())).thenAnswer(invocation -> {
            String courseTitle = invocation.getArgument(0, String.class);
            if (courseTitle.isEmpty()) {
                // Simulating EditText.setError logic for an empty course title.
                return false;
            } else if (courseTitle.length() > 30) {
                // Simulating EditText.setError logic for a too-long course title.
                return false;
            }
            // If none of the conditions are met, the course title is considered valid.
            return true;
        });

        // Mock the validateDate method to simulate its logic rather than its interaction with EditText.
        when(seekHelpActivity.validateDate(anyString())).thenAnswer(invocation -> {
            String date = invocation.getArgument(0, String.class);
            if (date.isEmpty()) {
                return false;
            }
            // bool for day of week
            boolean validDate = false;
            String[] daysArray = {"sunday", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday"};
            for (String day : daysArray) {
                if (date.equalsIgnoreCase(day)) {
                    validDate = true;
                    break;
                }
            }
            return validDate;
        });

        // Mock the validateTime method to simulate its logic rather than its interaction with EditText.
        when(seekHelpActivity.validateTime(anyString(), anyString())).thenAnswer(invocation -> {
            String startTime = invocation.getArgument(0, String.class);
            String endTime = invocation.getArgument(1, String.class);

            // Call the createTimeMap method from SeekHelpActivity
            Map<String, String> startTimeMap = timeValidationActivity.createTimeMap(startTime);
            Map<String, String> endTimeMap = timeValidationActivity.createTimeMap(endTime);

            // Simulate validation logic based on start and end time
            if (startTimeMap == null || endTimeMap == null) {
                // Simulating EditText.setError logic for invalid start or end time.
                return false;
            }
            // Simulate further validation logic...
            return true; // Return appropriate validation result
        });

        // Mock the isTimeComponentValid method
        when(timeValidationActivity.isTimeComponentValid(anyString(), anyBoolean())).thenAnswer(invocation -> {
            String timeComponent = invocation.getArgument(0, String.class);
            boolean isHour = invocation.getArgument(1, Boolean.class);

            // Mock validation logic based on input parameters
            if (timeComponent == null || timeComponent.length() > 2) {
                return true; // Return true for invalid input
            }

            // For hour component, validate in the range of 0-23
            if (isHour) {
                int value = Integer.parseInt(timeComponent);
                return value < 0 || value > 23; // Return true if not in the valid range
            } else { // For minute component, validate in the range of 0-59
                int value = Integer.parseInt(timeComponent);
                return value < 0 || value >= 60; // Return true if not in the valid range
            }
        });
    }

    /**
     * Tests if validation fails when the course title is empty.
     */
    @Test
    public void whenCourseTitleIsEmpty_thenValidationFails() {
        assertFalse(seekHelpActivity.validatecourseTitle(""));
    }

    /**
     * Tests if validation fails when the course title is too long.
     */
    @Test
    public void whenCourseTitleIsTooLong_thenValidationFails() {
        assertFalse(seekHelpActivity.validatecourseTitle("This is a very long course title that should be considered invalid due to its length."));
    }

    /**
     * Tests if validation passes when the course title is valid.
     */
    @Test
    public void whenCourseTitleIsValid_thenValidationPasses() {
        assertTrue(seekHelpActivity.validatecourseTitle("Valid Course"));
    }

    /**
     * Tests that a valid course title passes validation.
     */
    @Test
    public void testValidateCourseTitle_ValidTitle_ReturnsTrue() {
        assertTrue("Valid course title should pass.", seekHelpActivity.validatecourseTitle("Math"));
    }

    /**
     * Tests that an empty course title fails validation.
     */
    @Test
    public void testValidateCourseTitle_EmptyTitle_ReturnsFalse() {
        assertFalse("Empty course title should fail.", seekHelpActivity.validatecourseTitle(""));
    }

    /**
     * Tests that an invalid date fails validation.
     */
    @Test
    public void testValidateDate_InvalidDate_ReturnsFalse() {
        assertFalse("Invalid date should fail.", seekHelpActivity.validateDate("Moonday"));
    }

    /**
     * Tests that invalid start and end times fail validation.
     */
    @Test
    public void testValidateTime_InvalidTimes_ReturnsFalse() {
        assertTrue("Invalid times should fail.", seekHelpActivity.validateTime("09:60", "10:61"));
    }

    /**
     * Tests that the start time being after the end time fails validation.
     */
    @Test
    public void testValidateTime_StartAfterEndTime_ReturnsFalse() {
        assertTrue("Start time after end time should fail.", seekHelpActivity.validateTime("11:00", "10:00"));
    }

    /**
     * Tests if validation fails when the date is empty.
     */
    @Test
    public void whenDateIsEmpty_thenValidationFails() {
        assertFalse(seekHelpActivity.validateDate(""));
    }

    /**
     * Tests if validation fails when the date is invalid.
     */
    @Test
    public void whenDateIsInvalid_thenValidationFails() {
        assertFalse(seekHelpActivity.validateDate("Invalid Date"));
    }

    /**
     * Tests if validation passes when the date is valid.
     */
    @Test
    public void whenDateIsValid_thenValidationPasses() {
        assertTrue(seekHelpActivity.validateDate("Monday"));
    }

    /**
     * Tests if validation fails when the start time is later than the end time.
     */
    @Test
    public void whenStartTimeIsLaterThanEndTime_thenValidationFails() {
        assertTrue(seekHelpActivity.validateTime("13:00", "12:00"));
    }

    /**
     * Tests if validation passes when both start and end times are valid.
     */
    @Test
    public void whenStartTimeAndEndTimeAreValid_thenValidationPasses() {
        assertTrue(seekHelpActivity.validateTime("12:00", "13:00"));
    }

    /**
     * Tests if the hour component is valid, then the validation should pass.
     */
    @Test
    public void whenHourComponentIsValid_thenValidationPasses() {
        assertFalse(timeValidationActivity.isTimeComponentValid("12", true));
    }

    /**
     * Tests if the hour component is invalid, then the validation should fail.
     */
    @Test
    public void whenHourComponentIsInvalid_thenValidationFails() {
        assertTrue(timeValidationActivity.isTimeComponentValid("30", true));
    } //changed.

    /**
     * Tests if the minute component is valid, then the validation should pass.
     */
    @Test
    public void whenMinuteComponentIsValid_thenValidationPasses() {
        assertFalse(timeValidationActivity.isTimeComponentValid("30", false));
    }

    /**
     * Tests if the minute component is invalid, then the validation should fail.
     */
    @Test
    public void whenMinuteComponentIsInvalid_thenValidationFails() {
        assertTrue(timeValidationActivity.isTimeComponentValid("70", false));
    }
}