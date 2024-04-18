package com.example.studyconnect_g1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import utils.FirebaseUtil;

public class AllCoursesActivity extends AppCompatActivity {

    // list for course items
    private ArrayList<String> courses = new ArrayList<>();

    // adapter to list the courses
    private CoursesAdapter adapter;

    /**
     * Initializes the activity, sets the content view to the layout of 'activity_all_courses'.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     *                           Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_courses);

        // use the functionality for menu bar from MenuBarActivity class
        MenuBarActivity.getInstance().setupMenuBar(this);

        // initialize the recycler and set its layout manager
        RecyclerView recyclerView = findViewById(R.id.coursesNumeration);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        TextView profileName = findViewById(R.id.profile_name);
        TextView profileEmail = findViewById(R.id.profile_email);

        FirebaseFirestore.getInstance()
                .collection("users")
                .document(FirebaseUtil.currentUserId()) // Get the UID of the current user
                .get()
                .addOnCompleteListener(new
               OnCompleteListener<DocumentSnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                       String cour = "courses";
                       DocumentSnapshot document = task.getResult();
                       if((ArrayList<String>) document.get(cour) != null){
                           courses = (ArrayList<String>) document.get(cour);
                       }
                       String name = document.getString("name");
                       String email = document.getString("email");
                       profileName.setText(name);
                       profileEmail.setText(email);
                       adapter = new CoursesAdapter(courses);
                       recyclerView.setAdapter(adapter);
                   }
               });

        // when clicker, dialog will open to add a new course
        TextView editCourses = findViewById(R.id.allCourses_edit);
        editCourses.setOnClickListener(view -> showAddCourseDialog());

        // select home icon and message icon elements from all courses layout
        ImageView homeButton = findViewById(R.id.returnToHomePage);
        ImageView messageButton = findViewById(R.id.messagingIcon);

        /**
         * Configures an onClick listener for the home button to navigate the user to the HomeActivity.
         */
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // redirect user to home page
                Intent homeIntent = new Intent(AllCoursesActivity.this, HomeActivity.class);
                startActivity(homeIntent);
            }
        });

        /**
         * Establishes an onClick listener for the message button, facilitating navigation to the MessageSearchActivity for message interactions.
         */
        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent messageIntent = new Intent(AllCoursesActivity.this, MessageSearchActivity.class);
                startActivity(messageIntent);
            }
        });
    }

    /**
     * Method to display a dialog for adding a new course.
     */
    private void showAddCourseDialog() {
        final EditText courseInput = new EditText(this);
        new AlertDialog.Builder(this)
                .setTitle("Add course")
                .setView(courseInput)
                .setPositiveButton("Add", (dialog, which) -> {
                    String courseName = courseInput.getText().toString();
                    if (!courseName.isEmpty()) {
                        courses.add(courseName);
                        adapter.notifyItemInserted(courses.size() - 1);
                        addCoursesToFirebase(courses);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    /**
     * Method to add courses to Firestore.
     * @param courses ArrayList of courses to be added.
     */
    private void addCoursesToFirebase(ArrayList<String> courses) {
        Map<String, Object> data = new HashMap<>();
        data.put("courses", courses);

        // Add a new document with a generated ID
        FirebaseFirestore.getInstance().collection("users").document(FirebaseUtil.currentUserId())
                .update(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AllCoursesActivity.this, "Courses updated successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AllCoursesActivity.this, "Failed to update courses: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * RecyclerView Adapter for displaying courses.
     */
    private static class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.ViewHolder> {
        private final ArrayList<String> localCourses;

        /**
         * Constructor for CoursesAdapter.
         * @param courses ArrayList of courses to be displayed.
         */
        CoursesAdapter(ArrayList<String> courses) {
            this.localCourses = courses;
        }

        /**
         * Called when RecyclerView needs a new RecyclerView.ViewHolder of the given type to represent an item.
         *
         * @param parent   The ViewGroup into which the new View will be added after it is bound to an adapter position.
         * @param viewType The type of the new View.
         * @return A new ViewHolder that holds a View of the given view type.
         */
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // new view for each item.
            TextView textView = new TextView(parent.getContext());
            textView.setPadding(10, 10, 10, 10);
            textView.setTextSize(20);
            return new ViewHolder(textView);
        }

        /**
         * Binds the data from the courses list to the view holder at the specified position.
         * Each course name is prefixed with a bullet point and set to a TextView within the holder.
         * Additionally, an onClick listener is set for each item in the list, providing the functionality
         * to remove a course from the list upon user confirmation.
         *
         * @param holder The ViewHolder which should be updated to represent the contents of the
         *               item at the given position in the data set.
         * @param position The position of the item within the adapter's data set.
         */
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            // getting element from the dataset at this position and replacing the contents of the view with that element
            // prefixing each course name with a bullet point
            String course = "• " + localCourses.get(position);
            holder.textView.setText(course);

            // set a onClick listener for every item of the list
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // get current position
                    int currentPosition = holder.getAdapterPosition();
                    // check if pos is  valid
                    if (currentPosition != RecyclerView.NO_POSITION) {

                        new AlertDialog.Builder(holder.itemView.getContext())
                                .setTitle("Remove course")
                                .setMessage("Do you want to remove this course?")
                                .setPositiveButton("Yes", (dialog, which) -> {
                                    // remove the clicked item from the dataset
                                    localCourses.remove(position);
                                    // notify adapter
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
         * ViewHolder class for holding the TextView for displaying course name.
         */
        @Override
        public int getItemCount() {
            return localCourses.size();
        }

        /**
         * Represents a single item view in a RecyclerView.
         * This ViewHolder holds a TextView to display course name.
         */
        private static class ViewHolder extends RecyclerView.ViewHolder {
            TextView textView; // to display course name

            /**
             * Constructs a new ViewHolder with the provided TextView.
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