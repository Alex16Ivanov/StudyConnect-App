package com.example.studyconnect_g1.adapter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

import model.User;

// Use MockitoJUnitRunner to enable Mockito annotations and functionalities
@RunWith(MockitoJUnitRunner.class)
public class UserAdapterTest {

    // Mock the ArrayList that will hold User objects to simulate the dataset used by the adapter
    @Mock
    private ArrayList<User> mockUserList;

    // Mock the UserItemClickListener to verify interaction callbacks
    @Mock
    private UserAdapter.UserItemClickListener mockListener;

    // Reference to the UserAdapter that will be tested
    private UserAdapter adapter;

    // Setup method to initialize necessary objects and state before each test
    @Before
    public void setUp() {
        // Initialize the adapter with mocked user list and click listener
        // This setup isolates the adapter from external dependencies
        adapter = new UserAdapter(mockUserList, mockListener);
    }

    // Test to verify that getItemCount correctly returns the size of the user list
    @Test
    public void getItemCount_ReturnsCorrectSize() {
        // Configure the mocked user list to report a size of 10
        when(mockUserList.size()).thenReturn(10);

        // Assert that getItemCount() returns the expected size
        // This test checks if the adapter correctly interacts with its data source
        assertEquals("The item count should match the mock list's size.", 10, adapter.getItemCount());
    }
}