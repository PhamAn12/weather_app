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
import android.widget.Switch;
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
        String des = Paper.book().read("des");
        Log.d("temp","maa " + temp);
        String tempurature = temp + "°C";
        String icon = Paper.book().read("icon");
        Log.d("icon","nhu cuc " + icon);
        String link = "http://openweathermap.org/img/w/" + icon + ".png";


        String status = Paper.book().read("status").toString();
        views.setTextViewText(R.id.txt_temperature,tempurature);
        views.setTextViewText(R.id.txt_status, status);
        Log.d("icon","nhu lin " + status.getClass() + " ss: " + status.compareTo("Clouds"));
//        views.setImageViewUri(R.id.img_widget, Uri.parse(link));
//        switch(des){
//            case: ""
//        }
        if(des.equals("clear sky")){
            views.setImageViewResource(R.id.img_widget,R.drawable.clear_sky);
        }
        else if(des.equals("few clouds")){
            views.setImageViewResource(R.id.img_widget,R.drawable.few_clouds);
        }
        else if(des.equals("scattered clouds")){
            views.setImageViewResource(R.id.img_widget,R.drawable.scattered_clouds);
        }
        else if(des.equals("broken clouds")){
            views.setImageViewResource(R.id.img_widget,R.drawable.broken_clouds);
        }
        else if(des.equals("shower rain")){
            views.setImageViewResource(R.id.img_widget,R.drawable.shower_rain);
        }
        else if(des.equals("rain")){
            views.setImageViewResource(R.id.img_widget,R.drawable.rain);
        }
        else if(des.equals("thunderstorm")){
            views.setImageViewResource(R.id.img_widget,R.drawable.thunderstorm);
        }
        else if(des.equals("snow")){
            views.setImageViewResource(R.id.img_widget,R.drawable.snow);
        }
        else if(des.equals("mist")){
            views.setImageViewResource(R.id.img_widget,R.drawable.mist);
        }
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

