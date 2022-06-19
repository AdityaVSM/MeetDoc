package com.example.meetdoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.os.Bundle;
import android.widget.Spinner;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;
import android.content.Intent;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AiDiseasePredictorActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {
    Spinner s1,s2,s3,s4;
    Button b;
    String []symptom1={
            "high_fever","vomiting","skin_rash","nausea","fatigue","cough"
    };
    private OkHttpClient okhttp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_disease_predictor);
        s1 = (Spinner) findViewById(R.id.spin1);
        s2 = (Spinner) findViewById(R.id.spin2);
        s3 = (Spinner) findViewById(R.id.spin3);
        s4 = (Spinner) findViewById(R.id.spin4);


        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, symptom1);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s1.setAdapter(aa);
        s2.setAdapter(aa);
        s3.setAdapter(aa);
        s4.setAdapter(aa);
        b=findViewById(R.id.ai_button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String symptom1=s1.getSelectedItem().toString();
                String symptom2=s2.getSelectedItem().toString();
                String symptom3=s3.getSelectedItem().toString();
                String symptom4=s4.getSelectedItem().toString();
                okhttp=new OkHttpClient();
                RequestBody body=new FormBody.Builder().add("Symptom1",symptom1).
                        add("Symptom3",symptom2)
                        .add("Symptom2",symptom3)
                        .add("Symptom4",symptom4)
                        .build();
                Request request=new Request.Builder().url("https://niru150789.pythonanywhere.com/api/v1/preprocess").post(body).build();
                okhttp.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println(e.toString());
                                Toast.makeText(getApplicationContext(), "Serverdown"+e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        String k=response.body().string();
                        if(response.isSuccessful()){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "Successful "+k, Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(AiDiseasePredictorActivity.this, AiDiseasePredictorResultActivity.class);
                                    intent.putExtra("key1",k);
                                    startActivity(intent);
                                }
                            });
                        }
                    }
                });

            }
        });

    }
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        Toast.makeText(getApplicationContext(),symptom1[position] , Toast.LENGTH_LONG).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }
}