package com.example.weather.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.weather.MainActivity;
import com.example.weather.R;
import com.example.weather.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.ContentValues.TAG;

public class Today extends Fragment {
    TextView textViewDay , textViewFell, txtTemp;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        String city = "";
        Log.d("oid", "onCreateView: " );
        Bundle bundle = this.getArguments();
    //    Nhận argument từ main activity
        if(bundle != null) {
            city = bundle.getString("dataC");
            String country = bundle.getString("dn");
            Log.d("ok", "onCreateView: " + city);
            Log.d("ĐN", "onCreateView: " + country);
        }
        Log.d("okdccc", "onCreateView: " + city);
        View view = inflater.inflate(R.layout.today, container, false);
        textViewDay = (TextView) view.findViewById(R.id.day);
        textViewFell = (TextView) view.findViewById(R.id.camthay);
        txtTemp = (TextView) view.findViewById(R.id.txtTemp);
        GetCurrentWeatherData(city);
        textViewFell.setText("NHƯ LỒN");
        return view;
    }

    public void GetCurrentWeatherData(final String data) {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + data + "&units=metric&appid=b195255676fe196e397398381ac43e10";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Log.d("RESPONSE API", response);

                            String day = jsonObject.getString("dt");
                            String name = jsonObject.getString("name");
                            //    txtName.setText(name);

                            long l = Long.valueOf(day);
                            Date date = new Date(l * 1000L);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd HH-mm-ss");
                            String Day = simpleDateFormat.format(date);
                            textViewDay.setText(Day);

                            JSONArray jsonArrayWeather = jsonObject.getJSONArray("weather");
                            JSONObject jsonObject1Weather = jsonArrayWeather.getJSONObject(0);
                            String status = jsonObject1Weather.getString("main");
                            String icon = jsonObject1Weather.getString("icon");
                            //    Picasso.with(MainActivity.this).load("http://openweathermap.org/img/w/" + icon + ".png").into(imgIcon);
                            //    txtState.setText(status);

                            JSONObject jsonObject1Main = jsonObject.getJSONObject("main");
                            String nhietdo = jsonObject1Main.getString("temp");
                            String doam = jsonObject1Main.getString("humidity");
                            Double a = Double.valueOf(nhietdo);
                            String Nhietdo = String.valueOf(a.intValue());
                            txtTemp.setText(Nhietdo);
                            //    txtHumidity.setText(doam + "%");

                            JSONObject jsonObject1Wind = jsonObject.getJSONObject("wind");
                            String gio = jsonObject1Wind.getString("speed");
                            //   txtWind.setText(gio + "m/s");

                            JSONObject jsonObject1Clouds = jsonObject.getJSONObject("clouds");
                            String may = jsonObject1Clouds.getString("all");
                            //    txtCloud.setText(may + "%");

//                            JSONObject jsonObject1Sys = jsonObject.getJSONObject("sys");
//                            country = jsonObject1Sys.getString("country");
//                            Log.d("Nước", "onResponse: " + country);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity().getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        requestQueue.add(stringRequest);
    }

}
