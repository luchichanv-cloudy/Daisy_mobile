package com.example.daisy_mobile.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ToggleButton;

import com.example.daisy_mobile.R;

import java.util.ArrayList;

import dataclass.item;

public class KitchensalesItemAdapter extends ArrayAdapter<item>  {

    private Context context;
    private ArrayList<item> items;
    public KitchensalesItemAdapter(Context context, ArrayList<item> items) {
        super(context, 0,items);
        this.context=context;
        this.items=items;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        item item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_kitchensales, parent, false);
        }
        ToggleButton tog = (ToggleButton) convertView.findViewById(R.id.toggleButton);
        ImageView img=(ImageView) convertView.findViewById(R.id.img);

        return convertView;
    }
}
