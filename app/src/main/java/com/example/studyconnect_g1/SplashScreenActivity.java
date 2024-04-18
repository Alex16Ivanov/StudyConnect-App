package com.example.studyconnect_g1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {

    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *      previously being shut down then this Bundle contains the data it most
     *      recently supplied in onSaveInstanceState(Bundle).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // delayed navigation to the login page
        new Handler().postDelayed(() -> {
            // create intent from splash screen to login page
            Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
            // navigate to login page
            startActivity(intent);
            // finish the current splash screen activity to prevent returning to it
            finish();
        }, 3000);
    }
}
