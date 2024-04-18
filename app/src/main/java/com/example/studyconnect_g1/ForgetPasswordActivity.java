package com.example.studyconnect_g1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

// ForgetPasswordActivity class for handling password reset requests
public class ForgetPasswordActivity extends AppCompatActivity {

    // UI element declarations for email input, send button, and back button
    private EditText email;
    private Button send, back;

    // Firebase Authentication instance
    FirebaseAuth mAuth;

    // String variable to store user email
    String strEmail;

    // onCreate method to set up the activity
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the content view from layout file
        setContentView(R.layout.activity_forget_password);

        // Link UI elements with their layout IDs
        email = findViewById(R.id.email);
        send = findViewById(R.id.sendBtn);
        back = findViewById(R.id.backBtn);

        // Initialize Firebase Auth instance
        mAuth = FirebaseAuth.getInstance();

        // Set onClickListener for the back button to navigate back to LoginActivity
        back.setOnClickListener(v -> startActivity(new Intent(ForgetPasswordActivity.this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)));

        // Set onClickListener for the send button to initiate password reset process
        send.setOnClickListener(v -> {
            strEmail = email.getText().toString().trim();
            // Check if email field is not empty before sending password reset email
            if (!TextUtils.isEmpty(strEmail)) {
                ResetPassword();
            } else {
                // Display error if email field is empty
                email.setError("Email field can't be empty");
            }
        });
    }

    // Method to handle sending of the password reset email
    private void ResetPassword() {
        // Hide the send button temporarily
        send.setVisibility(View.INVISIBLE);

        // Use Firebase Auth to send a password reset email
        mAuth.sendPasswordResetEmail(strEmail)
                .addOnSuccessListener(unused -> {
                    // Notify user of successful password reset email send and navigate back to LoginActivity
                    Toast.makeText(ForgetPasswordActivity.this, "Reset Password link has been sent to your registered Email", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                })
                .addOnFailureListener(e -> {
                    // Display error message and show the send button again on failure
                    Toast.makeText(ForgetPasswordActivity.this, "Error :- " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    send.setVisibility(View.VISIBLE);
                });
    }
}
