package com.example.daisy_mobile.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.daisy_mobile.R;
import com.example.daisy_mobile.adapter.TopkitchenViewpagerAdapter;
import com.example.daisy_mobile.databinding.FragmentHomeBinding;
import com.example.daisy_mobile.p05_searchresult;
import com.example.daisy_mobile.p06_shopmenu;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import dataclass.kitchen;

public class HomeFragment extends Fragment {
    public static String kitchen_display_id;
    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private FirebaseFirestore db;
    private ImageView imageView;
    private ViewPager mViewPager;
    private DatabaseReference mDatabaseRef;
    private ImageButton btnsearch;
    public static  String searchtext;
    //private int[] images;
    int vpposition;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final TextView textView = binding.hfTvReccomend;
        mViewPager= (ViewPager)root.findViewById(R.id.hf_vp_topfavorite);
      //  ClickableViewPager mViewPager = (ClickableViewPager) root.findViewById(R.id.hf_vp_topfavorite);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int Position) {
               vpposition=Position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getContext(),p06_shopmenu.class);
                startActivity(i);
            }
        });
        //image slider
       db = FirebaseFirestore.getInstance();

        kitchen ID[]=new kitchen[3];

        //get id of 3 kitchens have top vote
        db.collection("kitchens")
                .orderBy("vote", Query.Direction.DESCENDING)
                .limit(3)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int i=0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                              //  Log.d(TAG, document.getId() + " => " + document.getData());
                                kitchen abc=document.toObject(kitchen.class);
                                ID[i]=abc;
                                i=i+1;

                            }
                            TopkitchenViewpagerAdapter mViewPagerAdapter = new TopkitchenViewpagerAdapter(getContext(),ID );
                            mViewPager.setAdapter(mViewPagerAdapter);
                            mViewPagerAdapter.notifyDataSetChanged();
                        } else {
                         //   Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        // nut search
        EditText etsearch = (EditText)root.findViewById(R.id.hf_et_search);
        ImageButton btnsearch = (ImageButton) root.findViewById(R.id.hf_ib_search);
        btnsearch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                searchtext=etsearch.getText().toString();
                startActivity(new Intent(getContext(), p05_searchresult.class));

            }
        });


        // Initializing the ViewPagerAdapter

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}