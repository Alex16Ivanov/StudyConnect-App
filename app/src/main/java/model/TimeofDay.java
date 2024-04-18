package model;


import androidx.annotation.NonNull;

import java.util.Locale;

public class TimeofDay {
    // Class variables
    private int hours; // Variable for storing hours
    private int minutes; // Variable for storing minutes

    // Constructor with parameter
    public TimeofDay(String time) {
        // Splitting the time string into hours and minutes
        String[] times = time.split(":");
        // Parsing hours and minutes from the string and assigning them to class variables
        this.hours = Integer.parseInt(times[0]);
        this.minutes = Integer.parseInt(times[1]);
    }

    // Default constructor
    public TimeofDay(){
    }

    // Getter method for hours
    public int getHours() {
        return hours; // Returning hours
    }

    // Getter method for minutes
    public int getMinutes() {
        return minutes; // Returning minutes
    }

    // Setter method for hours
    public void setHours(int hours) {
        this.hours = hours; // Setting hours
    }

    // Setter method for minutes
    public void setMinutes(int minutes) {
        this.minutes = minutes; // Setting minutes
    }

    // Method to format the time as string
    @NonNull
    public String toString(){
        // Formatting the time in HH:mm format
        return String.format(Locale.US, "%02d:%02d", hours, minutes);
    }
}
