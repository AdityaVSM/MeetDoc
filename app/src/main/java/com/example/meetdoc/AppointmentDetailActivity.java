package com.example.meetdoc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.meetdoc.Models.Doctor;

public class AppointmentDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_detail);

        Intent i = getIntent();
        Doctor curr_doctor = (Doctor) i.getSerializableExtra("chosen_doctor");
        System.out.println("in each"+curr_doctor.getName());
    }
}