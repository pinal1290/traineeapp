package com.example.softices;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.android.volley.VolleyLog.TAG;

public class SignInActivity extends AppCompatActivity {
    EditText emailEditText, passwordEditText;
    DatabaseHelper databaseHelper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        emailEditText = findViewById(R.id.edt_signinemail);
        passwordEditText = findViewById(R.id.edt_password);
        emailEditText.setText("pooja@gmail.com");
        passwordEditText.setText("12345678");
        Button btnSignIn = findViewById(R.id.btn_signin);
        TextView txtSignUp = findViewById(R.id.txt_create_an_account);
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

                FirebaseMessaging fm = FirebaseMessaging.getInstance();
                fm.send(new RemoteMessage.Builder(  "589949736353@fcm.googleapis.com")
                        .setMessageId(Integer.toString(123))
                        .addData("my_message", "Hello World")
                        .addData("my_action","SAY_HELLO")
                        .build());
            }
        });
        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseMessaging.getInstance().subscribeToTopic("general")
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                String msg = "Successfull.";
                                if (!task.isSuccessful()) {
                                    msg = "failed.";

                                    Log.e(TAG, msg);
                                    return;
                                }
                                Toast.makeText(SignInActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }
                        });
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void savePreference(String email) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email);
        editor.putBoolean("is_session_login", true);
        editor.apply();
    }

    boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    boolean isValidPassword(String password) {
        if (password != null && password.length() > 6) {
            return true;
        }
        return false;
    }

}
