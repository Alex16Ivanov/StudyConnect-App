package model;

public class Schedule {

    // Class variables
    private String scheduleId; // Variable for storing schedule ID
    private String userId; // Variable for storing user ID

    // Default constructor
    public Schedule(){

    }

    // Parameterized constructor
    public Schedule(String scheduleId, String userId) {
        this.scheduleId = scheduleId; // Assigning schedule ID from parameter
        this.userId = userId; // Assigning user ID from parameter
    }

    // Getter method for schedule ID
    public String getScheduleId() {
        return scheduleId; // Returning schedule ID
    }

    // Setter method for schedule ID
    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId; // Setting schedule ID
    }

    // Getter method for user ID
    public String getUserId() {
        return userId; // Returning user ID
    }

    // Setter method for user ID
    public void setUserId(String userId) {
        this.userId = userId; // Setting user ID
    }
}
