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
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.tianma8023.model.Time;
import com.github.tianma8023.ssv.SunriseSunsetView;
import com.google.android.gms.common.util.ArrayUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

import static android.content.ContentValues.TAG;

public class Today extends Fragment {
    TextView textViewDay , textDoam, txtTemp, txtWind, txtUvIndex, txtStatus,txtTempDay,txtTempNight;
    SunriseSunsetView mSunriseSunsetView;
    BarChart barChart;
    RecyclerView recyclerView24h;
    RecyclerView24hAdapter recycler24hAdapter;
    LineChartView lineChartView;
    LinearLayout layout;
    List<String> axisData = new ArrayList<>();
    List<String>yAxisData = new ArrayList<>();
    ImageView imageView;
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
        txtUvIndex = (TextView) view.findViewById(R.id.txtUvIndex);
        imageView = (ImageView) view.findViewById(R.id.iconweather);
        layout = (LinearLayout) view.findViewById(R.id.today);
        txtStatus = (TextView) view.findViewById(R.id.txtStatus);
        txtTempDay = (TextView) view.findViewById(R.id.txtTempDay);
        txtTempNight = (TextView) view.findViewById(R.id.txtTempNight);
        recycler24hAdapter = new RecyclerView24hAdapter(getActivity().getApplicationContext(), new ArrayList<Thoitiet24h>());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView24h.setLayoutManager(layoutManager);
        recyclerView24h.setAdapter(recycler24hAdapter);


        GetCurrentWeatherData(city);
        get24hData(city);
        Log.d("kdkd", "onCreateView: " + axisData);
        GetTempNightDay(city);
        GetUVIndex(city);
        barChart = (BarChart) view.findViewById(R.id.barChart);

        mSunriseSunsetView = (SunriseSunsetView) view.findViewById(R.id.ssv);
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
                            String status = jsonObject1Weather.getString("description");
                            String icon = jsonObject1Weather.getString("icon");
                            Picasso.with(getActivity().getApplicationContext()).load("http://openweathermap.org/img/w/" + icon + ".png").into(imageView);
                            //    txtState.setText(status);
                            Log.d("status", "onResponse: " + status);
                            JSONObject jsonObject1Main = jsonObject.getJSONObject("main");
                            String nhietdo = jsonObject1Main.getString("temp");
                            String doam = jsonObject1Main.getString("humidity");
                            Double a = Double.valueOf(nhietdo);
                            String Nhietdo = String.valueOf(a.intValue());
                            txtStatus.setText(status);
                            txtTemp.setText(Nhietdo + "°");
                            textDoam.setText("Humidity : " + doam + "%");

                            JSONObject jsonObject1Wind = jsonObject.getJSONObject("wind");
                            String gio = jsonObject1Wind.getString("speed");
                            txtWind.setText("Wind : "+ gio + " m/s");

                            JSONObject jsonObject1Clouds = jsonObject.getJSONObject("clouds");
                            String may = jsonObject1Clouds.getString("all");
                            //    txtCloud.setText(may + "%");
                            JSONObject jsonObjectSys = jsonObject.getJSONObject("sys");
                            String sunrise = jsonObjectSys.getString("sunrise");
                            String sunset = jsonObjectSys.getString("sunset");
                            long sr = Long.valueOf(sunrise);
                            Date dateSR = new Date(sr * 1000L);
                            SimpleDateFormat hSunrise = new SimpleDateFormat("HH");
                            String hourSunrise = hSunrise.format(dateSR);
                            SimpleDateFormat mSunrise = new SimpleDateFormat("mm");
                            String minuteSunrise = mSunrise.format(dateSR);

                            long ss = Long.valueOf(sunset);
                            Date dateSS = new Date(ss *1000L);
                            SimpleDateFormat hSunset = new SimpleDateFormat("HH");
                            String hourSunset = hSunset.format(dateSS);
                            SimpleDateFormat mSunset = new SimpleDateFormat("mm");
                            String minuteSunset = mSunset.format(dateSS);
                            Log.d("sunrise", hourSunrise +  " " + minuteSunrise + " " +hourSunset + " " + minuteSunset);
                            SunRiseSunSet(mSunriseSunsetView,Integer.parseInt(hourSunrise),Integer.parseInt(minuteSunrise),Integer.parseInt(hourSunset),Integer.parseInt(minuteSunset));
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
    private void GetTempNightDay (String data) {
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
                            int i = 0;
                            JSONArray jsonArrayList = jsonObject1.getJSONArray("list");
                            //    for (int i = 0; i < jsonArrayList.length(); i++){

                            JSONObject jsonObjectList = jsonArrayList.getJSONObject(i);

                            ngay = jsonObjectList.getString("dt");
                            l = Long.valueOf(ngay);
                            Date date = new Date(l * 1000L);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd");
                            String Day = simpleDateFormat.format(date);
                            Log.d("Day7ngay", "onResponse: " + Day);

                            JSONObject jsonObjectMain = jsonObjectList.getJSONObject("temp");
                            String tempDay = jsonObjectMain.getString("day");
                            String tempNight = jsonObjectMain.getString("night");
                            txtTempDay.setText("Day : " + tempDay + " °C ↑");
                            txtTempNight.setText("Night : " + tempNight + " °C ↓");

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

                            String ngay = "";
                            long l = 0;
                            JSONArray jsonArrayList = jsonObject1.getJSONArray("list");
                            List<Thoitiet24h> tempList = new ArrayList<>();
                            Log.d("LENGTH", "onResponse: " + jsonArrayList.length());
                            // data to draw bar graph
                            List<String> hourInChart = new ArrayList<>();
                            List<Float> speedInChart = new ArrayList<>();
                            for (int i = 0; i < jsonArrayList.length(); i++) {

                                JSONObject jsonObjectList = jsonArrayList.getJSONObject(i);

                                ngay = jsonObjectList.getString("dt");
                                l = Long.valueOf(ngay);
                                Date date = new Date(l * 1000L);
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                                String Time24h = simpleDateFormat.format(date);
                                Log.d("TIme", "onResponse: " + Time24h);
                                SimpleDateFormat hourDateFormat = new SimpleDateFormat("HH");
                                //String hour = simpleDateFormat.format(date).substring(0,2);

                                //int ih = Integer.parseInt(hour);
                                hourInChart.add(Time24h);
                                //Log.d("kgljgldfg" , hour);
                                JSONObject jsonObjectMain = jsonObjectList.getJSONObject("main");
                                String temp = jsonObjectMain.getString("temp");
                                //axisData.add(temp);
                                Double a = Double.valueOf(temp);

                                String Nhietdo24h = String.valueOf(a.intValue());

                                JSONArray jsonArrayWeather = jsonObjectList.getJSONArray("weather");
                                JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                                String icon = jsonObjectWeather.getString("icon");
                                //drawChart(barChart);

                                JSONObject jsonObjectWind = jsonObjectList.getJSONObject("wind");
                                String speed = jsonObjectWind.getString("speed");
                                Log.d("speed", "onResponse: " + speed);


                                speedInChart.add(Float.parseFloat(speed));
                                tempList.add(new Thoitiet24h(Time24h, icon, Nhietdo24h));
                            }
                            //drawChart(barChart,hourInChart,speedInChart);
                            // hourInChart
                            String [] hIC = hourInChart.toArray(new String[hourInChart.size()]);
                            Log.d("hic", "onResponse: " + hIC[7]);
                            drawChart2(barChart,hIC,speedInChart);
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
    private void GetUVIndex(String data) {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + data + "&units=metric&appid=b195255676fe196e397398381ac43e10";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Log.d("RESPONSE API", response);

                            JSONObject jsonObjectCoord = jsonObject.getJSONObject("coord");
                            String lat = jsonObjectCoord.getString("lat");
                            String lon = jsonObjectCoord.getString("lon");
                            RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
                            //String UVurl = "http://api.openweathermap.org/data/2.5/uvi/forecast?appid=b195255676fe196e397398381ac43e10&lat=" + lat + "&lon="+lon+"&cnt=0";
                            Log.d("LAT", "onResponse: " + lat + " " + lon);
                            String UVurl = "http://api.openweathermap.org/data/2.5/uvi?appid=b195255676fe196e397398381ac43e10&lat=" +lat+ "&lon=" +lon+ "";
                            StringRequest stringRequest = new StringRequest(Request.Method.GET, UVurl,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String responseUV) {
                                            try {
                                                Log.d("ABC", "onResponse: " + "ADDD");
                                                JSONObject jsonObjectUV = new JSONObject(responseUV);
                                                Log.d("UV INDEX API", responseUV);
                                                String UVIndex = jsonObjectUV.getString("value");
                                                Log.d("Uv index",UVIndex);
                                                txtUvIndex.setText("UV Index : " + UVIndex);
                                             //   String valueUV = jsonObject.getString("value");
                                             //   Log.d("UV : ", "onResponse: " + valueUV);
                                            } catch (Exception e) {
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


//                            String Ozonurl = "http://api.openweathermap.org/pollution/v1/o3/+"+lat+","+lon+"/current.json?appid=b195255676fe196e397398381ac43e10";
//                            StringRequest stringOzonRequest = new StringRequest(Request.Method.GET, Ozonurl,
//                                    new Response.Listener<String>() {
//                                        @Override
//                                        public void onResponse(String responseOzon) {
//                                            try {
//                                                Log.d("ABC", "onResponse: " + "ADDD");
//                                                JSONObject jsonObjectUV = new JSONObject(responseOzon);
//                                                Log.d("Ozon INDEX API", responseOzon);
//                                                String OzonIndex = jsonObjectUV.getString("data");
//                                                Log.d("dataO3",OzonIndex);
//                                                //txtUvIndex.setText("UV Index: " + OzonIndex);
//                                                //   String valueUV = jsonObject.getString("value");
//                                                //   Log.d("UV : ", "onResponse: " + valueUV);
//                                            } catch (Exception e) {
//                                                e.printStackTrace();
//                                            }
//                                        }
//                                    },
//                                    new Response.ErrorListener() {
//                                        @Override
//                                        public void onErrorResponse(VolleyError error) {
//                                            Log.d("today", "err:  " + error);
//                                            Toast.makeText(getActivity().getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
//                                        }
//                                    });
//                            requestQueue.add(stringOzonRequest);
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
    private void drawChart(BarChart barChart, List<Integer> a, List<Float> b) {
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.setBorderColor(1);
        barChart.getAxisRight().setDrawGridLines(false);
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getXAxis().setDrawGridLines(false);
        Description description = new Description();
        description.setText("");
        barChart.setDescription(description);
        barChart.setMaxVisibleValueCount(50);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(false);
        List<BarEntry> yVals = new ArrayList<BarEntry>();
        /* String c[] ={"1", "2"}; */
        for(int i = 0; i < 8; i++) {
            yVals.add(new BarEntry( i, b.get(i)));
        }

        BarDataSet barDataSet;
        barDataSet = new BarDataSet(yVals, "Wind (m/s)");
        barDataSet.setValues(yVals);
        barDataSet.setValueTextSize(17);
        barDataSet.setColor(Color.WHITE);
        BarData data = new BarData(barDataSet);
        //yAxisLeft.setAxisMaxValue(10000);
        barChart.setData(data);
    }
    private void drawChart2 (BarChart chart,String[] values,List<Float> A) {
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
    private void SunRiseSunSet(SunriseSunsetView mSunriseSunsetView, int hSunriseTime, int mSunriseTime, int hSunsetTime,int mSunsetTime) {
        mSunriseSunsetView.setSunriseTime(new com.github.tianma8023.model.Time(hSunriseTime,mSunriseTime));
        mSunriseSunsetView.setSunsetTime(new Time(hSunsetTime,mSunsetTime));
        mSunriseSunsetView.startAnimate();
    }
}
