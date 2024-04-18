package com.example.studyconnect_g1;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

import model.User;
import utils.FirebaseUtil;

public class MenuBarActivity extends AppCompatActivity {
    private static MenuBarActivity instance;

    public User user;
    public String userType;

    /**
     * Private constructor to enforce Singleton pattern.
     */
    private MenuBarActivity() {}

    /**
     * Retrieves the singleton instance of the MenuBarActivity.
     *
     * @return The singleton instance of MenuBarActivity.
     */
    public static synchronized MenuBarActivity getInstance() {
        if (instance == null) {
            instance = new MenuBarActivity();
        }
        return instance;
    }

    /**
     * Sets up the menu bar for the specified activity.
     *
     * @param activity The activity for which to set up the menu bar.
     */
    public void setupMenuBar(AppCompatActivity activity) {

        // retrieve user type from Firestore
        FirebaseFirestore.getInstance()
                .collection("users")
                .document(FirebaseUtil.currentUserId()) // Get the UID of the current user
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // get the user type
                        userType = documentSnapshot.getString("type");
                    }
                });

        BottomNavigationView menuBar = activity.findViewById(R.id.navigation);
        // set listener for the menu bar elements selection
        menuBar.setOnItemSelectedListener(item -> {
            int id = item.getItemId(); // get id of elements
            if (id == R.id.navigation_matching && !(activity instanceof MatchingActivity)) {
                activity.startActivity(new Intent(activity, MatchingActivity.class));
                return true;
            } else if (id == R.id.navigation_schedule) {
                if (!(activity instanceof ScheduleActivity)) {
                    activity.startActivity(new Intent(activity, ScheduleActivity.class));
                }
                return true;
            } else if (id == R.id.navigation_provide_help) {
                // check user type and start activity based on that
                if (!(activity instanceof ProvideHelpActivity)
                        && userType != null && !userType.equals("SeekHelp"))
                {
                    // start activity if user is not of type SeekHelp
                    activity.startActivity(new Intent(activity, ProvideHelpActivity.class));
                }
                return true;
            } else if (id == R.id.navigation_seek_help) {
                // check user type and start activity based on that
                if (!(activity instanceof SeekHelpActivity)
                        && userType != null && !userType.equals("ProvideHelp"))
                {
                    // start activity if user is not of type SeekHelp
                    activity.startActivity(new Intent(activity, SeekHelpActivity.class));
                }
                return true;
            } else if (id == R.id.navigation_profile) {
                if (!(activity instanceof ProfileActivity))
                {
                    activity.startActivity(new Intent(activity, ProfileActivity.class));
                }
                return true;
            }
            return false;
        });

        // automatically select icon when its activity is displayed
        if (activity instanceof ProfileActivity) {
            menuBar.setSelectedItemId(R.id.navigation_profile);
        } else if (activity instanceof MatchingActivity) {
            menuBar.setSelectedItemId(R.id.navigation_matching);
        } else if (activity instanceof ScheduleActivity) {
            menuBar.setSelectedItemId(R.id.navigation_schedule);
        } else if (activity instanceof SeekHelpActivity) {
            menuBar.setSelectedItemId(R.id.navigation_seek_help);
        } else if (activity instanceof ProvideHelpActivity) {
                menuBar.setSelectedItemId(R.id.navigation_provide_help);
        }
    }
}
