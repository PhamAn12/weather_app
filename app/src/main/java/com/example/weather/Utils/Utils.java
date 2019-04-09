package com.example.weather.Utils;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.weather.Fragments.Today;
import com.example.weather.MainActivity;
import com.example.weather.Object.WeatherToday;
import com.example.weather.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;

public class Utils extends AppCompatActivity{
////    public void GetCurrentWeatherData(final String data, final Context context,final WeatherToday weatherToday) {
//    //    final WeatherToday weatherToday;
//        final String[] props = new String[4];
//        ArrayList arrayList = new ArrayList();
//        RequestQueue requestQueue = Volley.newRequestQueue(context);
//        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + data + "&units=metric&appid=b195255676fe196e397398381ac43e10";
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                        //    WeatherToday weatherToday = null;
//                            JSONObject jsonObject = new JSONObject(response);
//                            Log.d("RESPONSE API", response);
//
//                            String day = jsonObject.getString("dt");
//                            Log.d("day",jsonObject.getString("name"));
//                            props[0] = jsonObject.getString("name");
////                            txtName.setText(name);
//                            Log.d("dady",props[0] + " ");
//                            weatherToday.setDay(props[0]);
//                            long l = Long.valueOf(day);
//                            Date date = new Date(l * 1000L);
//                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd HH-mm-ss");
//                            String currentDay = simpleDateFormat.format(date);
//
//                            JSONArray jsonArrayWeather = jsonObject.getJSONArray("weather");
//                            JSONObject jsonObject1Weather = jsonArrayWeather.getJSONObject(0);
//                            props[1] = jsonObject1Weather.getString("main");
//                            String icon = jsonObject1Weather.getString("icon");
//                            //   Picasso.with(context).load("http://openweathermap.org/img/w/" + icon + ".png").into(imgIcon);
//                        //    txtState.setText(status);
//
//                            JSONObject jsonObject1Main = jsonObject.getJSONObject("main");
//                            String nhietdo = jsonObject1Main.getString("temp");
//                            String doam = jsonObject1Main.getString("humidity");
//                            Double a = Double.valueOf(nhietdo);
//                            props[2] = String.valueOf(a.intValue());
//                            props[3] = doam;
//                            weatherToday.setStatus(props[2]);
//                            weatherToday.setMinTemp(props[3]);
//                            JSONObject jsonObject1Wind = jsonObject.getJSONObject("wind");
//                            String gio = jsonObject1Wind.getString("speed");
//                        //    txtWind.setText(gio + "m/s");
//
//                            JSONObject jsonObject1Clouds = jsonObject.getJSONObject("clouds");
//                            String may = jsonObject1Clouds.getString("all");
//                        //    txtCloud.setText(may + "%");
//
//                            JSONObject jsonObject1Sys = jsonObject.getJSONObject("sys");
//                            String country = jsonObject1Sys.getString("country");
//                         //   txtCountry.setText(country);
//                            Log.d("end", "final" + weatherToday.getMaxTemp());
//
//
//                        } catch (JSONException e) {
//                            Log.d("end", "fdiddal");
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
//                        Log.d("end", "fiddal" + error.toString());
//                    }
//                });
//        requestQueue.add(stringRequest);
//
//
//    }
    private String myString = "hello";
    TextView today;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.today);
        today = (TextView) findViewById(R.id.camthay);
        today.setText("anfdfsd,f");
    }
    public String getMyData() {
        return myString;
    }
    public void GetCurrentWeatherData(final String data) {
        RequestQueue requestQueue = Volley.newRequestQueue(Utils.this);
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
                            //tvDay.setText(name);
                            Log.d("na", "onResponse: " + name);
                            long l = Long.valueOf(day);
                            Date date = new Date(l * 1000L);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd HH-mm-ss");
                            String Day = simpleDateFormat.format(date);
                        //    txtDay.setText(Day);

                            JSONArray jsonArrayWeather = jsonObject.getJSONArray("weather");
                            JSONObject jsonObject1Weather = jsonArrayWeather.getJSONObject(0);
                            String status = jsonObject1Weather.getString("main");
                            String icon = jsonObject1Weather.getString("icon");
                        //    Picasso.with(Utils.this).load("http://openweathermap.org/img/w/" + icon + ".png").into(imgIcon);
                        //    txtState.setText(status);

                            JSONObject jsonObject1Main = jsonObject.getJSONObject("main");
                            String nhietdo = jsonObject1Main.getString("temp");
                            String doam = jsonObject1Main.getString("humidity");
                            Double a = Double.valueOf(nhietdo);
                            String Nhietdo = String.valueOf(a.intValue());
                         //   txtTemp.setText(Nhietdo + "°C");
                         //   txtHumidity.setText(doam + "%");

                            JSONObject jsonObject1Wind = jsonObject.getJSONObject("wind");
                            String gio = jsonObject1Wind.getString("speed");
                         //   txtWind.setText(gio + "m/s");

                            JSONObject jsonObject1Clouds = jsonObject.getJSONObject("clouds");
                            String may = jsonObject1Clouds.getString("all");
                        //    txtCloud.setText(may + "%");

                            JSONObject jsonObject1Sys = jsonObject.getJSONObject("sys");
                            String country = jsonObject1Sys.getString("country");
                        //    txtCountry.setText(country);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Utils.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        requestQueue.add(stringRequest);
    }
}
