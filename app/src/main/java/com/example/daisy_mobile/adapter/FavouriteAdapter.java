package com.example.daisy_mobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.daisy_mobile.FavouriteViewHolder;
import com.example.daisy_mobile.R;

import java.util.List;

import dataclass.kitchen;


public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteViewHolder> {
    List<kitchen> kitchenList;
    Context mContext;
    public FavouriteAdapter(List<kitchen> kitchenModelList, Context context) {
        this.kitchenList = kitchenModelList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public FavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        return new FavouriteViewHolder(inflater.inflate(R.layout.item_favourite, parent, false),mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteViewHolder holder, int position) {
        holder.setViewValue(kitchenList.get(position));
    }

    @Override
    public int getItemCount() {
        return kitchenList.size();
    }
}


