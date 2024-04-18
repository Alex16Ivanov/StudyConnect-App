package com.example.studyconnect_g1;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.studyconnect_g1.adapter.EventItemAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import android.widget.Button;

import model.Event;
import model.Schedule;
import utils.FirebaseUtil;
import utils.TimeValidationUtil;
import utils.TimeValidationUtil.*;

/**
 * A fragment representing a single day schedule.
 * This fragment allows users to view, add, and manage events for a specific day of the week.
 * It interacts with Firebase Firestore to retrieve and store event data.
 */
public class dayScheduleFragment extends Fragment {

    private TimeValidationUtil timeValidationUtil;
    // Declare variables
    private RecyclerView dayScheduleRecyclerView;
    Schedule scheduleModel;
    private EventItemAdapter eventAdapter;
    private ArrayList<Event> eventsOfDay = new ArrayList<>();
    private DayOfWeek dayOfWeek;
    String scheduleId;
    private Button newEventBtn, addEventBtn;
    private EditText eventTitleEditText, startTimeEditText, endTimeEditText;
    private String eventTitle, startTimeText, endTimeText;

    /**
     * Default constructor.
     * Required empty public constructor.
     */
    public dayScheduleFragment() {
        // Required empty public constructor
    }

    /**
     * Constructor with day of week.
     *
     * @param dayOfWeek The day of the week for which the schedule is being displayed.
     */
    public dayScheduleFragment(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scheduleId = FirebaseUtil.getScheduleId(FirebaseUtil.currentUserId());
        getOrCreateScheduleModel();
        getEventsFromDB();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_day_schedule, container, false);
        dayScheduleRecyclerView = rootView.findViewById(R.id.scheduleRecyclerView);
        dayScheduleRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        eventAdapter = new EventItemAdapter(eventsOfDay);
        dayScheduleRecyclerView.setAdapter(eventAdapter);

        // Set OnClickListener for new event button
        newEventBtn = rootView.findViewById(R.id.newEventBtn);
        newEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow();
            }
        });

        // Get events from database
        getEventsFromDB();

        return rootView;
    }

    /**
     * Retrieves events from the database for the current day.
     */
    private void getEventsFromDB() {
        // This takes events from Firebase.
        FirebaseUtil.getEventsOfDayReference(scheduleId, dayOfWeek)
                .orderBy("startTime", com.google.firebase.firestore.Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) { //If task is successful.
                        eventsOfDay.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            try {
                                Event event = document.toObject(Event.class);
                                eventsOfDay.add(event);
                            } catch (Exception e) {
                                Log.d(ScheduleActivity.class.getSimpleName(), "error making event model object");
                            }
                        }
                    } else { //If task is not successful.
                        Toast.makeText(getActivity(), "Failed to retrieve events from Firebase.", Toast.LENGTH_SHORT).show();
                    }
                    if (eventAdapter != null) {
                        try {
                            eventAdapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            Log.d(ScheduleActivity.class.getSimpleName(), "Error notifying adaptor");
                        }
                    }
                });
    }

    /**
     * Displays a popup window for adding a new event.
     */
    public void popupWindow() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View popupWindow = inflater.inflate(R.layout.popup_layout_event, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(requireActivity());
        alertDialogBuilder.setView(popupWindow);

        // Initialize EditText fields
        eventTitleEditText = popupWindow.findViewById(R.id.popup_Title);
        startTimeEditText = popupWindow.findViewById(R.id.startTime);
        endTimeEditText = popupWindow.findViewById(R.id.endTime);

        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Add Event", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        // Set OnClickListener for Add Event button in the popup
        addEventBtn = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        addEventBtn.setOnClickListener(new CustomListener(alertDialog));
    }

    /**
     * Implements a custom {@link View.OnClickListener} for handling clicks on the Add Event button.
     * This listener retrieves inputs from EditText fields, validates the provided time range, and,
     * if valid, adds a new event to the schedule before dismissing the associated dialog.
     */
    class CustomListener implements View.OnClickListener {
        private final Dialog dialog;

        public CustomListener(Dialog dialog) {
            this.dialog = dialog;
        }

        @Override
        public void onClick(View v) {
            // Retrieve input from EditText fields
            eventTitle = eventTitleEditText.getText().toString();
            startTimeText = startTimeEditText.getText().toString();
            endTimeText = endTimeEditText.getText().toString();

            // Validate input and add event if valid
            if (validateTime(startTimeText, endTimeText)) {
                addEventToSchedule(eventTitle, startTimeText, endTimeText, dayOfWeek);
                dialog.dismiss();
            }
        } //changed.
    }

    /**
     * Adds an event to the schedule and updates the database.
     *
     * @param title      The title of the event.
     * @param startTime  The start time of the event.
     * @param endTime    The end time of the event.
     * @param day        The day of the event.
     */
    void addEventToSchedule(String title, String startTime, String endTime, DayOfWeek day) {
        scheduleModel.setScheduleId(scheduleId);
        scheduleModel.setUserId(FirebaseUtil.currentUserId());
        FirebaseUtil.getScheduleReference(scheduleId).set(scheduleModel);

        Event eventModel = new Event(title, startTime, endTime, day);
        FirebaseUtil.getEventsOfDayReference(scheduleId, day).add(eventModel)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Event Made", Toast.LENGTH_SHORT).show();
                            eventTitleEditText.setText("");
                            startTimeEditText.setText("");
                            endTimeEditText.setText("");
                        }
                    }
                });
    }

    /**
     * Retrieves or creates the schedule model.
     */
    void getOrCreateScheduleModel() {
        FirebaseUtil.getScheduleReference(scheduleId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                try {
                    scheduleModel = task.getResult().toObject(Schedule.class);
                } catch (Exception e) {
                    Log.d(ScheduleActivity.class.getSimpleName(), "error making schedule model object");
                }
                if (scheduleModel == null) {
                    scheduleModel = new Schedule(
                            scheduleId,
                            FirebaseUtil.currentUserId()
                    );
                    FirebaseUtil.getScheduleReference(scheduleId).set(scheduleModel);
                }
            }
        });
    }

    /**
     * Validates the format of start and end time strings.
     *
     * @param startTime The start time string to validate.
     * @param endTime   The end time string to validate.
     * @return True if both start and end times are in the correct format (HH:MM), false otherwise.
     */
    public boolean validateTime(String startTime, String endTime) {
        // Create maps to hold parsed time components
        Map<String, String> startTimeMap = timeValidationUtil.createTimeMap(startTime);
        Map<String, String> endTimeMap = timeValidationUtil.createTimeMap(endTime);

        // Check if start time is valid
        if (startTimeMap == null) {
            startTimeEditText.requestFocus();
            startTimeEditText.setError("Start time is invalid. Use HH:MM format.");
            return false;
        }

        // Check if end time is valid
        if (endTimeMap == null) {
            endTimeEditText.requestFocus();
            endTimeEditText.setError("End time is invalid. Use HH:MM format.");
            return false;
        }

        // Extract individual hours and minutes from start and end times
        String startHours = startTimeMap.get("hours");
        String startMinutes = startTimeMap.get("minutes");
        String endHours = endTimeMap.get("hours");
        String endMinutes = endTimeMap.get("minutes");

        // Check if start hours or minutes are invalid
        if (timeValidationUtil.isTimeComponentValid(startHours, true) || timeValidationUtil.isTimeComponentValid(startMinutes, false)) {
            startTimeEditText.requestFocus();
            startTimeEditText.setError("Start time is invalid. Use HH:MM format.");
            return false;
        }

        // Check if end hours or minutes are invalid
        if (timeValidationUtil.isTimeComponentValid(endHours, true) || timeValidationUtil.isTimeComponentValid(endMinutes, false)) {
            endTimeEditText.requestFocus();
            endTimeEditText.setError("End time is invalid. Use HH:MM format.");
            return false;
        }

        // Convert hours and minutes to integers for comparison
        int startHr = Integer.parseInt(startHours);
        int endHr = Integer.parseInt(endHours);
        int startMin = Integer.parseInt(startMinutes);
        int endMin = Integer.parseInt(endMinutes);

        // Check if start time is earlier than end time
        if (startHr > endHr || (startHr == endHr && startMin >= endMin)) {
            startTimeEditText.requestFocus();
            startTimeEditText.setError("Start time must be earlier than end time.");
            return false;
        }

        // If all validations pass, return true
        return true;
    }
}