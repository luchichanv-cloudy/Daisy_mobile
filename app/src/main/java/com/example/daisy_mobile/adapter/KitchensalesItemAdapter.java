package com.example.daisy_mobile.adapter;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;

import com.bumptech.glide.Glide;
import com.example.daisy_mobile.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import dataclass.item;
import dataclass.order;

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
        Switch s=(Switch)convertView.findViewById(R.id.toggleButton);

        ImageView img=(ImageView) convertView.findViewById(R.id.img);
        TextView name=(TextView)convertView.findViewById(R.id.tv_name);
        name.setText(item.getName());
        getimage(item.getImageID(),img);
        if (item.isSalestatus())
        {
            s.setChecked(true);
        }
        else {
            s.setChecked(false);
        }
        s.setTag(position);
        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update(s.isChecked(),position);
            }
        });

        return convertView;
    }
    void update(boolean value,int position)
    {

        item a=items.get(position);
        a.setSalestatus(value);
        notifyDataSetChanged();
        FirebaseFirestore db=FirebaseFirestore.getInstance();
       db.collection("item").document(a.getImageID()).update("salestatus",a.isSalestatus());
    }
    private void getimage(String url, ImageView displayview)
    {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        storageRef.child("images/"+url).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                Glide.with(getContext() /* context */)
                        .load(uri)
                        .into(displayview);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        });
    }
}
