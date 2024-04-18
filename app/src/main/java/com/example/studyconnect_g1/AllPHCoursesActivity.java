package com.example.studyconnect_g1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import utils.FirebaseUtil;


/**
 * Main activity class for displaying all provided help courses.
 */
public class AllPHCoursesActivity extends AppCompatActivity {

    // ArrayList for course items
    private ArrayList<String> courses = new ArrayList<>();
    private PHCoursesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_courses);

        // Use the functionality for menu bar from MenuBarActivity class
        MenuBarActivity.getInstance().setupMenuBar(this);

        // Initialize the RecyclerView and set its layout manager
        RecyclerView recyclerView = findViewById(R.id.coursesNumeration);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Fetch user details and initialize the adapter
        FirebaseFirestore.getInstance()
                .collection("users")
                .document(FirebaseUtil.currentUserId()) // Get the UID of the current user
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            String pH = "provideHelpCourses";
                            if ((ArrayList<String>) document.get(pH) != null) {
                                courses = (ArrayList<String>) document.get(pH);
                            }
                            adapter = new PHCoursesAdapter(courses);
                            recyclerView.setAdapter(adapter);
                        }
                    }
                });

        // Set onClick listeners for home and messaging buttons
        ImageView homeButton = findViewById(R.id.returnToHomePage);
        ImageView messageButton = findViewById(R.id.messagingIcon);

        /**
         * Sets an onClick listener for the homeButton.
         * When clicked, redirects the user to the home page.
         */
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Redirect user to home page
                Intent homeIntent = new Intent(AllPHCoursesActivity.this, HomeActivity.class);
                startActivity(homeIntent);
            }
        });

        /**
         * Sets an onClick listener for the messageButton.
         * When clicked, opens the MessageSearchActivity.
         */
        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open message search activity
                Intent messageIntent = new Intent(AllPHCoursesActivity.this, MessageSearchActivity.class);
                startActivity(messageIntent);
            }
        });
    }

    /**
     * Nested adapter class for providing help courses.
     */
    static class PHCoursesAdapter extends RecyclerView.Adapter<PHCoursesAdapter.ViewHolder> {
        private final ArrayList<String> localCourses;

        // Constructor
        PHCoursesAdapter(ArrayList<String> courses) {
            this.localCourses = courses;
        }

        // Method to create new views
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // Inflate view for each item
            TextView textView = new TextView(parent.getContext());
            textView.setPadding(10, 10, 10, 10);
            textView.setTextSize(20);
            return new ViewHolder(textView);
        }

        /**
         * Called by RecyclerView to display the data at the specified position.
         *
         * @param holder   The ViewHolder which should be updated to represent the contents of the item at the given position in the dataset.
         * @param position The position of the item within the adapter's data set.
         */
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            // Get element from the dataset at this position and replace the contents of the view with that element
            // Prefix each course name with a bullet point
            String course = "• " + localCourses.get(position);
            holder.textView.setText(course);

            // Set onClick listener for every item of the list
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Get current position
                    int currentPosition = holder.getAdapterPosition();
                    // Check if position is valid
                    if (currentPosition != RecyclerView.NO_POSITION) {
                        // Show confirmation dialog to remove course
                        new AlertDialog.Builder(holder.itemView.getContext())
                                .setTitle("Remove course")
                                .setMessage("Do you want to remove this course?")
                                .setPositiveButton("Yes", (dialog, which) -> {
                                    // Remove the clicked item from the dataset
                                    localCourses.remove(position);
                                    // Notify adapter about item removal
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position, localCourses.size());
                                })
                                .setNegativeButton("No", null)
                                .show();
                    }
                }
            });
        }

        /**
         * Gets the number of items in the dataset represented by this adapter.
         *
         * @return The total number of items in the dataset.
         */
        @Override
        public int getItemCount() {
            return localCourses.size();
        }

        /**
         * ViewHolder class to hold references to views within each item in the RecyclerView.
         */
        static class ViewHolder extends RecyclerView.ViewHolder {
            TextView textView; // TextView to display course name

            /**
             * Constructor for the ViewHolder.
             *
             * @param v The TextView to be held by this ViewHolder.
             */
            ViewHolder(TextView v) {
                super(v);
                textView = v;
            }
        }
    }
}
