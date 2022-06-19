package com.example.meetdoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.meetdoc.Adapter.DoctorsAdapter;
import com.example.meetdoc.Adapter.Specialization;
import com.example.meetdoc.Models.Doctor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddAppointment extends AppCompatActivity {
    Spinner specializationView, doctorsView;
    String specializationList[] = {"Dentist","Cardiologist","ENT","General"};
    int spcializationImgs[] = {R.drawable.tooth, R.drawable.heart, R.drawable.ear, R.drawable.human};
    ArrayList<Doctor> doctors = new ArrayList<>();
    FirebaseAuth auth;
    FirebaseDatabase database;

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

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        specializationView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String specialization_chosen = specializationList[position].toString();
                getDoctorDetails(database, specialization_chosen);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        doctorsView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "doctors.get(position).getName()", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


}

    private void getDoctorDetails(FirebaseDatabase database, String specialization_chosen) {
        doctors.clear();
        database.getReference().child("doctor").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data_snapshot : snapshot.getChildren()){
                    Doctor curr_doctor = new Doctor();
                    curr_doctor = data_snapshot.getValue(Doctor.class);
                    System.out.println("in"+specialization_chosen);
                    System.out.println("curr"+curr_doctor.getSpecialization());
                    if(specialization_chosen.equals(curr_doctor.getSpecialization().toString())) {
                        System.out.println("doctor for particular spec"+curr_doctor.getName().toString());
                        doctors.add(curr_doctor);
                    }
                }
                DoctorsAdapter doctorsAdapter = new DoctorsAdapter(getApplicationContext(), doctors);
                doctorsView.setAdapter(doctorsAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}