package model;

import java.time.DayOfWeek;
import java.util.Map;

/**
 * Represents an event search request, encapsulating details such as the event itself,
 * time preferences, and associated course and creator information.
 */
public class SeekEvent {
    // The event details, including its title and timing
    private Event eventDetails;
    // Name of the course associated with the event
    private String courseName;
    // ID of the user who created the event
    private String creatorId;
    // Start time of the event as a map of hours and minutes
    private Map<String, String> startTimeString;
    // End time of the event as a map of hours and minutes
    private Map<String, String> endTimeString;
    // The day of the week as a string
    private String dayString;

    /**
     * Default constructor for use when no initial data is provided.
     */
    public SeekEvent() {
    }

    /**
     * Constructs a SeekEvent with specified details including timing, day, course name, and creator ID.
     *
     * @param title           The title of the event.
     * @param startTimeString Map containing start time hours and minutes.
     * @param endTimeString   Map containing end time hours and minutes.
     * @param dayString       The day of the week for the event.
     * @param courseName      The name of the associated course.
     * @param creatorId       The ID of the event's creator.
     */
    public SeekEvent(String title, Map<String, String> startTimeString, Map<String, String> endTimeString,
                     String dayString, String courseName, String creatorId) {

        this.eventDetails = new Event(title, startTimeString.get("hours") + ":" + startTimeString.get("minutes"),
                endTimeString.get("hours") + ":" + endTimeString.get("minutes"),
                DayOfWeek.valueOf(dayString.toUpperCase()));
        this.startTimeString = startTimeString;
        this.endTimeString = endTimeString;
        this.dayString = dayString;
        this.courseName = courseName;
        this.creatorId = creatorId;
    }

    // Getter and setter methods with comments

    /**
     * Gets the details of the event.
     *
     * @return The event details.
     */
    public Event getEventDetails() {
        return eventDetails;
    }

    /**
     * Sets the details of the event.
     *
     * @param eventDetails The event details to set.
     */
    public void setEventDetails(Event eventDetails) {
        this.eventDetails = eventDetails;
    }

    /**
     * Gets the name of the course associated with the event.
     *
     * @return The course name.
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * Sets the name of the course associated with the event.
     *
     * @param courseName The course name to set.
     */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    /**
     * Gets the ID of the user who created the event.
     *
     * @return The creator's ID.
     */
    public String getCreatorId() {
        return creatorId;
    }

    /**
     * Sets the ID of the user who created the event.
     *
     * @param creatorId The creator's ID to set.
     */
    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    /**
     * Gets the start time of the event as a map.
     *
     * @return The start time map.
     */
    public Map<String, String> getStartTimeString() {
        return startTimeString;
    }

    /**
     * Sets the start time of the event as a map.
     *
     * @param startTimeString The start time map to set.
     */
    public void setStartTimeString(Map<String, String> startTimeString) {
        this.startTimeString = startTimeString;
    }

    /**
     * Gets the end time of the event as a map.
     *
     * @return The end time map.
     */
    public Map<String, String> getEndTimeString() {
        return endTimeString;
    }

    /**
     * Sets the end time of the event as a map.
     *
     * @param endTimeString The end time map to set.
     */
    public void setEndTimeString(Map<String, String> endTimeString) {
        this.endTimeString = endTimeString;
    }

    /**
     * Gets the day of the week for the event as a string.
     *
     * @return The day of the week as a string.
     */
    public String getDayString() {
        return dayString;
    }

    /**
     * Sets the day of the week for the event as a string.
     *
     * @param dayString The day of the week to set as a string.
     */
    public void setDayString(String dayString) {
        this.dayString = dayString;
    }
}