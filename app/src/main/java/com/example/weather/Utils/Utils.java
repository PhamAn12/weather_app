package com.example.weather.Utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.android.volley.Request;
import com.android.volley.Response;
import com.example.weather.Objects.WeatherToday;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.toolbox.StringRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Utils {
    public static WeatherToday GetCurrentWeatherData(final String data, final Context context) {
        final ArrayList<String> props = new ArrayList<String>();
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + data + "&units=metric&appid=b195255676fe196e397398381ac43e10";
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Log.d("RESPONSE API", response);

                            String day = jsonObject.getString("dt");
                            String name = jsonObject.getString("name");
                            props.add(name);
                            Log.d("add", "" + props.get(0) + " length " + props.size());
//                            txtName.setText(name);

                            long l = Long.valueOf(day);
                            Date date = new Date(l * 1000L);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd HH-mm-ss");
                            String Day = simpleDateFormat.format(date);
//                            txtDay.setText(Day);

                            JSONArray jsonArrayWeather = jsonObject.getJSONArray("weather");
                            JSONObject jsonObject1Weather = jsonArrayWeather.getJSONObject(0);
                            String status = jsonObject1Weather.getString("main");
                            String icon = jsonObject1Weather.getString("icon");
//                            Picasso.with(context).load("http://openweathermap.org/img/w/" + icon + ".png").into(imgIcon);
//                            txtState.setText(status);

                            JSONObject jsonObject1Main = jsonObject.getJSONObject("main");
                            String nhietdo = jsonObject1Main.getString("temp");
                            String doam = jsonObject1Main.getString("humidity");
                            Double a = Double.valueOf(nhietdo);
                            String Nhietdo = String.valueOf(a.intValue());
//                            txtTemp.setText(Nhietdo + "°C");
//                            txtHumidity.setText(doam + "%");
                            JSONObject jsonObject1Wind = jsonObject.getJSONObject("wind");
                            String gio = jsonObject1Wind.getString("speed");
//                            txtWind.setText(gio + "m/s");

                            JSONObject jsonObject1Clouds = jsonObject.getJSONObject("clouds");
                            String may = jsonObject1Clouds.getString("all");
//                            txtCloud.setText(may + "%");

                            JSONObject jsonObject1Sys = jsonObject.getJSONObject("sys");
                            String country = jsonObject1Sys.getString("country");
//                            txtCountry.setText(country);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("getJ", e.toString() + " loi deo duoc");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                        Log.d("getJ", error.toString() + " deo duoc");
                    }
                });
        requestQueue.add(stringRequest);
        WeatherToday weatherToday ;
        while(props.size() == 0) {

        }
        weatherToday = new WeatherToday(props.get(0));
        Log.d("wea", weatherToday.getDay());
        return weatherToday;
    }
}
