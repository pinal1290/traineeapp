package com.example.softices.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.softices.R;

public class WebServicesActivity extends AppCompatActivity {
    Button btnget, btnpost, btnput, btndelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_services);

        init();
        /**
         * one activity to another activity open
         */
        btnget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WebServicesActivity.this, GetActivity.class);
                startActivity(intent);
            }
        });
        btnpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WebServicesActivity.this, PostActivity.class);
                startActivity(intent);
            }
        });
        btnput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WebServicesActivity.this, PutActivity.class);
                startActivity(intent);
            }
        });
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WebServicesActivity.this, DeleteActivity.class);
                startActivity(intent);
            }
        });
    }

    public void init() {
        btnget = findViewById(R.id.btn_get);
        btnpost = findViewById(R.id.btn_post);
        btnput = findViewById(R.id.btn_put);
        btndelete = findViewById(R.id.btn_delete);
    }
}