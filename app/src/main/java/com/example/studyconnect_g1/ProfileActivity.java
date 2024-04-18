package com.example.studyconnect_g1;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.User;
import utils.FirebaseUtil;

public class ProfileActivity extends AppCompatActivity {

    public User user = new User();

    private static final int PICK_IMAGE_REQUEST = 1;

    private StorageReference mStorageRef;
    private ImageView profileImageView;
    private Uri imageUri;
    private ActivityResultLauncher<String> mGetContent;

    String us = "users";
    String im = "images/";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ImageView homeButton = findViewById(R.id.returnToHomePage);
        TextView logoutButton = findViewById(R.id.profile_logout_button);
        Button delete = findViewById(R.id.deleteUserBtn);
        TextView addDescription = findViewById(R.id.profile_add_description);
        TextView profileDescription = findViewById(R.id.profile_bio_text);
        EditText majorDescription = findViewById(R.id.major_description);
        EditText degreeDescription = findViewById(R.id.degree_description);
        TextView nameDescription = findViewById(R.id.profile_name);
        TextView emailDescription = findViewById(R.id.profile_email);
        TextView allCourses = findViewById(R.id.profile_courses);
        TextView coursesText = findViewById(R.id.profile_courses_text);
        TextView addImage = findViewById(R.id.addImage);
        mStorageRef = FirebaseStorage.getInstance().getReference();

        profileImageView = findViewById(R.id.profile_pic_image_view2);

        // use the functionality for menu bar from MenuBarActivity class
        MenuBarActivity.getInstance().setupMenuBar(this);

        // put the values from the firebase to the user profile
        FirebaseFirestore.getInstance()
                .collection(us)
                .document(FirebaseUtil.currentUserId()) // Get the UID of the current user
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // retrieve user profile data from Firestore
                        String description = documentSnapshot.getString("description");
                        String major = documentSnapshot.getString("major");
                        String name = documentSnapshot.getString("name");
                        String email = documentSnapshot.getString("email");
                        String degree = documentSnapshot.getString("degree");
                        ArrayList<String> courses = (ArrayList<String>)documentSnapshot.get("courses");

                        // set retrieved data to corresponding views in the user profile
                        profileDescription.setText(description);
                        majorDescription.setText(major);
                        nameDescription.setText(name);
                        emailDescription.setText(email);
                        degreeDescription.setText(degree);

                        // populate coursesText view with user's courses
                        if(courses!= null){
                            for(int i = 0; i < courses.size() && i < 3; i++) {
                                coursesText
                                        .setText(coursesText.getText() + "\n" + "• " + courses.get(i));
                            }
                        } else {
                            // if there are not courses, set as empty
                            coursesText.setText("");
                        }
                    }
                });

        FirebaseStorage.getInstance().getReference().child(im + FirebaseUtil.currentUserId() + ".jpg").getDownloadUrl().addOnSuccessListener(uri -> {
            // load it into the ImageView
            Picasso.get().load(uri).into(profileImageView);
        });

        mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
            // Handle the returned Uri
            imageUri = uri;
            if(imageUri == null) {
                FirebaseStorage.getInstance().getReference().child(im + FirebaseUtil.currentUserId() + ".jpg").getDownloadUrl().addOnSuccessListener(uri1 -> {
                    // load it into the ImageView
                    Picasso.get().load(uri1).into(profileImageView);
                });
            } else {
                Picasso.get().load(imageUri).into(profileImageView);
                uploadImage();
            }
        });

        // on click event listener for add Image button on profile circle
        addImage.setOnClickListener(view -> openImagePicker());

        // set on click event listener for logout and home buttons
        setOnClickListener(homeButton, HomeActivity.class);
        setOnClickListener(logoutButton, LoginActivity.class);

        // select add description element from profile layout
        // add onClick event
        addDescription.setOnClickListener(v -> {
            // prompt a dialog to add the description
            final EditText input = new EditText(ProfileActivity.this);
            AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
            builder.setTitle("Add Description")
                    .setView(input)
                    .setPositiveButton("OK", (dialog, which) -> {

                        String description = input.getText().toString();

                        profileDescription.setText(description);

                        // prepare user data to update on Firestore
                        Map<String, Object> userData = new HashMap<>();
                        userData.put("description", description);

                        // update user document in Firestore
                        FirebaseFirestore.getInstance().collection(us).document(FirebaseUtil.currentUserId())
                                .update(userData)
                                .addOnSuccessListener(aVoid -> Log.d("UpdateUser", "User successfully updated both locally and in Firestore"))
                                .addOnFailureListener(e -> Log.w("UpdateUser", "Error updating user", e));
                        // TextView to show the description added by user

                    })
                    // set button name and functionality for cancelling the dialog
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.cancel())
                    .show();
        });

        // add TextWatcher to the majorDescription EditText field to listen for changes in text
        // when the text is changed, updates the major field in the Firestore database
        majorDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                // Update the "major" field in the Firestore database with the new text
                FirebaseFirestore.getInstance().collection(us).document(FirebaseUtil.currentUserId())
                        .update("major", s.toString())
                        .addOnSuccessListener(aVoid -> {
                            // display success message
                        })
                        .addOnFailureListener(e -> {
                            // display failure message
                            Toast.makeText(ProfileActivity.this, "Failed to update major: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            }
        });

        // add TextWatcher to the degreeDescription EditText field to listen for changes in text
        // when the text is changed, updates the degree field in the Firestore database
        degreeDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                FirebaseFirestore.getInstance().collection(us).document(FirebaseUtil.currentUserId())
                        .update("degree", s.toString())
                        .addOnSuccessListener(aVoid -> {
                            // display on success message
                        })
                        .addOnFailureListener(e -> {
                            // display on failure message
                            Toast.makeText(ProfileActivity.this, "Failed to update degree: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            }
        });

        // add onClick event for the profile_courses TextView
        allCourses.setOnClickListener(view -> {
            // create intent
            Intent allCoursesIntent = new Intent(ProfileActivity.this, AllCoursesActivity.class);
            // redirect user to All courses page
            startActivity(allCoursesIntent);
        });

        // add onClick event for the delete button
        delete.setOnClickListener(v -> {
            // delete user from Firestore
            FirebaseFirestore.getInstance().collection(us).document(FirebaseUtil.currentUserId())
                    .delete()
                    .addOnSuccessListener(aVoid -> {
                        // document successfully deleted
                        Log.d("DeleteUser", "DocumentSnapshot successfully deleted!");
                    })
                    .addOnFailureListener(e -> {
                        // error occurred while deleting document
                        Log.w("DeleteUser", "Error deleting document", e);
                    });

            // delete user account from Firebase Authentication
            FirebaseUser userFB = FirebaseAuth.getInstance().getCurrentUser();
            // if user account non empty
            if (userFB != null) {
                userFB.delete();
            }

            // direct user to login page after profile has been deleted
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    /**
     * Sets an onClickListener to the given view,
     * which navigates to the specified destination class
     * when the view is clicked.
     *
     * @param view             The view to set the onClickListener to.
     * @param destinationClass The class of the activity to navigate to when the view is clicked.
     */
    void setOnClickListener(View view, Class<?> destinationClass) {
        view.setOnClickListener(v -> {
            // create an intent to navigate to the specified class
            Intent intent = new Intent(ProfileActivity.this, destinationClass);
            // start activity associated with the intent
            startActivity(intent);
        });
    }

    /**
     * Launches the activity to pick an image using the ActivityResultLauncher.
     * This method is used when the user wants to choose an image for their profile picture.
     */
    private void openImagePicker() {
        // Launch the activity to pick an image
        mGetContent.launch("image/*");
    }

    /**
     * Uploads the selected image to Firebase Storage and updates the user's profile image URL
     * in Firestore. This method is invoked after the user has chosen an image
     * and it's ready to be uploaded.
     */
    void uploadImage() {
        if (imageUri != null) {
            // get reference to Firebase Storage location where the image will be stored
            StorageReference fileReference = mStorageRef
                    .child(im + FirebaseUtil.currentUserId() + ".jpg");

            // upload image to Firebase Storage
            fileReference.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        // message indicating successful upload
                        Toast.makeText(
                                ProfileActivity.this,
                                "Successfully Uploaded",
                                Toast.LENGTH_SHORT
                        ).show();

                        // retrieve download URL of the uploaded image from Firebase Storage
                        FirebaseStorage
                                .getInstance()
                                .getReference()
                                .child(
                                        im + FirebaseUtil.currentUserId() + ".jpg"
                                )
                                .getDownloadUrl()
                                .addOnSuccessListener(downloadUrl -> {
                                    // update user profile image URL in Firestore with the download URL
                                    String imageUrl = downloadUrl.toString();
                            FirebaseFirestore
                                    .getInstance()
                                    .collection(us)
                                    .document(FirebaseUtil.currentUserId())
                                    .update("imageUrl", imageUrl)
                                    .addOnSuccessListener(aVoid -> {
                                        // profile image URL updated successfully in Firestore
                                    });
                                });
                    });
        }
    }
}
