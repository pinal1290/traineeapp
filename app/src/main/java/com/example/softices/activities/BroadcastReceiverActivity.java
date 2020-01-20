package com.example.softices.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.content.IntentFilter;

import com.example.softices.R;

public class BroadcastReceiverActivity extends AppCompatActivity {

    BroadcastReceiver battery = new BroadcastReceiver() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra("level", 0);
            ProgressBar pgb = findViewById(R.id.pgb_battery);
            pgb.setProgress(level);
            TextView txt = findViewById(R.id.txt_bettery);
            txt.setText("battery percentage:" + (level) + "%");
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_receiver);
        registerReceiver(battery, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }
}