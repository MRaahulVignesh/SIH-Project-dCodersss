package com.example.agri;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.agri.pojos.Crops;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    Button addCrop;
    Context context;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        context = this;
        addCrop = findViewById(R.id.add_btn);
        addCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog mDialog = new Dialog(context);
                mDialog.setContentView(R.layout.add_crop_popup);
                mDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                //TODO:Read text from user
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
                    }
                });
            }
        });
    }
}
