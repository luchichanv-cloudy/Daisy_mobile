package com.example.daisy_mobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import dataclass.kitchen;
import dataclass.user;

public class p03_signup extends AppCompatActivity {

    private String name, address, email, phonenumber;
    private FirebaseFirestore db;
    private Uri filePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p03_signup);

        FirebaseAuth auth = FirebaseAuth.getInstance();
         db = FirebaseFirestore.getInstance();


            ProgressBar progressBar = (ProgressBar) findViewById(R.id.p03_progressBar);
            Button btnSignUp = (Button) findViewById(R.id.p03_btn_signup);
            EditText inputEmail = (EditText) findViewById(R.id.p03_et_email);
            EditText inputPassword = (EditText) findViewById(R.id.p03_et_password);
            EditText inputName = (EditText) findViewById(R.id.p03_et_name);
            EditText inputAddress = (EditText) findViewById(R.id.p03_et_address);
            EditText inputPhonenumber = (EditText) findViewById(R.id.p03_et_phonenumber);
            RadioGroup inputtype =(RadioGroup) findViewById(R.id.radio_group);
            RadioButton userbutton = (RadioButton)findViewById(R.id.radio_a);
            RadioButton kitchenbutton = (RadioButton)findViewById(R.id.radio_b);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String email = inputEmail.getText().toString().trim();
                    String password = inputPassword.getText().toString().trim();

                    if (TextUtils.isEmpty(email)) {
                        Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (TextUtils.isEmpty(password)) {
                        Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (password.length() < 6) {
                        Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    progressBar.setVisibility(View.VISIBLE);
                    //create user
                    auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(p03_signup.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Toast.makeText(p03_signup.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(p03_signup.this, "Authentication failed." + task.getException(),
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        name=inputName.getText().toString();
                                        address=inputAddress.getText().toString();
                                        phonenumber=inputPhonenumber.getText().toString();

                                        if(userbutton.isChecked())
                                        {
                                            user User = new user(name,address,email,phonenumber);
                                            //lay id cua user authentication
                                            String Id=FirebaseAuth.getInstance().getCurrentUser().getUid();
                                            //luu data vao firestore database voi custom id
                                            db.collection("users").document(Id).set(User);
                                        }
                                        else{
                                            kitchen User = new kitchen(name,address,email,phonenumber);
                                            //lay id cua user authentication
                                            String Id=FirebaseAuth.getInstance().getCurrentUser().getUid();
                                            //luu data vao firestore database voi custom id
                                            db.collection("kitchens").document(Id).set(User);
                                        };




                                       // addDataToFirestore(inputName.toString(),inputAddress.toString(),inputEmail.toString(),inputPhonenumber.toString());
                                        startActivity(new Intent(p03_signup.this, p02_signin.class));
                                        finish();
                                    }
                                }
                            });

                }
            });
        }


    }


