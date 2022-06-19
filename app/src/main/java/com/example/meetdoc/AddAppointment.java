package com.example.meetdoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.meetdoc.Adapter.DoctorsAdapter;
import com.example.meetdoc.Adapter.Specialization;
import com.example.meetdoc.Models.Doctor;
import com.example.meetdoc.Models.User;
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
    FirebaseAuth auth;
    FirebaseDatabase database;
    ArrayList<Doctor> doctors_arr = new ArrayList<>();
    String specialization_chosen;
    Doctor doctor_chosen;
    Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);

        nextButton = findViewById(R.id.nextButton);
        specializationView = findViewById(R.id.specialization);
        doctorsView = findViewById(R.id.doctors);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        Specialization specialization = new Specialization(getApplicationContext(), specializationList, spcializationImgs);
        specializationView.setAdapter(specialization);

        getDoctorDetails(database);

        DoctorsAdapter doctorsAdapter = new DoctorsAdapter(getApplicationContext(), doctors_arr);
        doctorsView.setAdapter(doctorsAdapter);

        specializationView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("Specialization selected");
                System.out.println(specializationList[position].toString());
                specialization_chosen = specializationList[position].toString();
                getDoctorDetails(database);
                DoctorsAdapter doctorsAdapter = new DoctorsAdapter(getApplicationContext(), doctors_arr);
                doctorsView.setAdapter(doctorsAdapter);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        System.out.println(doctorsView.getSelectedItem());
        doctorsView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                doctorsView.setSelection(position);
                doctorsAdapter.notifyDataSetChanged();
                System.out.println("doctor item selected");
                if(doctors_arr.size()==0){
                    Toast.makeText(AddAppointment.this, "Please select a doctor", Toast.LENGTH_SHORT).show();
                }else{
                    doctor_chosen = doctors_arr.get(position);
                    System.out.println("in chosen doctor"+doctor_chosen.getName());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(doctor_chosen!=null){
                    Intent i = new Intent(AddAppointment.this,AppointmentDetailActivity.class);
                    i.putExtra("chosen_doctor",doctor_chosen);
                    startActivity(i);
                }
            }
        });




    }

    private void getDoctorDetails(FirebaseDatabase database) {
        doctors_arr.clear();
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
                        doctors_arr.add(curr_doctor);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}