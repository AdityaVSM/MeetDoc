package com.example.meetdoc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

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
        doctors.add(new Doctor("adiga2", "222", "ap@123"));

        specializationView = findViewById(R.id.specialization);
        Specialization specialization = new Specialization(getApplicationContext(), specializationList, spcializationImgs);
        specializationView.setAdapter(specialization);

        doctorsView = findViewById(R.id.doctors);
        DoctorsAdapter doctorsAdapter = new DoctorsAdapter(getApplicationContext(), doctors);
        doctorsView.setAdapter(doctorsAdapter);

        specializationView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        doctorsView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), doctors.get(position).getName(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}