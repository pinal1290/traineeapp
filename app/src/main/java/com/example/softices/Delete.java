package com.example.softices;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class Delete extends AppCompatActivity {
TextView txtdelete;
    private RequestQueue queue;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        queue = Volley.newRequestQueue(this);  // this = context
        txtdelete=findViewById(R.id.txt_delete);
        deleterequest();
    }

    private void deleterequest() {
        StringRequest dr = new StringRequest(Request.Method.DELETE,
                "https://reqres.in/api/users/2",
                new Response.Listener<String>()
                {
                    public void onResponse(String response) {
                        // response
                        Log.e("Response",response);
                        txtdelete.setText(response);
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener()
                {
                    public void onErrorResponse(VolleyError error) {
                        // error.
                        Log.e("Error Response", error.toString());

                    }
                }
        );
        queue.add(dr);


    }
}
