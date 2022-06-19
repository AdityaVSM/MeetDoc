package com.example.meetdoc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.widget.TextView;

public class AiDiseasePredictorResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_disease_predictor_result);
        Intent i = getIntent();
        String txtData = i.getExtras().getString("key1","");
        TextView txtInput2 = (TextView)findViewById(R.id.results);
        String arr[]=txtData.split("/");
        System.out.println(arr[0]);

        String output="Based on your symptoms we advice you to "+arr[1]+
                " We believe there might be a possibility of you having "+arr[0]+
                " These analysis might not be accurate so we advice you to consult doctor if symptoms persists";
        txtInput2.setText(output);
    }
}