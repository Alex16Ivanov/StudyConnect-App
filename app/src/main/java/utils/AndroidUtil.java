package utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import model.User;

/**
 * Utility class for Android-related operations.
 * Provides methods for handling user models, profile picture settings, etc.
 */
public class AndroidUtil {

    /**
     * Passes a user model as an intent.
     * @param intent The intent to pass the user model to.
     * @param model The user model to pass.
     */
    public static void passUserModelAsIntent(Intent intent, User model){
        intent.putExtra("email",model.getEmail());
        intent.putExtra("name",model.getName());
        intent.putExtra("userId",model.getUserId());
        //intent.putExtra("type",model.getUserId());
    }

    /**
     * Retrieves a user model from an intent.
     * @param intent The intent containing the user model.
     * @return The user model retrieved from the intent.
     */
    public static User getUserModelFromIntent(Intent intent){
        User userModel = new User();
        userModel.setEmail(intent.getStringExtra("email"));
        userModel.setName(intent.getStringExtra("name"));
        userModel.setUserId(intent.getStringExtra("userId"));
        //userModel.setUserId(intent.getStringExtra("type"));
        return userModel;
    }

    /**
     * Sets the profile picture for an ImageView using Glide library.
     * @param context The context to use.
     * @param imageUri The URI of the image.
     * @param imageView The ImageView to set the profile picture.
     */
    public static void setProfilePic(Context context, Uri imageUri, ImageView imageView){
        Glide.with(context).load(imageUri).apply(RequestOptions.circleCropTransform()).into(imageView);
    }
}