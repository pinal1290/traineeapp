package com.example.softices.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.softices.ChangePasswordActivity;
import com.example.softices.R;
import com.example.softices.database.DatabaseHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class forgetpasswordActivity extends AppCompatActivity {
    TextView txtforgetpass;
    EditText edtemail;
    Button btnforgetpassword;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);
        init();
        btnforgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtemail.getText().toString();
                if (!isValidEmail(email))
                    edtemail.setError("Please enter valid email");
                if (databaseHelper.CheckUser(email)) {
                    Toast.makeText(getApplicationContext(), "valid email id...", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(forgetpasswordActivity.this, ChangePasswordActivity.class);
                    startActivity(intent);
                }else
                    Toast.makeText(getApplicationContext(),"email are not valid",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void init() {
        edtemail = findViewById(R.id.edt_email);
        txtforgetpass = findViewById(R.id.txt_forget_password);
        btnforgetpassword = findViewById(R.id.btn_forget_password);
        databaseHelper = new DatabaseHelper(this);
        edtemail.setText("pooja@gmail.com");

    }

    boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
