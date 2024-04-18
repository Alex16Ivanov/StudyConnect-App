package utils;

import java.util.HashMap;
import java.util.Map;

public class TimeValidationUtil {

    /**
     * Helper method to convert a time string to a map with keys "hours" and "minutes".
     * The time string should be in HH:MM format.
     *
     * @param time the time string to be converted
     * @return a map with keys "hours" and "minutes" if the time format is valid, otherwise null
     */
    public Map<String, String> createTimeMap(String time) {
        // Regex to match HH:MM format strictly
        if (!time.matches("^([01]?[0-9]|2[0-3]):[0-5][0-9]$")) {
            return null; // Return null if time format is not matched
        }
        Map<String, String> timeMap = new HashMap<>();
        String[] parts = time.split(":");
        timeMap.put("hours", parts[0]);
        timeMap.put("minutes", parts[1]);
        return timeMap;
    } //changed.

    /**
     * Helper method to validate each time component (hours or minutes).
     *
     * @param timeComponent the time component to validate
     * @param isHour true if the component is hours, false if minutes
     * @return false if the time component is valid, true otherwise
     */
    public boolean isTimeComponentValid(String timeComponent, boolean isHour) {
        if (timeComponent == null || timeComponent.length() > 2) {
            return false; // Corrected to return false for invalid input
        }
        try {
            int value = Integer.parseInt(timeComponent);
            if (isHour) {
                return !(value >= 0 && value <= 23); // Validate hours in the range of 0-23
            } else {
                return !(value >= 0 && value < 60); // Validate minutes in the range of 0-59
            }
        } catch (NumberFormatException e) {
            return false; // If parsing fails, return false
        }
    }
}