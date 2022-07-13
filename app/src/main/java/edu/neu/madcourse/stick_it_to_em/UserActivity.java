package edu.neu.madcourse.stick_it_to_em;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class UserActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_user_activity);
    }

    public void stickerBtn(View view)
    {
        startActivity(new Intent(this, StickersActivity.class));
    }
}
