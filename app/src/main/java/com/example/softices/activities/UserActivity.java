package com.example.softices.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.softices.R;
import com.example.softices.activities.DatabaseHelper;
import com.example.softices.adapter.AllUserAdapter;

public class UserActivity extends AppCompatActivity {
    RecyclerView rcvusers;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        init();
        getUser();
    }

    public void init() {
        databaseHelper = new DatabaseHelper(this);
        rcvusers = findViewById(R.id.rcvusers);
    }

    /**
     * getUser are use to display login user list
     */
    public void getUser() {
        AllUserAdapter userAdapter = new AllUserAdapter(databaseHelper.fetchUser(), this);
        rcvusers.setLayoutManager(new LinearLayoutManager(this));
        rcvusers.setAdapter(userAdapter);
    }
}