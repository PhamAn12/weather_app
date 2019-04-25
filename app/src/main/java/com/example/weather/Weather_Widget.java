package com.example.weather;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.weather.Object.WeatherToday;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.paperdb.Paper;

/**
 * Implementation of App Widget functionality.
 */
public class Weather_Widget extends AppWidgetProvider {
    static String CLICK_ACTION = "CLICKED";
    ImageView img_widget;
    void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        Intent intent = new Intent(context,Weather_Widget.class);
        intent.setAction(CLICK_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,intent,0);
        //initPaper
        Paper.init(context);
        Log.d("Widget", "1");
        //Read Content

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.weather__widget,null);
        Log.d("Widget", "2");
         img_widget = (ImageView) view.findViewById(R.id.img_widget);
        // Construct the RemoteViews object
        Log.d("Widget", "3");

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.weather__widget);


//
//        this.getCurrentWeatherWidget(context, "Hanoi");

        String temp = Paper.book().read("temp");
        Log.d("temp","maa " + temp);
        String tempurature = temp + "°C";
        String icon = Paper.book().read("icon");
        Log.d("icon","nhu cuc " + icon);
        String link = "http://openweathermap.org/img/w/" + icon + ".png";
        Log.d("icon","nhu cuc " + link);

        String status = Paper.book().read("status");
        views.setTextViewText(R.id.txt_temperature,tempurature);
        views.setTextViewText(R.id.txt_status, status);
//        views.setImageViewUri(R.id.img_widget, Uri.parse(link));
//
//        Picasso.with(context).load(link).into(img_widget);
        views.setOnClickPendingIntent(R.id.widget_layout,pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onReceive(Context context, Intent intent) {

            super.onReceive(context, intent);
            if (intent.getAction().equals(CLICK_ACTION)) {
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setClass(context, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
    }

//    public void getCurrentWeatherWidget(final Context context, final String data){
//        Log.d("widget", "city: " + data);
//        RequestQueue requestQueue = Volley.newRequestQueue(context);
//        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + data + "&units=metric&appid=b195255676fe196e397398381ac43e10";
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            Log.d("RESPONSE API", response);
//
//                            String day = jsonObject.getString("dt");
//                            String name = jsonObject.getString("name");
//                            //    txtName.setText(name);
//
//                            long l = Long.valueOf(day);
//                            Date date = new Date(l * 1000L);
//                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd HH-mm-ss");
//                            String Day = simpleDateFormat.format(date);
//                            //textViewDay.setText(Day);
//
//                            JSONArray jsonArrayWeather = jsonObject.getJSONArray("weather");
//                            JSONObject jsonObject1Weather = jsonArrayWeather.getJSONObject(0);
//                            String status = jsonObject1Weather.getString("main");
//                            String icon = jsonObject1Weather.getString("icon");
//                            txt_status.setText(status);
//                            Picasso.with(context).load("http://openweathermap.org/img/w/" + icon + ".png").into(img_widget);
//                            //    txtState.setText(status);
//
//                            JSONObject jsonObject1Main = jsonObject.getJSONObject("main");
//                            String nhietdo = jsonObject1Main.getString("temp");
//                            String doam = jsonObject1Main.getString("humidity");
//                            Double a = Double.valueOf(nhietdo);
//                            String Nhietdo = String.valueOf(a.intValue());
//                            txt_temperature.setText(Nhietdo);
//                            //    txtHumidity.setText(doam + "%");
//
//                            JSONObject jsonObject1Wind = jsonObject.getJSONObject("wind");
//                            String gio = jsonObject1Wind.getString("speed");
//                            //   txtWind.setText(gio + "m/s");
//
//                            JSONObject jsonObject1Clouds = jsonObject.getJSONObject("clouds");
//                            String may = jsonObject1Clouds.getString("all");
//                            //    txtCloud.setText(may + "%");
//                            txt_status.setText(status);
//                            txt_temperature.setText(Nhietdo);
//                            Picasso.with(context).load("http://openweathermap.org/img/w/"+icon+".png").into(img_widget);
//                            Log.d("widget", "infor:  " + txt_temperature.getText() + txt_status.getText());
//                        }
//                        catch (JSONException e) {
//                            Log.d("widget","bug: "+ e.toString());
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.d("today", "err network:  " + error);
//                        Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
//                    }
//                });
//        requestQueue.add(stringRequest);
//    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

