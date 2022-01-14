package com.example.daisy_mobile.ui.sales;

import static com.example.daisy_mobile.p02_signin.user_id;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.daisy_mobile.R;
import com.example.daisy_mobile.adapter.FoodItemAdapter;
import com.example.daisy_mobile.adapter.KitchensalesItemAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import dataclass.item;


public class Kitchensales extends Fragment {
    private ListView lv;
    private ArrayList<item> items;
    private KitchensalesItemAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kitchensales, container, false);
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        String user_id= FirebaseAuth.getInstance().getUid();
        lv = (ListView) view.findViewById(R.id.lv_sale);

        db = FirebaseFirestore.getInstance();
        db.collection("item")
                .whereEqualTo("kitchenID",user_id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        //    item ia=new item("denden","ae422c35-f366-40a7-bbee-67a82979d7b2","hello","drink","3vWBk5eX7lcjexVK0zVXLYPtFFq1",50,30,false);
                        items = new ArrayList<item>();
                        adapter = new KitchensalesItemAdapter(getContext(), items);
                        lv.setAdapter(adapter);
                        //   arrayOfitem.add(ia);
                        if (task.isSuccessful()) {
                            int i=0;
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                item abc=document.toObject(item.class);
                                items.add(abc);adapter.notifyDataSetChanged();
                            }

                        } else {
                            //   Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        return view;
    }
}