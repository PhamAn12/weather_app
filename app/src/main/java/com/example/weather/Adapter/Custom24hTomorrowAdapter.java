package com.example.weather.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weather.Object.Thoitiet24h;
import com.example.weather.Object.Tomorrow24h;
import com.example.weather.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Custom24hTomorrowAdapter extends BaseAdapter {
    public Custom24hTomorrowAdapter(Context context, ArrayList<Tomorrow24h> arrayList24hTomorrow) {
        this.context = context;
        this.arrayList24hTomorrow = arrayList24hTomorrow;
    }

    Context context;
    ArrayList<Tomorrow24h> arrayList24hTomorrow;
    @Override
    public int getCount() {
        return arrayList24hTomorrow.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList24hTomorrow.get(i);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.activity_24h_tomorrow, null);

        Tomorrow24h tomorrow24h = arrayList24hTomorrow.get(i);
        Log.d("TT24h", "getView: " + tomorrow24h);
        TextView txtTime24h = (TextView)view.findViewById(R.id.tvTime24hTomorrow);
        TextView txtNhietdo24h = (TextView)view.findViewById(R.id.tvNhietdo24hTomorrow);
        ImageView imgIcon24h = (ImageView) view.findViewById(R.id.imgIcon24hTomorrow);
        txtTime24h.setText(tomorrow24h.getTime24h());
        txtNhietdo24h.setText(tomorrow24h.getTemp24h());

        Picasso.with(context).load("http://openweathermap.org/img/w/"+tomorrow24h.getIcon24h()+".png").into(imgIcon24h);
        return view;
    }
}
