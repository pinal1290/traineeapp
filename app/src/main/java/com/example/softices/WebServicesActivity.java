package com.example.softices;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WebServicesActivity extends AppCompatActivity {
Button btnget,btnpost,btnput,btndelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_services);
        btnget=findViewById(R.id.btn_get);
        btnpost=findViewById(R.id.btn_post);
        btnput=findViewById(R.id.btn_put);
        btndelete=findViewById(R.id.btn_delete);
        btnget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(WebServicesActivity.this,Get.class);
                startActivity(intent);
            }
        });
        btnpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(WebServicesActivity.this,Post.class);
                startActivity(intent);
            }
        });
        btnput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(WebServicesActivity.this,Put.class);
                startActivity(intent);
            }
        });
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(WebServicesActivity.this,Delete.class);
                startActivity(intent);
            }
        });
    }
}
