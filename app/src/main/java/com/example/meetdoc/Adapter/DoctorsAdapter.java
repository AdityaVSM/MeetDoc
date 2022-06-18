package com.example.meetdoc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.meetdoc.Models.Doctor;
import com.example.meetdoc.R;

import java.util.ArrayList;

public class DoctorsAdapter extends BaseAdapter {
    Context context;
    ArrayList<Doctor> doctors;
    LayoutInflater inflater;

    public DoctorsAdapter(Context context, ArrayList<Doctor> doctors) {
        this.context = context;
        this.doctors = doctors;
        this.inflater = (LayoutInflater.from(context)) ;
    }


    @Override
    public int getCount() {
        return doctors.toArray().length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.doctor_card, null);
        TextView doctor_name = convertView.findViewById(R.id.doctor_name);
        TextView doctor_spc = convertView.findViewById(R.id.doctor_rating);

        doctor_name.setText(doctors.get(position).getName());
        doctor_spc.setText(String.valueOf(doctors.get(position).getRatings())+" stars");
        return convertView;
    }
}
