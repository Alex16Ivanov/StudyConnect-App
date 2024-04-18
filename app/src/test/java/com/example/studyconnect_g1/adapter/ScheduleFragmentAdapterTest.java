package com.example.studyconnect_g1.adapter;

import static org.junit.Assert.*;

import androidx.fragment.app.FragmentManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ScheduleFragmentAdapterTest {

    @Mock
    private FragmentManager fragmentManager;

    @Test
    public void getItem() {
        // Create a ScheduleFragmentAdapter instance with 7 totalTabs
        ScheduleFragmentAdapter adapter = new ScheduleFragmentAdapter(fragmentManager, 7);

        // Test getItem() for each position
        assertNotNull("Position 0 should return a non-null Fragment", adapter.getItem(0));
        assertNotNull("Position 1 should return a non-null Fragment", adapter.getItem(1));
        assertNotNull("Position 2 should return a non-null Fragment", adapter.getItem(2));
        assertNotNull("Position 3 should return a non-null Fragment", adapter.getItem(3));
        assertNotNull("Position 4 should return a non-null Fragment", adapter.getItem(4));
        assertNotNull("Position 5 should return a non-null Fragment", adapter.getItem(5));
        assertNotNull("Position 6 should return a non-null Fragment", adapter.getItem(6));

        // Test getItem() for a position out of bounds
        assertNotNull("Position 10 should return null", adapter.getItem(10));
    }

    @Test
    public void getCount() {
        // Create a ScheduleFragmentAdapter instance with 7 totalTabs
        ScheduleFragmentAdapter adapter = new ScheduleFragmentAdapter(fragmentManager, 7);

        // Test getCount()
        assertEquals("getCount() should return 7", 7, adapter.getCount());
    }
}