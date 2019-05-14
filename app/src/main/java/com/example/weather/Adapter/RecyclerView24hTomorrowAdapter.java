package com.example.weather.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weather.Object.Thoitiet24h;
import com.example.weather.Object.Tomorrow24h;
import com.example.weather.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerView24hTomorrowAdapter extends RecyclerView.Adapter<RecyclerView24hTomorrowAdapter.RecyclerViewHolder24h> {
    Context context;
    List<Tomorrow24h> arrayList24hTomorrow;
    public RecyclerView24hTomorrowAdapter(Context context, List<Tomorrow24h> arrayList24hTomorrow) {
        this.context = context;
        this.arrayList24hTomorrow = arrayList24hTomorrow;

    }
    @NonNull
    @Override
    public RecyclerView24hTomorrowAdapter.RecyclerViewHolder24h onCreateViewHolder(ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemview = inflater.inflate(R.layout.activity_24h_tomorrow, parent, false);
        return new RecyclerViewHolder24h(itemview);
    }

    @Override
    public void onBindViewHolder(RecyclerView24hTomorrowAdapter.RecyclerViewHolder24h holder, int i) {
        Tomorrow24h thoitiet24htomorrow = arrayList24hTomorrow.get(i);
        holder.txtTime24h.setText(thoitiet24htomorrow.getTime24h());
        holder.txtNhietdo24h.setText(thoitiet24htomorrow.getTemp24h());
        Picasso.with(context).load("http://openweathermap.org/img/w/"+thoitiet24htomorrow.getIcon24h()+".png").into(holder.imgIcon24h);
    }

    @Override
    public int getItemCount() {
        return arrayList24hTomorrow.size();
    }
    public void resetList(List<Tomorrow24h> arrayList24hTomorrow) {
        this.arrayList24hTomorrow = arrayList24hTomorrow;
        notifyDataSetChanged();
    }
    public class RecyclerViewHolder24h extends RecyclerView.ViewHolder {
        TextView txtTime24h;
        TextView txtNhietdo24h;
        ImageView imgIcon24h;
        public RecyclerViewHolder24h(View itemView) {
            super(itemView);
            txtTime24h = (TextView)itemView.findViewById(R.id.tvTime24hTomorrow);
            txtNhietdo24h = (TextView)itemView.findViewById(R.id.tvNhietdo24hTomorrow);
            imgIcon24h = (ImageView) itemView.findViewById(R.id.imgIcon24hTomorrow);
        }
    }
}
