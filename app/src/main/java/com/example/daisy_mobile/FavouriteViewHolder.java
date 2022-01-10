package com.example.daisy_mobile;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import dataclass.kitchen;

public class FavouriteViewHolder extends RecyclerView.ViewHolder {
    final TextView kitchen_name; ImageView kitchen_avatar;

    public FavouriteViewHolder(@NonNull View itemView) {
        super(itemView);
        kitchen_name = itemView.findViewById(R.id.kitchen_name);
        kitchen_avatar=itemView.findViewById(R.id.kitchen_avatar);

    }

    public void setViewValue(kitchen kitchen) {
        kitchen_name.setText(kitchen.getName());
    }
}
