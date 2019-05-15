package com.example.weather.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.example.weather.Adapter.RecyclerView24hAdapter;
import com.example.weather.Adapter.RecyclerView24hTomorrowAdapter;
import com.example.weather.Object.Thoitiet24h;
import com.example.weather.Object.Tomorrow24h;
import com.example.weather.Object.WeatherToday;
import com.example.weather.R;
import com.example.weather.Utils.Utils;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Tomorrow extends Fragment {
    ArrayList<WeatherToday> mangthoitiet = new ArrayList<>();
    TextView tomorrowDay , txtStatus ,txtDoam, txtWind,txtTempDayTomorrow,txtTempNightTomorrow,txtWindTitleTomorrow;
    ImageView imageView;
    RecyclerView24hTomorrowAdapter recyclerView24hTomorrowAdapter;
    RecyclerView recyclerView24hTomorrow;
    BarChart barChart;

    String tempUnit = "m/s";
    String windUnit = "°C";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tomorrow, container, false);

        txtStatus = (TextView) view.findViewById(R.id.txtStatus);
        tomorrowDay = (TextView) view.findViewById(R.id.tomorrow);
        imageView = (ImageView) view.findViewById(R.id.iconweather);
        txtDoam =(TextView) view.findViewById(R.id.doam);
        txtWind = (TextView) view.findViewById(R.id.wind);
        txtTempDayTomorrow = (TextView) view.findViewById(R.id.txtTempDayTomorrow);
        txtTempNightTomorrow = (TextView) view.findViewById(R.id.txtTempNightTomorrow);
        recyclerView24hTomorrow = (RecyclerView) view.findViewById(R.id.recycler24hTomorrow);
        txtWindTitleTomorrow = (TextView) view.findViewById(R.id.txtWindTitleTomorrow);
        barChart = (BarChart) view.findViewById(R.id.barChart);
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
            if ( bundle.getString("tempUnit") != null && bundle.getString("tempUnit") != "") {
                tempUnit = bundle.getString("tempUnit");
                Log.d("today", "temp: " + tempUnit);
            }
            if ( bundle.getString("windUnit") != null && bundle.getString("windUnit") != "") {
                windUnit = bundle.getString("windUnit");
                Log.d("today", "windUnit: " + windUnit);

            }
        }

        recyclerView24hTomorrowAdapter = new RecyclerView24hTomorrowAdapter(getActivity().getApplicationContext(), new ArrayList<Tomorrow24h>());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView24hTomorrow.setLayoutManager(layoutManager);
        recyclerView24hTomorrow.setAdapter(recyclerView24hTomorrowAdapter);
        getTomorrowData(city);
        getTomorrow24hData(city);
        Date today = new Date();
        Date tomorrow = new Date(today.getTime() + (1000 * 60 * 60 * 24));
        String strDateFormat = "EEEE yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        sdf.format(new Date());
        Log.d("Tomorrow", sdf.format(tomorrow));

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
                                String tempDay = jsonObjectMain.getString("day");
                                String tempNight = jsonObjectMain.getString("night");
//                                txtTempDayTomorrow.setText("Day : " + tempDay + "°C ↑");
//                                txtTempNightTomorrow.setText("Night : " + tempNight + "°C ↓");
                                Double a = Double.valueOf(tempDay);
                                Double b = Double.valueOf(tempNight);
                                String TempDay = String.valueOf(a.intValue());
                                String TempNight = String.valueOf(b.intValue());
                                if(tempUnit.equals("°F") ) {
                                    Log.d("today", "temp: " + "true");
                                    a = Utils.convertToF(Double.valueOf(tempDay));
                                    TempDay = String.valueOf(a.intValue());
                                    txtTempDayTomorrow.setText("Day : " + TempDay + " °F ↑");
                                    Log.d("today", "temp: " + "true");
                                    b = Utils.convertToF(Double.valueOf(TempNight));
                                    TempNight = String.valueOf(b.intValue());
                                    txtTempNightTomorrow.setText("Night : " + TempNight + " °F ↓");
                                }
                                else {
                                    txtTempDayTomorrow.setText("Day : " + TempDay + " °C ↑");
                                    txtTempNightTomorrow.setText("Night : " + TempNight + " °C ↓");
                                }


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

    private void getTomorrow24hData(String data){
        Log.d("7ngay", "city " + data);
        String url = "https://api.openweathermap.org/data/2.5/forecast?q="+ data + "&units=metric&appid=b195255676fe196e397398381ac43e10";
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("tomorrow : ", "7 ngay: " + response);
                        try {
                            Date today = new Date();
                            Date tomorrow = new Date(today.getTime() + (1000 * 60 * 60 * 24));
                            String strDateFormat = "EEEE yyyy-MM-dd";
                            SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
                            String tomorrowDay = sdf.format(tomorrow);//Tuesday 2019-05-14
                            Log.d("TomorrowDay",tomorrowDay);
                            JSONObject jsonObject1 = new JSONObject(response);
                            List<Tomorrow24h> tempList = new ArrayList<>();
                            JSONObject jsonObject1City = jsonObject1.getJSONObject("city");
                            String name = jsonObject1City.getString("name");

                            List<String> hourInChart = new ArrayList<>();
                            List<Float> speedInChart = new ArrayList<>();
                            JSONArray jsonArrayList = jsonObject1.getJSONArray("list");
                            String ngay = "";
                            long l = 0;
                            Log.d("LengJsonARL", "onResponse: " + jsonArrayList.length());
                            for (int i = 0; i < jsonArrayList.length(); i++){
                                JSONObject jsonObjectList = jsonArrayList.getJSONObject(i);

                                ngay = jsonObjectList.getString("dt");

                                l = Long.valueOf(ngay);
                                Date date = new Date(l * 1000L);
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd");
                                String Day = simpleDateFormat.format(date);
                                Log.d("DAYDAY",Day);
                                if(Day.equals(tomorrowDay)) {
                                    SimpleDateFormat simple = new SimpleDateFormat("HH:mm");
                                    String time24h = simple.format(date);
                                    hourInChart.add(time24h);
                                    JSONObject jsonObjectMain = jsonObjectList.getJSONObject("main");
                                    String temp = jsonObjectMain.getString("temp");
                                    //axisData.add(temp);
                                    Double a = Double.valueOf(temp);

                                    String temp24h = String.valueOf(a.intValue());
                                    if(tempUnit.equals("°F") ) {
                                        Log.d("today", "temp: " + "true");
                                        a = Utils.convertToF(Double.valueOf(temp));
                                        temp24h = String.valueOf(a.intValue());
                                    }
                                    JSONArray jsonArrayWeather = jsonObjectList.getJSONArray("weather");
                                    JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                                    String icon24h = jsonObjectWeather.getString("icon");

                                    Log.d("temppp", "onResponse: " + time24h + " " + temp24h + " " + icon24h);
                                    tempList.add(new Tomorrow24h(time24h,icon24h,temp24h));

                                    JSONObject jsonObjectWind = jsonObjectList.getJSONObject("wind");
                                    String speed = jsonObjectWind.getString("speed");
                                    if(windUnit.equals("m/s")) {
                                        txtWindTitleTomorrow.setText("Wind speed (m/s)");
                                        speedInChart.add(Float.parseFloat(speed));
                                    }
                                    else {
                                        double win = Utils.convertToKmh(Double.valueOf(speed));
                                        Log.d("main", "win: " + win);
                                        txtWindTitleTomorrow.setText("Wind speed (km/s)");
                                        speedInChart.add((float) win);
                                    }

                                }
                            }
                            JSONObject jsonObjectList = jsonArrayList.getJSONObject(3);
                            JSONObject jsonObjectMain = jsonObjectList.getJSONObject("main");
                            String humidity = jsonObjectMain.getString("humidity");
                            txtDoam.setText("Humidity : " + humidity + "%");
                            //txtWind.setText("Wind : " + Float.toString(speedInChart.get(3)) + "m/s");
                            if(windUnit.equals("m/s")) {
                                txtWind.setText("Wind : " + Float.toString(speedInChart.get(3)) + "m/s");
                            }
                            else {
                                double win = Utils.convertToKmh(Double.valueOf(Float.toString(speedInChart.get(3))));
                                Log.d("main", "win: " + win);
                                txtWind.setText("Wind : " + Float.toString(speedInChart.get(3)) + "km/s");
                            }
                            String [] hIC = hourInChart.toArray(new String[hourInChart.size()]);
                            Log.d("hic", "onResponse: " + hIC[7]);
                            drawChart2(barChart,hIC,speedInChart);
                            recyclerView24hTomorrowAdapter.resetList(tempList);

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

    private void drawChart2 (BarChart chart, String[] values, List<Float> A) {
        BarDataSet set1;
        set1 = new BarDataSet(getDataSet(A), "Wind");

        set1.setColors(Color.parseColor("#F78B5D"), Color.parseColor("#FCB232"), Color.parseColor("#FDD930"), Color.parseColor("#ADD137"), Color.parseColor("#A0C25A"));
        set1.setValueTextSize(17);
        set1.setValueTextColor(Color.WHITE);
        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);

        BarData data = new BarData(dataSets);

        // hide Y-axis
        YAxis left = chart.getAxisLeft();
        left.setDrawLabels(false);
        YAxis right = chart.getAxisRight();
        right.setTextColor(Color.WHITE);
        right.setTextSize(11);
        // custom X-axis labels
        //String[] values = new String[] { "1 star", "2 stars", "3 stars", "4 stars", "5 stars"};
        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new MyXAxisValueFormatter(values));
        xAxis.setTextSize(11);
        xAxis.setTextColor(Color.WHITE);
        chart.setData(data);

        // custom description
        Description description = new Description();
        description.setText("");
        chart.setDescription(description);

        // hide legend
        chart.getLegend().setEnabled(false);

        chart.animateY(1000);
        chart.invalidate();
    }
    public class MyXAxisValueFormatter implements IAxisValueFormatter {

        private String[] mValues;

        public MyXAxisValueFormatter(String[] values) {
            this.mValues = values;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return mValues[(int) value];
        }

    }
    private ArrayList<BarEntry> getDataSet(List<Float> A) {

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        for(int i = 0; i< A.size(); i++) {
            valueSet1.add(new BarEntry(i, A.get(i)));
        }
        return valueSet1;
    }
}
