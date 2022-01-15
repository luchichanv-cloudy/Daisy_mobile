package com.example.daisy_mobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.daisy_mobile.ui.kitchensetting.Kitchensetting;
import com.example.daisy_mobile.ui.setting.SettingFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import dataclass.kitchen;
import dataclass.user;

public class editkichenprofile extends AppCompatActivity {
    private EditText edname, edaddress, edemail, edphonenumber;
    private kitchen kitchen;
    private Button btnSave;
    private FirebaseFirestore db;
    private ImageView iv_avtar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editkichenprofile);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        initialView();
        initialData();
        initialEvent();
    }
    private void initialEvent() {
        btnSave.setOnClickListener(view -> {
            String name = edname.getText().toString().trim();
            String user_id= FirebaseAuth.getInstance().getUid();
            String phoneNumber = edphonenumber.getText().toString().trim();
            String address = edaddress.getText().toString().trim();
            if (!name.isEmpty() && !phoneNumber.isEmpty() && !address.isEmpty()) {
                Map<String, Object> inputuser = new HashMap<>();
                inputuser.put("name", name);
                inputuser.put("phonenumber", phoneNumber);

                //inputuser.put("email", edemail.getText());
                inputuser.put("address", address);
                db.collection("kitchens")
                        .document(user_id)
                        .update(inputuser).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Update profile is successful!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, Kitchensetting.class));
                    }
                });
            }
        });
    }

    private void initialData() {
        Intent intent = getIntent();
        String user_id=FirebaseAuth.getInstance().getUid();

        String userId= FirebaseAuth.getInstance().getUid();
        db.collection("kitchens").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful())
                {
                    DocumentSnapshot document=task.getResult();
                    if(document.exists())
                    {
                        kitchen=document.toObject(dataclass.kitchen.class);
                        edname.setText(kitchen.getName());
                        edphonenumber.setText(kitchen.getPhonenumber());
                        edaddress.setText(kitchen.getAddress());
                        edemail.setText(kitchen.getEmail());
//                   // load hinh anh
                        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                        storageRef.child("images/"+kitchen.getImageID()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                // Got the download URL for 'users/me/profile.png'
                                Glide.with(editkichenprofile.this /* context */)
                                        .load(uri)
                                        .into(iv_avtar);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                            }
                        });
                    }
                }
            }
        });

    }

    private void initialView() {
        db = FirebaseFirestore.getInstance();
        edname = findViewById(R.id.edname);
        edaddress = findViewById(R.id.edaddress);
        edphonenumber = findViewById(R.id.edphone);
        edemail = findViewById(R.id.edemail);
        btnSave = findViewById(R.id.button2);
        iv_avtar=findViewById(R.id.iv_avatar);

        edemail.setFocusable(false);
    }
}