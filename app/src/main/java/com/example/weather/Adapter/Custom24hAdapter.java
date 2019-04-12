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
import com.example.weather.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Custom24hAdapter extends BaseAdapter {
    public Custom24hAdapter(Context context, ArrayList<Thoitiet24h> arrayList24h) {
        this.context = context;
        this.arrayList24h = arrayList24h;
    }

    Context context;
    ArrayList<Thoitiet24h> arrayList24h;

    @Override
    public int getCount() {
        return arrayList24h.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList24h.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.activity_24h, null);

        Thoitiet24h thoitiet24h = arrayList24h.get(i);
        Log.d("TT24h", "getView: " + thoitiet24h);
        TextView txtTime24h = (TextView)view.findViewById(R.id.tvTime24h);
        TextView txtNhietdo24h = (TextView)view.findViewById(R.id.tvNhietdo24h);
        ImageView imgIcon24h = (ImageView) view.findViewById(R.id.imgIcon24h);
        txtTime24h.setText(thoitiet24h.getTime24h());
        txtNhietdo24h.setText(thoitiet24h.getNhietdo24h());

        Picasso.with(context).load("http://openweathermap.org/img/w/"+thoitiet24h.getIcon24h()+".png").into(imgIcon24h);
        return view;
    }
}
