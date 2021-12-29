package com.example.daisy_mobile.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.daisy_mobile.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

import dataclass.kitchen;

public class TopkitchenViewpagerAdapter extends PagerAdapter {

    Context context;
    kitchen images[];
    LayoutInflater layoutInflater;


    public TopkitchenViewpagerAdapter(Context context, kitchen images[]) {
        this.context = context;
        this.images = images;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = layoutInflater.inflate(R.layout.topfavoritekitchen, container, false);

        TextView name=(TextView) itemView.findViewById(R.id.textname);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
      //  imageView.setImageResource(images[position]);

        String ID=images[position].getImageID();
        String Name=images[position].getName();
        name.setText(Name);
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        storageRef.child("images/"+ID).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
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
        container.addView(itemView);

        //listening to image click
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "you clicked image " + (position + 1), Toast.LENGTH_LONG).show();
            }
        });

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
