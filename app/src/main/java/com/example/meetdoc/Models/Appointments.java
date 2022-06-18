package com.example.meetdoc.Models;

public class Appointments {
    int patient_id, doctor_id, accepted;
    String patient_name, doctor_name, slot_date, slot_time, doctor_specialization;

    public Appointments(int patient_id, int doctor_id,String patient_name, String doctor_name, String slot_date, String slot_time, String doctor_specialization) {
        this.patient_id = patient_id;
        this.doctor_id = doctor_id;
        this.accepted = 2;
        this.patient_name = patient_name;
        this.doctor_name = doctor_name;
        this.slot_date = slot_date;
        this.slot_time = slot_time;
        this.doctor_specialization = doctor_specialization;
    }

    public String getDoctor_specialization() {
        return doctor_specialization;
    }

    public void setDoctor_specialization(String doctor_specialization) {
        this.doctor_specialization = doctor_specialization;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    public int getAccepted() {
        return accepted;
    }

    public void setAccepted(int accepted) {
        this.accepted = accepted;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getSlot_date() {
        return slot_date;
    }

    public void setSlot_date(String slot_date) {
        this.slot_date = slot_date;
    }

    public String getSlot_time() {
        return slot_time;
    }

    public void setSlot_time(String slot_time) {
        this.slot_time = slot_time;
    }
}
