package com.example.daisy_mobile;

import static com.example.daisy_mobile.p02_signin.user_id;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.daisy_mobile.adapter.FoodItemAdapter;
import com.example.daisy_mobile.adapter.ShopItemAdapter;
import com.example.daisy_mobile.adapter.TopkitchenViewpagerAdapter;
import com.example.daisy_mobile.ui.order.OrderFragment;
import com.example.daisy_mobile.ui.setting.SettingFragment;
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

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import dataclass.item;
import dataclass.kitchen;
import dataclass.kitchen_favourite;
import dataclass.order;
import dataclass.order_item;

public class p06_shopmenu extends AppCompatActivity {
    public static String display_kitchenid;
    private TextView tv_kitchenname1, tv_kitchenname2;
    private ImageButton ib_favorite, ib_back;
    private Button btn_done;

    private Activity myActivity;
    private ArrayList<Integer> quantity;
    private ShopItemAdapter adapter;
    private ListView lv_fooditem;

    private ImageView iv_kitchenimage;
    private ArrayList<item> arrayOfitem;
    private FirebaseFirestore db;
    private boolean favoritestatus;
    private void init()
    {
     //   display_kitchenid="3vWBk5eX7lcjexVK0zVXLYPtFFq1";
      //  display_kitchenid=p05_searchresult.display_id;

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user_id=user.getUid();
        btn_done=(Button)findViewById(R.id.btndone);
        iv_kitchenimage=(ImageView)findViewById(R.id.p06_iv_kitchenimage);
     //   tv_kitchenname1=(TextView)findViewById(R.id.p06_tv_kitchenname);
        tv_kitchenname2=(TextView)findViewById(R.id.p06_tv_kitchenname2);
        lv_fooditem=(ListView)findViewById(R.id.p06_lv_food);
     //   ib_back=(ImageButton) findViewById(R.id.p06_ib_back);
        ib_favorite=(ImageButton) findViewById(R.id.p06_ib_favorite);
        ib_favorite.setImageResource(R.drawable.ic_baseline_favorite_border_24);

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
                .whereEqualTo("userid",user_id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            QuerySnapshot document = task.getResult();
                            if (document.isEmpty())
                            {
                                ib_favorite.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                                favoritestatus=false;
                            }
                            else
                            {
                                ib_favorite.setImageResource(R.drawable.ic_baseline_favorite_24);
                                favoritestatus=true;
                            }
                        }
                    }
                    });

    }
    private void orderdetail()
    {

        Integer totalprice=0;
        ArrayList<order_item> list = new ArrayList<order_item>();
        for(int i=0;i< arrayOfitem.size();i++)
        {
            int quantity=adapter.getQuantity().get(i);
            String itemname,itemid;
            int itemprice;
            if (quantity>0)
            {
                itemname=arrayOfitem.get(i).getName();
                itemid=arrayOfitem.get(i).getImageID();
                itemprice=arrayOfitem.get(i).getPrice();
                if (arrayOfitem.get(i).isSalestatus()) {
                    itemprice=arrayOfitem.get(i).getPricesale();
                }
                totalprice=totalprice+itemprice*quantity;
                order_item aaa=new order_item(itemname,itemid,quantity,itemprice);
                list.add(aaa);
            }
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        String timeStamp = sdf.format(new Date());
        order A = new order(display_kitchenid,user_id,timeStamp,0,totalprice,list);
        String message="Your order total price is "+totalprice.toString()+"$ . Please confirm your order";

        //show alert box
            AlertDialog.Builder builder
                    = new AlertDialog
                    .Builder(p06_shopmenu.this);
            builder.setMessage(message);
            builder.setTitle("Alert !");
            builder.setCancelable(false);
            builder
                    .setPositiveButton(
                            "Confirm",
                            new DialogInterface
                                    .OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which)
                                {

                                    db.collection("order").document().set(A);
                                    myActivity.startActivity(new Intent(myActivity,MainActivity.class));

                                }
                            });
            builder
                    .setNegativeButton(
                            "Cancel",
                            new DialogInterface
                                    .OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which)
                                {

                                    dialog.cancel();
                                }
                            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        //show alert box




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
                        quantity = new ArrayList<Integer>();
                         adapter= new ShopItemAdapter(p06_shopmenu.this,arrayOfitem,quantity);
                        lv_fooditem.setAdapter(adapter);
                        //   arrayOfitem.add(ia);
                        if (task.isSuccessful()) {
                            int i=0;
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                item abc=document.toObject(item.class);
                                arrayOfitem.add(abc);
                                quantity.add(0);
                                adapter.notifyDataSetChanged();
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
        favoritestatus=false;
        init();
        initfirebase();
        myActivity=this;

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

      btn_done.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              orderdetail();
          }
      });
    }
}