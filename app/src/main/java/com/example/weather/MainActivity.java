package com.example.weather;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.weather.Fragments.SevenDays;
import com.example.weather.Fragments.Today;
import com.example.weather.Fragments.Tomorrow;
import com.example.weather.Object.WeatherToday;
import com.example.weather.Utils.Utils;
import com.github.mikephil.charting.charts.CombinedChart;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity {
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    ImageView imageSearch;
    TextView textViewCity ;
    Button button;
    private String country;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        imageSearch = (ImageView) findViewById(R.id.search);
        imageSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivityForResult(intent, 810);
            }
        });

        String dataCity = "Ha Noi";


        textViewCity = (TextView) findViewById(R.id.txt_appbar_city);
        Log.d("text", "onCreate: " + textViewCity.getText().toString());
        if(textViewCity.getText().toString().equals("") || textViewCity.getText().toString() == null) {
            Log.d("vaoday", "onCreate: vao day");
            textViewCity.setText("Hanoi");
            GetCurrentWeatherData("Hanoi");
        }

//
        Intent intent = getIntent();
        if(intent.getStringExtra("cityName") != "" && intent.getStringExtra("cityName") !=null) {
            dataCity = intent.getStringExtra("cityName");
        }
        else if(intent.getStringExtra("city") != "" && intent.getStringExtra("city") !=null) {
            Log.d("main", "city from gps: " + intent.getStringExtra("city"));
            dataCity = intent.getStringExtra("city");
        }
        Log.d("main", "city: " + dataCity);

//        Log.d("datacity", "onCreate: "+ dataCity);
        textViewCity.setText(dataCity);
        GetCurrentWeatherData(dataCity);
        Log.d("country", "onCreate: " + country);
        getWindow().setBackgroundDrawableResource(R.drawable.blue);
    }
    // Lấy các dữ liệu hiện tại
    public void GetCurrentWeatherData(final String data) {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
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
                        //    txtDay.setText(Day);

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
                        //    txtTemp.setText(Nhietdo + "°C");
                        //    txtHumidity.setText(doam + "%");

                            JSONObject jsonObject1Wind = jsonObject.getJSONObject("wind");
                            String gio = jsonObject1Wind.getString("speed");
                         //   txtWind.setText(gio + "m/s");

                            JSONObject jsonObject1Clouds = jsonObject.getJSONObject("clouds");
                            String may = jsonObject1Clouds.getString("all");
                        //    txtCloud.setText(may + "%");

                            JSONObject jsonObject1Sys = jsonObject.getJSONObject("sys");
                            country = jsonObject1Sys.getString("country");
                            Log.d("Nước", "onResponse: " + country);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        requestQueue.add(stringRequest);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter (FragmentManager n) { super(n); }
        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    Today today = new Today();
                    Bundle bundle = new Bundle();
                    bundle.putString("dataC",textViewCity.getText().toString());
                    Log.d("adap", "getItem: " + country);
                    bundle.putString("dn",country);
                    today.setArguments(bundle);
                    return today;
                case 1:
                    Tomorrow tomorrow = new Tomorrow();
                    Bundle bundleTomrrow = new Bundle();
                    bundleTomrrow.putString("dataC",textViewCity.getText().toString());
                    Log.d("adapTomorrow", "getItem: " + country);
                    bundleTomrrow.putString("dn",country);
                    tomorrow.setArguments(bundleTomrrow);
                    return tomorrow;
                case 2:
                    SevenDays sevenDays = new SevenDays();
                    Bundle bundleSevenDays = new Bundle();
                    bundleSevenDays.putString("dataC",textViewCity.getText().toString());
                    Log.d("sevendatddd", "getItem: " + country);
                    bundleSevenDays.putString("dn",country);
                    sevenDays.setArguments(bundleSevenDays);
                    return sevenDays;
                default:
                    return null;
            }

        }

        @Override
        public int getCount() {
            return 3;
        }
    }



}
