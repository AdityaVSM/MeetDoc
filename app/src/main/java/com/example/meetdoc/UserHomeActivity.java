package com.example.meetdoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.meetdoc.Fragments.User.UserHomePageFragment;
import com.example.meetdoc.Fragments.User.UserProfileFragment;
import com.example.meetdoc.Fragments.User.UserSettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class UserHomeActivity extends AppCompatActivity {

    FirebaseAuth auth;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        bottomNavigationView = findViewById(R.id.bottom_navigation_bar);

        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser()==null){
            startActivity(new Intent(UserHomeActivity.this,SiginActivity.class));
        }

        UserHomePageFragment homeFragment = new UserHomePageFragment();
        UserSettingsFragment settingFragment = new UserSettingsFragment();
        UserProfileFragment profileFragment = new UserProfileFragment();


        bottomNavigationView = findViewById(R.id.bottom_navigation_bar);
        bottomNavigationView.setSelectedItemId(R.id.home);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager mFragmentManager = getSupportFragmentManager();
                switch (item.getItemId()){
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                        return true;
                    case R.id.portfolio:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, profileFragment).commit();
                        return true;
                    case R.id.settings:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, settingFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }
}