package com.example.meetdoc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Spinner;

import com.example.meetdoc.Adapter.DoctorsAdapter;
import com.example.meetdoc.Adapter.Specialization;
import com.example.meetdoc.Models.Doctor;

import java.util.ArrayList;

public class AddAppointment extends AppCompatActivity {
    Spinner specializationView, doctorsView;
    String specializationList[] = {"Dentist","Cardiologist","ENT","General"};
    int spcializationImgs[] = {R.drawable.tooth, R.drawable.heart, R.drawable.ear, R.drawable.human};

    ArrayList<Doctor> doctors = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);
        doctors.add(new Doctor("adiga", "222", "ap@123"));
        doctors.add(new Doctor("adiga", "222", "ap@123"));

        specializationView = findViewById(R.id.specialization);
        Specialization specialization = new Specialization(getApplicationContext(), specializationList, spcializationImgs);
        specializationView.setAdapter(specialization);

        doctorsView = findViewById(R.id.doctors);
        DoctorsAdapter doctorsAdapter = new DoctorsAdapter(getApplicationContext(), doctors);
        doctorsView.setAdapter(doctorsAdapter);

    }
}