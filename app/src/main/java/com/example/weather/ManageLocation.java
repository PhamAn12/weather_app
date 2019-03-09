package com.example.weather;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ManageLocation extends AppCompatActivity {
    Button btn_location;
    AutoCompleteTextView actv;
    Button btn_listlocation;

    TextView txtView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_location);

        txtView = (TextView) findViewById(R.id.textViewPlace);

        btn_location = (Button) findViewById(R.id.add_location_button);

        actv = (AutoCompleteTextView) findViewById(R.id.new_location);

        btn_listlocation = (Button) findViewById(R.id.go_to_location_button);

        btn_listlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_listlocation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent= new Intent(ManageLocation.this,ListLocation.class);
                        startActivity(intent);
                    }
                });
            }
        });

        btn_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = actv.getText().toString();
                getCurentWeather(data);
            }
        });

    }


    public void getCurentWeather(String data) {
        RequestQueue requestQueue = Volley.newRequestQueue(ManageLocation.this);
        String url ="http://api.openweathermap.org/data/2.5/find?q=" + data + "&units=metric&appid=94f7d25e8b529c9034a92a47e021663c";
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Kết quả", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray_list = jsonObject.getJSONArray("list");
                            JSONObject jsonObjectList = jsonArray_list.getJSONObject(0);
                            String city = jsonObjectList.getString("name");

                            Log.d("Kq" ,city);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);
    }
}
