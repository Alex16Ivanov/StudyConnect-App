package com.example.studyconnect_g1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studyconnect_g1.adapter.SearchUserRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

import model.User;
import utils.FirebaseUtil;

/**
 * Activity to search for users within the app.
 * Allows users to enter a search term and displays matching users in a RecyclerView.
 */
public class MessageSearchActivity extends AppCompatActivity {

    // UI components
    EditText searchInput;
    ImageButton searchButton;
    ImageButton backButton;
    ImageView homeButton;
    RecyclerView recyclerView;

    // Adapter for the RecyclerView
    SearchUserRecyclerAdapter adapter;

    TextView imageProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content view to the XML layout for this activity
        setContentView(R.layout.activity_message_search);

        // use menu bar functionality from the MenuBar class
        MenuBarActivity.getInstance().setupMenuBar(this);

        // Initialize UI components by finding them by their ID

        searchInput = findViewById(R.id.seach_username_input);
        searchButton = findViewById(R.id.search_user_btn);
        backButton = findViewById(R.id.back_btn);
        recyclerView = findViewById(R.id.search_user_recycler_view);
        homeButton = findViewById(R.id.returnToHomePage);

        // Automatically focus the search input when the activity starts
        searchInput.requestFocus();

        // Set up onClick listener for the home button to return to the HomeActivity
        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(MessageSearchActivity.this, HomeActivity.class);
            startActivity(intent);
        });

        // Set up onClick listener for the back button to return to the previous activity
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(MessageSearchActivity.this , HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        // Set up onClick listener for the search button to execute a search based on the input term
        searchButton.setOnClickListener(v -> {
            String searchTerm = searchInput.getText().toString();
            // Validate the search term to ensure it's not empty
            if(searchTerm.isEmpty() || searchTerm.length()<1){
                searchInput.setError("Invalid Username");
                return;
            }
            // Set up the RecyclerView with search results
            setupSearchRecyclerView(searchTerm);
        });
    }

    /**
     * Sets up the RecyclerView to display search results.
     * Configures a Firestore query to fetch users matching the search term.
     *
     * @param searchTerm The term to search for in user names.
     */
    void setupSearchRecyclerView(String searchTerm){
        // Query Firestore for users whose names match the search term
        Query query = FirebaseUtil.allUserCollectionReference()
                .whereGreaterThanOrEqualTo("name",searchTerm)
                .whereLessThanOrEqualTo("name",searchTerm+'\uf8ff');

        // Configure FirestoreRecyclerOptions for the adapter
        FirestoreRecyclerOptions<User> options = new FirestoreRecyclerOptions.Builder<User>()
                .setQuery(query,User.class).build();

        // Initialize the adapter and set it to the RecyclerView
        adapter = new SearchUserRecyclerAdapter(options,getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        // Start listening for database changes
        adapter.startListening();

    }

    @Override
    protected void onStart() {
        super.onStart();
        // Resume listening for Firestore updates when the activity starts
        if(adapter!=null)
            adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Stop listening for Firestore updates when the activity is not visible
        if(adapter!=null)
            adapter.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Resume listening for Firestore updates when the activity resumes from paused state
        if(adapter!=null)
            adapter.startListening();
    }
}