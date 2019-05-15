package com.example.weather;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {
    TextView txt_temp_unit;
    TextView txt_wind_unit;
    boolean isC = true;
    boolean isMs = true;
    String tempUnit = "°C";
    String windUnit = "m/s";
    String dataCity = "Hanoi";
    String position = "";
    ImageView imageView;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Intent intent = getIntent();
        if(intent.getStringExtra("tempUnit") != "" && intent.getStringExtra("tempUnit") !=null) {
            tempUnit = intent.getStringExtra("tempUnit");
            Log.d("main", "temp:" + tempUnit);
        }
        if(intent.getStringExtra("windUnit") != "" && intent.getStringExtra("windUnit") !=null) {
            windUnit = intent.getStringExtra("windUnit");
            Log.d("main", "wind:" + windUnit);
        }
        if(intent.getStringExtra("cityName") != "" && intent.getStringExtra("city") !=null) {
            dataCity = intent.getStringExtra("cityName");
            Log.d("main", "temp:" + tempUnit);
        }
        if(intent.getStringExtra("city") != "" && intent.getStringExtra("city") !=null) {
            position = intent.getStringExtra("city");
            Log.d("main", "temp:" + tempUnit);
        }
        txt_temp_unit = (TextView) findViewById(R.id.txt_temp_unit);
        txt_wind_unit = (TextView) findViewById(R.id.txt_wind_unit);
        txt_temp_unit.setText(tempUnit);
        txt_wind_unit.setText(windUnit);
        LinearLayout tempLinearLayout = findViewById(R.id.layout_temp);
        imageView = (ImageView) findViewById(R.id.imageBackSetting);
        tempLinearLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (isC == true) {
                    txt_temp_unit.setText("°F");
                    tempUnit = "°F";
                    isC = false;
                }
                else {
                    txt_temp_unit.setText("°C");
                    tempUnit = "°C";
                    isC = true;
                }
            }

        });
        LinearLayout windLinearLayout = findViewById(R.id.layout_wind);
        windLinearLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (isMs == true) {
                    txt_wind_unit.setText("km/h");
                    windUnit = "km/h";
                    isMs = false;
                }
                else {
                    txt_wind_unit.setText("m/s");
                    windUnit = "m/s";
                    isMs = true;
                }
            }

        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                intent.putExtra("tempUnit",tempUnit);
                intent.putExtra("windUnit",windUnit);
                intent.putExtra("cityName",dataCity);
                intent.putExtra("city",position);
                startActivityForResult(intent, 810);
            }
        });
    }

}
