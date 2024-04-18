package model;

import java.time.DayOfWeek;


public class Event {
    // Class variables
    private String title; // Variable for storing event title
    private TimeofDay startTime; // Variable for storing start time of the event
    private TimeofDay endTime; // Variable for storing end time of the event
    private DayOfWeek day; // Variable for storing day of the week for the event

    // Default constructor
    public Event() {
    }

    // Getter method for title
    public String getTitle() {
        return title; // Returning event title
    }

    // Setter method for title
    public void setTitle(String title) {
        this.title = title; // Setting event title
    }

    // Getter method for startTime
    public TimeofDay getStartTime() {
        return startTime; // Returning start time of the event
    }

    // Setter method for startTime
    public void setStartTime(TimeofDay startTime) {
        this.startTime = startTime; // Setting start time of the event
    }

    // Getter method for endTime
    public TimeofDay getEndTime() {
        return endTime; // Returning end time of the event
    }

    // Setter method for endTime
    public void setEndTime(TimeofDay endTime) {
        this.endTime = endTime; // Setting end time of the event
    }

    // Getter method for day
    public DayOfWeek getDay() {
        return day; // Returning day of the week for the event
    }

    // Setter method for day
    public void setDay(DayOfWeek day) {
        this.day = day; // Setting day of the week for the event
    }

    // Parameterized constructor
    public Event(String title, String startTime, String endTime, DayOfWeek day) {
        this.title = title; // Assigning event title from parameter
        this.startTime = new TimeofDay(startTime); // Creating TimeofDay object from startTime parameter
        this.endTime = new TimeofDay(endTime); // Creating TimeofDay object from endTime parameter
        this.day = day; // Assigning day of the week from parameter
    }
}

