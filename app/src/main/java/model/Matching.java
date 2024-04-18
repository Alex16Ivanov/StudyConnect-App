package model;

import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.studyconnect_g1.HomeActivity;
import com.example.studyconnect_g1.MatchingActivity;
import com.example.studyconnect_g1.MessageSearchActivity;
import com.example.studyconnect_g1.adapter.UserAdapter;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.User;
import utils.FirebaseUtil;

/**
 * Represents the functionality for matching users based on specified criteria.
 */
public class Matching {
    private MatchingActivity activity;
    private UserAdapter adapter;
    private User currentUser;

    /**
     * Constructs a Matching object with the provided MatchingActivity and UserAdapter.
     *
     * @param activity The MatchingActivity associated with this Matching object.
     * @param adapter  The UserAdapter associated with this Matching object.
     */
    public Matching(@NonNull MatchingActivity activity, @NonNull UserAdapter adapter) {
        this.activity = activity;
        this.adapter = adapter;
    }

    /**
     * Initiates the Matching object by retrieving the current user and setting up onClick listeners.
     */
    public void initiate() {
        retrieveCurrentUser();
        setupOnClickListeners();
    }

    /**
     * Fetches the current user's data from Firebase and initiates retrieval of matching users.
     */
    private void retrieveCurrentUser() {
        String currentUserId = FirebaseUtil.currentUserId();
        if (currentUserId != null) {
            FirebaseUtil.allUserCollectionReference().document(currentUserId).get().addOnCompleteListener(task -> {
                if (task.isSuccessful() && task.getResult() != null) {
                    currentUser = task.getResult().toObject(User.class);
                    retrieveMatchingUsers();
                    adapter.setClickable(true);
                } else {
                    Toast.makeText(activity, "Failed to retrieve current user data.", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(activity, "No current user ID found.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Retrieves matching users from Firebase and updates the UI based on common courses and user details.
     */
    private void retrieveMatchingUsers() {
        FirebaseUtil.allUserCollectionReference().get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Map<User, Integer> userCommonCourses = new HashMap<>();

                /**
                 * Iterates through the QueryDocumentSnapshot instances obtained from the task result,
                 * extracts User objects, and computes the number of common courses between the current user and each user in the documents.
                 *
                 * @param task The Task containing the QueryDocumentSnapshot instances.
                 */
                for (QueryDocumentSnapshot document : task.getResult()) {
                    User user = document.toObject(User.class);
                    user.setUserId(document.getId()); // Ensure userId is set from document ID

                    if (currentUser != null && !currentUser.getUserId().equals(user.getUserId())) {
                        int commonCourses = computeCommonCourses(currentUser.getCourses(), user.getCourses());
                        userCommonCourses.put(user, commonCourses);
                    }
                }

                // Sorting users based on common courses
                List<Map.Entry<User, Integer>> list = new ArrayList<>(userCommonCourses.entrySet());
                list.sort((o1, o2) -> {
                    // Primary sort based on the number of common courses
                    int compareValue = o2.getValue().compareTo(o1.getValue());
                    // If the number of common courses is the same, sort alphabetically
                    if (compareValue == 0) {
                        return o1.getKey().getName().compareToIgnoreCase(o2.getKey().getName());
                    }
                    return compareValue;
                });

                // Clear the userList and then add up to 5 users
                activity.userList.clear();
                int addedCount = 0;
                for (Map.Entry<User, Integer> entry : list) {
                    if (addedCount >= 5) {
                        break; // Only add up to 5 users
                    }
                    User user = entry.getKey();
                    if (user.getUserId() == null) {
                        Toast.makeText(activity, "A user with null userId was found.", Toast.LENGTH_SHORT).show();
                        continue; // Skip this user but don't increment addedCount
                    }
                    activity.userList.add(user);
                    addedCount++;
                }
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(activity, "Failed to retrieve users from Firebase.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Computes the number of common courses between two lists of courses.
     *
     * @param currentUserCourses The list of courses for the current user.
     * @param otherUserCourses The list of courses for another user.
     * @return The number of courses that are common between the two lists.
     * If either list is {@code null}, the method returns 0.
     */
    public int computeCommonCourses(ArrayList<String> currentUserCourses, ArrayList<String> otherUserCourses) {
        if (currentUserCourses == null || otherUserCourses == null) {
            return 0;
        }
        Set<String> common = new HashSet<>(currentUserCourses);
        common.retainAll(otherUserCourses);
        return common.size();
    }

    /**
     * Sets up onClickListeners for home and message buttons within an activity.
     * When the home button is clicked, the application navigates to the HomeActivity.
     * When the message button is clicked, the application navigates to the MessageSearchActivity.
     */
    private void setupOnClickListeners() {
        activity.homeButton.setOnClickListener(view -> {
            Intent homeIntent = new Intent(activity, HomeActivity.class);
            activity.startActivity(homeIntent);
        });

        activity.messageButton.setOnClickListener(v -> {
            Intent messageIntent = new Intent(activity, MessageSearchActivity.class);
            activity.startActivity(messageIntent);
        });
    }
}