package com.example.meetdoc.Fragments.Doctor;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meetdoc.Models.Doctor;
import com.example.meetdoc.Models.User;
import com.example.meetdoc.R;
import com.example.meetdoc.UserHomeActivity;
import com.example.meetdoc.userSignup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class DoctorProfileFragment extends Fragment {

    EditText name, email, designation;
    ImageView namePencil,dobPencil,emailPencil,phoneNumberPencil;
    FirebaseAuth auth;
    FirebaseDatabase database;
    Button submit_button, cancel_button;

    public DoctorProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_doctor_profile,container,false);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        name = view.findViewById(R.id.name);
        email = view.findViewById(R.id.email);
        designation = view.findViewById(R.id.designation);
        submit_button = view.findViewById(R.id.submit_button);
        cancel_button = view.findViewById(R.id.cancel_button);

        namePencil = view.findViewById(R.id.namePencil);
        dobPencil = view.findViewById(R.id.dobPencil);
        emailPencil = view.findViewById(R.id.emailPencil);
        phoneNumberPencil = view.findViewById(R.id.phoneNumberPencil);

        enableTextFields(namePencil,name);
        enableTextFields(emailPencil,email);
        enableTextFields(phoneNumberPencil,designation);

        setDataFromFirebase(database);

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDataInFirebase(database);
            }
        });

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(), "No changes were made", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }


    private void enableTextFields(ImageView itemPencil, EditText item) {
        itemPencil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setEnabled(true);
                item.requestFocus();
            }
        });
    }

    private void setDataFromFirebase(FirebaseDatabase database) {
        ArrayList<User> user_arraylist;
        database.getReference().child("doctor").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data_snapshot : snapshot.getChildren()){
                    Doctor doctor = new Doctor();
                    if(data_snapshot.child("uid").getValue().toString().equals(auth.getCurrentUser().getUid())) {
                        doctor = data_snapshot.getValue(Doctor.class);
                        name.setText(doctor.getName());
                        email.setText(doctor.getEmail());
                        designation.setText(doctor.getDesignation());
                        break;
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void updateDataInFirebase(FirebaseDatabase database){
        DatabaseReference ref = database.getReference().child("user").child(auth.getCurrentUser().getUid().toString());
        ref.child("name").setValue(name.getText().toString());
        ref.child("email").setValue(email.getText().toString());
        ref.child("designation").setValue(designation.getText().toString());
        Toast.makeText(getActivity().getApplicationContext(), "Details updated successfully", Toast.LENGTH_SHORT).show();
    }

}