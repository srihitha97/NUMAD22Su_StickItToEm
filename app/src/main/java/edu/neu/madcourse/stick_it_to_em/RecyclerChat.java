package edu.neu.madcourse.stick_it_to_em;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

public class RecyclerChat extends
        RecyclerView.Adapter<RecyclerChat.ChatRecordView>
{
    private final String sender_user;
    private final String receiver_user;
    private final HashMap<String, Integer> hp;
    private final ArrayList<ChatActivity> chat_list;

    public RecyclerChat(String sender_user, String receiver_user,ArrayList<ChatActivity> chat_list)
    {

        this.sender_user = sender_user;
        this.receiver_user = receiver_user;
        this.hp = new HashMap<>();
        this.chat_list = chat_list;
        map();
    }


    private void map()
    {
        hp.put("sticker1", R.drawable.sticker1);
        hp.put("sticker2", R.drawable.sticker2);
        hp.put("sticker3", R.drawable.sticker3);
        hp.put("sticker4", R.drawable.sticker4);
        hp.put("sticker5", R.drawable.sticker5);
        hp.put("sticker6", R.drawable.sticker6);
    }

    @NonNull
    @Override
    public ChatRecordView onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.chat_record_activity, parent, false);
        return new ChatRecordView(view);
    }
 @Override
    public void onBindViewHolder(@NonNull ChatRecordView viewHolder, int position)
    {

        ChatActivity cr = chat_list.get(position);
        String st = cr.getSticker();
        String t = cr.getTimeStamp();
        String ss = cr.getSender();
        String rs = cr.getReceiver();
        long val = Long.parseLong(t);
        Date date = new Date(val);
        DateFormat f = new SimpleDateFormat("yyyy/MM/dd|HH:mm:ss");
        f.setTimeZone(TimeZone.getTimeZone("GMT-4"));
        String format = f.format(date);

        if ( rs.equals(receiver_user) && ss.equals(sender_user))
        {
            viewHolder.getSends().setImageResource(hp.get(st));
            viewHolder.getSendt().setText(format);
            viewHolder.getReceivs().setImageResource(android.R.color.transparent);
            viewHolder.getReceivt().setText("");
        }
       else if (ss.equals(receiver_user) && rs.equals(sender_user))
        {
            viewHolder.getReceivs().setImageResource(hp.get(st));
            viewHolder.getReceivt().setText(format);
            viewHolder.getSends().setImageResource(android.R.color.transparent);
            viewHolder.getSendt().setText("");
        }
    }
 @Override
    public int getItemCount()
    {
        return chat_list.size();
    }

    public static class ChatRecordView extends RecyclerView.ViewHolder {
        private final ImageView sendsI;
        private final ImageView receivsI;
        private final TextView sendtV;
        private final TextView receivtV;

        public ImageView getSends()
        {return sendsI;
        }

        public TextView getSendt()
        {return sendtV;
        }

        public TextView getReceivt()
        {return receivtV;
        }

        public ImageView getReceivs()
        {return receivsI;
        }


        public ChatRecordView(@NonNull View itemView)
        {
            super(itemView);
            sendsI = itemView.findViewById(R.id.imageSender);
            receivsI = itemView.findViewById(R.id.imageReceiver);
            sendtV = itemView.findViewById(R.id.textSTime);
            receivtV = itemView.findViewById(R.id.textRTime);
        }
    }
}
