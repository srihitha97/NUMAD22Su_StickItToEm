package edu.neu.madcourse.stick_it_to_em;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class NotificationsActivity extends AppCompatActivity {
    private String sender, sticker;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifications_activity);

        text = findViewById(R.id.text1);
        Bundle i = getIntent().getExtras();
        if (i != null) {
            sender = i.getString("sender");
            sticker = i.getString("sticker");
        }
        text.setText(sender + " sent you the emoji sticker: " + sticker);
    }
}