package com.example.studyconnect_g1.adapter;


import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import model.SeekEvent;
import model.User;

public class ProvideHelpAdapterTest {

    @Mock
    private ProvideHelpAdapter.ProvideHelpItemClickListener mockItemClickListener;

    private ProvideHelpAdapter adapter;
    private ArrayList<SeekEvent> mockSeekEventList;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mockSeekEventList = new ArrayList<>();
        mockSeekEventList.add(mock(SeekEvent.class));
        mockSeekEventList.add(mock(SeekEvent.class));

        adapter = new ProvideHelpAdapter(mockSeekEventList, mockItemClickListener);
    }

    @Test
    public void testGetItemCount() {
        int expectedItemCount = mockSeekEventList.size();
        int actualItemCount = adapter.getItemCount();
        assert(actualItemCount == expectedItemCount);
    }

}