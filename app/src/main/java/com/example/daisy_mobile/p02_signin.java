package com.example.daisy_mobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.AsyncListUtil;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class p02_signin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p02_signin);

        FirebaseFirestore db = FirebaseFirestore.getInstance();





        TextView next = (TextView) findViewById(R.id.et_p02_register);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                next.setTextColor(Color.parseColor("#FFFFFF"));
                startActivity(new Intent(p02_signin.this, p03_signup.class));

            }

        });

        Button signin = (Button) findViewById(R.id.p02_bt_signin);
        signin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {


            }

        });

    }
}