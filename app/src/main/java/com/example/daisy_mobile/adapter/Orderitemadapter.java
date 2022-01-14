package com.example.daisy_mobile.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.daisy_mobile.R;
import com.example.daisy_mobile.p06_shopmenu;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import dataclass.kitchen;
import dataclass.order;
import dataclass.user;

public class Orderitemadapter extends ArrayAdapter<order> {
    private Context context;
    private ArrayList<order> orders;
    private kitchen aaa; private user bbb;
    public Orderitemadapter(Context context ,ArrayList<order> orders) {
        super(context, 0,orders);
        this.context = context;
        this.orders = orders;
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

    private kitchen getkitchen(String kitchenid)
    {
        aaa=new kitchen();
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        DocumentReference documentRef = db.document("kitchens/"+kitchenid);
        documentRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        aaa=document.toObject(kitchen.class);

                    }
                }
            }
        });
        return  aaa;
    }
    private user getuser(String userid)
    {
       return null;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.orderitem, parent, false);
        }
        //init value
        ImageView iv_avatar= (ImageView) convertView.findViewById(R.id.iv_avatar);
        TextView tv_timecreated=(TextView) convertView.findViewById(R.id.tv_timecreated);
        TextView tv_othersidename=(TextView) convertView.findViewById(R.id.tv_othername);
        TextView tv_status=(TextView)convertView.findViewById(R.id.tv_status);
        ListView lv_item = (ListView) convertView.findViewById(R.id.lv_item);
        Button btn_dosomething = (Button) convertView.findViewById(R.id.btn_dosomething);
        String id = FirebaseAuth.getInstance().getUid();
        order order= orders.get(position);
        Integer status=order.getStatus();

        //set value
        tv_timecreated.setText(order.getCreated());
      //  tv_othersidename.setText(order.getKitchen_id());
        if(id.equals(order.getKitchen_id()))
        {
            getimage(order.getUser_id(),iv_avatar);
          //  tv_othersidename.setText(getuser(order.getUser_id()).getName());
            switch (status){
                case 0:  tv_status.setText("You have new order"); btn_dosomething.setText("Accept");
                case 1: tv_status.setText("Your customer is waiting for you"); btn_dosomething.setText("Done");
                case 2: tv_status.setText("Your order has been delivered"); btn_dosomething.setVisibility(View.GONE);
            }
        }
        else if(id.equals(order.getUser_id()))
        {
            tv_othersidename.setText(getkitchen(order.getUser_id()).getName());
            getimage(getkitchen(order.getUser_id()).getImageID(),iv_avatar);

            btn_dosomething.setVisibility(View.INVISIBLE);
            switch (status){
                case 0:  tv_status.setText("Your order is sending to kitchen");
                case 1: tv_status.setText("Kitchen is preparing your order");
                case 2: tv_status.setText("Your order has been delivered");
            }
        }

        btn_dosomething.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return convertView;
    }

    @NonNull
    @Override
    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<order> orders) {
        this.orders = orders;
    }

}
