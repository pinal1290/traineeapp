package com.example.softices;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Get extends AppCompatActivity {

    TextView txtpage, txtperpage, txttotal, txttotalpages, txtget;
    ArrayList<HashMap<String, String>> contactList;
    private RequestQueue queue;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get);
        contactList = new ArrayList<>();
        txtpage = findViewById(R.id.txt_page);
        txtperpage = findViewById(R.id.txt_perpage);
        txttotal = findViewById(R.id.txt_total);
        txttotalpages = findViewById(R.id.txt_total_pages);
        txtget = findViewById(R.id.txt_get);
        queue = Volley.newRequestQueue(this);  // this = context

        String getUrl = "https://reqres.in/api/users";
        getRequest(getUrl);
    }

    private void getRequest(final String url) {
        // prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    public void onResponse(JSONObject response) {
                        // display response
                        parseGetReponse(response);
                        txtget.setText(response.toString());
                        Log.e("Response", response.toString());

                    }
                },
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error.Response", error.toString());
                    }
                }
        );
        queue.add(getRequest);
    }


    @SuppressLint("SetTextI18n")
    private void parseGetReponse(JSONObject response) {
        try {
            int page = response.getInt("page");
            txtpage.setText("page: " + page);
            String perPage = response.getString("per_page");
            txtperpage.setText("perpage: " + perPage);
            String total = response.getString("total");
            txttotal.setText("total: " + total);
            String total_pages = response.getString("total_pages");
            txttotalpages.setText("total_pages: " + total_pages);
            JSONArray dataJSONArray = response.getJSONArray("data");
            for (int i = 0; i < dataJSONArray.length(); i++) {
                JSONObject object = dataJSONArray.getJSONObject(i);
                String id = object.getString("id");
                String email = object.getString("email");
                String first_name = object.getString("first_name");
                String last_name = object.getString("last_name");
                String avatar = object.getString("avatar");
                HashMap<String, String> json = new HashMap<>();
                json.put("id", id);
                json.put("email", email);
                json.put("first_name", first_name);
                json.put("last_name", last_name);
                json.put("avatar", avatar);
                contactList.add(json);
                txtget.setText(object.toString());

            }
        } catch (Exception e) {
            Log.e("parseGetReponse", e.toString());
        }
    }


}
