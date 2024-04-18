package utils;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.Map;

public class TimeValidationUtilTest {

    @Test
    public void createTimeMap_ValidTime() {
        TimeValidationUtil util = new TimeValidationUtil();
        Map<String, String> timeMap = util.createTimeMap("12:30");
        assertNotNull(timeMap);
        assertEquals("12", timeMap.get("hours"));
        assertEquals("30", timeMap.get("minutes"));
    }

    @Test
    public void createTimeMap_InvalidTime() {
        TimeValidationUtil util = new TimeValidationUtil();
        assertNull(util.createTimeMap("24:60")); // Invalid time format
        assertNull(util.createTimeMap("12:63")); // Invalid minutes
        assertNull(util.createTimeMap("25:00")); // Invalid hours
        assertNull(util.createTimeMap("12-30")); // Invalid separator
        assertNull(util.createTimeMap("12:3"));  // Invalid minutes format
        assertNull(util.createTimeMap("1230"));  // Invalid format
    }

    @Test
    public void isTimeComponentValid_ValidHour() {
        TimeValidationUtil util = new TimeValidationUtil();
        assertFalse(util.isTimeComponentValid("12", true)); // Valid hour
    }

    @Test
    public void isTimeComponentValid_InvalidHour() {
        TimeValidationUtil util = new TimeValidationUtil();
        assertTrue(util.isTimeComponentValid("24", true));  // Invalid hour
        assertTrue(util.isTimeComponentValid("-1", true));  // Invalid hour
        assertFalse(util.isTimeComponentValid("abc", true));  // Invalid input
        assertFalse(util.isTimeComponentValid(null, true));  // Null input
    }

    @Test
    public void isTimeComponentValid_ValidMinute() {
        TimeValidationUtil util = new TimeValidationUtil();
        assertFalse(util.isTimeComponentValid("30", false)); // Valid minute
    }

    @Test
    public void isTimeComponentValid_InvalidMinute() {
        TimeValidationUtil util = new TimeValidationUtil();
        assertTrue(util.isTimeComponentValid("60", false));  // Invalid minute
        assertTrue(util.isTimeComponentValid("-1", false));  // Invalid minute
        assertFalse(util.isTimeComponentValid("abc", false));  // Invalid input
        assertFalse(util.isTimeComponentValid(null, false));  // Null input
    }
}
