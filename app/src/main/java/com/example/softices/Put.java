package com.example.softices;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Put extends AppCompatActivity {
    EditText edtputfirst, edtputsecond;
    TextView txtput;
    Button btnputupdate;
    private RequestQueue queue;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put);
        queue = Volley.newRequestQueue(this);  // this = context
//        edtputfirst = findViewById(R.id.edt_putone);
//        edtputsecond = findViewById(R.id.edt_puttwo);
//        btnputupdate = findViewById(R.id.btn_put);
        txtput = findViewById(R.id.txt_put);
//        btnputupdate.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                putrequest();
//            }
//        });
        String puturl="https://reqres.in/api/users/2";
        putrequest(puturl);
    }

    public void putrequest(String url) {
//        final String firstname = edtputfirst.getText().toString();
//        final String lastname = edtputsecond.getText().toString();
        try {
            JSONObject js = new JSONObject();
            js.put("page", 4);
            js.put("per_pages",8);
            js.put("total",32);
            js.put("total_pages",4);
            JSONArray jsonArray=new JSONArray();
            for(int i=0; i<1; i++){
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("id",11);
                jsonObject.put("email","pinal@gmail.com");
                jsonObject.put("first_name","pinal");
                JSONObject jsonObject1=new JSONObject();
                jsonObject.put("mobile_no.",jsonObject1);
                jsonObject1.put("home","3214568");
                jsonObject1.put("office","4567778");
                jsonArray.put(jsonObject1);
                jsonObject.put("avatar","https://s3.amazonaws.com/uifaces/faces/twitter/vivekprvr/128.jpg");
                jsonObject.put("website","hildegard.org");
                JSONObject jsonObject2=new JSONObject();
                jsonObject.put("address",jsonObject2);
                jsonObject2.put("street","vishal nagar");
                jsonObject2.put("suite","soc-84");
                jsonObject2.put("city","surat");
                jsonObject2.put("zipcode","395006");
                jsonArray.put(jsonObject2);
                jsonArray.put(jsonObject);
            }
            js.put("data",jsonArray);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.PUT,
                    url,
                    js,
                    new Response.Listener<JSONObject>() {
                        public void onResponse(JSONObject response) {
                            Log.e("Response", response.toString());
                            txtput.setText(response.toString());

                        }
                    },
                    new Response.ErrorListener() {
                        public void onErrorResponse(VolleyError error) {
                            // error
                            Log.e("Error.Response", error.toString());
                        }
                    }
            ) {
            };
            queue.add(jsonObjectRequest);
        } catch (Exception e) {
            Log.e("putrequest",e.toString());
        }

    }

}

