package edu.neu.madcourse.stick_it_to_em;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class LoginHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_home);
    }
    public void onClickStickerView(View view) {
        startActivity(new Intent(LoginHome.this, Stickers.class));
    }
}
