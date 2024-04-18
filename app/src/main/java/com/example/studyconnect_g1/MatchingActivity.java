package com.example.studyconnect_g1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.studyconnect_g1.adapter.UserAdapter;
import java.util.ArrayList;
import model.Matching;
import model.User;
import utils.AndroidUtil;

/**
 * Called when the activity is starting. This is where most initialization should go: calling setContentView(int) to
 * inflate the activity's UI, using findViewById(int) to programmatically interact with widgets in the UI, setting up
 * the RecyclerView and its adapter, and initializing onClickListeners for the home and message buttons.
 *
 * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle
 *                           contains the data it most recently supplied in onSaveInstanceState(Bundle). Otherwise, it is null.
 */
public class MatchingActivity extends AppCompatActivity {

    public ArrayList<User> userList = new ArrayList<>();
    RecyclerView recyclerView;
    UserAdapter adapter;
    public ImageView homeButton, messageButton;
    Matching matchingLogic;

    /**
     * Sets up the MatchingActivity by initializing the RecyclerView, adapter, and buttons.
     * Creates an instance of the Matching class to handle matching logic.
     * Defines onClick listeners for homeButton and messageButton.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching);

        recyclerView = findViewById(R.id.recyclerView123);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MenuBarActivity.getInstance().setupMenuBar(this);

        adapter = new UserAdapter(userList, user -> {
            if (user == null || user.getUserId() == null) {
                Toast.makeText(MatchingActivity.this, "User information is incomplete.", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(MatchingActivity.this, ChatActivity.class);
            AndroidUtil.passUserModelAsIntent(intent, user); // Original method call to pass user model
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);

        homeButton = findViewById(R.id.returnToHomePage);
        messageButton = findViewById(R.id.messagingIcon);

        matchingLogic = new Matching(this, adapter); // Initialize Matching logic handler
        matchingLogic.initiate(); // Begin the matching process with the separated logic

        setupOnClickListeners();
    }

    /**
     * Sets up onClick listeners for the homeButton and messageButton.
     * Redirects the user to the HomeActivity when homeButton is clicked.
     * Starts the MessageSearchActivity when messageButton is clicked.
     */
    private void setupOnClickListeners() {
        homeButton.setOnClickListener(view -> { // Setting onClickListener for homeButton
            Intent homeIntent = new Intent(MatchingActivity.this, HomeActivity.class); // Creating Intent for HomeActivity
            startActivity(homeIntent); // Starting HomeActivity
        });

        messageButton.setOnClickListener(v -> { // Setting onClickListener for messageButton
            Intent messageIntent = new Intent(MatchingActivity.this, MessageSearchActivity.class); // Creating Intent for MessageSearchActivity
            startActivity(messageIntent); // Starting MessageSearchActivity
        });
    }
}