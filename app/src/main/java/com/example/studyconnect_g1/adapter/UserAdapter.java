package com.example.studyconnect_g1.adapter;

import android.util.Log;
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
import model.User;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    // List of user objects to be displayed in RecyclerView
    private ArrayList<User> userList;
    // Listener for handling click events on items
    private UserItemClickListener listener;
    // Flag to control the clickability of items
    private boolean isClickable = true;

    // Interface to define the callback method for when a user item is interacted with
    public interface UserItemClickListener {
        void onMessageDirectlyClicked(User user);
    }

    // Constructor to initialize the adapter with a list of users and a listener for item clicks
    public UserAdapter(ArrayList<User> userList, UserItemClickListener listener) {
        this.userList = userList;
        this.listener = listener;
    }

    // Setter to enable or disable click actions on the items
    public void setClickable(boolean clickable) {
        this.isClickable = clickable;
    }

    // Inflates the layout for each item of the RecyclerView
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item_recycler_row, parent, false);
        return new UserViewHolder(view, listener);
    }

    // Binds data to each item of the RecyclerView
    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User currentUser = userList.get(position);
        holder.bind(currentUser, isClickable);
    }

    // Returns the total number of items in the list
    @Override
    public int getItemCount() {
        return userList.size();
    }

    // ViewHolder class for user items
    public class UserViewHolder extends RecyclerView.ViewHolder {
        // UI components to display user details
        TextView nameTextView, majorTextView, coursesTextView;
        CircleImageView profileImageView;
        LinearLayout overlayLayout;
        Button btnGoToProfile, btnMessageDirectly;
        // Current user object
        private User currentUser;

        public UserViewHolder(@NonNull View itemView, final UserItemClickListener listener) {
            super(itemView);

            // Initialize UI components
            nameTextView = itemView.findViewById(R.id.nameTextView);
            majorTextView = itemView.findViewById(R.id.major_view);
            coursesTextView = itemView.findViewById(R.id.list_matching_courses);
            profileImageView = itemView.findViewById(R.id.profile_pic_image_view);
            overlayLayout = itemView.findViewById(R.id.overlay_layout);
            btnMessageDirectly = overlayLayout.findViewById(R.id.btnMessageDirectly);

            // Setup item click listener
            itemView.setOnClickListener(v -> {
                if(isClickable && currentUser != null) {
                    overlayLayout.setVisibility(overlayLayout.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                }
            });

            // Setup button click listener
            btnMessageDirectly.setOnClickListener(v -> {
                if (currentUser != null) {
                    listener.onMessageDirectlyClicked(currentUser);
                    overlayLayout.setVisibility(View.GONE);
                }
            });
        }

        // Binds user data to the item view
        public void bind(final User user, boolean isClickable) {
            Log.d("UserAdapter", "Binding user: " + user.getUserId());
            // Store the user in the currentUser field when binding
            this.currentUser = user;

            nameTextView.setText(user.getName());
            majorTextView.setText(user.getMajor());
            coursesTextView.setText(String.join(", ", user.getCourses()));
            Picasso.get().load(user.getImageUrl()).into(profileImageView);

            // Set buttons' clickable state based on isClickable
            btnMessageDirectly.setEnabled(isClickable);
        }
    }
}