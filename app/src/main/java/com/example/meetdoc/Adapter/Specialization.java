package com.example.meetdoc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.meetdoc.R;

public class Specialization extends BaseAdapter {
    Context context;
    String specializationTopic[];
    int Images[];
    LayoutInflater inflater;

    public Specialization(Context context, String[] specializationTopic, int[] images, LayoutInflater inflater) {
        this.context = context;
        this.specializationTopic = specializationTopic;
        Images = images;
        this.inflater = inflater;
    }

    public Specialization(Context context, String[] specializationTopic, int[] images) {
        this.context = context;
        this.specializationTopic = specializationTopic;
        Images = images;
        this.inflater = (LayoutInflater.from(context));
    }


    @Override
    public int getCount() {
        return specializationTopic.length;
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
        convertView = inflater.inflate(R.layout.specialization_card, null);
        TextView spec_name = convertView.findViewById(R.id.spec_name);
        ImageView spec_img = convertView.findViewById(R.id.spec_img);

        spec_name.setText(specializationTopic[position]);
        spec_img.setImageResource(Images[position]);
        return convertView;
    }
}
