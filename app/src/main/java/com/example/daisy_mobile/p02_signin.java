package com.example.daisy_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class p02_signin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p02_signin);

                TextView next = (TextView) findViewById(R.id.et_p02_register);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                next.setTextColor(Color.parseColor("#FFFFFF"));
                startActivity(new Intent(p02_signin.this, p03_signup.class));

            }

        });

        Button signin = (Button) findViewById(R.id.bt_p02_signin);
        signin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                startActivity(new Intent(p02_signin.this, MainActivity.class));

            }

        });

    }
}