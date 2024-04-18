package com.example.studyconnect_g1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import model.ChatMessage;
import model.ChatRoom;
import model.User;
import utils.AndroidUtil;
import utils.FirebaseUtil;

import com.example.studyconnect_g1.adapter.ChatRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Arrays;

import androidx.core.content.FileProvider;

import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Locale;

public class ChatActivity extends AppCompatActivity {

    // Class members for UI elements, data, and Firebase
    User otherUser;
    String chatroomId;
    ChatRoom chatroomModel;
    ChatRecyclerAdapter adapter;
    String imageMessage;

    // ActivityResultLaunchers for handling various actions
    private ActivityResultLauncher<String> mGetContent; // For picking images from the gallery
    private ActivityResultLauncher<Uri> takePictureLauncher; // For taking pictures via camera


    // UI elements
    EditText messageInput;
    ImageButton sendMessageBtn;
    ImageButton backBtn;
    TextView otherUsername;
    RecyclerView recyclerView;
    ImageView imageView;
    ImageView homeButton;

    public static final int CAMERA_PERM_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    ImageView selectedImage;
    ImageButton cameraBtn;
    ImageButton galleryBtn;
    String currentPhotoPath;
    StorageReference storageReference;
    String fail = "Failed to capture image";
    String image = "images/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Initialization of UI components and setting up onClick listeners
        otherUser = AndroidUtil.getUserModelFromIntent(getIntent());
        chatroomId = FirebaseUtil.getChatroomId(FirebaseUtil.currentUserId(), otherUser.getUserId());
        messageInput = findViewById(R.id.chat_message_input);
        sendMessageBtn = findViewById(R.id.message_send_btn);
        backBtn = findViewById(R.id.back_btn);
        otherUsername = findViewById(R.id.other_username);
        recyclerView = findViewById(R.id.chat_recycler_view);
        imageView = findViewById(R.id.profile_pic_image_view);
        homeButton = findViewById(R.id.returnToHomePage);
        selectedImage = findViewById(R.id.displayImageView);
        cameraBtn = findViewById(R.id.cameraBtn);
        galleryBtn = findViewById(R.id.galleryBtn);
        storageReference = FirebaseStorage.getInstance().getReference();


        // return to home button
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // redirect user to home page
                Intent homeIntent = new Intent(ChatActivity.this, HomeActivity.class);
                startActivity(homeIntent);
            }
        });

        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(ChatActivity.this, MessageSearchActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
        otherUsername.setText(otherUser.getName());

        sendMessageBtn.setOnClickListener((v -> {
            String message = messageInput.getText().toString().trim();
            if (message.isEmpty())
                return;
            sendMessageToUser(message);
        }));

        // Setup for handling image selection from gallery
        mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(), this::uploadImage);

        // Setup for capturing images from the camera
        setupTakePictureLauncher();

        // Button listeners for camera and gallery
        cameraBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                askCameraPermissions();
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePicture, CAMERA_REQUEST_CODE);
            }
        });

        galleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGetContent.launch("image/*");


            }
        });

        // Additional setup methods
        getOrCreateChatroomModel();
        setupChatRecyclerView();

        // Permission request handling
            ActivityResultLauncher<String[]> requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), permissions -> {
            Boolean cameraGranted = permissions.getOrDefault(Manifest.permission.CAMERA, false); // Check if camera permission is granted
            Boolean storageGranted = permissions.getOrDefault(Manifest.permission.WRITE_EXTERNAL_STORAGE, false); // Check if storage permission is granted
            if (cameraGranted && storageGranted) {
                dispatchTakePictureIntent(); // If both camera and storage permissions are granted, dispatch take picture intent
            } else {
                Toast.makeText(this, "Camera and Storage permissions are required.", Toast.LENGTH_SHORT).show(); // Show toast message if permissions are not granted
            }
        });



        takePictureLauncher = registerForActivityResult(new ActivityResultContracts.TakePicture(), result -> {
            if (result) {
                // Image was captured and saved to the fileUri specified in the Intent
                uploadImage(Uri.parse(currentPhotoPath));
            } else {
                Toast.makeText(this, fail, Toast.LENGTH_SHORT).show();
            }
        });

    }

    // onActivityResult for handling deprecated methods or specific result handling

    /**
     * @Deprecated
     */
    @Override
    @Deprecated
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data); // Call super method to handle activity result
        if(requestCode == CAMERA_REQUEST_CODE){ // Check if the result is from the camera request
            if(data == null) { // Check if data is null
                Toast.makeText(this, fail, Toast.LENGTH_SHORT).show(); // Show toast message if failed to capture image
                return;
            }
            Bitmap bitmap = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data"); // Retrieve captured image bitmap from intent data
            if(bitmap == null) { // Check if bitmap is null
                Toast.makeText(this, fail, Toast.LENGTH_SHORT).show(); // Show toast message if failed to capture image
                return;
            }
            firebaseUploadBitmap(bitmap); // Upload captured image to Firebase
        }
    }

    // Sets up the RecyclerView including querying Firebase Firestore, configuring the adapter and layout manager
    void setupChatRecyclerView() {
        Query query = FirebaseUtil.getChatroomMessageReference(chatroomId) // Get reference to chatroom messages in Firebase
                .orderBy("timestamp", Query.Direction.DESCENDING); // Order messages by timestamp in descending order

        FirestoreRecyclerOptions<ChatMessage> options = new FirestoreRecyclerOptions.Builder<ChatMessage>() // Build options for Firestore recycler view
                .setQuery(query, ChatMessage.class).build(); // Set query and model class for Firestore recycler view

        adapter = new ChatRecyclerAdapter(options, getApplicationContext()); // Create adapter for recycler view
        LinearLayoutManager manager = new LinearLayoutManager(this); // Create linear layout manager for recycler view
        manager.setReverseLayout(true); // Set reverse layout to display messages from bottom to top
        recyclerView.setLayoutManager(manager); // Set layout manager to recycler view
        recyclerView.setAdapter(adapter); // Set adapter to recycler view
        adapter.startListening(); // Start listening for data changes in Firestore
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() { // Register observer to scroll to top when new item inserted
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                recyclerView.smoothScrollToPosition(0); // Smoothly scroll to top when new item inserted
            }
        });
    }

    // Handles sending of text and image messages to Firebase Firestore
    void sendMessageToUser(String message) {
        chatroomModel.setLastMessageTimestamp(Timestamp.now());
        chatroomModel.setLastMessageSenderId(FirebaseUtil.currentUserId());
        chatroomModel.setLastMessage(message);
        FirebaseUtil.getChatroomReference(chatroomId).set(chatroomModel);

        ChatMessage chatMessageModel = new ChatMessage(message, FirebaseUtil.currentUserId(), Timestamp.now());
        FirebaseUtil.getChatroomMessageReference(chatroomId).add(chatMessageModel)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            messageInput.setText("");
                        }
                    }
                });
    }

    // Retrieves or creates a new chat room model in Firebase Firestore
    void getOrCreateChatroomModel() {
        FirebaseUtil.getChatroomReference(chatroomId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                chatroomModel = task.getResult().toObject(ChatRoom.class); // Convert Firestore document to ChatRoom object
                if (chatroomModel == null) {
                    // First time chat: Create new chatroom model
                    chatroomModel = new ChatRoom(
                            chatroomId,
                            Arrays.asList(FirebaseUtil.currentUserId(), otherUser.getUserId()), // List of user IDs
                            Timestamp.now(), // Current timestamp
                            "", // Initial message content
                            "" // Initial message sender ID
                    );
                    FirebaseUtil.getChatroomReference(chatroomId).set(chatroomModel); // Set chatroom model in Firestore
                }
            }
        });
    }

    // Requests camera and storage permissions if not granted, otherwise initiates taking a picture.
    private void askCameraPermissions() {
        if (ContextCompat.checkSelfPermission(ChatActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(ChatActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ChatActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_PERM_CODE);
        } else {
            dispatchTakePictureIntent();
        }
    }

    // Creates a temporary image file for storing a captured image
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    // Dispatches an intent to take a picture, creating a file for the image
    private void dispatchTakePictureIntent() {
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
            // Error occurred while creating the File
        }
        // Continue only if the File was successfully created
        if (photoFile != null) {
            Uri photoURI = FileProvider.getUriForFile(this, "net.smallacademy.android.fileprovider", photoFile);
            takePictureLauncher.launch(photoURI);
        }
    }

    // Uploads a selected image to Firebase Storage and sends a message containing the image URL
    public void uploadImage(Uri imageUri) {
        if (imageUri != null) {
            String fileName = Timestamp.now().toString();
            StorageReference fileReference = storageReference.child(image + fileName + ".jpg");

            fileReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            FirebaseStorage.getInstance().getReference().child(image + fileName + ".jpg").getDownloadUrl().addOnSuccessListener(downloadUrl -> {
                                // Here you get the download URL of the image
                                 imageMessage = downloadUrl.toString();
                                if(imageMessage !=null){
                                    sendMessageToUser(imageMessage);
                                    imageMessage = null;
                                }
                            });
                        }
                    });
        }
    }

    // Uploads a bitmap image (from camera capture) to Firebase Storage
    private void firebaseUploadBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] data = stream.toByteArray();
        String fileName = Timestamp.now().toString();
        StorageReference imageRef = storageReference.child(image + fileName + ".jpg");

        imageRef.putBytes(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        FirebaseStorage.getInstance().getReference().child(image + fileName + ".jpg").getDownloadUrl().addOnSuccessListener(downloadUrl -> {
                            // Here you get the download URL of the image
                            imageMessage = downloadUrl.toString();
                            sendMessageToUser(imageMessage);
                            imageMessage = null;

                        });
                    }
                });
    }

    // Setup for capturing images from the camera with new API
    private void setupTakePictureLauncher() {
        takePictureLauncher = registerForActivityResult(new ActivityResultContracts.TakePicture(), result -> {
            if (result) {
                // Image was successfully captured and saved to the fileUri specified in the Intent
                Uri photoUri = Uri.fromFile(new File(currentPhotoPath));
                uploadImage(photoUri);
            } else {
                // Image capture failed or was cancelled
                Toast.makeText(ChatActivity.this, fail, Toast.LENGTH_SHORT).show();
            }
        });
    }
}