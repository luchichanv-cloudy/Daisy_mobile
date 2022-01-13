package com.example.daisy_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.daisy_mobile.ui.setting.SettingFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import dataclass.user;

public class editprofile extends AppCompatActivity {

    private EditText edname, edaddress, edemail, edphonenumber;
    private user user;
    private Button btnSave;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        initialData();
        initialView();
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
                db.collection("users")
                        .document(user_id)
                        .update(inputuser).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Update profile is successful!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, SettingFragment.class));
                    }
                });
            }
        });
    }

    private void initialData() {
        Intent intent = getIntent();

        String name = intent.getStringExtra("NAME");
        String phonenumber = intent.getStringExtra("PHONE_NUMBER");
        String email = intent.getStringExtra("EMAIL");
        String address = intent.getStringExtra("ADDRESS");
        String imageid = intent.getStringExtra("IMAGE_ID");
       // String userid = intent.getStringExtra("USER_ID");

        user = new user( name, phonenumber, email, address, imageid);
    }

    private void initialView() {
        db = FirebaseFirestore.getInstance();
        edname = findViewById(R.id.edname);
        edaddress = findViewById(R.id.edaddress);
        edphonenumber = findViewById(R.id.edphone);
        edemail = findViewById(R.id.edemail);
        btnSave = findViewById(R.id.button2);

        edname.setText(user.getName());
        edaddress.setText(user.getAddress());
        edphonenumber.setText(user.getPhonenumber());
        edemail.setText(user.getEmail());
        edemail.setFocusable(false);
    }
}
