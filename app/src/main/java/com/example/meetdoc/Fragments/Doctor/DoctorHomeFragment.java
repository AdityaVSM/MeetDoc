package com.example.meetdoc.Fragments.Doctor;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.meetdoc.Adapter.AppointmentAdapter;
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

    FirebaseAuth auth;
    FirebaseDatabase database;
    TextView doctor_name;
    ListView doctor_appointment_list_view;
    ArrayList<Appointments> appointments_list = new ArrayList<>();
    AppointmentAdapter appointments_adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_doctor_home, container, false);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        doctor_name = view.findViewById(R.id.doctor_name);
        doctor_appointment_list_view = view.findViewById(R.id.doctor_appointment_list_view);

        setDataFromFirebase(database);
        getAppointmentDetails(database);

        appointments_adapter = new AppointmentAdapter(getActivity().getApplicationContext(),appointments_list);
        doctor_appointment_list_view.setAdapter(appointments_adapter);

        return view;
    }

    private void setDataFromFirebase(FirebaseDatabase database) {
        database.getReference().child("doctor").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data_snapshot : snapshot.getChildren()){
                    Doctor doctor = new Doctor();
                    if(data_snapshot.child("uid").getValue().toString().equals(auth.getCurrentUser().getUid())) {
                        doctor = data_snapshot.getValue(Doctor.class);
                        System.out.println("doctor"+doctor.getName());
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

    private void getAppointmentDetails(FirebaseDatabase database){
        database.getReference().child("appointments").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                appointments_list.clear();
                for (DataSnapshot data_Snapshot : snapshot.getChildren()){
                    if(data_Snapshot.child("doctor_id").getValue().toString().equals(auth.getCurrentUser().getUid())){
                        String patient_id = data_Snapshot.child("patient_id").getValue().toString();
                        String doctor_id = data_Snapshot.child("doctor_id").getValue().toString();
                        String patient_name = data_Snapshot.child("patient_name").getValue().toString();
                        String doctor_name = data_Snapshot.child("doctor_name").getValue().toString();
                        String doctor_specialization = data_Snapshot.child("doctor_specialization").getValue().toString();
                        String slot_date = data_Snapshot.child("slot_date").getValue().toString();
                        String slot_time = data_Snapshot.child("slot_time").getValue().toString();
                        long accepted = (long) data_Snapshot.child("accepted").getValue();

                        Appointments curr_appointment = new Appointments(patient_id,doctor_id,patient_name,doctor_name,slot_date,slot_time,doctor_specialization);
                        curr_appointment.setAccepted(accepted);
                        appointments_list.add(curr_appointment);
                        System.out.println(curr_appointment.getDoctor_name());
                    }
                }
                appointments_adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}