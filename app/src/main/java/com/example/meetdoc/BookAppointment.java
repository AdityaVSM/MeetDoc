package com.example.meetdoc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.meetdoc.Models.Appointments;
import com.example.meetdoc.Models.Doctor;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class BookAppointment extends AppCompatActivity {

    TextView doctor_name, doctor_specialization, date_textView, time_textView;
    Button submit_button;
    ImageView date_icon, time_icon;
    String date,time;

    FirebaseAuth auth;
    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        doctor_name = findViewById(R.id.doctor_name);
        doctor_specialization = findViewById(R.id.doctor_specialization);
        LinearLayout dateLayout = findViewById(R.id.date_Layout);
        LinearLayout timeLayout = findViewById(R.id.time_Layout);
        date_textView = findViewById(R.id.date_textView);
        time_textView = findViewById(R.id.time_textView);
        submit_button = findViewById(R.id.submit_button);
        date_icon = findViewById(R.id.date_icon);
        time_icon = findViewById(R.id.time_icon);

        Intent i = getIntent();
        Doctor curr_doctor = (Doctor) i.getSerializableExtra("chosen_doctor");
        String user_name = i.getStringExtra("current_user");

        doctor_name.setText(curr_doctor.getName());
        doctor_specialization.setText(curr_doctor.getSpecialization());

        date_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(BookAppointment.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date = String.valueOf(dayOfMonth) + "/"+String.valueOf(month+1)+"/" +String.valueOf(year);
                        date_textView.setText(date);
                    }
                },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.show();
            }
        });

        time_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(BookAppointment.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        time = String.valueOf(hourOfDay)+":"+String.valueOf(minute);
                        time_textView.setText(time);
                    }
                },Calendar.HOUR_OF_DAY,Calendar.MINUTE,false);
                timePickerDialog.show();
            }
        });

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String patient_id = auth.getCurrentUser().getUid();

                Appointments current_appointment = new Appointments(
                        patient_id,
                        curr_doctor.getUid(),
                        user_name,
                        curr_doctor.getName(),
                        date,time,
                        curr_doctor.getSpecialization());
                DatabaseReference ref = database.getReference().child("appointments");
                ref.child(patient_id+curr_doctor.getUid()).setValue(current_appointment).addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        Toast.makeText(BookAppointment.this, "Appointment requested", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        });
    }
}