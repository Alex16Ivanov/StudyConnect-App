package com.example.studyconnect_g1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.studyconnect_g1.adapter.ScheduleFragmentAdapter;
import com.google.android.material.tabs.TabLayout;

/**
 * Activity for displaying the weekly schedule.
 * This activity sets up the tab layout, view pager, and adapter for managing fragments in a ViewPager.
 * It defines tabs for each day of the week, connects the ViewPager changes to the TabLayout, and handles onClick events for buttons.
 */
public class ScheduleActivity extends AppCompatActivity {

    // Class variables
    TabLayout tabLayout;
    ViewPager viewPager;
    ImageView homeButton, messageButton;
    ScheduleFragmentAdapter adapter;

    /**
     * Sets up the schedule activity by initializing the tab layout, view pager, and adapter.
     * Defines tab layout tabs, sets up the adapter, and connects view pager changes to tab layout.
     * Also handles onClick events for buttons and sets up the menu bar.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        // Use the functionality for menu bar from MenuBarActivity class
        MenuBarActivity.getInstance().setupMenuBar(this);

        // Initializing tab layout and view pager
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        // Define tab layout tabs
        tabLayout.addTab(tabLayout.newTab().setText("Mon"));
        tabLayout.addTab(tabLayout.newTab().setText("Tue"));
        tabLayout.addTab(tabLayout.newTab().setText("Wed"));
        tabLayout.addTab(tabLayout.newTab().setText("Thu"));
        tabLayout.addTab(tabLayout.newTab().setText("Fri"));
        tabLayout.addTab(tabLayout.newTab().setText("Sat"));
        tabLayout.addTab(tabLayout.newTab().setText("Sun"));

        // Fill entire width of tab layout
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // Instantiate adapter
        adapter = new ScheduleFragmentAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setOffscreenPageLimit(1);
        viewPager.setAdapter(adapter);

        // Connect view pager changes to tab layout
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        // Connect tab layout changes to view pager
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        // Elements from the layout
        homeButton = findViewById(R.id.returnToHomePage);
        messageButton = findViewById(R.id.messagingIcon);

        // Add onClick events
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Redirect user to home page
                Intent homeIntent = new Intent(ScheduleActivity.this, HomeActivity.class);
                startActivity(homeIntent);
            }
        });

        // Messaging
        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent messageIntent = new Intent(ScheduleActivity.this, MessageSearchActivity.class);
                startActivity(messageIntent);
            }
        });
    }
}
