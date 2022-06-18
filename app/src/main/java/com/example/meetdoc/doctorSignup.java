package com.example.meetdoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.example.meetdoc.Models.Doctor;


public class doctorSignup extends AppCompatActivity {


    EditText doctorName,doctorEmail,doctorPassword;
    Spinner doctorDesignation, doctorSpecialization;
    Button registerButton;
    FirebaseAuth auth;
    FirebaseDatabase database;

    String email_pattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_signup);

        doctorName = findViewById(R.id.doctorName);
        doctorEmail = findViewById(R.id.doctorEmail);
        doctorPassword = findViewById(R.id.doctorPassword);
        doctorDesignation = findViewById(R.id.doctorDesignation);
        doctorSpecialization = findViewById(R.id.doctorSpecialization);
        registerButton = findViewById(R.id.registerButton);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();


//        doctorDesignation.setOnItemClickListener(new );

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = doctorName.getText().toString();
                String email = doctorEmail.getText().toString();
                String password = doctorPassword.getText().toString();

                if(name.isEmpty() || email.isEmpty() || password.isEmpty())
                    Toast.makeText(doctorSignup.this, "One or more fields are empty", Toast.LENGTH_SHORT).show();
                else if(!email.matches(email_pattern))
                    doctorEmail.setError("Enter valid email");
                else if(password.length()<6){
                    doctorPassword.setError("Length should be minimum 6");
                }else{
                    auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(doctorSignup.this, "Registration successful", Toast.LENGTH_SHORT).show();

                                //Store data in database
                                DatabaseReference databaseReference = database.getReference().child("user").child(auth.getUid());
                                Doctor current_user = new Doctor(name,auth.getUid(),email);
//                                current_user.setDesignation(doctorDesignation.getSelectedItem().toString());
//                                current_user.setSpecialization(doctorSpecialization.getSelectedItem().toString());
                                databaseReference.setValue(current_user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()) {
                                            startActivity(new Intent(doctorSignup.this,SiginActivity.class));
                                        }else
                                            Toast.makeText(doctorSignup.this, "Unable to create user", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }else{
                                Toast.makeText(doctorSignup.this, "Unable to create user", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });
    }
}