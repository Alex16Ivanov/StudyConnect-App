package com.example.studyconnect_g1.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.studyconnect_g1.dayScheduleFragment;

import java.time.DayOfWeek;

/**
 * Adapter class for managing fragments in a ViewPager.
 */
public class ScheduleFragmentAdapter extends FragmentPagerAdapter {

    // Total number of tabs
    int totalTabs;

    /**
     * Constructor for ScheduleFragmentAdapter.
     *
     * @param fm         FragmentManager instance to manage fragments
     * @param totalTabs  Total number of tabs in the ViewPager
     */
    public ScheduleFragmentAdapter(@NonNull FragmentManager fm, int totalTabs) {
        super(fm);
        // Set total number of tabs for this manager
        this.totalTabs = totalTabs;
    }

    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position  Position of the fragment in the ViewPager
     * @return          The Fragment associated with the specified position
     */
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                // Return fragment 1 for position zero (tab1)
                return new dayScheduleFragment(DayOfWeek.MONDAY);
            case 1:
                // Return fragment 2 for position one (tab2)
                return new dayScheduleFragment(DayOfWeek.TUESDAY);
            case 2:
                // Return fragment 3 for position two (tab3)
                return new dayScheduleFragment(DayOfWeek.WEDNESDAY);
            case 3:
                // Return fragment 4 for position three (tab4)
                return new dayScheduleFragment(DayOfWeek.THURSDAY);
            case 4:
                // Return fragment 5 for position four (tab5)
                return new dayScheduleFragment(DayOfWeek.FRIDAY);
            case 5:
                // Return fragment 6 for position five (tab6)
                return new dayScheduleFragment(DayOfWeek.SATURDAY);
            case 6:
                // Return fragment 7 for position six (tab7)
                return new dayScheduleFragment(DayOfWeek.SUNDAY);
            default:
                // Return fragment for Monday otherwise
                return new dayScheduleFragment(DayOfWeek.MONDAY);
        }
    }

    /**
     * Return the number of views available.
     *
     * @return  Total number of tabs in the ViewPager
     */
    @Override
    public int getCount() {
        return this.totalTabs;
    }
}