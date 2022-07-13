package edu.neu.madcourse.stick_it_to_em;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class StartActivity extends AppCompatActivity implements
        RecyclerUser.UserRecordListener
{
    private RecyclerUser recUsr;
    private String name;
    private final ArrayList<Users> info = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);
        name = getIntent().getStringExtra("username");
        Users inf = new Users(name);
        TextView text = findViewById(R.id.inf);
        DatabaseReference dr = FirebaseDatabase.getInstance().getReference();
        DatabaseReference udr = dr.child("users");
        udr.child(name).setValue(inf);
        text.append("Hello " + name + "!");
        text.append("Send a sticker to other users. Click on user row to see sticker exchange history between you and the other user");
        RecyclerView all = findViewById(R.id.chats);
        RecyclerView.LayoutManager layout = new LinearLayoutManager(this);
        all.setLayoutManager(layout);
        recUsr = new RecyclerUser
                (info, this);
        all.setAdapter(recUsr);
        udr.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot sp)
            {
                info.clear();
                for(DataSnapshot i :sp.getChildren())
                {
                    Users u = i.getValue(Users.class);
                    assert u != null;
                    if(!u.getUser().equals(name))
                    {
                        info.add(u);
                    }
                }
                recUsr.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Log.w( "Error", error.toException());
            }
        });
    }

    @Override
    public void ChatClick(int position)
    {
        Intent intent = new Intent(this, UserChatActivity.class);
        intent.putExtra("receiver_user", info.get(position).getUser());
        intent.putExtra("sender_user", name);
        startActivity(intent);
    }

    @Override
    public void SendStickerClick(int position)
    {
        Intent intent = new Intent(this, Stickers.class);
        intent.putExtra("receiver_user", info.get(position).getUser());
        intent.putExtra("sender_user", name);
        startActivity(intent);
    }
}