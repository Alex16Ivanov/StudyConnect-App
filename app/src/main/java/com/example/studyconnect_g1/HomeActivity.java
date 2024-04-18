package com.example.studyconnect_g1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import android.view.View.OnClickListener;

/**
 * The HomeActivity class represents the main activity of the application.
 * It serves as the entry point for users after they log in.
 * This activity provides a dashboard with various options and functionalities for users.
 */
public class HomeActivity extends AppCompatActivity {

    private String userType;
    ImageView messageButton;
    private View provideHelpView;
    private View seekHelpView;

    /**
     * Initializes the activity layout and sets up necessary components.
     *
     * @param savedInstanceState A Bundle object containing the activity's previously saved state, if available.
     */
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // initialise message button and provide help with seek help layouts
        messageButton = findViewById(R.id.icMessageIcon);
        provideHelpView = findViewById(R.id.provide_help);
        seekHelpView = findViewById(R.id.seek_help);

        setupDashboard();
        // Get current user
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            // Get UID of the current user
            String currentUserId = currentUser.getUid();
            // Retrieve user type from Firestore
            FirebaseFirestore.getInstance()
                    .collection("users")
                    .document(currentUserId)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document != null && document.exists()) {
                                    // get the user type
                                    userType = document.getString("type");
                                    // check which elements to hide depending on the user type
                                    if (userType.equals("SeekHelp")) {
                                        provideHelpView.setVisibility(View.GONE);
                                    } else if (userType.equals("ProvideHelp")) {
                                        seekHelpView.setVisibility(View.GONE);
                                    }
                                }
                            }
                        }
                    });
        } else {
            // Handle the case where the current user is null (not signed in)
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish();
        }

        /**
         * Sets an onClick listener for the messageButton.
         * When clicked, opens the MessageSearchActivity.
         */
        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent messageIntent = new Intent(HomeActivity.this, MessageSearchActivity.class);
                startActivity(messageIntent);
            }
        });
    }

    /**
     * Sets up the dashboard menu items with their respective click listeners.
     */
    private void setupDashboard() {
        String[] titles = {
                "Matching", "Schedule", "Provide Help", "Seek Help", "Profile"
        };
        int[] icons = {
                R.drawable.ic_match,
                R.drawable.ic_schedule,
                R.drawable.ic_provide_help,
                R.drawable.ic_seek_help,
                R.drawable.ic_profile
        };
        int[] itemIds = {
                R.id.matching, R.id.schedule, R.id.provide_help, R.id.seek_help, R.id.profile
        };

        /**
         * Iterates through the array of itemIds, sets text and compound drawables for each TextView found by id.
         *
         * @param itemIds An array of resource IDs representing the IDs of TextViews.
         * @param titles  An array of Strings representing the text to set for each TextView.
         * @param icons   An array of resource IDs representing the icons to set as compound drawables for each TextView.
         */
        for (int i = 0; i < itemIds.length; i++) {
            TextView item = findViewById(itemIds[i]);
            item.setText(titles[i]);
            item.setCompoundDrawablesWithIntrinsicBounds(icons[i], 0, 0, 0);

            final int position = i;
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Set an onClick listener for the dashboard/menu item
                    switch (position) {
                        // If the first item is clicked
                        case 0:
                            // Create an Intent to launch the MatchingActivity
                            startActivity(new Intent(HomeActivity.this, MatchingActivity.class));
                            // Finish the current activity
                            finish();
                            break;

                        // If the second item is clicked
                        case 1:
                            // Create an Intent to launch the ScheduleActivity
                            startActivity(new Intent(HomeActivity.this, ScheduleActivity.class));
                            // Finish the current activity
                            finish();
                            break;

                        // If the third item is clicked
                        case 2:
                            // Create an Intent to launch the ProvideHelpActivity
                            startActivity(new Intent(HomeActivity.this, ProvideHelpActivity.class));
                            // Finish the current activity
                            finish();
                            break;

                        // If the fourth item is clicked
                        case 3:
                            // Create an Intent to launch the SeekHelpActivity
                            startActivity(new Intent(HomeActivity.this, SeekHelpActivity.class));
                            // Finish the current activity
                            finish();
                            break;

                        // If the fifth item is clicked
                        case 4:
                            // Create an Intent to launch the ProfileActivity
                            startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                            // Finish the current activity
                            finish();
                            break;
                    }
                }
            });
        }
    }
}
