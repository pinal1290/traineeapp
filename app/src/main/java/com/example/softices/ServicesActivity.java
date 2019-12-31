package com.example.softices;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ServicesActivity extends AppCompatActivity implements View.OnClickListener {
    private Button buttonStart;
    private Button buttonStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        buttonStart = findViewById(R.id.btn_start);
        buttonStop = findViewById(R.id.btn_stop);

        buttonStart.setOnClickListener(this);
        buttonStop.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == buttonStart) {
            startService(new Intent(this, MyService.class));
        } else if (view == buttonStop) {
            stopService(new Intent(this, MyService.class));
        }
    }
}
