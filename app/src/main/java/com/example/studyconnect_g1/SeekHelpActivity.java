package com.example.studyconnect_g1;

import static java.lang.Integer.parseInt;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import model.SeekEvent;
import utils.FirebaseUtil;
import utils.TimeValidationUtil;

/**
 * SeekHelpActivity allows users to request help for specific courses at given times.
 * Users can add up to 3 requests, each displaying the requested course and time in a box format.
 * The activity also displays previously requested help events retrieved from Firestore.
 */
public class SeekHelpActivity extends AppCompatActivity {

    private TimeValidationUtil timeValidationUtil;
    ImageView homeButton, messageButton;
    Button requestHelpBtn, reqHelpBtn;
    EditText
            courseTitleEditText, dateEditText,
            startTimeEditText, endTimeEditText;
    TextView
            noReqText, reqCourse, reqTime, reqAccept,
            reqCourse2, reqTime2, reqAccept2,
            reqCourse3, reqTime3, reqAccept3;
    RelativeLayout requestBox, requestBox2,
            requestBox3, textBox;
    String courseTitle, dateText, startTimeText, endTimeText, timeString;
    String[] daysArray = new String[]{ "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    private int reqBoxCount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seek_help);
        timeValidationUtil = new TimeValidationUtil();

        // use the functionality for menu bar from MenuBarActivity class
        MenuBarActivity.getInstance().setupMenuBar(this);

        // elements from the layout
        homeButton = findViewById(R.id.returnToHomePage);
        messageButton = findViewById(R.id.messagingIcon);
        requestHelpBtn = findViewById(R.id.requestHelpBtn);

        // Initialize the RelativeLayout and TextView components for the main activity
        requestBox = findViewById(R.id.request_box);
        requestBox2 = findViewById(R.id.request_box2);
        requestBox3 = findViewById(R.id.request_box3);
        textBox = findViewById(R.id.seek_help_requests);
        noReqText = findViewById(R.id.noRequests_text);
        reqCourse = findViewById(R.id.req1_course);
        reqTime = findViewById(R.id.req1_time);
        reqAccept = findViewById(R.id.req1_accept);
        reqCourse2 = findViewById(R.id.req2_course);
        reqTime2 = findViewById(R.id.req2_time);
        reqAccept2 = findViewById(R.id.req2_accept);
        reqCourse3 = findViewById(R.id.req3_course);
        reqTime3 = findViewById(R.id.req3_time);
        reqAccept3 = findViewById(R.id.req3_accept);

        /**
         * Initiates the retrieval of seek events specific to the current user from Firebase.
         * The results are processed through a callback, where successful retrieval leads to UI updates
         * and errors are logged appropriately.
         */
        FirebaseUtil.retrieveSeekEventsForCurrentUser(new FirebaseUtil.SeekEventsCallback() {
            /**
             * Callback method invoked upon successful retrieval of seek events from Firebase.
             * This method updates the UI with the retrieved seek events.
             *
             * @param seekEvents The list of seek events associated with the current user.
             */
            @Override
            public void onSeekEventsRetrieved(List<SeekEvent> seekEvents) {
                // Handle successful retrieval, maybe update UI
                handleFilteredSeekEvents(seekEvents);
            }

            /**
             * Callback method invoked when an error occurs during the retrieval of seek events.
             * This method logs the encountered error.
             *
             * @param e The exception thrown during the retrieval process.
             */
            @Override
            public void onError(Exception e) {
                // Handle error
                Log.e("Firestore Query", "Error getting documents: ", e);
            }
        });

        /**
         * Sets an onClick listener for the home button to navigate the user to the HomeActivity.
         */
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // redirect user to home page
                Intent homeIntent = new Intent(SeekHelpActivity.this, HomeActivity.class);
                startActivity(homeIntent);
            }
        });

        /**
         * Establishes an onClick listener for the message button, directing the user to the MessageSearchActivity.
         */
        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent messageIntent = new Intent(SeekHelpActivity.this, MessageSearchActivity.class);
                startActivity(messageIntent);
            }
        });

        /**
         * Configures an onClick listener for the request help button. When clicked, it checks if the maximum number
         * of help requests has been reached. If not, it triggers a popup window for further action. If the maximum
         * is reached, it displays a toast message indicating the limit.
         */
        requestHelpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check if max num of requests has been reached
                if (reqBoxCount >= 3) {
                    Toast.makeText(
                            SeekHelpActivity.this,
                            "Maximum number of requests reached",
                            Toast.LENGTH_SHORT
                    ).show();
                    return;
                }
                popupWindow();
            }
        });
    } //changed.

    /**
     * Creates and displays a dialog window (popup) containing input fields for requesting help.
     */
    public void popupWindow() {
        LayoutInflater inflater = LayoutInflater.from(SeekHelpActivity.this);
        View popupWindow = inflater.inflate(R.layout.popup_layout, null);

        // encapsulate popupWindow within a scrollable view
        ScrollView scrollView = new ScrollView(SeekHelpActivity.this);
        scrollView.addView(popupWindow);

        // build alert dialog in scrollable view
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SeekHelpActivity.this);
        alertDialogBuilder.setView(scrollView);

        // editext variables on popup
        courseTitleEditText = popupWindow.findViewById(R.id.popup_courseCode);
        dateEditText = popupWindow.findViewById(R.id.daysOfWeek);
        startTimeEditText = popupWindow.findViewById(R.id.startTime);
        endTimeEditText = popupWindow.findViewById(R.id.endTime);

        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Request Help", new DialogInterface.OnClickListener() {
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
        // positive dialog button event
        reqHelpBtn = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        reqHelpBtn.setOnClickListener(new CustomListener(alertDialog));
    }

    /**
     * The CustomListener class implements the View.OnClickListener interface to handle click events
     * on the dialog window for input field validation and request submission.
     * It is used in the SeekHelpActivity to validate user input and submit help requests.
     */
    class CustomListener implements View.OnClickListener {
        private final Dialog dialog;

        /**
         * Constructor to initialize the CustomListener with the dialog window.
         * @param dialog The dialog window where input fields are displayed.
         */
        public CustomListener(Dialog dialog) {
            this.dialog = dialog;
        }

        /**
         * Handles click events when the "Request Help" button is clicked on the dialog window.
         * Validates user input and submits help requests if all fields are valid.
         * @param v The view that was clicked (ignored in this implementation).
         */
        @Override
        public void onClick(View v) {
            courseTitle = courseTitleEditText.getText().toString();
            dateText = dateEditText.getText().toString();
            startTimeText = startTimeEditText.getText().toString();
            endTimeText = endTimeEditText.getText().toString();
            timeString = "Time:  " + dateText + "     " + startTimeText + " - " + endTimeText;

            // Check if all input fields are valid and the maximum number of requests hasn't been reached
            if (validatecourseTitle(courseTitle) && validateDate(dateText)
                    && validateTime(startTimeText, endTimeText) && reqBoxCount < 3) {

                Toast.makeText(SeekHelpActivity.this, "Request Made", Toast.LENGTH_SHORT).show();

                ViewGroup.LayoutParams textBoxParams = textBox.getLayoutParams();
                textBoxParams.height = 200;
                textBox.setLayoutParams(textBoxParams);
                noReqText.setText("Requests");

                RelativeLayout[] requestBoxes = { requestBox, requestBox2, requestBox3 };
                TextView[] requestCourses = { reqCourse, reqCourse2, reqCourse3 };
                TextView[] requestTimes = { reqTime, reqTime2, reqTime3 };

                boolean boxAdded = false;
                // Iterate over request boxes to find the first hidden box and add the new request
                for (int i = 0; i < requestBoxes.length; i++) {

                    if (requestBoxes[i].getVisibility() == View.GONE) {
                        addRequestBox(courseTitle, timeString, requestCourses[i], requestTimes[i], requestBoxes[i]);
                        reqBoxCount++;
                        boxAdded = true;
                        break;
                    }
                }

                if (!boxAdded) { // If no box was added, display a message indicating the maximum number of requests has been reached
                    Toast.makeText(SeekHelpActivity.this, "Maximum number of requests reached", Toast.LENGTH_SHORT).show();
                }

                Map<String, String> startTimeMap = new HashMap<>();
                startTimeMap.put("hours", startTimeText.split(":")[0]);
                startTimeMap.put("minutes", startTimeText.split(":")[1]);

                Map<String, String> endTimeMap = new HashMap<>();
                endTimeMap.put("hours", endTimeText.split(":")[0]);
                endTimeMap.put("minutes", endTimeText.split(":")[1]);

                FirebaseUtil.addSeekEventToFirestore(
                        courseTitle,                 // e.g., "Calculus"
                        dateText,                   // e.g., "MONDAY"
                        startTimeMap,               // e.g., {"hours": "15", "minutes": "30"}
                        endTimeMap,                 // e.g., {"hours": "16", "minutes": "30"}
                        FirebaseUtil.currentUserId()
                );
                dialog.dismiss();
            }
        }
    }

    /**
     * The addRequestBox method adds details as a box that displays on the activity.
     * It sets the course title, time, and visibility of the request box.
     *
     * @param courseTitle The title of the course for the request.
     * @param timeString  The string representation of the time for the request.
     * @param course      The TextView component for displaying the course title.
     * @param time        The TextView component for displaying the time.
     * @param box         The RelativeLayout container for the request box.
     */
    private void addRequestBox(
            String courseTitle, String timeString,
            TextView course, TextView time, RelativeLayout box) {
        course.setText(courseTitle);
        time.setText(timeString);
        ViewGroup.LayoutParams reqBoxParams = box.getLayoutParams();
        box.setVisibility(View.VISIBLE);
    }

    /**
     * The handleFilteredSeekEvents method updates the UI based on the filtered seek events.
     * It updates the visibility of the request text and box based on the events retrieved.
     *
     * @param userSeekEvents The list of SeekEvent objects filtered for the current user.
     */
    private void handleFilteredSeekEvents(List<SeekEvent> userSeekEvents) {
        // Update the visibility of the noReqText and the textBox depending on whether there are events
        if (userSeekEvents.isEmpty()) {
            // No seek events found, show the "No Requests" message
            textBox.setVisibility(View.VISIBLE);
            noReqText.setVisibility(View.VISIBLE);
            noReqText.setText("No Requests");
        } else {
            // Seek events found, hide the "No Requests" message and display the request boxes
            ViewGroup.LayoutParams textBoxParams = textBox.getLayoutParams();
            textBoxParams.height = 200;
            textBox.setLayoutParams(textBoxParams);
            noReqText.setText("Requests");}

        // Clear the request boxes first
        requestBox.setVisibility(View.GONE);
        requestBox2.setVisibility(View.GONE);
        requestBox3.setVisibility(View.GONE);
        List<RelativeLayout> requestBoxes = new ArrayList<>();
        requestBoxes.add(requestBox);
        requestBoxes.add(requestBox2);
        requestBoxes.add(requestBox3);
        List<TextView> requestCourses = new ArrayList<>();
        requestCourses.add(reqCourse);
        requestCourses.add(reqCourse2);
        requestCourses.add(reqCourse3);
        List<TextView> requestTimes = new ArrayList<>();
        requestTimes.add(reqTime);
        requestTimes.add(reqTime2);
        requestTimes.add(reqTime3);

        for (int i = 0; i < userSeekEvents.size() && i < 3; i++) {
            SeekEvent event = userSeekEvents.get(i); // Get the current event
            String courseTitle = event.getCourseName(); // Get the course title
            String startTime = "Not provided", endTime = "Not provided"; // Default start and end time values

            Map<String, String> startTimeMap = event.getStartTimeString();
            if (startTimeMap != null && startTimeMap.get("hours") != null && startTimeMap.get("minutes") != null) {
                startTime = startTimeMap.get("hours") + ":" + startTimeMap.get("minutes"); // Construct start time string if details are present
            }

            Map<String, String> endTimeMap = event.getEndTimeString();
            if (endTimeMap != null && endTimeMap.get("hours") != null && endTimeMap.get("minutes") != null) {
                endTime = endTimeMap.get("hours") + ":" + endTimeMap.get("minutes"); // Construct end time string if details are present
            }

            String timeString = "Time:  " + event.getDayString() + "     " + startTime + " - " + endTime; // Construct the full time string
            addRequestBox(courseTitle, timeString, requestCourses.get(i), requestTimes.get(i), requestBoxes.get(i)); // Display the request in the UI
        }
    }

    /**
     * Validates the course title.
     *
     * @param courseTitle The course title to validate.
     * @return True if the course title is valid, otherwise false.
     */
    public boolean validatecourseTitle(String courseTitle) {
        // check if text is empty
        if (courseTitle.isEmpty()) {
            courseTitleEditText.requestFocus();
            courseTitleEditText.setError("Course code cannot be empty");
            return false;
        }
        // check if course code is of length 5
        if (courseTitle.length() > 30) {
            courseTitleEditText.setError("Too long");
        }
        return true;
    }

    /**
     * Validates the date.
     *
     * @param date The date to validate.
     * @return True if the date is valid, otherwise false.
     */
    public boolean validateDate(String date) {
        if (date.isEmpty()) {
            dateEditText.requestFocus();
            dateEditText.setError("Date cannot be empty");
            return false;
        }
        // bool for day of week
        boolean validDate = false;
        for (String day : daysArray) {
            if (date.equalsIgnoreCase(day)) {
                validDate = true;
                break;
            }
        }
        if (!validDate) {
            dateEditText.requestFocus();
            dateEditText.setError("Should be day of the week");
            return false;
        }
        return true;
    }

    /**
     * Validates the start and end time.
     *
     * @param startTime The start time to validate.
     * @param endTime   The end time to validate.
     * @return True if the times are valid, otherwise false.
     */
    public boolean validateTime(String startTime, String endTime) {
        Map<String, String> startTimeMap = timeValidationUtil.createTimeMap(startTime);
        Map<String, String> endTimeMap = timeValidationUtil.createTimeMap(endTime);

        // Check if the startTimeMap is null
        if (startTimeMap == null) {
            startTimeEditText.requestFocus();
            startTimeEditText.setError("Start time is invalid. Use HH:MM format.");
            return false;
        }

        // Check if the endTimeMap is null
        if (endTimeMap == null) {
            endTimeEditText.requestFocus();
            endTimeEditText.setError("End time is invalid. Use HH:MM format.");
            return false;
        }

        // Extract parts of the time from the maps
        String startHours = startTimeMap.get("hours");
        String startMinutes = startTimeMap.get("minutes");
        String endHours = endTimeMap.get("hours");
        String endMinutes = endTimeMap.get("minutes");

        if (timeValidationUtil.isTimeComponentValid(startHours, true) || timeValidationUtil.isTimeComponentValid(startMinutes, false)) {
            startTimeEditText.requestFocus();
            startTimeEditText.setError("Start time is invalid. Use HH:MM format.");
            return false;
        }

        if (timeValidationUtil.isTimeComponentValid(endHours, true) || timeValidationUtil.isTimeComponentValid(endMinutes, false)) {
            endTimeEditText.requestFocus();
            endTimeEditText.setError("End time is invalid. Use HH:MM format.");
            return false;
        }

        int startHr = parseInt(startHours);
        int endHr = parseInt(endHours);
        int startMin = parseInt(startMinutes);
        int endMin = parseInt(endMinutes);

        if (startHr > endHr || (startHr == endHr && startMin >= endMin)) {
            startTimeEditText.requestFocus();
            startTimeEditText.setError("Start time must be earlier than end time.");
            return false;
        }
        return true;
    }
}
