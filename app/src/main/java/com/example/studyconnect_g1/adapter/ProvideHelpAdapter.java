package com.example.studyconnect_g1.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studyconnect_g1.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import model.SeekEvent;
import model.User;
import utils.FirebaseUtil;

/**
 * Adapter class for managing SeekEvent items in a RecyclerView for providing help.
 */
public class ProvideHelpAdapter extends RecyclerView.Adapter<ProvideHelpAdapter.ProvideHelpViewHolder> {

    private ArrayList<SeekEvent> seekEventList; // List of SeekEvents
    private ProvideHelpItemClickListener listener; // Item click listener

    private static boolean isClickable = true; // Flag to control item clickability

    /**
     * Interface for handling click events on items in the ProvideHelpAdapter.
     */
    public interface ProvideHelpItemClickListener {
        /**
         * Handles the click event on accepting a seek help request.
         *
         * @param seekEvent       The SeekEvent being accepted
         * @param requestCreator  The User who created the seek help request
         */
        void acceptSeekHelpRequestClicked(SeekEvent seekEvent, User requestCreator);
    }

    /**
     * Constructor to initialize adapter with SeekEvent list and item click listener.
     *
     * @param seekEventList  List of SeekEvents to be displayed
     * @param listener       Item click listener for handling click events
     */
    public ProvideHelpAdapter(ArrayList<SeekEvent> seekEventList, ProvideHelpItemClickListener listener) {
        this.seekEventList = seekEventList;
        this.listener = listener;
    }

    /**
     * Sets whether items are clickable.
     *
     * @param clickable  True if items should be clickable, false otherwise
     */
    public void setClickable(boolean clickable) {
        this.isClickable = clickable;
    }

    /**
     * Inflates layout for each item in the RecyclerView.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to an adapter position
     * @param viewType The view type of the new View
     * @return         A new ProvideHelpViewHolder that holds a View with the provided layout ID
     */
    @NonNull
    @Override
    public ProvideHelpViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout for individual items
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reqest_item, parent, false);
        return new ProvideHelpViewHolder(view, listener);
    }

    /**
     * Binds data to the views within each item.
     *
     * @param holder    The ProvideHelpViewHolder to bind data to
     * @param position  The position of the item within the adapter's data set
     */
    @Override
    public void onBindViewHolder(@NonNull ProvideHelpViewHolder holder, int position) {
        SeekEvent currentSeekEvent = seekEventList.get(position); // Get current SeekEvent
        holder.bind(currentSeekEvent, isClickable); // Bind SeekEvent to ViewHolder
    }

    /**
     * Returns the total number of items in the data set.
     *
     * @return  The total number of items in the data set
     */
    @Override
    public int getItemCount() {
        return seekEventList.size(); // Return size of SeekEvent list
    }

    /**
     * ViewHolder class to hold references to views within each item.
     */
    public static class ProvideHelpViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, timeView, dayView, courseTextView;
        CircleImageView profileImageView;
        LinearLayout overlayLayout;
        Button btnAcceptRequest;
        private SeekEvent currentSeekEvent;
        private User requestCreator;

        /**
         * Constructor to initialize ViewHolder.
         *
         * @param itemView  The view contained within the RecyclerView item
         * @param listener  Item click listener for handling click events
         */
        public ProvideHelpViewHolder(@NonNull View itemView, final ProvideHelpItemClickListener listener) {
            super(itemView);

            // Initialize views within each item
            nameTextView = itemView.findViewById(R.id.nameTextView);
            timeView = itemView.findViewById(R.id.time);
            dayView = itemView.findViewById(R.id.day);
            courseTextView = itemView.findViewById(R.id.course);
            profileImageView = itemView.findViewById(R.id.profile_pic_image_view);
            overlayLayout = itemView.findViewById(R.id.overlay_layout);
            btnAcceptRequest = overlayLayout.findViewById(R.id.btnAcceptRequest);

            // Toggle overlay visibility when item clicked
            itemView.setOnClickListener(v -> {
                if (isClickable && currentSeekEvent != null) {
                    overlayLayout.setVisibility(overlayLayout.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                }
            });

            // Handle click on accept button
            btnAcceptRequest.setOnClickListener(v -> {
                if (currentSeekEvent != null) {
                    listener.acceptSeekHelpRequestClicked(currentSeekEvent, requestCreator);
                    overlayLayout.setVisibility(View.GONE);
                }
            });
        }

        /**
         * Bind SeekEvent data to views within ViewHolder.
         *
         * @param seekEvent     The SeekEvent to bind
         * @param isClickable   Flag indicating whether the item is clickable
         */
        public void bind(final SeekEvent seekEvent, boolean isClickable) {
            this.currentSeekEvent = seekEvent; // Store current SeekEvent

            // Load user details from Firebase
            FirebaseUtil.getUserDetails(seekEvent.getCreatorId()).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    requestCreator = task.getResult().toObject(User.class); // Convert Firebase document to User object
                    if (requestCreator != null) {
                        // Populate views with user and SeekEvent data
                        nameTextView.setText(requestCreator.getName());
                        String time = currentSeekEvent.getStartTimeString().get("hours") + ":" + currentSeekEvent.getStartTimeString().get("minutes");
                        time += "-" + currentSeekEvent.getEndTimeString().get("hours") + ":" + currentSeekEvent.getEndTimeString().get("minutes");
                        timeView.setText(time);
                        String day = currentSeekEvent.getDayString().substring(0, 3);
                        day = day.charAt(0) + day.substring(1, 3).toLowerCase();
                        dayView.setText(day);
                        courseTextView.setText(currentSeekEvent.getCourseName());
                        Picasso.get().load(requestCreator.getImageUrl()).into(profileImageView);

                        // Set button's clickable state based on isClickable
                        btnAcceptRequest.setEnabled(isClickable);
                    }
                }
            });
        }

    }

}
