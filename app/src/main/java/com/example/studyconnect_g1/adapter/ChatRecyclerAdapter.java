package com.example.studyconnect_g1.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studyconnect_g1.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;

import model.ChatMessage;
import utils.FirebaseUtil;

public class ChatRecyclerAdapter extends FirestoreRecyclerAdapter<ChatMessage, ChatRecyclerAdapter.ChatModelViewHolder> {

    Context context;

    /**
     * Constructor for the chat recycler adapter.
     * @param options Configuration options for the FirestoreRecyclerAdapter.
     * @param context The current context.
     */
    public ChatRecyclerAdapter(@NonNull FirestoreRecyclerOptions<ChatMessage> options, Context context) {
        super(options);
        this.context = context;
    }

    /**
     * Binds a ChatMessage object to a ViewHolder to display the message content in the RecyclerView.
     * Different layouts are used depending on whether the message was sent or received.
     * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position.
     * @param position The position of the item within the adapter's data set.
     * @param model The ChatMessage object which contains the data that should be displayed by the holder.
     */
    @Override
    protected void onBindViewHolder(@NonNull ChatModelViewHolder holder, int position, @NonNull ChatMessage model) {
        Log.i("haushd","asjd");
        if(model.getSenderId().equals(FirebaseUtil.currentUserId())){
            holder.leftChatLayout.setVisibility(View.GONE);
            holder.rightChatLayout.setVisibility(View.VISIBLE);
            if(( ( model.getMessage() ).substring(0, Math.min(model.getMessage().length(), 8))).equals("https://")){
                holder.rightChatTextview.setVisibility(View.GONE);
                Picasso.get().load(model.getMessage()).into(holder.rightChatImageview);
            } else {
                holder.rightChatImageview.setVisibility(View.GONE);
                holder.rightChatTextview.setText(model.getMessage());
            }

        }else{
            holder.rightChatLayout.setVisibility(View.GONE);
            holder.leftChatLayout.setVisibility(View.VISIBLE);
            if(( ( model.getMessage() ).substring(0, Math.min(model.getMessage().length(), 8))).equals("https://")){
                holder.leftChatTextview.setVisibility(View.GONE);
                Picasso.get().load(model.getMessage()).into(holder.leftChatImageview);
            } else {
                holder.leftChatImageview.setVisibility(View.GONE);
                holder.leftChatTextview.setText(model.getMessage());
            }
        }
    }

    /**
     * Creates a new ViewHolder when there are no existing ViewHolders available that the RecyclerView can reuse.
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public ChatModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_message_recycler_row,parent,false);
        return new ChatModelViewHolder(view);
    }

    public class ChatModelViewHolder extends RecyclerView.ViewHolder{

        public LinearLayout leftChatLayout;
        public LinearLayout rightChatLayout;
        public TextView leftChatTextview;
        public TextView rightChatTextview;

        public ImageView leftChatImageview;
        public ImageView rightChatImageview;

        /**
         * ViewHolder class for chat messages. Holds references to the layout and views that display a chat message.
         */
        public ChatModelViewHolder(@NonNull View itemView) {
            super(itemView);

            leftChatLayout = itemView.findViewById(R.id.left_chat_layout);
            rightChatLayout = itemView.findViewById(R.id.right_chat_layout);
            leftChatTextview = itemView.findViewById(R.id.left_chat_textview);
            rightChatTextview = itemView.findViewById(R.id.right_chat_textview);
            leftChatImageview = itemView.findViewById(R.id.left_chat_imageview);
            rightChatImageview = itemView.findViewById(R.id.right_chat_imageview);
        }
    }
}
