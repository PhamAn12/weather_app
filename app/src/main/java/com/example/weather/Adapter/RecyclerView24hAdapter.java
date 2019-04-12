package com.example.weather.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weather.Object.Thoitiet24h;
import com.example.weather.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerView24hAdapter extends RecyclerView.Adapter<RecyclerView24hAdapter.RecyclerViewHolder24h> {
    Context context;
    List<Thoitiet24h> arrayList24h;
    public RecyclerView24hAdapter(Context context, List<Thoitiet24h> arrayList24h) {
        this.context = context;
        this.arrayList24h = arrayList24h;

    }

    @Override
    public RecyclerView24hAdapter.RecyclerViewHolder24h onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemview = inflater.inflate(R.layout.activity_24h, parent, false);
        return new RecyclerViewHolder24h(itemview);
    }

    @Override
    public void onBindViewHolder(RecyclerView24hAdapter.RecyclerViewHolder24h holder, int position) {

        Thoitiet24h thoitiet24h = arrayList24h.get(position);
        holder.txtTime24h.setText(thoitiet24h.getTime24h());
        holder.txtNhietdo24h.setText(thoitiet24h.getNhietdo24h());
        Picasso.with(context).load("http://openweathermap.org/img/w/"+thoitiet24h.getIcon24h()+".png").into(holder.imgIcon24h);
    }

    @Override
    public int getItemCount() {
        return arrayList24h.size();
    }

    public void resetList(List<Thoitiet24h> arrayList24h) {
        this.arrayList24h = arrayList24h;
        notifyDataSetChanged();
    }
    public class RecyclerViewHolder24h extends RecyclerView.ViewHolder{
        TextView txtTime24h;
        TextView txtNhietdo24h;
        ImageView imgIcon24h;
        public RecyclerViewHolder24h(View itemView) {
            super(itemView);
            txtTime24h = (TextView)itemView.findViewById(R.id.tvTime24h);
            txtNhietdo24h = (TextView)itemView.findViewById(R.id.tvNhietdo24h);
            imgIcon24h = (ImageView) itemView.findViewById(R.id.imgIcon24h);
        }
    }
}
