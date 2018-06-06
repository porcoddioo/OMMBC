package com.example.alessandro.testbottom;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ActivityLauncher extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;
    private SharedPreferences mPrefs;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        mPrefs = getSharedPreferences("label", 0);
        token = mPrefs.getString("token",null);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(token==null){
                Intent homeIntent = new Intent(ActivityLauncher.this, ActivityLogin.class);
                startActivity(homeIntent);}
                else{
                    Intent homeIntent = new Intent(ActivityLauncher.this, MainActivity.class);
                    startActivity(homeIntent);
                }
                finish();
            }
        },SPLASH_TIME_OUT);
    }
}