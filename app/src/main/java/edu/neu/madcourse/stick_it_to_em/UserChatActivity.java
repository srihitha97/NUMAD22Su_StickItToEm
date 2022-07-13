package edu.neu.madcourse.stick_it_to_em;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.text.MessageFormat;

public class UserChatActivity extends AppCompatActivity
{
    private final ArrayList<ChatActivity> chatL = new ArrayList<>();
    private TextView stickerCount;
    private RecyclerChat chatHist;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_recycle_activity);
        Intent intent = getIntent();
        String sender_user = intent.getStringExtra("sender_user");
        String receiver_user = intent.getStringExtra("receiver_user");
        TextView chatInfo = findViewById(R.id.chatInfoView);
        chatInfo.append("Chat History with " + receiver_user);
        RecyclerView recyclerChat = findViewById(R.id.chatRecycleView);
        RecyclerView.LayoutManager rvlm = new LinearLayoutManager(this);
        recyclerChat.setLayoutManager(rvlm);
        chatHist = new RecyclerChat(sender_user, receiver_user, chatL);
        recyclerChat.setAdapter(chatHist);
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        DatabaseReference cbd = db.child("chat");
        cbd.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                int sSendCount = 0;
                int sReceiveCount = 0;
                chatL.clear();
                for(DataSnapshot i : snapshot.getChildren())
                {
                    ChatActivity c = i.getValue(ChatActivity.class);
                    assert c != null;
                    if(c.getSender().equals(sender_user) && c.getReceiver()
                            .equals(receiver_user))
                    { chatL.add(c);
                        ++sSendCount;
                    }else if(c.getSender().equals(receiver_user) && c.getReceiver()
                            .equals(sender_user))
                    { chatL.add(c);
                        ++sReceiveCount;
                    }
                }
                chatL.sort(Comparator.comparing(ChatActivity::getTimeStamp));

                stickerCount = findViewById(R.id.StickerCount);
                stickerCount.setText(MessageFormat.format("Sent {0}" +
                        " Stickers and Received {1} Stickers", sSendCount, sReceiveCount));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Log.w("Error", error.toException());
            }
        });
    }
}
