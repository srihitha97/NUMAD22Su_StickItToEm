package edu.neu.madcourse.stick_it_to_em;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StickersActivity extends AppCompatActivity
{
    private String sender_user;
    private String receiver_user;
    private boolean begin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stickers);
        begin = true;
        sender_user = getIntent().getStringExtra("sender_user");
        receiver_user = getIntent().getStringExtra("receiver_user");
        TextView inf = findViewById(R.id.stkrInf);
        inf.append("Send any sticker to " + receiver_user);
    }

    @Override
    public void onBackPressed() {
        if (begin) {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Closing Activity")
                    .setMessage("Are you sure you want to close this activity?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        } else {
            finish();
        }
    }

    public void stickerBtn(View view)
    {
        String tag = (String)view.getTag();
        long t = System.currentTimeMillis();
        save(sender_user, receiver_user,String.valueOf(t),
                tag);
    }

    private void save(String sender, String receiver, String t,String tag)
    {
        DatabaseReference r = FirebaseDatabase.getInstance().getReference();
        DatabaseReference c = r.child("chat");
        ChatActivity chat = new ChatActivity(sender, receiver, t, tag);
        String id = sender.concat(t);
        Context con = getApplicationContext();
        c.child(id).setValue(chat, (databaseError, ref) ->
        {
            if (databaseError != null)
            {
                Toast t1= Toast.makeText(con, "Could not send sticker.", Toast.LENGTH_LONG);
                t1.show();
            } else
            {
                Toast t2 = Toast.makeText(con, "Sticker with ID " + chat.getSticker().concat(" Sent!"),
                        Toast.LENGTH_LONG);
                t2.show();
            }
        });
    }
}