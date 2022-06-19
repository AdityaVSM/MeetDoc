package com.example.meetdoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.example.meetdoc.Models.Doctor;


public class doctorSignup extends AppCompatActivity {
    String[] designation = {"Doctorate of Medicine", "Master of Chirurgiae" , "Bachelor of Medicine, Bachelor of Surgery", "Master of Surgery"};
    String[] specializations = {"Dentist","Cardiologist","ENT","General"};

    EditText doctorName,doctorEmail,doctorPassword;
    Spinner doctorDesignation, doctorSpecialization;
    Button registerButton;
    FirebaseAuth auth;
    FirebaseDatabase database;

    String doctor_designation_str, doctor_specialization_str;

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
        TextView signin_button = findViewById(R.id.toSignIn);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();



        Spinner doctorDesignationSpinner = findViewById(R.id.doctorDesignation);
        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,designation);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        doctorDesignationSpinner.setAdapter(aa);

        Spinner doctorSpecializationSpinner = findViewById(R.id.doctorSpecialization);
        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter ab = new ArrayAdapter(this,android.R.layout.simple_spinner_item,specializations);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        doctorSpecializationSpinner.setAdapter(ab);

        doctorDesignationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                doctor_designation_str = designation[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                doctor_designation_str = "Doctorate of Medicine";
            }
        });
        doctorSpecializationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                doctor_specialization_str = specializations[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                doctor_specialization_str = "Aerospace Medicine";
            }
        });

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
                                DatabaseReference databaseReference = database.getReference().child("doctor").child(auth.getUid());
                                Doctor current_doctor = new Doctor(name,auth.getUid(),email);
                                current_doctor.setSpecialization(doctor_specialization_str);
                                current_doctor.setDesignation(doctor_designation_str);
                                current_doctor.setUser(false);
                                databaseReference.setValue(current_doctor).addOnCompleteListener(new OnCompleteListener<Void>() {
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



        signin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(doctorSignup.this, SiginActivity.class));
            }
        });

    }

}