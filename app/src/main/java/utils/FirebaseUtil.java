package utils;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.SeekEvent;
import model.User;

/**
 * Utility class for Firebase-related operations.
 * Provides methods for interacting with Firestore, Firebase Auth, and Firebase Storage.
 */
public class FirebaseUtil {

    // Callback interface for SeekHelp user retrieval.
    public interface SeekEventsCallback {
        /**
         * Called when seek events are successfully retrieved.
         * @param seekEvents List of SeekEvents retrieved.
         */
        void onSeekEventsRetrieved(List<SeekEvent> seekEvents);

        /**
         * Called when an error occurs during seek events retrieval.
         * @param e Exception representing the error.
         */
        void onError(Exception e);
    }

    // Callback interface for retrieving current user.
    public interface CurrentUserCallback {
        /**
         * Called when the current user is successfully retrieved.
         * @param currentUser The current user retrieved.
         */
        void onCurrentUserRetrieved(User currentUser);

        /**
         * Called when an error occurs while retrieving the current user.
         * @param message Error message.
         */
        void onError(String message);
    }

    private static FirebaseFirestore firebaseFirestore;

    // Setter for testing purposes
    public static void setFirestoreInstance(FirebaseFirestore firestore) {
        firebaseFirestore = firestore;
    }

    /**
     * Retrieves the current user's ID.
     * @return The current user's ID.
     */
    public static String currentUserId(){
        return FirebaseAuth.getInstance().getUid();
    }

    /**
     * Checks if a user is logged in.
     * @return True if a user is logged in, false otherwise.
     */
    public static boolean isLoggedIn(){
        return currentUserId() != null;
    }

    /**
     * Retrieves a reference to the current user's details in Firestore.
     * @return A reference to the current user's details.
     */
    public static DocumentReference currentUserDetails(){
        return FirebaseFirestore.getInstance().collection("users").document(currentUserId());
    }

    /**
     * Retrieves a user's details from Firestore given the user's ID.
     * @param UserId The ID of the user to retrieve.
     * @return A reference to the user's details.
     */
    public static DocumentReference getUserDetails(String UserId){
        return FirebaseFirestore.getInstance().collection("users").document(UserId);
    }

    /**
     * Retrieves a reference to the collection of all users in Firestore.
     * @return A reference to the collection of all users.
     */
    public static CollectionReference allUserCollectionReference(){
        return FirebaseFirestore.getInstance().collection("users");
    }

    /**
     * Retrieves a reference to the collection of all seek events in Firestore.
     * @return A reference to the collection of all seek events.
     */
    public static CollectionReference allSeekEventsCollectionReference(){
        if (firebaseFirestore == null) {
            firebaseFirestore = FirebaseFirestore.getInstance();
        }
        return firebaseFirestore.collection("seekevents");
    }

    /**
     * Adds a seek event to Firestore.
     * @param courseCode The course code associated with the seek event.
     * @param day The day of the seek event.
     * @param startTime The start time of the seek event.
     * @param endTime The end time of the seek event.
     * @param creatorId The ID of the user who created the seek event.
     */
    public static void addSeekEventToFirestore(
            String courseCode,
            String day,
            Map<String, String> startTime,
            Map<String, String> endTime,
            String creatorId) {

        String title = courseCode + " " + day + " "+ startTime.get("hours") + ":" + startTime.get("minutes")
                + "-" + endTime.get("hours") + ":" + endTime.get("minutes");

        Map<String, Object> seekEvent = new HashMap<>();
        seekEvent.put("courseName", courseCode);
        seekEvent.put("dayString", day.toUpperCase()); // Capitalize the day
        seekEvent.put("startTimeString", startTime);
        seekEvent.put("endTimeString", endTime);
        seekEvent.put("title", title);
        seekEvent.put("creatorId", creatorId);

        FirebaseFirestore.getInstance().collection("seekevents").add(seekEvent);
    }

    /**
     * Retrieves the chatroom ID for the given pair of user IDs.
     * @param userId1 The ID of the first user.
     * @param userId2 The ID of the second user.
     * @return The chatroom ID.
     */
    public static String getChatroomId(String userId1,String userId2){
        if(userId1.hashCode()<userId2.hashCode()){
            return userId1+"_"+userId2;
        }else{
            return userId2+"_"+userId1;
        }
    }

    /**
     * Retrieves the schedule ID for the given user ID.
     * @param userId The ID of the user.
     * @return The schedule ID.
     */
    public static String getScheduleId(String userId){
        return userId + "schedule";
    }

    /**
     * Retrieves a reference to the collection of chatroom messages for the given chatroom ID.
     * @param chatroomId The ID of the chatroom.
     * @return A reference to the collection of chatroom messages.
     */
    public static CollectionReference getChatroomMessageReference(String chatroomId){
        return getChatroomReference(chatroomId).collection("chats");
    }

    /**
     * Retrieves a reference to the collection of events for the given day in the schedule.
     * @param scheduleId The ID of the schedule.
     * @param day The day of the events.
     * @return A reference to the collection of events for the given day.
     */
    public static CollectionReference getEventsOfDayReference(String scheduleId, DayOfWeek day){
        if(day == null) {return getScheduleReference(scheduleId).collection("monday");}
        switch(day) {
            case MONDAY: return getScheduleReference(scheduleId).collection("monday");
            case TUESDAY: return getScheduleReference(scheduleId).collection("tuesday");
            case WEDNESDAY: return getScheduleReference(scheduleId).collection("wednesday");
            case THURSDAY: return getScheduleReference(scheduleId).collection("thursday");
            case FRIDAY: return getScheduleReference(scheduleId).collection("friday");
            case SATURDAY: return getScheduleReference(scheduleId).collection("saturday");
            default: return getScheduleReference(scheduleId).collection("sunday");
        }
    }

    /**
     * Retrieves a reference to the chatroom in Firestore.
     * @param chatroomId The ID of the chatroom.
     * @return A reference to the chatroom.
     */
    public static DocumentReference getChatroomReference(String chatroomId){
        return FirebaseFirestore.getInstance().collection("chatrooms").document(chatroomId);
    }

    /**
     * Retrieves a reference to the schedule in Firestore.
     * @param scheduleId The ID of the schedule.
     * @return A reference to the schedule.
     */
    public static DocumentReference getScheduleReference(String scheduleId){
        return FirebaseFirestore.getInstance().collection("schedules").document(scheduleId);
    }

    /**
     * Retrieves a reference to the collection of all chatrooms in Firestore.
     * @return A reference to the collection of all chatrooms.
     */
    public static CollectionReference allChatroomCollectionReference(){
        return FirebaseFirestore.getInstance().collection("chatrooms");
    }

    /**
     * Retrieves a reference to the other user in the chatroom.
     * @param userIds List of user IDs in the chatroom.
     * @return A reference to the other user in the chatroom.
     */
    public static DocumentReference getOtherUserFromChatroom(List<String> userIds){
        if(userIds.get(0).equals(FirebaseUtil.currentUserId())){
            return allUserCollectionReference().document(userIds.get(1));
        }else{
            return allUserCollectionReference().document(userIds.get(0));
        }
    }

    /**
     * Converts a timestamp to a string representation of time.
     * @param timestamp The timestamp to convert.
     * @return A string representation of time.
     */
    public static String timestampToString(Timestamp timestamp) {
        LocalDateTime dateTime = timestamp.toDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return dateTime.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    /**
     * Retrieves seek events for the current user.
     * @param callback Callback to handle the retrieval result.
     */
    public static void retrieveSeekEventsForCurrentUser(SeekEventsCallback callback) {
        String currentUserId = currentUserId(); // Assuming currentUserId() is a method in FirebaseUtil

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("seekevents")
                .whereEqualTo("creatorId", currentUserId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<SeekEvent> userSeekEvents = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            try {
                                SeekEvent seekEvent = document.toObject(SeekEvent.class);
                                userSeekEvents.add(seekEvent);
                            } catch (Exception e) {
                                // Adjusted error handling to use the callback
                                callback.onError(e);
                                return;
                            }
                        }
                        // Use callback to return the events
                        callback.onSeekEventsRetrieved(userSeekEvents);
                    } else {
                        // Error handling
                        callback.onError(task.getException());
                    }
                });
    }

    /**
     * Retrieves the current user.
     * @param callback Callback to handle the retrieval result.
     */
    public static void retrieveCurrentUser(CurrentUserCallback callback) {
        String currentUserId = currentUserId(); // Assumes currentUserId() method exists within FirebaseUtil
        if (currentUserId != null) {
            allUserCollectionReference().document(currentUserId).get().addOnCompleteListener(task -> {
                if (task.isSuccessful() && task.getResult() != null) {
                    User user = task.getResult().toObject(User.class);
                    callback.onCurrentUserRetrieved(user); // Pass the user to the callback
                } else {
                    callback.onError("Failed to retrieve current user data.");
                }
            });
        } else {
            callback.onError("No current user ID found.");
        }
    }

    /**
     * Logs out the current user.
     */
    public static void logout(){
        FirebaseAuth.getInstance().signOut();
    }

    /**
     * Retrieves the storage reference for an image.
     * @param fileName The name of the image file.
     * @return The storage reference for the image.
     */
    public static StorageReference getImage(String fileName){
        return FirebaseStorage.getInstance().getReference().child("images/" + fileName + ".jpg");
    }
}

