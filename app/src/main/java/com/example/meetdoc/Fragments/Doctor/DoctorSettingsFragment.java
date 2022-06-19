package com.example.meetdoc.Fragments.Doctor;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.meetdoc.R;
import com.example.meetdoc.WelcomeActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;

public class DoctorSettingsFragment extends Fragment {

    FirebaseAuth auth;
    ImageButton logout_button;
    public DoctorSettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_doctor_settings, container, false);

        auth = FirebaseAuth.getInstance();
        logout_button = view.findViewById(R.id.logout_button);

        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialAlertDialogBuilder(getActivity())
                        .setTitle("Confirm logout")
                        .setMessage("Are you sure want to logout from this account")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                auth.signOut();
                                startActivity(new Intent(getActivity().getApplicationContext(), WelcomeActivity.class));
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
            }
        });
        return view;
    }
}