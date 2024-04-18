package com.example.studyconnect_g1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


import model.User;
import utils.FirebaseUtil;

// Define the RegisterActivity class that extends AppCompatActivity
public class RegisterActivity extends AppCompatActivity {

    // Declare variables for UI elements and Firebase authentication
    private EditText email, password, name;
    private Button register;
    private FirebaseAuth mAuth;
    private RadioButton seekHelp;
    private RadioButton provideHelp;

    public User user;

    /**
     * Getter method to retrieve the FirebaseAuth instance.
     *
     * @return The FirebaseAuth instance.
     */
    public FirebaseAuth getAuth() {
        return mAuth;
    }

    /**
     * The onCreate method is called when the activity is starting.
     * It initializes UI elements, sets up click listeners, and retrieves FirebaseAuth instance.
     *
     * @param savedInstanceState A Bundle object containing the activity's previously saved state.
     */
    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the content view to the activity_register layout
        setContentView(R.layout.activity_register);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);
        name = findViewById(R.id.username);
        seekHelp = findViewById(R.id.seek_help_radio);
        provideHelp = findViewById(R.id.provide_help_radio);
        mAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve text from input fields
                String txtEmail = email.getText().toString();
                String txtPassword = password.getText().toString();
                String txtName = name.getText().toString();

                if (TextUtils.isEmpty(txtEmail) || TextUtils.isEmpty(txtPassword)){
                    Toast.makeText(RegisterActivity.this, "Empty credentials!", Toast.LENGTH_SHORT).show();
                } else if (txtPassword.length() < 6){
                    // Validate password length
                    Toast.makeText(RegisterActivity.this, "Password too short!", Toast.LENGTH_SHORT).show();
                } else {
                    registerUser(txtEmail , txtPassword, txtName);
                }
            }
        });
    }

    /**
     * The registerUser method registers a new user with the provided email, password, and name.
     * It uses FirebaseAuth to create a new user account and handles the registration process.
     *
     * @param email    The email address of the user.
     * @param password The password for the user account.
     * @param name     The name of the user.
     */
    public void registerUser(final String email, String password, String name) {

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>(){
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    // Notify user of successful registration
                    Toast.makeText(RegisterActivity.this, "Register Successful", Toast.LENGTH_SHORT).show();
                    // Initialize the user object with email, name, and user ID
                    user = new User(email, name, FirebaseUtil.currentUserId());
                    // Determine user type based on radio button selection
                    radioButtonChoice(user);
                    // Register user details in Cloud Firestore
                    registerInCloudFirestore();
                } else {
                    // Notify user of registration failure
                    Toast.makeText(RegisterActivity.this, "Register failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * The registerInCloudFirestore method saves user details to Cloud Firestore.
     * It uses FirebaseUtil to access the current user's Firestore document and sets user details.
     * Upon completion, it notifies the user and navigates to the ProfileActivity.
     */
    private void registerInCloudFirestore(){
        FirebaseUtil.currentUserDetails().set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    // Notify user of successful Firestore registration and navigate to ProfileActivity
                    Toast.makeText(RegisterActivity.this, "Registered", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this , ProfileActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    /**
     * The radioButtonChoice method assigns a type to the user based on radio button selection.
     * It sets the user type to "SeekHelp" if the seekHelp radio button is checked,
     * "ProvideHelp" if the provideHelp radio button is checked, and "Both" if neither is checked.
     *
     * @param user The User object for which the type is to be determined.
     */
    public void radioButtonChoice(User user){
        if(seekHelp.isChecked()){
            user.setType("SeekHelp");
        } else if(provideHelp.isChecked()) {
            user.setType("ProvideHelp");
        } else {
            // This branch may never be executed as it lacks a condition where neither is checked
            user.setType("Both");
        }
    }
}
