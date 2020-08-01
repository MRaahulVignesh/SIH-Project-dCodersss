package com.example.agri;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.agri.pojos.Farmers;
import com.example.agri.pojos.FarmersFB;
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

public class RegisterActivity extends AppCompatActivity {
    Button contBtn;
    Farmers farmer;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    FarmersFB data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        DocumentReference docRef = db.collection("Agri").document("Farmers");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        data = document.toObject(FarmersFB.class);
                    } else {
                        //Log.d(TAG, "No such document");\
                    }
                } else {
                    //Log.d(TAG, "get failed with ", task.getException());
                    Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        farmer = new Farmers();
        farmer.setMobNo(getIntent().getStringExtra("MobNo"));
        farmer.setId(mAuth.getUid());

        if (data == null) {
            data = new FarmersFB();
            data.setFarmersList(new ArrayList<Farmers>());
        }

        contBtn = findViewById(R.id.ctnBtn);
        contBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO:Take inputs and verify if correct then go to main page

                //after verification and fixing created Farmer class object
                data.getFarmersList().add(farmer);

                CollectionReference dbUsers = db.collection("Agri");
                dbUsers.document("Farmers").set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //here is where we go into the app
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Sign Up Failed, Try Again " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
