package com.example.daisy_mobile.ui.menu;

import static com.example.daisy_mobile.p02_signin.user_id;

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
import com.example.daisy_mobile.ui.home.HomeViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
        kitchenmenuviewmodel =
                new ViewModelProvider(this).get(KitchenmenuViewModel.class);
        binding = FragmentKitchenmenuBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        arrayOfitem = new ArrayList<item>();

        FoodItemAdapter adapter = new FoodItemAdapter(getContext(), arrayOfitem);
        lv =(ListView) root.findViewById(R.id.kitchenmenu_lv);
        lv.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();
        db.collection("item")
                .whereEqualTo("kitchenid",user_id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int i=0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //  Log.d(TAG, document.getId() + " => " + document.getData());
                                item abc=document.toObject(item.class);
                                arrayOfitem.add(abc);

                            }
                        } else {
                            //   Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        adapter.notifyDataSetChanged();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}