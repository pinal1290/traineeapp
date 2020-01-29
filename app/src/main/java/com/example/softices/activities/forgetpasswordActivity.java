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

import com.example.softices.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class forgetpasswordActivity extends AppCompatActivity {
    TextView txtforgetpass;
    EditText edtemail;
    ImageView imgframe;
    FrameLayout frmforgetpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);
        init();
        final TextView textView=new TextView(this);
        textView.setText("android example");
        textView.setTextSize(20);
        frmforgetpass.addView(textView);
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
        Button resetpassword = findViewById(R.id.btn_reset_password);
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
        imgframe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtforgetpass.getVisibility()== View.GONE){
                    textView.setVisibility(View.VISIBLE);
                    frmforgetpass.setBackgroundColor(Color.MAGENTA);
                } else {
                    textView.setVisibility(View.GONE);
                }
            }
        });
    }

    public void init() {
        edtemail = findViewById(R.id.edt_email);
        txtforgetpass = findViewById(R.id.txt_forget_password);
        frmforgetpass = findViewById(R.id.frm_forget_password);
        imgframe=findViewById(R.id.img_frameimage);
    }

    boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
