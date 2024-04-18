package com.example.studyconnect_g1;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;

// LoginActivity class that manages user login functionality
public class LoginActivity extends AppCompatActivity {

    // UI element declarations
    private EditText email, password;
    private Button login, registerUser, forgotPassword;

    // Firebase authentication instance
    private FirebaseAuth mAuth;

    /**
     * Sets up the LoginActivity UI elements and onClick listeners.
     */
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the content view from layout file
        setContentView(R.layout.activity_login);

        // Link UI elements with their layout IDs
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.loginBtn);
        registerUser = findViewById(R.id.regBtn);
        forgotPassword = findViewById(R.id.frgBtn);

        // Initialize Firebase Auth instance
        mAuth = FirebaseAuth.getInstance();

        // Set onClickListener for the register button to navigate to RegisterActivity
        registerUser.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)));

        // Set onClickListener for the forgot password button to navigate to ForgetPasswordActivity
        forgotPassword.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)));

        // Set onClickListener for the login button to attempt login with entered credentials
        login.setOnClickListener(v -> {
            String txt_email = email.getText().toString();
            String txt_password = password.getText().toString();

            // Check for empty credentials and show a toast message if any
            if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)){
                Toast.makeText(LoginActivity.this, "Empty Credentials!", Toast.LENGTH_SHORT).show();
            } else {
                // Call loginUser method with the entered credentials
                loginUser(txt_email, txt_password);
            }
        });
    }

    /**
     * Attempts to log in the user with Firebase Authentication.
     * Notifies the user of successful login and navigates to HomeActivity upon success.
     * Notifies the user of login failure and displays an error message upon failure.
     */
    private void loginUser(String email, String password) {
        // Attempt sign-in with email and password
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                // Notify the user of successful login and navigate to HomeActivity
                Toast.makeText(LoginActivity.this, "Successful Login", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }).addOnFailureListener(e -> {
            // Notify the user of login failure and display error message
            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }
}
