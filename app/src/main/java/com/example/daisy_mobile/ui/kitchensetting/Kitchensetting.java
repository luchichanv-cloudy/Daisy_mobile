package com.example.daisy_mobile.ui.kitchensetting;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.daisy_mobile.R;
import com.example.daisy_mobile.databinding.FragmentSettingsBinding;
import com.example.daisy_mobile.editkichenprofile;
import com.example.daisy_mobile.ui.setting.SettingViewModel;
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

import java.util.Objects;

import dataclass.kitchen;


public class Kitchensetting extends Fragment {

    private SettingViewModel settingViewModel;
    private FragmentSettingsBinding binding;
    private Button btnEdit;
    private TextView tvName, tvPhoneNumber, tvAddress, tvEmail;
    private ImageView img_Avatar;
    private FirebaseFirestore db;
    private SharedPreferences pref;
    private String userId;
    private dataclass.user user; private dataclass.kitchen kitchen;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_kitchensetting, container, false);
        btnEdit = root.findViewById(R.id.btnEdit);
        img_Avatar=root.findViewById(R.id.img_Avatar);
        initialView(root);
        initialData(root);
        initialEvent();
        return root;
    }
    private void initialData(View view) {
        pref = view.getContext().getSharedPreferences("myPref", 0);
        db = FirebaseFirestore.getInstance();
        userId= FirebaseAuth.getInstance().getUid();
       db.collection("kitchens").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
           @Override
           public void onComplete(@NonNull Task<DocumentSnapshot> task) {
               if (task.isSuccessful())
               {
                   DocumentSnapshot document=task.getResult();
                   if(document.exists())
                   {
                      kitchen=document.toObject(dataclass.kitchen.class);
                       tvName.setText(kitchen.getName());
                       tvPhoneNumber.setText(kitchen.getPhonenumber());
                       tvAddress.setText(kitchen.getAddress());
                       tvEmail.setText(kitchen.getEmail());
//                   // load hinh anh
                       StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                       storageRef.child("images/"+kitchen.getImageID()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                           @Override
                           public void onSuccess(Uri uri) {
                               // Got the download URL for 'users/me/profile.png'
                               Glide.with(getContext() /* context */)
                                       .load(uri)
                                       .into(img_Avatar);
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

    private void initialEvent() {
        btnEdit.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), editkichenprofile.class);
            startActivity(intent);
        });
    }

    private void initialView(View view) {
        btnEdit = view.findViewById(R.id.btnEdit);
        tvName = view.findViewById(R.id.tvName);
        tvAddress = view.findViewById(R.id.tvAddress);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvPhoneNumber = view.findViewById(R.id.tvPhoneNumber);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}