package com.example.daisy_mobile;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import dataclass.kitchen;

public class FavouriteViewHolder extends RecyclerView.ViewHolder {
    final TextView kitchen_name; ImageView kitchen_avatar;
    private Context context;
    public FavouriteViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        kitchen_name = itemView.findViewById(R.id.kitchen_name);
        kitchen_avatar=itemView.findViewById(R.id.kitchen_avatar);
        this.context=context;
    }

    public void setViewValue(kitchen kitchen) {
        kitchen_name.setText(kitchen.getName());
        String kitchenimageid=kitchen.getImageID();
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        storageRef.child("images/"+kitchenimageid).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                Glide.with(context /* context */)
                        .load(uri)
                        .into(kitchen_avatar);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                Toast.makeText(context,"Failed ",Toast.LENGTH_LONG);
            }
        });
    }
}
