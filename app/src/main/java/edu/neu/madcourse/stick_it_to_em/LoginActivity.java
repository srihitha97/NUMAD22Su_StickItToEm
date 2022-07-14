package edu.neu.madcourse.stick_it_to_em;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    public String name;
    private static Button login;
    boolean begin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        begin = true;
        login = findViewById(R.id.login);
        login.setOnClickListener(view -> {
            name = ((EditText) findViewById(R.id.username)).getText().toString();
            if (name.equals("")) {
                Toast.makeText(this, "Username can't be empty!", Toast.LENGTH_SHORT).show();
            } else{
                login_user();
            }
        });
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


    private void login_user() {
        new Thread(() -> {
            DatabaseReference myUserRef = FirebaseDatabase.getInstance().getReference(
                    "Users/" + name);

            myUserRef.addValueEventListener(new ValueEventListener() {
                public Users user;
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        user = dataSnapshot.getValue(Users.class);
                        Toast.makeText(LoginActivity.this, "Logged you in.",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        myUserRef.setValue(new Users(name));

                        Toast.makeText(LoginActivity.this, "New user created",
                                Toast.LENGTH_SHORT).show();
                    }
                    //startActivity(new Intent(LoginActivity.this, StartActivity.class));

                    Intent login = new Intent(LoginActivity.this,
                            StartActivity.class);

                    login.putExtra("username", name);

                    startActivity(login);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.w( "onCancelled",
                            databaseError.toException());
                }
            });
        }).start();
    }
}