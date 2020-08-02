package com.example.agri;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agri.adapters.MyCropListRVAdapter;
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
import com.google.type.Date;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button addCropBtn;
    Context context;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    public static CropsFB data;
    private RecyclerView cropRV;
    public static List<Crops> myCropList = new ArrayList<>();
    public static MyCropListRVAdapter myCropListRVAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        context = this;

        cropRV = findViewById(R.id.crops_rv);
        addCropBtn = findViewById(R.id.add_btn);

        myCropListRVAdapter = new MyCropListRVAdapter(context, myCropList);
        cropRV.setLayoutManager(new LinearLayoutManager(this));
        cropRV.setAdapter(myCropListRVAdapter);
        myCropListRVAdapter.notifyDataSetChanged();

        DocumentReference docRef = db.collection("Agri").document("Crops");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        data = document.toObject(CropsFB.class);
                        if (data == null) {
                            data = new CropsFB();
                            data.setCropsList(new ArrayList<Crops>());
                        }
                        //adding my crops to myCropsList
                        myCropList.clear();
                        for (Crops c : data.getCropsList()) {
                            if (c.getSellerId().equals(mAuth.getUid())) {
                                myCropList.add(c);
                            }
                        }
                        myCropListRVAdapter.notifyDataSetChanged();

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


        addCropBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog mDialog = new Dialog(context);
                mDialog.setContentView(R.layout.add_crop_popup);
                mDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                final EditText addCropDialogueCropNameET = mDialog.findViewById(R.id.cropname);
                final EditText addCropDialogueTotalQuantityET = mDialog.findViewById(R.id.totalQuantity);
                final EditText addCropDialogueDateET = mDialog.findViewById(R.id.expectedDate);
                final EditText unitPriceET = mDialog.findViewById(R.id.unitPrice);
                Button addBtn = mDialog.findViewById(R.id.add_btn);
                addBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String cropName = addCropDialogueCropNameET.getText().toString();
                        String totalQuantity = addCropDialogueTotalQuantityET.getText().toString();
                        String expectedDate = addCropDialogueDateET.getText().toString();
                        String unitPrice = unitPriceET.getText().toString();

                        if (cropName.length() == 0 || cropName.trim().length() == 0) {
                            Toast.makeText(context, "Please enter your crop's name", Toast.LENGTH_SHORT).show();
                        } else if (totalQuantity.length() == 0 || totalQuantity.trim().length() == 0) {
                            Toast.makeText(context, "Please enter total quantity of your crop", Toast.LENGTH_SHORT).show();
                        } else if (expectedDate.length() == 0 || expectedDate.trim().length() == 0) {
                            Toast.makeText(context, "Please enter your expected date of crop", Toast.LENGTH_SHORT).show();
                        } else if (Integer.parseInt(totalQuantity) <= 0) {
                            Toast.makeText(context, "Total quantity must be greater than zero", Toast.LENGTH_SHORT).show();
                        } else if (Integer.parseInt(unitPrice) <= 0) {
                            Toast.makeText(context, "Unit price must be greater than zero", Toast.LENGTH_SHORT).show();
                        }
                        //TODO:check if entered date is greater than current date or open date picker to avoid all this
                        else {
                            Crops crop = new Crops();
                            Date cropExpectedDate = Date.newBuilder()
                                    .setDay(Integer.parseInt(expectedDate.substring(0, 2)))
                                    .setMonth(Integer.parseInt(expectedDate.substring(3, 5)))
                                    .setYear(Integer.parseInt(expectedDate.substring(6)))
                                    .build();

                            crop.setCropName(cropName);
                            crop.setExpectedDate(cropExpectedDate.toString());
                            crop.setTotalQuantity(Integer.parseInt(totalQuantity));
                            crop.setRemainingQuantity(crop.getTotalQuantity());
                            crop.setOrganic(false);
                            crop.setDelivered(false);
                            crop.setPrice(Integer.parseInt(unitPrice));

                            crop.setSellerId(mAuth.getUid());
                            crop.setCropId(mAuth.getUid() + crop.getCropName());
                            data.getCropsList().add(crop);

                            CollectionReference dbUsers = db.collection("Agri");
                            dbUsers.document("Crops").set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(MainActivity.this, "Crop Added Successfully!", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "Failed To Upload!, Try Again!" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                            myCropListRVAdapter.notifyDataSetChanged();
                            mDialog.hide();
                        }
                    }
                });
                mDialog.show();
            }
        });
    }
}
