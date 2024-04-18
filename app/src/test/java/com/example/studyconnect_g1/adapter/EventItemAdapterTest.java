package com.example.studyconnect_g1.adapter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import model.Event;

@RunWith(MockitoJUnitRunner.class)
public class EventItemAdapterTest {

    @Mock
    ArrayList<Event> mockEventList;

    @Test
    public void getItemCount() {
        // Create an instance of the adapter
        EventItemAdapter adapter = new EventItemAdapter(mockEventList);

        // Define the behavior of mockEventList
        when(mockEventList.size()).thenReturn(2);

        // Call the getItemCount method
        int itemCount = adapter.getItemCount();

        // Verify that the getItemCount method returns the expected count
        assertEquals(2, itemCount);
    }
}