package edu.neu.madcourse.stick_it_to_em;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
    }

    public void onClick(View view) {
        switch (view.getId()){

            case R.id.register:
                Intent intent0 = new Intent(this, LoginHome.class);
                startActivity(intent0);
                break;

            case R.id.login:
                Intent intent1 = new Intent(this, LoginActivity.class);
                startActivity(intent1);
                break;
        }
    }
}
