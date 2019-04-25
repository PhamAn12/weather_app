package com.example.weather.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.weather.Adapter.RecyclerView24hAdapter;
import com.example.weather.MainActivity;
import com.example.weather.Object.Thoitiet24h;
import com.example.weather.Object.WeatherToday;
import com.example.weather.R;
import com.example.weather.Utils.Utils;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.paperdb.Paper;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

import static android.content.ContentValues.TAG;

public class Today extends Fragment {
    TextView textViewDay , textDoam, txtTemp, txtWind;
    RecyclerView recyclerView24h;
    RecyclerView24hAdapter recycler24hAdapter;
    LineChartView lineChartView;
    LinearLayout layout;
    List<String> axisData = new ArrayList<>();
    List<String>yAxisData = new ArrayList<>();
    ImageView imageView;
//    LineChartView lineChartView;
//    String[] axisData = {"Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sept",
//            "Oct", "Nov", "Dec"};
//    int[] yAxisData = {50, 20, 15, 30, 20, 60, 15, 40, 45, 10, 90, 18};
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
        Log.d("okdccc", "onCreateView: " + city);
        View view = inflater.inflate(R.layout.today, container, false);
        txtWind = (TextView) view.findViewById(R.id.wind);
        textViewDay = (TextView) view.findViewById(R.id.day);
        textDoam = (TextView) view.findViewById(R.id.doam);
        txtTemp = (TextView) view.findViewById(R.id.txtTemp);
        recyclerView24h = (RecyclerView) view.findViewById(R.id.recycler24h);

        imageView = (ImageView) view.findViewById(R.id.iconweather);
        layout = (LinearLayout) view.findViewById(R.id.today);

        recycler24hAdapter = new RecyclerView24hAdapter(getActivity().getApplicationContext(), new ArrayList<Thoitiet24h>());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView24h.setLayoutManager(layoutManager);
        recyclerView24h.setAdapter(recycler24hAdapter);

//        lineChartView = view.findViewById(R.id.chart);
//        List yAxisValues = new ArrayList();
//        List axisValues = new ArrayList();
//
//        Line line = new Line(yAxisValues).setColor(Color.parseColor("#9C27B0"));
//
//        for(int i = 0; i < axisData.length; i++){
//            axisValues.add(i, new AxisValue(i).setLabel(axisData[i]));
//        }
//
//        for (int i = 0; i < yAxisData.length; i++){
//            yAxisValues.add(new PointValue(i, yAxisData[i]));
//        }
//
//        List lines = new ArrayList();
//        lines.add(line);
//
//        LineChartData data = new LineChartData();
//        data.setLines(lines);
//        Axis axis = new Axis();
//        axis.setValues(axisValues);
//        axis.setTextSize(16);
//        axis.setTextColor(Color.parseColor("#03A9F4"));
//        data.setAxisXBottom(axis);
//
////        Axis yAxis = new Axis();
////        yAxis.setTextColor(Color.parseColor("#03A9F4"));
////        yAxis.setTextSize(16);
////        data.setAxisYLeft(yAxis);
//
//        lineChartView.setLineChartData(data);
//        Viewport viewport = new Viewport(lineChartView.getMaximumViewport());
//        viewport.top = 110;
//        lineChartView.setMaximumViewport(viewport);
//        lineChartView.setCurrentViewport(viewport);

        GetCurrentWeatherData(city);
        get24hData(city);
        Log.d("kdkd", "onCreateView: " + axisData);
        return view;
    }


    public void GetCurrentWeatherData(final String data) {
        Log.d("today", "city: " + data);
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
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd");
                            String Day = simpleDateFormat.format(date);
                            textViewDay.setText(Day);

                            JSONArray jsonArrayWeather = jsonObject.getJSONArray("weather");
                            JSONObject jsonObject1Weather = jsonArrayWeather.getJSONObject(0);
                            String status = jsonObject1Weather.getString("main");
                            String icon = jsonObject1Weather.getString("icon");
                            Picasso.with(getActivity().getApplicationContext()).load("http://openweathermap.org/img/w/" + icon + ".png").into(imageView);
                            //    txtState.setText(status);
                            Log.d("status", "onResponse: " + status);
                            JSONObject jsonObject1Main = jsonObject.getJSONObject("main");
                            String nhietdo = jsonObject1Main.getString("temp");
                            String doam = jsonObject1Main.getString("humidity");
                            Double a = Double.valueOf(nhietdo);
                            String Nhietdo = String.valueOf(a.intValue());
                            txtTemp.setText(Nhietdo);
                            textDoam.setText("Humidity: " + doam + "%");
                            Paper.book().write("temp",nhietdo);
                            Paper.book().write("status",status);
                            Paper.book().write("icon",icon);
                            //    txtHumidity.setText(doam + "%");
//                            int nd = Integer.parseInt(Nhietdo);
//                            if(nd > 22)
//                                layout.setBackgroundColor(Color.RED);
//                            else
//                                layout.setBackgroundColor(Color.GREEN);

                            JSONObject jsonObject1Wind = jsonObject.getJSONObject("wind");
                            String gio = jsonObject1Wind.getString("speed");
                            txtWind.setText("Wind : "+ gio + " m/s");

                            JSONObject jsonObject1Clouds = jsonObject.getJSONObject("clouds");
                            String may = jsonObject1Clouds.getString("all");
                            //    txtCloud.setText(may + "%");



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("today", "err:  " + error);
                        Toast.makeText(getActivity().getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        requestQueue.add(stringRequest);
    }

    private void get24hData(String data) {
        String url = "http://api.openweathermap.org/data/2.5/forecast?q=" + data + "&units=metric&cnt=8&appid=b195255676fe196e397398381ac43e10";
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Ket qua : ", response);
                        try {
                            JSONObject jsonObject1 = new JSONObject(response);

//                            JSONObject jsonObject1City = jsonObject1.getJSONObject("city");
//                            String name = jsonObject1City.getString("name");
//                            txtName.setText(name);

                            String ngay = "";
                            long l = 0;
                            JSONArray jsonArrayList = jsonObject1.getJSONArray("list");
                            List<Thoitiet24h> tempList = new ArrayList<>();
                            Log.d("LENGTH", "onResponse: " + jsonArrayList.length());
                            for (int i = 0; i < jsonArrayList.length(); i++) {

                                JSONObject jsonObjectList = jsonArrayList.getJSONObject(i);

                                ngay = jsonObjectList.getString("dt");
                                l = Long.valueOf(ngay);
                                Date date = new Date(l * 1000L);
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                                String Time24h = simpleDateFormat.format(date);
                                Log.d("TIme", "onResponse: " + Time24h);
                                //yAxisData.add(Time24h);
                                JSONObject jsonObjectMain = jsonObjectList.getJSONObject("main");
                                String temp = jsonObjectMain.getString("temp");
                                //axisData.add(temp);
                                Double a = Double.valueOf(temp);

                                String Nhietdo24h = String.valueOf(a.intValue());

                                JSONArray jsonArrayWeather = jsonObjectList.getJSONArray("weather");
                                JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                                String icon = jsonObjectWeather.getString("icon");

                                tempList.add(new Thoitiet24h(Time24h, icon, Nhietdo24h));
                            }
                            recycler24hAdapter.resetList(tempList);
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
