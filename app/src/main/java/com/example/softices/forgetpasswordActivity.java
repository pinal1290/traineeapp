package com.example.softices;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class forgetpasswordActivity extends AppCompatActivity {
TextView txtforgetpass;
EditText edtemail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);
         edtemail=findViewById(R.id.edt_email);
         txtforgetpass=findViewById(R.id.txt_forget_password);
         txtforgetpass.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 // TODO Auto-generated method stub
                 forgetpasswordActivity.this.finish();
                 Intent intent = new Intent();
                 intent.setClass(v.getContext(), SignInActivity.class);
                 startActivity(intent);
             }
         });
        Button resetpassword =findViewById(R.id.btn_reset_password);
        resetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtemail.getText().toString();
                if (!isValidEmail(email)) {
                    edtemail.setError("Please enter valid email");
                }
                   sendEmail();
            }

            private void sendEmail() {
                final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.setType("plain/text");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"poojavaghasiya045@gmail.com"});
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, edtemail.getText());
                forgetpasswordActivity.this.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            }
        });
    }
    boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
