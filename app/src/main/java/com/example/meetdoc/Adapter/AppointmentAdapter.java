package com.example.meetdoc.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.meetdoc.Models.Appointments;
import com.example.meetdoc.R;

import java.util.ArrayList;

public class AppointmentAdapter extends ArrayAdapter<Appointments> {

    public AppointmentAdapter(@NonNull Context context, ArrayList<Appointments> resource) {
        super(context,0,resource);
    }

    @SuppressLint("ResourceAsColor")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View currentItemView = convertView;

        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.appointment_card, parent, false);
        }

        // get the position of the view from the ArrayAdapter
        Appointments currentAppointment = getItem(position);

        assert currentAppointment != null;

        TextView doctor_spc = currentItemView.findViewById(R.id.doctor_spc);
        TextView dr_name = currentItemView.findViewById(R.id.dr_name);
        TextView status = currentItemView.findViewById(R.id.status);
        TextView time = currentItemView.findViewById(R.id.time);
        LinearLayout appointment_card = currentItemView.findViewById(R.id.appointment_card);


        doctor_spc.setText(currentAppointment.getDoctor_specialization());
        dr_name.setText(currentAppointment.getDoctor_name());
        if(currentAppointment.getAccepted() == 2){
            appointment_card.setBackgroundColor(Color.parseColor("#d7b6ff"));
            status.setText("Status: Waiting");
        }else if (currentAppointment.getAccepted() == 1){
            appointment_card.setBackgroundColor(Color.parseColor("#B6FFCE"));
            status.setText("Status: Accepted");
        }else{
            appointment_card.setBackgroundColor(Color.parseColor("#ffb6b6"));
            status.setText("Status: Declined");
        }

        time.setText(currentAppointment.getSlot_date() +currentAppointment.getSlot_time());

        // then return the recyclable view
        return currentItemView;
    }
}
