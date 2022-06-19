package com.example.meetdoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meetdoc.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class userSignup extends AppCompatActivity{
    String[] bloodGroup = { "A+", "A-", "B+", "B-", "O+", "O-", "AB+","AB-"};
    LinearLayout dobLayout;
    Button user_signup_button;
    EditText username, user_email, user_password;
    TextView user_dob;
    Spinner user_bg;
    RadioGroup user_gender;
    FirebaseAuth auth;
    FirebaseDatabase database;
    String dob_str,user_bg_str,user_gender_str;


    String email_pattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signup);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        username = findViewById(R.id.user_name);
        user_email = findViewById(R.id.user_email);
        user_password = findViewById(R.id.user_password);
        user_dob = findViewById(R.id.dat_and_time_picker);
        user_bg = findViewById(R.id.user_bg);
        user_gender = findViewById(R.id.user_gender);
        dobLayout = findViewById(R.id.dobLayout);
        user_signup_button = findViewById(R.id.user_signup_button);
        TextView signin_button = findViewById(R.id.toSignIn);

        signin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(userSignup.this, SiginActivity.class));
            }
        });


        dobLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(userSignup.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dob_str = String.valueOf(dayOfMonth) + "/"+String.valueOf(month+1)+"/" +String.valueOf(year);
                        user_dob.setText(dob_str);
                    }
                },
                        Calendar.getInstance().get(Calendar.YEAR),
                        Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.show();
            }
        });

        user_bg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                user_bg_str = bloodGroup[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                user_bg_str = "A+";
            }
        });

        user_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.male_radio_button:
                        user_gender_str = "male";
                        break;
                    case R.id.female_radio_button:
                        user_gender_str = "female";
                        break;
                    default:user_gender_str="";
                }
            }
        });

        ArrayAdapter blood_group_arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,bloodGroup);
        blood_group_arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        user_bg.setAdapter(blood_group_arrayAdapter);

        user_signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = username.getText().toString();
                String email = user_email.getText().toString();
                String password = user_password.getText().toString();


                if(name.isEmpty() || email.isEmpty() || password.isEmpty())
                    Toast.makeText(userSignup.this, "One or more fields are empty", Toast.LENGTH_SHORT).show();
                else if(!email.matches(email_pattern))
                    user_email.setError("Enter valid email");
                else if(password.length()<6){
                    user_password.setError("Length should be minimum 6");
                }else{
                    auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(userSignup.this, "Registration successful", Toast.LENGTH_SHORT).show();

                                //Store data in database
                                DatabaseReference databaseReference = database.getReference().child("user").child(auth.getUid());
                                User current_user = new User(name,auth.getUid(),email,dob_str,user_bg_str,user_gender_str);
                                databaseReference.setValue(current_user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()) {
                                            startActivity(new Intent(userSignup.this,SiginActivity.class));
                                        }else
                                            Toast.makeText(userSignup.this, "Unable to create user", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }else{
                                Toast.makeText(userSignup.this, "Unable to create user", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }









}