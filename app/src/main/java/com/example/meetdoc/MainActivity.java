package com.example.meetdoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();


        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        if(auth.getCurrentUser()==null)
            startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
        else {
            database.getReference().child("user");
            checkAnswerSubmission(new SimpleCallback<Boolean>() {
                @Override
                public void callback(Boolean data) {
                    System.out.println("data"+data);
                    if(data)
                        startActivity(new Intent(MainActivity.this,UserHomeActivity.class));
                    else
                        startActivity(new Intent(MainActivity.this,DoctorHomeActivity.class));
                }
            });
        }
    }
    private void checkAnswerSubmission(@NonNull SimpleCallback<Boolean> finishedCallback) {
        DatabaseReference answerDatabase = FirebaseDatabase.getInstance().getReference().child("user");
        answerDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This will simple call your callback containing a boolean true/false
                for(DataSnapshot data_snapshot: dataSnapshot.getChildren()){
                    String uid = data_snapshot.child("user_id").getValue().toString();
                    if(uid.equals(auth.getCurrentUser().getUid().toString())) {
                        System.out.println("exists");
                        finishedCallback.callback(true);
                    }else{
                        System.out.println("Not a user");
                        finishedCallback.callback(false);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}