package com.example.softices;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class UserActivity extends AppCompatActivity {
    RecyclerView rcvusers;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        databaseHelper = new DatabaseHelper(this);
        rcvusers = findViewById(R.id.rcvusers);
        getUser();
    }

    public void getUser() {
        AllUserAdapter userAdapter = new AllUserAdapter(databaseHelper.fetchUser(), this);
        rcvusers.setLayoutManager(new LinearLayoutManager(this));
        rcvusers.setAdapter(userAdapter);
    }
}

