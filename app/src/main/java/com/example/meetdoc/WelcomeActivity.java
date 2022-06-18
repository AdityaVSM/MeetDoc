package com.example.meetdoc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    Button register_as_doctor_button, register_as_User_button;
    TextView signin_textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        register_as_doctor_button = findViewById(R.id.register_as_doctor_button);
        register_as_User_button = findViewById(R.id.register_as_User_button);
        signin_textView = findViewById(R.id.signin_textView);

        register_as_doctor_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this,doctorSignup.class));
            }
        });

        register_as_User_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this,userSignup.class));
            }
        });

        signin_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this,SiginActivity.class));
            }
        });

    }
}