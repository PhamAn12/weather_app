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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.weather.Adapter.WeekAdapter;
import com.example.weather.Object.WeatherToday;
import com.example.weather.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SevenDays extends Fragment {
    String tenthanhpho;
    ImageView imageBack;
    TextView txtName;
    ListView lv;
    WeekAdapter customAdapter;
    ArrayList<WeatherToday> mangthoitiet;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        String city = "Hanoi";
        Bundle bundle = this.getArguments();
        if(bundle != null) {
            if ( bundle.getString("dataC") != null && bundle.getString("dataC") != "") {
                city = bundle.getString("dataC");
                String country = bundle.getString("dn");
                Log.d("ok", "onCreateView: " + city);
                Log.d("ĐN", "onCreateView: " + country);
            }

        }
        View view = inflater.inflate(R.layout.seven_day, container, false);
        Anhxa(view);
        get7DaysData(city);
        return view;
    }
    private void Anhxa(View view) {
//        imageBack = (ImageView) view.findViewById(R.id.imageviewTrangthai);
        txtName = (TextView) view.findViewById(R.id.txtCity_Week);
        lv = (ListView) view.findViewById(R.id.lvWeatherOfWeek);

        mangthoitiet = new ArrayList<WeatherToday>();
        customAdapter = new WeekAdapter(getContext(), mangthoitiet);
        lv.setAdapter(customAdapter);
    }
    private void get7DaysData(String data){
        Log.d("7ngay", "city " + data);
        String url = "http://api.openweathermap.org/data/2.5/forecast/daily?q=" + data + "&units=metric&cnt=7&appid=53fbf527d52d4d773e828243b90c1f8e";
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Ket qua : ", "7 ngay: " + response);
                        try {
                            JSONObject jsonObject1 = new JSONObject(response);

                            JSONObject jsonObject1City = jsonObject1.getJSONObject("city");
                            String name = jsonObject1City.getString("name");
                            txtName.setText(name);

                            String ngay = "";
                            long l = 0;
                            JSONArray jsonArrayList = jsonObject1.getJSONArray("list");
                            for (int i = 0; i < jsonArrayList.length(); i++){

                                JSONObject jsonObjectList = jsonArrayList.getJSONObject(i);

                                ngay = jsonObjectList.getString("dt");
                                l = Long.valueOf(ngay);
                                Date date = new Date(l * 1000L);
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd");
                                String Day = simpleDateFormat.format(date);

                                JSONObject jsonObjectMain = jsonObjectList.getJSONObject("temp");
                                String tempMin = jsonObjectMain.getString("min");
                                String tempMax = jsonObjectMain.getString("max");

                                Double a = Double.valueOf(tempMin);
                                Double b = Double.valueOf(tempMax);
                                String NhietdoMin = String.valueOf(a.intValue());
                                String NhietdoMax = String.valueOf(b.intValue());

                                JSONArray jsonArrayWeather = jsonObjectList.getJSONArray("weather");
                                JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                                String status = jsonObjectWeather.getString("description");
                                String icon = jsonObjectWeather.getString("icon");
                                Picasso.with(getContext()).load("http://openweathermap.org/img/w/" + icon + ".png").into(imgStatus);
                                mangthoitiet.add(new WeatherToday(Day, status, NhietdoMin, NhietdoMax));
                            }
                            customAdapter.notifyDataSetChanged();
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
