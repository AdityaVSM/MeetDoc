package com.example.meetdoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SiginActivity extends AppCompatActivity {

    EditText login_email, login_password;
    Button login_button;
    FirebaseAuth auth;

    String email_pattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sigin);

        login_email = findViewById(R.id.login_email);
        login_password = findViewById(R.id.login_password);
        login_button = findViewById(R.id.login_button);
        TextView signup_button = findViewById(R.id.toSignUp);

        auth = FirebaseAuth.getInstance();

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = login_email.getText().toString();
                String password =login_password.getText().toString();

                if(email.isEmpty() && password.isEmpty()){
                    login_email.setError("This field can't be empty");
                    login_password.setError("This field can't be empty");
                }
                else if(email.isEmpty())
                    login_email.setError("This field can't be empty");
                else if(password.isEmpty())
                    login_password.setError("This field can't be empty");
                else if(!email.matches(email_pattern))
                    login_email.setError("Enter valid email");
                else if(password.length()<6)
                    login_password.setError("Length should be minimum 6");
                else{
                    auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                startActivity(new Intent(SiginActivity.this,MainActivity.class));
                            }else{
                                Toast.makeText(SiginActivity.this, "Error while logging in", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SiginActivity.this, userSignup.class));
            }
        });
    }
}