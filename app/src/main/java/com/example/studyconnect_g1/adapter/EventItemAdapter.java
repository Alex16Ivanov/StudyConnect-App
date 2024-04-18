package com.example.studyconnect_g1.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studyconnect_g1.R;

import java.util.ArrayList;

import model.Event;

/**
 * Adapter class for managing Event items in a RecyclerView.
 */
public class EventItemAdapter extends RecyclerView.Adapter<EventItemAdapter.EventViewHolder> {

    ArrayList<Event> eventList; // List of events

    /**
     * Constructor for the EventItemAdapter.
     *
     * @param eventList  List of events to be displayed
     */
    public EventItemAdapter(ArrayList<Event> eventList) {
        this.eventList = eventList;
    }

    /**
     * Inflates the layout for the ViewHolder.
     *
     * @param parent    The ViewGroup into which the new View will be added after it is bound to an adapter position
     * @param viewType  The view type of the new View
     * @return          A new EventViewHolder that holds a View with the provided layout ID
     */
    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, parent, false);
        return new EventViewHolder(itemView);
    }

    /**
     * Binds data to the ViewHolder.
     *
     * @param holder    The EventViewHolder to bind data to
     * @param position  The position of the item within the adapter's data set
     */
    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event currentEvent = eventList.get(position);
        holder.startTime.setText((currentEvent.getStartTime()).toString());
        holder.title.setText(currentEvent.getTitle());
        holder.duration.setText(currentEvent.getStartTime().toString() + "-" + currentEvent.getEndTime().toString());
    }

    /**
     * Returns the number of items in the list.
     *
     * @return  The total number of items in the data set held by the adapter
     */
    @Override
    public int getItemCount() {
        return eventList.size();
    }

    /**
     * ViewHolder class for holding the views.
     */
    public static class EventViewHolder extends RecyclerView.ViewHolder {
        TextView startTime;
        TextView title;
        TextView duration;

        /**
         * Constructor for the ViewHolder.
         *
         * @param itemView  The view contained within the RecyclerView item
         */
        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            startTime = itemView.findViewById(R.id.startTime);
            title = itemView.findViewById(R.id.title);
            duration = itemView.findViewById(R.id.duration);
        }
    }
}
