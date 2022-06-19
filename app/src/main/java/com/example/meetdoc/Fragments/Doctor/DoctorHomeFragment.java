package com.example.meetdoc.Fragments.Doctor;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.meetdoc.Adapter.AppointmentAdapter;
import com.example.meetdoc.AddAppointment;
import com.example.meetdoc.Models.Appointments;
import com.example.meetdoc.Models.Doctor;
import com.example.meetdoc.Models.User;
import com.example.meetdoc.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;



public class DoctorHomeFragment extends Fragment {

    AppointmentAdapter appointments_adapter;
    ArrayList<Appointments> appointments_list = new ArrayList<>();
    ListView doctor_appointment_list_view;
    ImageView add_appointment;
    FirebaseAuth auth;
    FirebaseDatabase database;
    TextView doctor_name;

    public DoctorHomeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_doctor_home,container,false);
        doctor_appointment_list_view = view.findViewById(R.id.doctor_appointment_list_view);

        doctor_name = view.findViewById(R.id.doctor_name);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();


        appointments_adapter = new AppointmentAdapter(getActivity().getApplicationContext(),appointments_list);
        doctor_appointment_list_view.setAdapter(appointments_adapter);


        setDataFromFirebase(database);
        return view;
    }

    private void setDataFromFirebase(FirebaseDatabase database) {
        appointments_list.clear();
        database.getReference().child("doctor").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data_snapshot : snapshot.getChildren()){
                    Doctor doctor = new Doctor();
                    if(data_snapshot.child("uid").getValue().toString().equals(auth.getCurrentUser().getUid())) {
                        doctor = data_snapshot.getValue(Doctor.class);
                        doctor_name.setText(doctor.getName());
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}