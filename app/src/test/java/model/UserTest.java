package model;

import org.junit.Test;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class UserTest {

    @Test
    public void testDefaultConstructor() {
        // Arrange
        User user = new User();

        // Assert
        assertEquals("", user.getUserId());
        assertEquals("", user.getEmail());
        assertEquals("", user.getName());
        assertEquals("", user.getDescription());
        assertEquals("", user.getMajor());
        assertEquals("", user.getDegree());
        assertEquals("", user.getType());
        assertEquals("https://firebasestorage.googleapis.com/v0/b/studyconnect-g1.appspot.com/o/images%2Fdefault_pic.png?alt=media&token=23421ab9-ec9b-47c1-b1e3-a4d46470d75f", user.getImageUrl());
    }

    @Test
    public void testConstructorWithEmailNameAndUserId() {
        // Arrange
        String email = "test@example.com";
        String name = "Test User";
        String userId = "123456";
        User user = new User(email, name, userId);

        // Assert
        assertEquals(email, user.getEmail());
        assertEquals(name, user.getName());
        assertEquals(userId, user.getUserId());
        assertEquals("", user.getDescription());
        assertEquals("https://firebasestorage.googleapis.com/v0/b/studyconnect-g1.appspot.com/o/images%2Fdefault_pic.png?alt=media&token=23421ab9-ec9b-47c1-b1e3-a4d46470d75f", user.getImageUrl());
    }

    // test case for getting user ID
    @Test
    public void getUserId() {
        // initialize test data
        String userId = "123456";
        User user = new User();
        user.setUserId(userId);

        // assert that the getter method retrieves the correct user ID
        assertEquals(userId, user.getUserId());
    }

    // test case for setting user ID
    @Test
    public void setUserId() {
        // initialize test data
        String userId = "789012";
        User user = new User();

        // set user ID using the setter method
        user.setUserId(userId);

        // assert that the setter method correctly sets user ID
        assertEquals(userId, user.getUserId());
    }

    // test case for getting email
    @Test
    public void getEmail() {
        // initialize test data
        String email = "user@test.com";
        User user = new User();
        user.setEmail(email);

        // assert that the getter method retrieves the correct email
        assertEquals(email, user.getEmail());
    }

    // test case for setting email
    @Test
    public void setEmail() {
        // initialize test data
        String email = "test@user.com";
        User user = new User();

        // set email using the setter method
        user.setEmail(email);

        // assert that the setter method correctly sets email
        assertEquals(email, user.getEmail());
    }

    // Test case for getting name
    @Test
    public void getName() {
        // Initialize test data
        String name = "John Doe";
        User user = new User();
        user.setName(name);

        // Assert that the getter method retrieves the correct name
        assertEquals(name, user.getName());
    }

    // test case for setting name
    @Test
    public void setName() {
        // initialize test data
        String name = "Jane Smith";
        User user = new User();

        // set name using the setter method
        user.setName(name);

        // assert that the setter method correctly sets name
        assertEquals(name, user.getName());
    }

    // test case for getting major
    @Test
    public void getMajor() {
        // initialize test data
        String major = "Computer Science";
        User user = new User();
        user.setMajor(major);

        // assert that the getter method retrieves the correct major
        assertEquals(major, user.getMajor());
    }

    // test case for setting major
    @Test
    public void setMajor() {
        // initialize test data
        String major = "Mechanical Engineering";
        User user = new User();

        // set major using the setter method
        user.setMajor(major);

        // assert that the setter method correctly sets major
        assertEquals(major, user.getMajor());
    }

    // test case for getting degree
    @Test
    public void getDegree() {
        // initialize test data
        String degree = "Bachelor of Science";
        User user = new User();
        user.setDegree(degree);

        // assert that the getter method retrieves the correct degree
        assertEquals(degree, user.getDegree());
    }

    // test case for setting degree
    @Test
    public void setDegree() {
        // initialize test data
        String degree = "Master of Arts";
        User user = new User();

        // set degree using the setter method
        user.setDegree(degree);

        // assert that the setter method correctly sets degree
        assertEquals(degree, user.getDegree());
    }

    // test case for getting courses
    @Test
    public void getCourses() {
        // initialize test data
        ArrayList<String> courses = new ArrayList<>();
        courses.add("Mathematics");
        courses.add("Physics");
        User user = new User();
        user.setCourses(courses);

        // assert that the getter method retrieves the correct courses
        assertEquals(courses, user.getCourses());
    }

    // test case for setting courses
    @Test
    public void setCourses() {
        // initialize test data
        ArrayList<String> courses = new ArrayList<>();
        courses.add("Computer Science");
        courses.add("Chemistry");
        User user = new User();

        // set courses using the setter method
        user.setCourses(courses);

        // assert that the setter method correctly sets courses
        assertEquals(courses, user.getCourses());
    }

    // test case for getting description
    @Test
    public void getDescription() {
        // initialize test data
        String description = "Test description";
        User user = new User();
        user.setDescription(description);

        // assert that the getter method retrieves the correct description
        assertEquals(description, user.getDescription());
    }

    // test case for setting description
    @Test
    public void setDescription() {
        // initialize test data
        String description = "Test description";
        User user = new User();

        // set description using the setter method
        user.setDescription(description);

        // assert that the setter method correctly sets description
        assertEquals(description, user.getDescription());
    }

    // test case for getting type
    @Test
    public void getType() {
        // initialize test data
        String type = "Test type";
        User user = new User();
        user.setType(type);

        // assert that the getter method retrieves the correct type
        assertEquals(type, user.getType());
    }

    // test case for setting type
    @Test
    public void setType() {
        // initialize test data
        String type = "Test type";
        User user = new User();

        // set type using the setter method
        user.setType(type);

        // assert that the setter method correctly sets type
        assertEquals(type, user.getType());
    }

    // test case for getting provideHelpCourses
    @Test
    public void getProvideHelpCourses() {
        // initialize test data
        ArrayList<String> courses = new ArrayList<>();
        courses.add("Course1");
        courses.add("Course2");
        User user = new User();
        user.setProvideHelpCourses(courses);

        // assert that the getter method retrieves the correct provideHelpCourses
        assertEquals(courses, user.getProvideHelpCourses());
    }

    // test case for setting provideHelpCourses
    @Test
    public void setProvideHelpCourses() {
        // initialize test data
        ArrayList<String> courses = new ArrayList<>();
        courses.add("Course1");
        courses.add("Course2");
        User user = new User();

        // set provideHelpCourses using the setter method
        user.setProvideHelpCourses(courses);

        // assert that the setter method correctly sets provideHelpCourses
        assertEquals(courses, user.getProvideHelpCourses());
    }

    // test case for getting imageUrl
    @Test
    public void getImageUrl() {
        // initialize test data
        String imageUrl = "https://example.com/image.jpg";
        User user = new User();
        user.setImageUrl(imageUrl);

        // assert that the getter method retrieves the correct imageUrl
        assertEquals(imageUrl, user.getImageUrl());
    }

    // test case for setting imageUrl
    @Test
    public void setImageUrl() {
        // initialize test data
        String imageUrl = "https://example.com/image.jpg";
        User user = new User();

        // set imageUrl using the setter method
        user.setImageUrl(imageUrl);

        // assert that the setter method correctly sets imageUrl
        assertEquals(imageUrl, user.getImageUrl());
    }
}
