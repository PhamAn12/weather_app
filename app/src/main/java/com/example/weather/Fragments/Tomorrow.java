package com.example.weather.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.weather.Object.WeatherToday;
import com.example.weather.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Tomorrow extends Fragment {
    ArrayList<WeatherToday> mangthoitiet = new ArrayList<>();
    TextView tomorrowDay , txtStatus ,txtDoam, txtWind;
    ImageView imageView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tomorrow, container, false);

        txtStatus = (TextView) view.findViewById(R.id.txtStatus);
        tomorrowDay = (TextView) view.findViewById(R.id.tomorrow);
        imageView = (ImageView) view.findViewById(R.id.iconweather);
        txtDoam =(TextView) view.findViewById(R.id.doam);
        txtWind = (TextView) view.findViewById(R.id.wind);
        String city = "Hanoi";
        Log.d("oid", "onCreateView: " );
        Bundle bundle = this.getArguments();
        //    Nhận argument từ main activity
        if(bundle != null) {
            if ( bundle.getString("dataC") != null && bundle.getString("dataC") != "") {
                city = bundle.getString("dataC");
                String country = bundle.getString("dn");
                Log.d("ok", "onCreateView: " + city);
                Log.d("ĐN", "onCreateView: " + country);
            }
        }
        getTomorrowData(city);

        return view;
    }

    private void getTomorrowData(String data){
        Log.d("7ngay", "city " + data);
        String url = "http://api.openweathermap.org/data/2.5/forecast/daily?q=" + data + "&units=metric&cnt=7&appid=53fbf527d52d4d773e828243b90c1f8e";
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("tomorrow : ", "7 ngay: " + response);
                        try {
                            JSONObject jsonObject1 = new JSONObject(response);

                            JSONObject jsonObject1City = jsonObject1.getJSONObject("city");
                            String name = jsonObject1City.getString("name");

                            String ngay = "";
                            long l = 0;
                            int i = 1;
                            JSONArray jsonArrayList = jsonObject1.getJSONArray("list");
                        //    for (int i = 0; i < jsonArrayList.length(); i++){

                                JSONObject jsonObjectList = jsonArrayList.getJSONObject(i);

                                ngay = jsonObjectList.getString("dt");
                                l = Long.valueOf(ngay);
                                Date date = new Date(l * 1000L);
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd");
                                String Day = simpleDateFormat.format(date);
                                Log.d("Day7ngay", "onResponse: " + Day);
                                tomorrowDay.setText(Day);
                                JSONObject jsonObjectMain = jsonObjectList.getJSONObject("temp");
                                String tempMin = jsonObjectMain.getString("min");
                                String tempMax = jsonObjectMain.getString("max");
                                txtDoam.setText("Max temp : " + tempMax + "°C");
                                txtWind.setText("Min temp : " + tempMin + "°C");
                                Double a = Double.valueOf(tempMin);
                                Double b = Double.valueOf(tempMax);
                                String NhietdoMin = String.valueOf(a.intValue());
                                String NhietdoMax = String.valueOf(b.intValue());

                                JSONArray jsonArrayWeather = jsonObjectList.getJSONArray("weather");
                                JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                                String status = jsonObjectWeather.getString("description");
                                txtStatus.setText(status);
                                String icon = jsonObjectWeather.getString("icon");
                                Picasso.with(getActivity().getApplicationContext()).load("http://openweathermap.org/img/w/" + icon + ".png").into(imageView);
                            //    mangthoitiet.add(new WeatherToday(Day, status, NhietdoMin, NhietdoMax, icon));
                        //    }
                        //    customAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("7ngay", "err:  " + error);
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        requestQueue.add(stringRequest);
    }
}
