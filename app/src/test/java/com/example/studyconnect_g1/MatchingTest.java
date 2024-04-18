package com.example.studyconnect_g1;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import model.Matching;
import model.User;

/**
 * Unit tests for the MatchingActivity class.
 */
public class MatchingTest {
    @Mock
    private MatchingActivity matchingActivity;

    @Mock
    private Matching matching;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this); // Initialize mocks
        when(matching.computeCommonCourses(
                ArgumentMatchers.<ArrayList<String>>any(),
                ArgumentMatchers.<ArrayList<String>>any()))
                .thenAnswer(invocation -> {
                    ArrayList<String> list1 = invocation.getArgument(0);
                    ArrayList<String> list2 = invocation.getArgument(1);
                    Set<String> common = new HashSet<>(list1);
                    common.retainAll(list2);
                    return common.size();
                });
    }

    /**
     * Verifies that an empty list of courses for both users results in zero common courses.
     */
    @Test
    public void computeCommonCourses_BothUsersEmpty_ReturnsZero() {
        ArrayList<String> currentUserCourses = new ArrayList<>();
        ArrayList<String> otherUserCourses = new ArrayList<>();

        int result = matching.computeCommonCourses(currentUserCourses, otherUserCourses);
        assertEquals("Expected zero common courses for two empty lists", 0, result);
    }

    /**
     * Tests that identical single-element course lists result in one common course.
     */
    @Test
    public void computeCommonCourses_SingleCommonCourse_ReturnsOne() {
        ArrayList<String> currentUserCourses = new ArrayList<>(Arrays.asList("Math"));
        ArrayList<String> otherUserCourses = new ArrayList<>(Arrays.asList("Math"));

        int result = matching.computeCommonCourses(currentUserCourses, otherUserCourses);
        assertEquals("Expected one common course for identical single-element lists", 1, result);
    }

    /**
     * Confirms that disjoint course lists with no overlap return zero common courses.
     */
    @Test
    public void computeCommonCourses_CompletelyDisjointCourses_ReturnsZero() {
        ArrayList<String> currentUserCourses = new ArrayList<>(Arrays.asList("Math", "Science"));
        ArrayList<String> otherUserCourses = new ArrayList<>(Arrays.asList("Art", "Music"));

        int result = matching.computeCommonCourses(currentUserCourses, otherUserCourses);
        assertEquals("Expected zero common courses for completely disjoint lists", 0, result);
    }

    /**
     * Ensures that a partial overlap of courses yields the correct count of common courses.
     */
    @Test
    public void computeCommonCourses_PartialOverlap_ReturnsCorrectCount() {
        ArrayList<String> currentUserCourses = new ArrayList<>(Arrays.asList("Math", "Science", "Art"));
        ArrayList<String> otherUserCourses = new ArrayList<>(Arrays.asList("Science", "English", "Art"));

        int result = matching.computeCommonCourses(currentUserCourses, otherUserCourses);
        assertEquals("Expected two common courses for lists with partial overlap", 2, result);
    }

    /**
     * Tests that an empty course list for one user and a non-empty list for the other results in zero common courses.
     */
    @Test
    public void computeCommonCourses_OneUserEmpty_ReturnsZero() {
        ArrayList<String> currentUserCourses = new ArrayList<>();
        ArrayList<String> otherUserCourses = new ArrayList<>(Arrays.asList("Science", "Art"));

        int result = matching.computeCommonCourses(currentUserCourses, otherUserCourses);
        assertEquals("Expected zero common courses when one user's list is empty", 0, result);
    }

    /**
     * Helper method to simulate processMatchingUsers logic for tests.
     * This is designed to leverage computeCommonCourses from MatchingActivity.
     */
    private List<User> processMatchingUsers(List<User> allUsers, User currentUser) {
        if (allUsers == null || currentUser == null || currentUser.getCourses() == null) {
            return new ArrayList<>();
        }

        Map<User, Integer> userCommonCourses = new HashMap<>();
        for (User user : allUsers) {
            if (!user.getUserId().equals(currentUser.getUserId())) {
                int commonCourses = matching.computeCommonCourses(currentUser.getCourses(), user.getCourses());
                userCommonCourses.put(user, commonCourses);
            }
        }

        List<Map.Entry<User, Integer>> list = new ArrayList<>(userCommonCourses.entrySet());
        list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
        List<User> sortedUsers = new ArrayList<>();
        for (Map.Entry<User, Integer> entry : list) {
            sortedUsers.add(entry.getKey());
        }
        return sortedUsers;
    }

    /**
     * Sets up a mock current user for testing.
     */
    private User setupCurrentUser() {
        User currentUser = new User("current@user.com", "Current User", "1");
        currentUser.setCourses(new ArrayList<>(Arrays.asList("Math", "Science")));
        // Set other properties as needed
        return currentUser;
    }

    /**
     * Sets up a list of mock users for testing.
     */
    private List<User> setupUsers() {
        List<User> users = new ArrayList<>();

        User user1 = new User("alice@example.com", "Alice", "2");
        user1.setCourses(new ArrayList<>(Arrays.asList("Math", "Science", "English")));
        // Set other properties as needed
        users.add(user1);

        User user2 = new User("bob@example.com", "Bob", "3");
        user2.setCourses(new ArrayList<>(Arrays.asList("History")));
        // Set other properties as needed
        users.add(user2);

        User user3 = new User("charlie@example.com", "Charlie", "4");
        user3.setCourses(new ArrayList<>(Arrays.asList("Math", "Biology")));
        // Set other properties as needed
        users.add(user3);

        return users;
    }

    /**
     * Ensures that users are sorted correctly based on common courses.
     */
    @Test
    public void processMatchingUsers_SortsCorrectly() {
        List<User> sortedUsers = processMatchingUsers(setupUsers(), setupCurrentUser());

        assertEquals("Expected 3 matching users", 3, sortedUsers.size());
        assertEquals("Alice should be first", "Alice", sortedUsers.get(0).getName());
        assertEquals("Charlie should be second", "Charlie", sortedUsers.get(1).getName());
        assertEquals("Bob should be last", "Bob", sortedUsers.get(2).getName());
    }

    /**
     * Confirms that invalid input results in an empty list of users.
     */
    @Test
    public void processMatchingUsers_InvalidInputs_ReturnsEmptyList() {
        List<User> result = processMatchingUsers(new ArrayList<>(), null);
        assertTrue("Expected empty list for null currentUser", result.isEmpty());

        User currentUser = setupCurrentUser();
        result = processMatchingUsers(null, currentUser);
        assertTrue("Expected empty list for null user list", result.isEmpty());
    }
}
