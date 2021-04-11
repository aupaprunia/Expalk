package com.bitcamp.expalk.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.bitcamp.expalk.R;
import com.bitcamp.expalk.fragment.ConnectionFragment;
import com.bitcamp.expalk.fragment.MentorFragment;
import com.bitcamp.expalk.fragment.ProfileFragment;
import com.bitcamp.expalk.fragment.MenteeFragment;
import com.bitcamp.expalk.util.Constants;
import com.bitcamp.expalk.util.NetworkConnectivityHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;


public class HomeActivity extends AppCompatActivity {

    public static BottomNavigationView bottomNavigationView;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sharedPreferences = getSharedPreferences(Constants.MY_PREF,MODE_PRIVATE);

        new NetworkConnectivityHelper(this).startNetworkCallBack();

        FirebaseMessaging.getInstance().subscribeToTopic(sharedPreferences.getString(Constants.UID,"guest"))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });

        bottomNavigationView = findViewById(R.id.bottomNavigation);

        bottomNavigationView.setSelectedItemId(R.id.nav_profile);
        openProfile();


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if(item.getItemId() == R.id.nav_profile){
                    openProfile();
                }else if(item.getItemId() == R.id.nav_mentee){
                    openSpeaker();
                }else if(item.getItemId() == R.id.nav_connection){
                    openConnections();
                }else{
                    openListener();
                }

                return true;
            }
        });
    }

    private void openProfile(){
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fadein, R.anim.fadeout)
                .replace(R.id.frameLayout, new ProfileFragment()).commit();
    }

    private void openSpeaker(){
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fadein, R.anim.fadeout)
                .replace(R.id.frameLayout, new MenteeFragment()).commit();
    }

    private void openListener(){
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fadein, R.anim.fadeout)
                .replace(R.id.frameLayout, new MentorFragment()).commit();
    }

    private void openConnections(){
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fadein, R.anim.fadeout)
                .replace(R.id.frameLayout, new ConnectionFragment()).commit();
    }
}