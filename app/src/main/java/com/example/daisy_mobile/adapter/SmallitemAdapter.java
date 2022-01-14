package com.example.daisy_mobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.daisy_mobile.R;

import java.util.ArrayList;

import dataclass.item;
import dataclass.order_item;

public class SmallitemAdapter extends ArrayAdapter<order_item> {
    private ArrayList<order_item> items;

    public SmallitemAdapter(Context context, ArrayList<order_item> items) {
        super(context, 0,items);
        this.items = items;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.smallitem, parent, false);
        }
        order_item item= items.get(position);
        TextView name=convertView.findViewById(R.id.tv_name);
        TextView quantity=convertView.findViewById(R.id.tv_quantity);
        TextView price=convertView.findViewById(R.id.tv_price);
        name.setText(item.getName());
        quantity.setText(Integer.toString(item.getQuantity()));
        price.setText(Integer.toString(item.getPrice()));
        return convertView;
    }
}
