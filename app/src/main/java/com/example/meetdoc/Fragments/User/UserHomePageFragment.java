package com.example.meetdoc.Fragments.User;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.meetdoc.Adapter.AppointmentAdapter;
import com.example.meetdoc.Models.Appointments;
import com.example.meetdoc.R;

import java.util.ArrayList;

public class UserHomePageFragment extends Fragment {



    AppointmentAdapter appointments_adapter;
    ArrayList<Appointments> appointments_list = new ArrayList<>();
    ListView user_appointment_list_view;

    public UserHomePageFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_user_home_page,container,false);
        user_appointment_list_view = view.findViewById(R.id.user_appointment_list_view);

        appointments_list.add(new Appointments(1,2,"Prathvi","Niranjan","14/02/2022","12:00","MD"));
        appointments_list.add(new Appointments(1,2,"Prathvi","Niranjan","14/02/2022","12:00","MD"));
        appointments_list.add(new Appointments(1,2,"Prathvi","Niranjan","14/02/2022","12:00","MD"));
        appointments_adapter = new AppointmentAdapter(getActivity().getApplicationContext(),appointments_list);

        user_appointment_list_view.setAdapter(appointments_adapter);
        return inflater.inflate(R.layout.fragment_user_home_page, container, false);
    }
}