package com.example.daisy_mobile;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.daisy_mobile.adapter.FavouriteAdapter;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import dataclass.kitchen;
import dataclass.kitchen_favourite;

public class p16_favorite extends AppCompatActivity {
    List<kitchen> kitchens = new ArrayList<>();
    RecyclerView favourite_rcv;
    FavouriteAdapter adapter;
    FirebaseFirestore db;
    SharedPreferences pref;
    String userId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favourite_fragment);
        pref = getApplicationContext().getSharedPreferences("myPref", 0);
        userId = pref.getString("USER_ID", "");
        Log.d("USER_ID", "onCreate: " + userId);
        initialView();
        initialDataFirebaseStore();


    }

    private void initialView() {
        db = FirebaseFirestore.getInstance();
        favourite_rcv = findViewById(R.id.favourite_rcv);
    }

    private void initialDataFirebaseStore() {
        CollectionReference collection = db.collection("favourite_kitchen");
        Query findFavorite = collection.whereEqualTo("user_id", userId);
        findFavorite.get().addOnCompleteListener(task -> {
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
                                for (QueryDocumentSnapshot document : Objects.requireNonNull(taskKitchen.getResult())) {
                                    kitchens.add(new kitchen(Objects.requireNonNull(document.get("kitchenId")).toString(),
                                            Objects.requireNonNull(document.get("name")).toString(),
                                            Objects.requireNonNull(document.get("address")).toString(),
                                            Objects.requireNonNull(document.get("email")).toString(),
                                            Objects.requireNonNull(document.get("phonenumber")).toString()
//                                            Objects.requireNonNull(document.get("imageID")).toString()
//                                            Integer.parseInt(Objects.requireNonNull(document.get("vote")).toString()))
                                    ));
                                }

                                if (kitchens.size() > 0) {
                                    adapter = new FavouriteAdapter(kitchens, p16_favorite.this);
                                    favourite_rcv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                    favourite_rcv.setAdapter(adapter);
                                }
                            }

                        });

                    }
                }


            }
        });
    }
}

