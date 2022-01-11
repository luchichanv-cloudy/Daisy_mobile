package com.example.daisy_mobile.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.net.Uri;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
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

public class ShopItemAdapter extends ArrayAdapter<item> {
    private Context context;
    private ArrayList<item> items;

    public ShopItemAdapter(Context context, ArrayList<item> items) {
        super(context, 0,items);
        this.context=context;
        this.items=items;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.shopmenu_item, parent, false);
        }
        item item = getItem(position);
        //init id
        TextView tv_price=(TextView) convertView.findViewById(R.id.tv_price);
        TextView tv_description=(TextView) convertView.findViewById(R.id.tv_description);
        TextView tv_name=(TextView) convertView.findViewById(R.id.tv_itemname);
        TextView tv_number=(TextView)convertView.findViewById(R.id.tv_number);
        ImageView iv_image=(ImageView) convertView.findViewById(R.id.iv_itemimage);
        ImageButton ib_plus=(ImageButton) convertView.findViewById(R.id.ib_plus);
        ImageButton ib_minus=(ImageButton) convertView.findViewById(R.id.ib_minus);
        //
        //set value
        tv_name.setText(item.getName());
        tv_description.setText(item.getDescription());
        if(item.isSalestatus())
        {
            String text = "<string name=\"line\">"+Integer.toString(item.getPricesale())+
                    "$ <strike>"+Integer.toString(item.getPrice())+"$ </strike> </string>";
            tv_price.setText(Html.fromHtml(text));
        }
        else
        {
            int res=item.getPrice();
            tv_price.setText(Integer.toString(res)+"$");
        }
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        storageRef.child("images/"+item.getImageID()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                Glide.with(context /* context */)
                        .load(uri)
                        .into(iv_image);
                Toast.makeText(context,"Success "+uri,Toast.LENGTH_LONG);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                Toast.makeText(context,"Failed ",Toast.LENGTH_LONG);
            }
        });

        // quantity
        ib_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer number= Integer.parseInt(tv_number.getText().toString());
                if(number>0) {number--;}
                tv_number.setText(Integer.toString(number));
            }
        });

        ib_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer number= Integer.parseInt(tv_number.getText().toString());
                number++;
                tv_number.setText(Integer.toString(number));
            }
        });
        return convertView;
    }

}
