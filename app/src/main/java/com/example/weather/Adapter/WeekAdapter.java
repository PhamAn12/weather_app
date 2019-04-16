package com.example.weather.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weather.Object.WeatherToday;
import com.example.weather.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class WeekAdapter extends BaseAdapter {
    Context context;
    ArrayList<WeatherToday> arrayList;

    public WeekAdapter(Context context, ArrayList<WeatherToday> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }
    @Override

    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.line, null);

        WeatherToday thoitiet = arrayList.get(i);

        TextView txtDay = (TextView)view.findViewById(R.id.day);
        TextView txtStatus = (TextView)view.findViewById(R.id.textviewTrangthai);
        TextView txtMinTemp = (TextView)view.findViewById(R.id.textviewMinTemp);
        TextView txtMaxTemp = (TextView)view.findViewById(R.id.textviewMaxTemp);
        ImageView imgStatus = (ImageView) view.findViewById(R.id.imgStatus);

        txtDay.setText(thoitiet.getDay());
        txtStatus.setText(thoitiet.getStatus());
        txtMinTemp.setText(thoitiet.getMinTemp() + "°C");
        txtMaxTemp.setText(thoitiet.getMaxTemp() + "°C");

        Picasso.with(context).load("http://openweathermap.org/img/w/"+thoitiet.getImgStatus()+".png").into(imgStatus);
        return view;
    }
}
