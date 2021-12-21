package com.example.daisy_mobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.daisy_mobile.R;

import java.util.ArrayList;

import dataclass.item;

public class FoodItemAdapter extends ArrayAdapter<item> {
    public FoodItemAdapter(Context context, ArrayList<item> items) {
        super(context, 0,items);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        item item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fooditem, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.fooditem_TV_name);
        TextView tvDescription= (TextView) convertView.findViewById(R.id.fooditem_TV_description);
        TextView tvprice = (TextView) convertView.findViewById(R.id.fooditem_TV_price);

        // Populate the data into the template view using the data object
        tvName.setText(item.getName());
        tvDescription.setText(item.getDescription());
        if(item.isSalestatus())
        {
            tvprice.setText(item.getPricesale());
        }
        else
        {
            tvprice.setText(item.getPrice());
        }

        // Return the completed view to render on screen
        return convertView;
    }
}
