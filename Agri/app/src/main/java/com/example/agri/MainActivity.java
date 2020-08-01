package com.example.agri;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.agri.pojos.Crops;
import com.example.agri.pojos.CropsFB;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button addCrop;
    Context context;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    CropsFB data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        context = this;

        DocumentReference docRef = db.collection("Agri").document("Crops");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        data = document.toObject(CropsFB.class);
                    } else {
                        //Log.d(TAG, "No such document");\
                    }
                } else {
                    //Log.d(TAG, "get failed with ", task.getException());
                    Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
        if (data == null) {
            data = new CropsFB();
            data.setCropsList(new ArrayList<Crops>());
        }
        //TODO:Display Crops data on rv if crop's sellerId == mAuth.getUid()

        addCrop = findViewById(R.id.add_btn);
        addCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog mDialog = new Dialog(context);
                mDialog.setContentView(R.layout.add_crop_popup);
                mDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                //TODO:Make edit texts and all
                Button addBtn = mDialog.findViewById(R.id.add_btn);
                addBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Crops crop = new Crops();
                        //TODO:add taken data here!!
                        crop.setCropName("");
                        crop.setExpectedDate(null);
                        crop.setTotalQuantity(0);
                        crop.setRemainingQuantity(0);
                        crop.setOrganic(false);
                        crop.setPrice(0);

                        crop.setSellerId(mAuth.getUid());
                        crop.setCropId(mAuth.getUid() + crop.getCropName());
                        data.getCropsList().add(crop);

                        CollectionReference dbUsers = db.collection("Agri");
                        dbUsers.document("Crops").set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                //TODO:refresh recycler view
                                Toast.makeText(MainActivity.this, "Crop Added Successfully!", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Failed To Upload!, Try Again!" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });
    }
}
