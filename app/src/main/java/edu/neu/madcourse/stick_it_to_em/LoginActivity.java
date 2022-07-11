package edu.neu.madcourse.stick_it_to_em;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    public String name;
    private static Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        login = findViewById(R.id.login);
        login.setOnClickListener(view -> {
            name = ((EditText) findViewById(R.id.username)).getText().toString();
            login_user();
        });
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
                    startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
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