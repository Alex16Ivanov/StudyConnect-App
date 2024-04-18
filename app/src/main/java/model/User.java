package model;
import java.util.ArrayList;

/**
 * Represents a user in the application, including their personal and academic information,
 * as well as their preferences for study help.
 */
public class User {
    private String userId; // Unique identifier for the user
    private String email; // Email address of the user
    private String name; // Full name of the user
    private String description; // A short bio or description about the user
    private String major; // Academic major of the user
    private String degree; // Degree pursued by the user, e.g., Bachelor's, Master's
    private ArrayList<String> courses; // List of courses the user is taking

    private ArrayList<String> provideHelpCourses; // Courses the user can provide help in
    private String type; // User type, e.g., Tutor, Student
    private String imageUrl; // URL to the user's profile image

    /**
     * Default constructor initializes fields with default values or empty strings to prevent null pointer exceptions.
     * Sets a default profile picture URL.
     */
    public User() {
        this.userId = "";
        this.email = "";
        this.name = "";
        this.description = "";
        this.major = "";
        this.degree = "";
        this.type = "";
        // Default profile image URL points to a pre-defined image stored in Firebase Storage.
        this.imageUrl = "https://firebasestorage.googleapis.com/v0/b/studyconnect-g1.appspot.com/o/images%2Fdefault_pic.png?alt=media&token=23421ab9-ec9b-47c1-b1e3-a4d46470d75f";
    }

    /**
     * Constructor with parameters for email, name, and userId, useful for quick user creation.
     * Initializes other fields with default values or empty strings, including a default profile picture URL.
     *
     * @param email Email address of the user.
     * @param name Full name of the user.
     * @param userId Unique identifier for the user.
     */
    public User(String email, String name, String userId) {
        this();
        this.email = email;
        this.userId = userId;
        this.name = name;
        // Inherits the imageUrl and other default values from the default constructor.
    }

    /**
     * Gets the URL of the user's profile image.
     *
     * @return The URL of the profile image.
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Sets the URL for the user's profile image.
     *
     * @param imageUrl The new URL of the profile image.
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

// Getters and setters for the userId field

    /**
     * Gets the user's unique identifier.
     *
     * @return The user's ID.
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the user's unique identifier.
     *
     * @param userId The new user ID.
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

// Getters and setters for the email field

    /**
     * Gets the user's email address.
     *
     * @return The email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email address.
     *
     * @param email The new email address.
     */
    public void setEmail(String email) {
        this.email = email;
    }

// Getters and setters for the name field

    /**
     * Gets the full name of the user.
     *
     * @return The user's full name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the full name of the user.
     *
     * @param name The new full name.
     */
    public void setName(String name) {
        this.name = name;
    }

// Getters and setters for the description field

    /**
     * Gets the description or bio of the user.
     *
     * @return The user's description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description or bio of the user.
     *
     * @param description The new description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

// Getters and setters for the major field

    /**
     * Gets the academic major of the user.
     *
     * @return The major.
     */
    public String getMajor() {
        return major;
    }

    /**
     * Sets the academic major of the user.
     *
     * @param major The new major.
     */
    public void setMajor(String major) {
        this.major = major;
    }

// Getters and setters for the degree field

    /**
     * Gets the academic degree pursued by the user.
     *
     * @return The degree.
     */
    public String getDegree() {
        return degree;
    }

    /**
     * Sets the academic degree pursued by the user.
     *
     * @param degree The new degree.
     */
    public void setDegree(String degree) {
        this.degree = degree;
    }

// Getters and setters for the courses field

    /**
     * Gets the list of courses the user is taking or teaching.
     *
     * @return The list of courses.
     */
    public ArrayList<String> getCourses() {
        return courses;
    }

    /**
     * Sets the list of courses the user is taking or teaching.
     *
     * @param courses The new list of courses.
     */
    public void setCourses(ArrayList<String> courses) {
        this.courses = courses;
    }

// Getters and setters for the type field

    /**
     * Gets the type of user (e.g., student, tutor).
     *
     * @return The user's type.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of user (e.g., student, tutor).
     *
     * @param type The new type of the user.
     */
    public void setType(String type) {
        this.type = type;
    }

// Getters and setters for the provideHelpCourses field

    /**
     * Gets the list of courses the user can provide help in.
     *
     * @return The list of courses for providing help.
     */
    public ArrayList<String> getProvideHelpCourses() {
        return provideHelpCourses;
    }

    /**
     * Sets the list of courses the user can provide help in.
     *
     * @param provideHelpCourses The new list of courses for providing help.
     */
    public void setProvideHelpCourses(ArrayList<String> provideHelpCourses) {
        this.provideHelpCourses = provideHelpCourses;
    }
}
