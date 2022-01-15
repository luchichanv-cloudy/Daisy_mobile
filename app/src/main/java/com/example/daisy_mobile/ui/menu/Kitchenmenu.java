package com.example.daisy_mobile.ui.menu;

import static com.example.daisy_mobile.p02_signin.user_id;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.daisy_mobile.R;
import com.example.daisy_mobile.adapter.FoodItemAdapter;
import com.example.daisy_mobile.adapter.TopkitchenViewpagerAdapter;
import com.example.daisy_mobile.databinding.FragmentHomeBinding;
import com.example.daisy_mobile.databinding.FragmentKitchenmenuBinding;
import com.example.daisy_mobile.p02_signin;
import com.example.daisy_mobile.p03_signup;
import com.example.daisy_mobile.p28_addnewitem;
import com.example.daisy_mobile.ui.home.HomeViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import dataclass.item;
import dataclass.kitchen;


public class Kitchenmenu extends Fragment {
    private FragmentKitchenmenuBinding binding;
    private ListView lv;
    private FirebaseFirestore db;
    private Button btnnext;
    private ArrayList<item> arrayOfitem;
    private KitchenmenuViewModel kitchenmenuviewmodel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_kitchenmenu, container, false);



        user_id= FirebaseAuth.getInstance().getUid();
        lv =(ListView) view.findViewById(R.id.kitchenmenu_lv);
            db = FirebaseFirestore.getInstance();
            db.collection("item")
                    .whereEqualTo("kitchenID",user_id)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        //    item ia=new item("denden","ae422c35-f366-40a7-bbee-67a82979d7b2","hello","drink","3vWBk5eX7lcjexVK0zVXLYPtFFq1",50,30,false);
                            arrayOfitem = new ArrayList<item>();
                            FoodItemAdapter adapter = new FoodItemAdapter(getContext(), arrayOfitem);
                            lv.setAdapter(adapter);
                         //   arrayOfitem.add(ia);
                            if (task.isSuccessful()) {
                                int i=0;
                                for (QueryDocumentSnapshot document : task.getResult()) {

                                    item abc=document.toObject(item.class);
                                    arrayOfitem.add(abc);adapter.notifyDataSetChanged();
                                }

                            } else {


                                //   Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });

            btnnext=(Button) view.findViewById(R.id.kitchenmenu_btn);
            btnnext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getContext(), p28_addnewitem.class));
                }
            });
        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}