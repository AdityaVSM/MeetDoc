package com.example.meetdoc.Models;

import java.io.Serializable;

public class Doctor implements Serializable {
    String name,uid,email,designation,specialization;
    int ratings,no_of_people_to_treat;
    boolean user;

    public Doctor(){}

    public Doctor(String name, String uid, String email) {
        this.name = name;
        this.uid = uid;
        this.email = email;
        this.no_of_people_to_treat = 0;
    }

    public boolean isUser() {
        return user;
    }

    public void setUser(boolean user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public int getRatings() {
        return ratings;
    }

    public void setRatings(int ratings) {
        this.ratings = ratings;
    }

    public int getNo_of_people_to_treat() {
        return no_of_people_to_treat;
    }

    public void setNo_of_people_to_treat(int no_of_people_to_treat) {
        this.no_of_people_to_treat = no_of_people_to_treat;
    }
}
