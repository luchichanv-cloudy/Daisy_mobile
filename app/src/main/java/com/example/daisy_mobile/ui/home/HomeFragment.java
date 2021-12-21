package com.example.daisy_mobile.ui.home;
import static com.example.daisy_mobile.p02_signin.user_id;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.daisy_mobile.R;
import com.example.daisy_mobile.adapter.TopkitchenViewpagerAdapter;
import com.example.daisy_mobile.databinding.FragmentHomeBinding;
import com.example.daisy_mobile.p03_signup;
import com.example.daisy_mobile.p05_searchresult;
import com.example.daisy_mobile.uploadava;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import dataclass.Image;
import dataclass.kitchen;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private FirebaseFirestore db;
    private ImageView imageView;
    private ViewPager mViewPager;
    private DatabaseReference mDatabaseRef;
    //private int[] images;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final TextView textView = binding.hfTvReccomend;
        mViewPager= (ViewPager)root.findViewById(R.id.hf_vp_topfavorite);

        //image slider
       db = FirebaseFirestore.getInstance();

        String ID[]=new String[3];

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
                                ID[i]=abc.getImageID();
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
        ImageButton btn = (ImageButton) root.findViewById(R.id.hf_ib_search);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

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