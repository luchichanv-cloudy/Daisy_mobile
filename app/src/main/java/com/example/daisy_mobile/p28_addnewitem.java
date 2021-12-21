package com.example.daisy_mobile;

import static com.example.daisy_mobile.p02_signin.user_id;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import dataclass.item;
import dataclass.user;

public class p28_addnewitem extends AppCompatActivity {
    private final int PICK_IMAGE_REQUEST = 71;
    private Button btn_upload, btn_save;
    private EditText et_name, et_price, et_pricesale, et_description;
    private Spinner sp_category;
    private ImageView img;
    private List<String> list;
    private Uri filePath;
    private  String itemid,imageid;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private FirebaseFirestore db;
    private void init()
    {

        btn_upload=findViewById(R.id.p28_btn_addimage);
        btn_save=findViewById(R.id.p28_btn_save);
        et_name=findViewById(R.id.p28_et_name);
        et_price=findViewById(R.id.p28_et_price);
        et_description=findViewById(R.id.p28_et_description);
        et_pricesale=findViewById(R.id.p28_et_priceaftersale);
        sp_category=findViewById(R.id.p28_sp_category);
        img=findViewById(R.id.p28_iv_image);
        list = new ArrayList<>();
        list.add("Appetizers");
        list.add("Main Dish");
        list.add("Dessert");
        list.add("Drinks");
        ArrayAdapter spinnerAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, list);

        sp_category.setAdapter(spinnerAdapter);
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                img.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }


    private void uploadImage() {

        if(filePath != null)
        {

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/"+ imageid);
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            progressDialog.dismiss();
                            Toast.makeText(p28_addnewitem.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(p28_addnewitem.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }

    private boolean notnull(){
        if ((et_description.getText().toString().equals("")) || (et_name.getText().toString().equals(""))
        || (et_price.getText().toString().equals("")) || (et_pricesale.getText().toString().equals(""))
                 || (et_name.getText().toString().equals(""))
        ){
            Toast.makeText(this,"Please fill all the required fields",Toast.LENGTH_SHORT);
            return false;
        }
        else
        {
            return true;
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p28_addnewitem);
        init();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (notnull())
                {

                    String name=et_name.getText().toString();
                    imageid= UUID.randomUUID().toString();
                    String description=et_description.getText().toString();
                    String category=sp_category.getSelectedItem().toString();
                    int price = Integer.parseInt(et_price.getText().toString());
                    int pricesale = Integer.parseInt(et_pricesale.getText().toString());
                    item Item = new item(name,imageid,description,category, user_id, price,pricesale,false);
                    db.collection("item").document(imageid).set(Item);
                    uploadImage();
                    startActivity(new Intent(p28_addnewitem.this, MainActivity2.class));
                    //finish();

                }






            }
        });

    }
}