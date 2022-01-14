package com.example.daisy_mobile.ui.favorite;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.daisy_mobile.R;
import com.example.daisy_mobile.adapter.FavorAdapter;
import com.example.daisy_mobile.databinding.FavouriteFragmentBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import dataclass.kitchen;
import dataclass.kitchen_favourite;

public class FavouriteFragment extends Fragment {

    private ArrayList<kitchen> kitchens;
    private ArrayList<kitchen_favourite> favourites;
    private FavorAdapter adapter;
    private ListView lv;
    private FirebaseFirestore db;
    private FavouriteViewModel mViewModel;
    private FavouriteFragmentBinding binding;


    private void initialDataFirebaseStore() {
        String user_id = FirebaseAuth.getInstance().getUid();
        kitchens=new ArrayList<kitchen>();  favourites=new ArrayList<kitchen_favourite>();

        db=FirebaseFirestore.getInstance();
        db.collection("favourite_kitchen")
                .whereEqualTo("userid",user_id)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful())
                {

                    for (QueryDocumentSnapshot document:task.getResult())
                    {
                        if (document.exists())
                        {
                            kitchen_favourite a=document.toObject(kitchen_favourite.class);
                            favourites.add(a);
                            db.collection("kitchens").document(a.getKitchenid())
                                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task1) {
                                    if (task1.isSuccessful())
                                    {
                                        DocumentSnapshot doc=task1.getResult();
                                        kitchen b=doc.toObject(kitchen.class);
                                        kitchens.add(b);
                                        adapter = new FavorAdapter(getContext(),kitchens);
                                        lv.setAdapter(adapter); adapter.notifyDataSetChanged();
                                    }
                                }
                            });

                        }
                    }
                }
            }
        });


    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.favourite_fragment, container, false);
        db = FirebaseFirestore.getInstance();
        lv=(ListView)root.findViewById(R.id.favourite_lv);
        initialDataFirebaseStore();
        return root;

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }
}