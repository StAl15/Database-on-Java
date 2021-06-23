package com.example.database;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

//обработать и показать данные
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    Activity activity;
    private ArrayList moneta_id,
            nominal_coin,
            year_coin,
            price_coin ;
    Animation translate_anim;

    CustomAdapter(Activity activity,
                  Context context,
                  ArrayList moneta_id,
                  ArrayList nominal_coin,
                  ArrayList year_coin,
                  ArrayList price_coin){
        this.activity = activity;
        this.moneta_id = moneta_id;
        this.context = context;
        this.nominal_coin = nominal_coin;
        this.year_coin = year_coin;
        this.price_coin = price_coin;



    }
    //показать содержимое
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    //указать содержимое и устаносить его текст
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.moneta_id_txt.setText(String.valueOf(moneta_id.get(position)));
        holder.nominal_txt.setText(String.valueOf(nominal_coin.get(position)));
        holder.year_txt.setText(String.valueOf(year_coin.get(position)));
        holder.price_txt.setText(String.valueOf(price_coin.get(position)));
        holder.mainLayout.setOnClickListener(v -> {
            Intent intent = new Intent(context, UpdateActivity.class);
            intent.putExtra("id", String.valueOf(moneta_id.get(position)));
            intent.putExtra("nominal", String.valueOf(nominal_coin.get(position)));
            intent.putExtra("year", String.valueOf(year_coin.get(position)));
            intent.putExtra("price", String.valueOf(price_coin.get(position)));

            activity.startActivityForResult(intent, 1);
        });
    }

    //счётчик для id
    @Override
    public int getItemCount() {

        return moneta_id.size();
    }

    //что будет в recyclerView
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView moneta_id_txt, nominal_txt, year_txt, price_txt;
        LinearLayoutCompat mainLayout;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            moneta_id_txt = itemView.findViewById(R.id.moneta_id_txt);
            nominal_txt = itemView.findViewById(R.id.nominal_txt);
            year_txt = itemView.findViewById(R.id.year_txt);
            price_txt = itemView.findViewById(R.id.price_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);

            //анимация recycler view
            translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);


        }
    }
}
