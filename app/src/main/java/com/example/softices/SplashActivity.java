package com.example.softices;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

public class SplashActivity extends AppCompatActivity {
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        final boolean islogin = sharedPreferences.getBoolean("is_session_login", false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i;
                if (islogin)
                    i = new Intent(SplashActivity.this, DashboardActivity.class);
                else
                    i = new Intent(SplashActivity.this, SignInActivity.class);
                startActivity(i);
                finish();
            }
        }, 1000);

    }
}
