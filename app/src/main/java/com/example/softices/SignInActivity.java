package com.example.softices;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.MultiDex;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.android.volley.VolleyLog.TAG;

public class SignInActivity extends AppCompatActivity {
    public static SignInActivity sInstance;
    EditText emailEditText, passwordEditText;
    DatabaseHelper databaseHelper = new DatabaseHelper(this);
    @SuppressLint("StaticFieldLeak")
    private static String tag = "traineeapp";
    private RequestQueue mRequestQueue;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
//        try {
//            MultiDex.install(this);
//            sInstance = this;
//        } catch (Exception e) {
//            Log.e(tag + "error", e + "");
//        }
//
//        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
//        StrictMode.setVmPolicy(builder.build());
//
//        FirebaseApp.initializeApp(this);
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
            }
        });

        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                FirebaseMessaging.getInstance().subscribeToTopic("general")
//                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                String msg = "Successfull.";
//                                if (!task.isSuccessful()) {
//                                    msg = "failed.";
//
//                                    Log.e(TAG, msg);
//                                    return;
//                                }
//                                Toast.makeText(SignInActivity.this, msg, Toast.LENGTH_SHORT).show();
//                            }
//                        });

                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    //    public void attachBaseContext(Context base) {
//        MultiDex.install(base);
//        super.attachBaseContext(base);
//    }
//
//    public static SignInActivity getsInstance() {
//        return sInstance;
//    }
//
//    public static Context getAppContext() {
//        return sInstance.getApplicationContext();
//    }
//
//    public RequestQueue getRequestQueue() {
//        if (mRequestQueue == null) {
//            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
//        }
//        return mRequestQueue;
//    }
//
//    public <T> void add(Request<T> req, String tag) {
//        // set the default tag if tag is empty
//        req.setTag(TextUtils.isEmpty(tag) ? tag : tag);
//        getRequestQueue().add(req);
//    }
//
//    public <T> void add(Request<T> req) {
//        req.setTag(tag);
//        getRequestQueue().add(req);
//    }
//
//    public SignInActivity() {
//        super();
//    }
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
