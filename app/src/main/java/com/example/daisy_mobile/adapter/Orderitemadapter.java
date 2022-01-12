package com.example.daisy_mobile.adapter;

import android.content.Context;
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

import com.example.daisy_mobile.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import dataclass.order;

public class Orderitemadapter extends ArrayAdapter<order> {
    private Context context;
    private ArrayList<order> orders;

    public Orderitemadapter(@NonNull Context context, int resource, Context context1, ArrayList<order> orders) {
        super(context, resource);
        this.context = context1;
        this.orders = orders;
    }

    private void getimage(String id,ImageView imgview)
    {

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
        if(id==order.getKitchen_id())
        {
            getimage(order.getUser_id(),iv_avatar);
        }
        else if(id==order.getUser_id())
        {
            getimage(order.getKitchen_id(),iv_avatar);
        }

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
