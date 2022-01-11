package com.example.daisy_mobile.ui.setting;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.daisy_mobile.editprofile;
import com.example.daisy_mobile.MainActivity;
import com.example.daisy_mobile.MainActivity2;
import com.example.daisy_mobile.R;
import com.example.daisy_mobile.databinding.FragmentSettingsBinding;
import com.example.daisy_mobile.databinding.FragmentSettingsBinding;
//import com.example.daisy_mobile.models.User;
import com.example.daisy_mobile.p02_signin;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import dataclass.user;
import java.util.Objects;

public class SettingFragment extends Fragment {

    private SettingViewModel settingViewModel;
    private FragmentSettingsBinding binding;
    private Button btnEdit;
    private TextView tvName, tvPhoneNumber, tvAddress, tvEmail;
    private ImageView img_Avatar;
    private FirebaseFirestore db;
    private SharedPreferences pref;
    private String userId;
    private user user;
    private Context context;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        settingViewModel = new ViewModelProvider(this).get(SettingViewModel.class);
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        btnEdit = root.findViewById(R.id.btnEdit);

        initialView(root);
        initialData(root);
        initialEvent();

        return root;
    }

    private void initialData(View view) {
        pref = view.getContext().getSharedPreferences("myPref", 0);
        db = FirebaseFirestore.getInstance();
        userId = pref.getString("USER_ID", "");
        DocumentReference docIdRef = db.collection("users").document(userId);
        docIdRef.get().addOnCompleteListener(task1 -> {
            if (task1.isSuccessful()) {
                DocumentSnapshot document = task1.getResult();
                if (document.exists()) {
                    user = new user(
                            task1.getResult().getId(),
                            Objects.requireNonNull(task1.getResult().get("name")).toString(),
                            Objects.requireNonNull(task1.getResult().get("phonenumber")).toString(),
                            Objects.requireNonNull(task1.getResult().get("email")).toString(),
                            Objects.requireNonNull(task1.getResult().get("address")).toString(),
                            Objects.requireNonNull(task1.getResult().get("imageID")).toString());


                    tvName.setText(user.getName());
                    tvPhoneNumber.setText(user.getPhonenumber());
                    tvAddress.setText(user.getAddress());
                    tvEmail.setText(user.getEmail());
//                    String userimageid=user.getImageID();
//                    StorageReference storageRef = FirebaseStorage.getInstance().getReference();
//                    storageRef.child("images/"+userimageid).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                        @Override
//                        public void onSuccess(Uri uri) {
//                            // Got the download URL for 'users/me/profile.png'
//                            Glide.with(context /* context */)
//                                    .load(uri)
//                                    .into(img_Avatar);
//
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception exception) {
//                            // Handle any errors
//                            Toast.makeText(context,"Failed ",Toast.LENGTH_LONG);
//                        }
//                    });


                }
            }
        });

    }

    private void initialEvent() {
        btnEdit.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), editprofile.class);
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