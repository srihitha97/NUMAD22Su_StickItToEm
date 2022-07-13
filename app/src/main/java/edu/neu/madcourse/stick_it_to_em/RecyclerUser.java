package edu.neu.madcourse.stick_it_to_em;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;


import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



public class RecyclerUser extends
        RecyclerView.Adapter<RecyclerUser
                .RecyclerUserRecordContainerViewHolder>
{

    private final UserRecordListener uRListener;
    private final ArrayList<Users> userInfo;

    public RecyclerUser(ArrayList<Users> userCard,
                        UserRecordListener uRListener)
    {
        this.userInfo = userCard;
        this.uRListener = uRListener;
    }

    @NonNull
    @Override
    public RecyclerUserRecordContainerViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                    int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_user_activity, parent,
                false);
        return new RecyclerUserRecordContainerViewHolder(view, uRListener);
    }
  @Override
    public void onBindViewHolder(@NonNull RecyclerUserRecordContainerViewHolder viewHolder,
                                 int position)
    {   String username = userInfo.get(position).getUser();
        viewHolder.getUsername().setText(username);
        viewHolder.getSendStickerBtn().setTag(username);
    }

    @Override
    public int getItemCount()
    {
        return userInfo.size();
    }

    public static class RecyclerUserRecordContainerViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener
    {
         private final TextView username;
        private final Button sendStickerBtn;
        private final UserRecordListener uRListener;

        // Constructor.
        public RecyclerUserRecordContainerViewHolder(@NonNull View itemView, UserRecordListener
                uRListener)
        {
            super(itemView);

           username = itemView.findViewById(R.id.usernameTextView);
            sendStickerBtn = itemView.findViewById(R.id.btnSendSticker);
            this.uRListener = uRListener;


            itemView.setOnClickListener(this);
            sendStickerBtn.setOnClickListener(v ->
                    uRListener.SendStickerClick(getAdapterPosition()));
        }

        // Getter functions.

        public TextView getUsername()
        {
            return username;
        }

        public Button getSendStickerBtn()
        {
            return sendStickerBtn;
        }

       @Override
        public void onClick(View v)
        {
            uRListener.ChatClick(getAdapterPosition());
        }
    }

    public interface UserRecordListener
    {
        void SendStickerClick(int position);
        void ChatClick(int position);


    }
}
