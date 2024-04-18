package com.example.studyconnect_g1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studyconnect_g1.adapter.ProvideHelpAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Objects;

import model.Event;
import model.Schedule;
import model.SeekEvent;
import model.User;
import utils.FirebaseUtil;



    public class ProvideHelpActivity extends AppCompatActivity {

        private final ArrayList<SeekEvent> eventList = new ArrayList<>();
        private ProvideHelpAdapter adapter;
        private User currentUser;
        ImageView homeButton;
        ImageView messageButton;
        TextView noReqText;
        TextView allPHCourses;
        TextView coursesPHText;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_provide_help);
            RecyclerView recyclerView = findViewById(R.id.provideHelpRecyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            // This line utilizes the MenuBarActivity to set up the menu bar for the current context
            MenuBarActivity.getInstance().setupMenuBar(this);

            adapter = new ProvideHelpAdapter(eventList, new ProvideHelpAdapter.ProvideHelpItemClickListener() {
                @Override
                public void acceptSeekHelpRequestClicked(SeekEvent seekEvent, User requestCreator) {
                    if (requestCreator == null || requestCreator.getUserId() == null) {
                        Toast.makeText(ProvideHelpActivity.this, "User information is incomplete.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    addRequestToSchedule(seekEvent, requestCreator);
                }
            });

            recyclerView.setAdapter(adapter);
            retrieveCurrentUser();

            // elements from the layout
            homeButton = findViewById(R.id.returnToHomePage);
            messageButton = findViewById(R.id.messagingIcon);
            allPHCourses = findViewById(R.id.provide_help_all_courses);
            coursesPHText = findViewById(R.id.provide_help_courses_text);
            noReqText = findViewById(R.id.noRequests_text);

            // add onClick events
            homeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // redirect user to home page
                    Intent homeIntent = new Intent(ProvideHelpActivity.this, HomeActivity.class);
                    startActivity(homeIntent);
                }
            });

            // messaging go to redirect
            messageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent messageIntent = new Intent(ProvideHelpActivity.this, MessageSearchActivity.class);
                    startActivity(messageIntent);
                }
            });

            allPHCourses.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    // redirect user to All courses page
                    Intent allCoursesIntent = new Intent(ProvideHelpActivity.this, AllPHCoursesActivity.class);
                    startActivity(allCoursesIntent);
                }
            });
        }

        /**
         * Retrieves the current user data from Firebase Firestore.
         * If successful, sets up the ProvideHelpAdapter and retrieves provide help requests for the current user.
         * Also displays the menu bar and handles onClick events for buttons.
         */
        private void retrieveCurrentUser() {
            String currentUserId = FirebaseUtil.currentUserId();
            if (currentUserId != null) {
                FirebaseUtil.allUserCollectionReference().document(currentUserId).get().addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        currentUser = task.getResult().toObject(User.class);

                        retrieveProvideHelpRequests();
                        adapter.setClickable(true);
                        displayProvideHelpCourses(currentUser);
                    } else {
                        Toast.makeText(ProvideHelpActivity.this, "Failed to retrieve current user data.", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(ProvideHelpActivity.this, "No current user ID found.", Toast.LENGTH_SHORT).show();
            }
        }

        /**
         * Retrieves provide help requests for the current user from Firebase Firestore.
         * Updates the eventList and notifies the adapter of changes.
         */
        private void retrieveProvideHelpRequests() {
            if (currentUser.getProvideHelpCourses() == null || currentUser.getProvideHelpCourses().isEmpty())
                return;
            FirebaseUtil.allSeekEventsCollectionReference()
                    .whereIn("courseName", currentUser.getProvideHelpCourses())
                    .get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {

                            eventList.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                SeekEvent seekEvent = document.toObject(SeekEvent.class);
                                if (!Objects.equals(seekEvent.getCreatorId(), currentUser.getUserId())) {
                                    eventList.add(seekEvent);
                                }

                            }
                            adapter.notifyDataSetChanged();
                            if (eventList.isEmpty()) noReqText.setText("No Requests");
                            else noReqText.setText("Requests");
                        } else {
                            Toast.makeText(ProvideHelpActivity.this, "Failed to retrieve request events from Firebase.", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

        /**
         * Displays the courses for which the current user can provide help.
         * Constructs the text and sets it to the corresponding TextView.
         *
         * @param user The User object containing the user's data.
         */
        private void displayProvideHelpCourses(User user) {
            // retrieve list of courses that the user can provide help for
            ArrayList<String> courses = user.getProvideHelpCourses();

            // check whether courses is empty
            if (courses != null) {
                // string builder to construct text for the courses
                StringBuilder coursesText = new StringBuilder();

                // add courses to the coursesText
                for (int i = 0; i < courses.size() && i < 3; i++) {
                    coursesText.append("• ").append(courses.get(i)).append("\n");
                }
                // set the text of courses to the TextView
                coursesPHText.setText(coursesText.toString());
            } else {
                // if there are no courses, set empty string
                coursesPHText.setText("");
            }

        }

        /**
         * Adds a seek help request to the current user's schedule after it has been accepted.
         * Creates schedule models, event titles, and event models for both the accepted user and the request creator.
         * Adds events to their respective schedules and deletes the request from the database upon successful addition.
         *
         * @param seekEvent      The SeekEvent object representing the seek help request.
         * @param requestCreator The User object representing the creator of the seek help request.
         */
        void addRequestToSchedule(SeekEvent seekEvent, User requestCreator) {

            // Create schedule models for both the accepted user and the creator
            Schedule scheduleAcceptedModel = new Schedule(FirebaseUtil.getScheduleId(FirebaseUtil.currentUserId()), FirebaseUtil.currentUserId());
            Schedule scheduleCreatorModel = new Schedule(FirebaseUtil.getScheduleId(requestCreator.getUserId()), requestCreator.getUserId());

            // Set chatroom references for both users
            FirebaseUtil.getChatroomReference(FirebaseUtil.getScheduleId(FirebaseUtil.currentUserId())).set(scheduleAcceptedModel);
            FirebaseUtil.getChatroomReference(FirebaseUtil.getScheduleId(requestCreator.getUserId())).set(scheduleCreatorModel);

            // Create event titles for both users
            String titleAccepted = seekEvent.getCourseName() + ", " + requestCreator.getName();
            String titleCreator = seekEvent.getCourseName() + ", " + currentUser.getName();

            // Create event models for both users
            String m = "minutes";
            String h = "hours";
            Event eventAcceptedModel = new Event(titleAccepted, seekEvent.getStartTimeString().get(h) + ":" + seekEvent.getStartTimeString().get(m),
                    seekEvent.getEndTimeString().get(h) + ":" + seekEvent.getEndTimeString().get(m),
                    DayOfWeek.valueOf(seekEvent.getDayString().toUpperCase()));
            Event eventCreatorModel = new Event(titleCreator, seekEvent.getStartTimeString().get(h) + ":" + seekEvent.getStartTimeString().get(m),
                    seekEvent.getEndTimeString().get(h) + ":" + seekEvent.getEndTimeString().get(m),
                    DayOfWeek.valueOf(seekEvent.getDayString().toUpperCase()));

            // Add event to the accepted user's schedule
            FirebaseUtil.getEventsOfDayReference(scheduleAcceptedModel.getScheduleId(), DayOfWeek.valueOf(seekEvent.getDayString().toUpperCase())).add(eventAcceptedModel)
                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if (task.isSuccessful()) {
                                // Add event to the creator's schedule
                                FirebaseUtil.getEventsOfDayReference(scheduleCreatorModel.getScheduleId(), DayOfWeek.valueOf(seekEvent.getDayString().toUpperCase())).add(eventCreatorModel)
                                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                                if (task.isSuccessful()) {
                                                    // Delete the request
                                                    deleteRequest(seekEvent);
                                                    Toast.makeText(ProvideHelpActivity.this, "Request Accepted", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                        }
                    });
        }

        /**
         * Deletes a seek help request from the database.
         * Searches for the request based on the creator ID and course name,
         * then deletes the corresponding document.
         *
         * @param seekEvent The SeekEvent object representing the seek help request to be deleted.
         */
        void deleteRequest(SeekEvent seekEvent) {
            FirebaseUtil.allSeekEventsCollectionReference().whereEqualTo("creatorId", seekEvent.getCreatorId())
                    .whereEqualTo("courseName", seekEvent.getCourseName())
                    .get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {

                            for (DocumentSnapshot document : task.getResult()) {
                                FirebaseUtil.allSeekEventsCollectionReference().document(document.getId()).delete()
                                        .addOnSuccessListener(aVoid -> {
                                            // Document successfully deleted
                                            Log.d("DeleteUser", "DocumentSnapshot successfully deleted!");
                                        })
                                        .addOnFailureListener(e ->
                                            // Error occurred
                                            Log.w("DeleteUser", "Error deleting document", e)
                                        );
                            }
                        }
                    });
        }
    }
