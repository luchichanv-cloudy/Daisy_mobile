package com.example.daisy_mobile;

import static com.example.daisy_mobile.p02_signin.user_id;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.daisy_mobile.adapter.FoodItemAdapter;
import com.example.daisy_mobile.adapter.ShopItemAdapter;
import com.example.daisy_mobile.adapter.TopkitchenViewpagerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.UUID;

import dataclass.item;
import dataclass.kitchen;
import dataclass.kitchen_favourite;

public class p06_shopmenu extends AppCompatActivity {
    public static String display_kitchenid;
    private TextView tv_kitchenname1, tv_kitchenname2;
    private ImageButton ib_favorite, ib_back;
    private ListView lv_fooditem;
    private ImageView iv_kitchenimage;
    private ArrayList<item> arrayOfitem;
    private FirebaseFirestore db;
    private boolean favoritestatus;
    private void init()
    {
     //   display_kitchenid="3vWBk5eX7lcjexVK0zVXLYPtFFq1";
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user_id=user.getUid();
        iv_kitchenimage=(ImageView)findViewById(R.id.p06_iv_kitchenimage);
        tv_kitchenname1=(TextView)findViewById(R.id.p06_tv_kitchenname);
        tv_kitchenname2=(TextView)findViewById(R.id.p06_tv_kitchenname2);
        lv_fooditem=(ListView)findViewById(R.id.p06_lv_food);
        ib_back=(ImageButton) findViewById(R.id.p06_ib_back);
        ib_favorite=(ImageButton) findViewById(R.id.p06_ib_favorite);
    }
    private void getimage(String url, ImageView displayview)
    {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        storageRef.child("images/"+url).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                Glide.with(p06_shopmenu.this /* context */)
                        .load(uri)
                        .into(displayview);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        });
    }
    private void checkfavorite(String kitchen_id)
    {
        db.collection("favourite_kitchen")
                .whereEqualTo("kitchenid",kitchen_id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            QuerySnapshot document = task.getResult();
                            if (document.isEmpty())
                            {
                                ib_favorite.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                            }
                            else
                            {
                                ib_favorite.setImageResource(R.drawable.ic_baseline_favorite_24);
                            }
                        }
                    }
                    });

    }
    private void initfirebase()
    {
        db = FirebaseFirestore.getInstance();
        //lay kitchen
        DocumentReference documentRef = db.document("kitchens/"+display_kitchenid);
       documentRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
           @Override
           public void onComplete(@NonNull Task<DocumentSnapshot> task) {
               if (task.isSuccessful()) {
                   DocumentSnapshot document = task.getResult();
                   if (document.exists()) {
                       tv_kitchenname1.setText(document.get("name").toString());
                       tv_kitchenname2.setText(document.get("name").toString());
                       getimage(document.get("imageID").toString(),iv_kitchenimage);
                       checkfavorite(display_kitchenid);
                   } else {
                   }
               } else {

               }
           }
       });

       //layitem
        db.collection("item")
                .whereEqualTo("kitchenID",display_kitchenid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        //    item ia=new item("denden","ae422c35-f366-40a7-bbee-67a82979d7b2","hello","drink","3vWBk5eX7lcjexVK0zVXLYPtFFq1",50,30,false);
                        arrayOfitem = new ArrayList<item>();
                        ShopItemAdapter adapter= new ShopItemAdapter(p06_shopmenu.this,arrayOfitem);
                        lv_fooditem.setAdapter(adapter);
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
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p06_shopmenu);
        init();
        initfirebase();
        favoritestatus=false;
        ib_favorite.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View view) {
                                               if (favoritestatus==false)
                                               {
                                                   String Id= UUID.randomUUID().toString();
                                                   kitchen_favourite abc= new kitchen_favourite(display_kitchenid,user_id);
                                                   db.collection("favourite_kitchen").document(Id).set(abc);

                                                   ib_favorite.setImageResource(R.drawable.ic_baseline_favorite_24);
                                                   favoritestatus=true;
                                               }

                                           }
                                       }
        );
    }
}