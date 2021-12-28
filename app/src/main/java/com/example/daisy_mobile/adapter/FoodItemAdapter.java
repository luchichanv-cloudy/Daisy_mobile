package com.example.daisy_mobile.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.daisy_mobile.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import dataclass.item;

public class FoodItemAdapter extends ArrayAdapter<item> {
    private Context context;
    private ArrayList<item> items;

    public FoodItemAdapter(Context context, ArrayList<item> items) {
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fooditem, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.fooditem_TV_name);
        TextView tvDescription= (TextView) convertView.findViewById(R.id.fooditem_TV_description);
        TextView tvprice = (TextView) convertView.findViewById(R.id.fooditem_TV_price);
        ImageView imageView=(ImageView)convertView.findViewById(R.id.img);
        // Populate the data into the template view using the data object
        tvName.setText(item.getName());
        tvDescription.setText(item.getDescription());
        if(item.isSalestatus())
        {
            int res=item.getPricesale();
            tvprice.setText(Integer.toString(res));
        }
        else
        {
            int res=item.getPrice();
            tvprice.setText(Integer.toString(res));
        }

        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        storageRef.child("images/"+item.getImageID()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                Glide.with(context /* context */)
                        .load(uri)
                        .into(imageView);
                Toast.makeText(context,"Success "+uri,Toast.LENGTH_LONG);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                Toast.makeText(context,"Failed ",Toast.LENGTH_LONG);
            }
        });
        // Return the completed view to render on screen
        return convertView;
    }
}
