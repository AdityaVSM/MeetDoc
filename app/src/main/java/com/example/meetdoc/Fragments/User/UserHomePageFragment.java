package com.example.meetdoc.Fragments.User;

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
import com.example.meetdoc.AiDiseasePredictorActivity;
import com.example.meetdoc.Models.Appointments;
import com.example.meetdoc.Models.User;
import com.example.meetdoc.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserHomePageFragment extends Fragment {

    AppointmentAdapter appointments_adapter;
    ArrayList<Appointments> appointments_list = new ArrayList<>();
    ListView user_appointment_list_view;
    ImageView add_appointment;
    FirebaseAuth auth;
    FirebaseDatabase database;
    TextView user_name;
    FloatingActionButton aiButton;

    public UserHomePageFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_user_home_page,container,false);
        user_appointment_list_view = view.findViewById(R.id.user_appointment_list_view);
        add_appointment = view.findViewById(R.id.add_appointment);
        user_name = view.findViewById(R.id.user_name);
        aiButton = view.findViewById(R.id.ai_button);

        aiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity().getApplicationContext(), AiDiseasePredictorActivity.class));
            }
        });

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        getAppointmentDetails(database);

        appointments_adapter = new AppointmentAdapter(getActivity().getApplicationContext(),appointments_list);
        user_appointment_list_view.setAdapter(appointments_adapter);

        add_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity().getApplicationContext(), AddAppointment.class));
            }
        });
        setDataFromFirebase(database);
        return view;
    }

    private void setDataFromFirebase(FirebaseDatabase database) {
        ArrayList<User> user_arraylist;
        database.getReference().child("user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data_snapshot : snapshot.getChildren()){
                    User user = new User();
                    if(data_snapshot.child("user_id").getValue().toString().equals(auth.getCurrentUser().getUid())) {
                        user = data_snapshot.getValue(User.class);
                        user_name.setText(user.getUser_name().toString());
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
                    if(data_Snapshot.child("patient_id").getValue().toString().equals(auth.getCurrentUser().getUid())){
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