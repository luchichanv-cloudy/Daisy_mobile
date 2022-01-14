package com.example.daisy_mobile.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.daisy_mobile.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collection;

import dataclass.kitchen;

public class FavorAdapter extends ArrayAdapter<kitchen> {
    private Context context;
    private ArrayList<kitchen> kitchens;

    public FavorAdapter(Context context, ArrayList<kitchen> kitchens) {
        super(context,0,kitchens);
        this.context = context;
        this.kitchens = kitchens;
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
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_favourite, parent, false);
        }
        kitchen kitchen= kitchens.get(position);
        TextView name = (TextView) convertView.findViewById(R.id.kitchen_name);
        ImageView img=(ImageView) convertView.findViewById(R.id.kitchen_avatar);
        getimage(kitchen.getImageID(),img);
        name.setText(kitchen.getName());
        return convertView;
    }
}
