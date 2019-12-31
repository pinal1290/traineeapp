package com.example.softices;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Post extends AppCompatActivity {
    EditText edtfirst, edtsecond;
    TextView txtpost;
    Button btnpostcreate;
    private RequestQueue queue;
    private String jsonResponse;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queue = Volley.newRequestQueue(this);  // this = context
        setContentView(R.layout.activity_post);
//        edtfirst = findViewById(R.id.edt_name);
//        edtsecond = findViewById(R.id.edt_lastname);
//        btnpostcreate = findViewById(R.id.btn_post);
        txtpost = findViewById(R.id.txt_post);
        final String postUrl = "https://jsonplaceholder.typicode.com/users";
        try {
            parse(postUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        btnpostcreate.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                String firstname = edtfirst.getText().toString();
//                String lastname = edtsecond.getText().toString();
////                postrequest(storeName, id, postUrl);
//                try {
//                    parse(postUrl);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }

    public void parse(String url) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("page", 2);
            jsonObject.put("per_pages", 6);
            jsonObject.put("total", "12");
            jsonObject.put("total_pages", "2");
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < 1; i++) {
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("id", "7");
                jsonObject1.put("email", "michael.lawson@reqres.in");
                jsonObject1.put("firstname", "michael");
                jsonObject1.put("lastname", "lawson");
                jsonObject1.put("avatar", "https://s3.amazonaws.com/uifaces/faces/twitter/follettkyle/128.jpg");
                jsonArray.put(jsonObject1);
            }
            jsonObject.put("data", jsonArray);
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    jsonObject,
                    new Response.Listener<JSONObject>() {
                        public void onResponse(JSONObject response) {
                            Log.e("response", response.toString());
                            txtpost.setText(response.toString());
                        }
                    }, new Response.ErrorListener() {

                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d("Error: ", error.getMessage());
                }
            });
            queue.add(jsonObjReq);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}