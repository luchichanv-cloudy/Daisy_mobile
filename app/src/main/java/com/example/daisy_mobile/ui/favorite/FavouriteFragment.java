package com.example.daisy_mobile.ui.favorite;

import static com.example.daisy_mobile.p02_signin.user_id;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.daisy_mobile.R;
import com.example.daisy_mobile.adapter.FavouriteAdapter;
import com.example.daisy_mobile.databinding.FavouriteFragmentBinding;
import com.example.daisy_mobile.databinding.FragmentHomeBinding;
import com.example.daisy_mobile.databinding.FragmentOrderBinding;
import com.example.daisy_mobile.p02_signin;
import com.example.daisy_mobile.p16_favorite;
import com.example.daisy_mobile.ui.home.HomeViewModel;
import com.example.daisy_mobile.ui.order.OrderViewModel;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import dataclass.kitchen;
import dataclass.kitchen_favourite;

public class FavouriteFragment extends Fragment {
    private List<kitchen> kitchens = new ArrayList<>();
    private  RecyclerView favourite_rcv;
    private FavouriteAdapter adapter;
    private FirebaseFirestore db;
    private FavouriteViewModel mViewModel;
    private FavouriteFragmentBinding binding;


    private void initialDataFirebaseStore() {
        CollectionReference collection = db.collection("favourite_kitchen");
        String userId= p02_signin.user_id;
        Query findFavorite = collection.whereEqualTo("user_id", userId);
        db.collection("favourite_kitchen")
                .whereEqualTo("user_id",user_id)
                .get()
        .addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<kitchen_favourite> favourites = new ArrayList<>();
                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                    favourites.add(new kitchen_favourite(Objects.requireNonNull(document.get("kitchen_id")).toString(), Objects.requireNonNull(document.get("user_id")).toString()));
                }

                if (favourites.size() > 0) {
                    for (kitchen_favourite favourite : favourites) {
                        CollectionReference kitchenCollection = db.collection("kitchens");
                        Query findKitchen = kitchenCollection.whereEqualTo("kitchenId", favourite.getKitchenid());

                        findKitchen.get().addOnCompleteListener(taskKitchen -> {
                            if (taskKitchen.isSuccessful()) {
                                for (QueryDocumentSnapshot documentk : Objects.requireNonNull(taskKitchen.getResult())) {
                                    kitchens.add(new kitchen(Objects.requireNonNull(documentk.get("kitchenId")).toString(),
                                            Objects.requireNonNull(documentk.get("name")).toString(),
                                          //  documentk.get("imageID").toString(),
                                            Objects.requireNonNull(documentk.get("address")).toString(),
                                            Objects.requireNonNull(documentk.get("email")).toString(),
                                            Objects.requireNonNull(documentk.get("phonenumber")).toString(),documentk.get("imageID").toString(),0
                                    ));
                                }

                                if (kitchens.size() > 0) {
                                    adapter = new FavouriteAdapter(kitchens, getContext());
                                    favourite_rcv.setLayoutManager(new LinearLayoutManager(getContext()));
                                    favourite_rcv.setAdapter(adapter);
                                }
                            }

                        });

                    }
                }


            }
        });
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel =
                new ViewModelProvider(this).get(FavouriteViewModel.class);

        binding = FavouriteFragmentBinding.inflate(inflater, container, false);

        View root = binding.getRoot();
        db = FirebaseFirestore.getInstance();
        favourite_rcv = (root).findViewById(R.id.favourite_rcv);

        initialDataFirebaseStore();
        return root;

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }
}