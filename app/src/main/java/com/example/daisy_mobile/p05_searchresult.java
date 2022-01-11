package com.example.daisy_mobile;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;

import android.os.Bundle;
import android.view.View;

import android.widget.AdapterView;

import android.widget.EditText;
import android.widget.ImageButton;

import android.widget.ListView;

import com.example.daisy_mobile.adapter.FoodItemAdapter;

import com.example.daisy_mobile.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import java.util.Locale;

import dataclass.item;

public class p05_searchresult extends AppCompatActivity {
    private FirebaseFirestore db;
    private String searchtext;
    private EditText et_search;
    private ImageButton btn_search;
    public static String display_id;
    private ListView lv;
    ArrayList<item> items= new ArrayList<item>();
  //  ArrayAdapter<item> arrayAdapter;
   private FoodItemAdapter arrayAdapter;
    private void firebasesearchitem()
    {
      //  searchtext.toLowerCase(Locale.ROOT);
        db = FirebaseFirestore.getInstance();
      //  DatabaseReference scoresRef = db.
        db.collection("item")
                .orderBy("name").startAt(searchtext.toLowerCase(Locale.ROOT)).endAt(searchtext.toLowerCase(Locale.ROOT)+"\uf8ff")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int i=0;
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                item abc=document.toObject(item.class);
                                items.add(abc);
                                arrayAdapter.notifyDataSetChanged();
                            }

                        } else {

                    }
                    }
                });


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p05_searchresult);
        searchtext= HomeFragment.searchtext;

        firebasesearchitem();
        lv=(ListView)findViewById(R.id.lv);
        et_search=(EditText)findViewById(R.id.hf_et_search);
        btn_search=(ImageButton)findViewById(R.id.hf_ib_search);
        arrayAdapter=new FoodItemAdapter(this,items);
        lv.setAdapter(arrayAdapter);
        et_search.setText(searchtext);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Intent i=new Intent(p05_searchresult.this,p05_searchresult.class);
                HomeFragment.searchtext=et_search.getText().toString();
                startActivity(new Intent(p05_searchresult.this, p05_searchresult.class));
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                display_id=items.get(i).getKitchenID();
                p06_shopmenu.display_kitchenid=display_id;
                startActivity(new Intent(p05_searchresult.this,p06_shopmenu.class));
            }
        });
    }
}