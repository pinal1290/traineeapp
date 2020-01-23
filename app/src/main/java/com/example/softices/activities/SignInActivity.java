package com.example.softices.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.softices.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignInActivity extends AppCompatActivity {
    public static SignInActivity sInstance;
    EditText emailEditText, passwordEditText;
    Button btnSignIn;
    TextView txtSignUp;
    DatabaseHelper databaseHelper = new DatabaseHelper(this);
    @SuppressLint("StaticFieldLeak")
    private static String tag = "traineeapp";
    private RequestQueue mRequestQueue;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        init();
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                if (!isValidEmail(email)) {
                    emailEditText.setError("invalid email");
                } else if (!isValidPassword(password)) {
                    passwordEditText.setError("Password must be 6 digits");
                } else {
                    if (databaseHelper.Checkemailpassword(email, password)) {
                        Toast.makeText(getApplicationContext(), "email & password are valid.", Toast.LENGTH_SHORT).show();
                        savePreference(email);
                        Intent i = new Intent(SignInActivity.this, DashboardActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "email & password are not valid.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void init() {
        emailEditText = findViewById(R.id.edt_signinemail);
        passwordEditText = findViewById(R.id.edt_password);
        emailEditText.setText("pooja@gmail.com");
        passwordEditText.setText("12345678");
        btnSignIn = findViewById(R.id.btn_signin);
        txtSignUp = findViewById(R.id.txt_create_an_account);
    }

    private void savePreference(String email) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email);
        editor.putBoolean("is_session_login", true);
        editor.apply();
    }

    /**
     * proper format for email validation
     *
     * @param email
     * @return
     */
    boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * proper validation for password
     *
     * @param password
     * @return
     */
    boolean isValidPassword(String password) {
        if (password != null && password.length() > 6) {
            return true;
        }
        return false;
    }
}