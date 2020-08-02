package com.example.agri;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.agri.pojos.Crops;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class CropDetailsActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 100;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 200;
    private FirebaseFirestore db;
    private Button uploadNewImageBtn;
    private ImageView cropImageView;
    private StorageReference mStorage;
    private Context context;
    private ProgressDialog mProgress;
    private Crops crop;
    private TextView cropNameTV, cropIdTV, totalQuantityTV, remainingQuantityTV, priceTV, organicTV, sellerIdTV, expectedDateTV, deliveredTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_details);
        context = this;
        db = FirebaseFirestore.getInstance();

        mStorage = FirebaseStorage.getInstance().getReference();

        uploadNewImageBtn = findViewById(R.id.upload_new_image_btn);
        cropImageView = findViewById(R.id.crop_image_view);
        cropNameTV = findViewById(R.id.crop_name);
        cropIdTV = findViewById(R.id.crop_id);
        totalQuantityTV = findViewById(R.id.crop_total_quantity);
        remainingQuantityTV = findViewById(R.id.crop_remaining_quantity);
        priceTV = findViewById(R.id.crop_price);
        organicTV = findViewById(R.id.crop_isOrganic);
        sellerIdTV = findViewById(R.id.crop_seller_id);
        expectedDateTV = findViewById(R.id.crop_expected_date);
        deliveredTV = findViewById(R.id.crop_delivered);


        crop = (Crops) getIntent().getSerializableExtra("crop");

        if (crop != null) {
            cropNameTV.setText(crop.getCropName());
            cropIdTV.setText(crop.getCropId());
            totalQuantityTV.setText(crop.getTotalQuantity() + "");
            remainingQuantityTV.setText(crop.getRemainingQuantity() + "");
            priceTV.setText(crop.getPrice() + "");
            organicTV.setText("IsOrganic " + crop.getOrganic().toString());
            sellerIdTV.setText(crop.getSellerId());
            expectedDateTV.setText(crop.getExpectedDate());
            deliveredTV.setText("delivered " + (crop.getDelivered() != null ? crop.getDelivered().toString() : false));
            Picasso.get().load(crop.getImageURL())
                    .centerCrop()
                    .into(cropImageView);
        }

        mProgress = new ProgressDialog(this);

        uploadNewImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                } else {
                    ActivityCompat.requestPermissions(CropDetailsActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_PERMISSION_REQUEST_CODE);
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[1] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            mProgress.setMessage("Uploading image...");
            mProgress.show();
//            uncomment to use the compression
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            cropImageView.setImageBitmap(imageBitmap);
//            encodeBitmapAndSaveToFirebase(imageBitmap);


            Uri uri = data.getData();
            cropImageView.setImageURI(uri);

            StorageReference filepath = mStorage.child("CropPhotos").child(crop.getCropId() + crop.getCropName() + uri.getLastPathSegment());
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    mProgress.dismiss();
                    Toast.makeText(context, "Image uploaded successfully", Toast.LENGTH_SHORT).show();

                    Uri imageURL = taskSnapshot.getUploadSessionUri();

                    if (imageURL != null) {
                        crop.setImageURL(imageURL.toString());
                        updateImageURLInDatabase();

                        Picasso.get().load(imageURL)
                                .centerCrop()
                                .into(cropImageView);
                    } else {
                        Toast.makeText(context, "Something went wrong try uploading again", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    mProgress.dismiss();
                    Toast.makeText(context, "Something went wrong try uploading again", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void updateImageURLInDatabase() {
        for (Crops c : MainActivity.myCropList) {
            if (c.getCropId().equals(crop.getCropId())) {
                c.setImageURL(crop.getImageURL());
            }
        }
        CollectionReference dbUsers = db.collection("Agri");
        dbUsers.document("Crops").set(MainActivity.data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mProgress.dismiss();
                Toast.makeText(CropDetailsActivity.this, "Image URL updated Successfully!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Failed To Upload!, Try Again!" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void encodeBitmapAndSaveToFirebase(final Bitmap imageBitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        StorageReference filepath = mStorage.child("CropPhotos").child(crop.getCropId() + crop.getCropName());
        filepath.putBytes(baos.toByteArray()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                mProgress.dismiss();
                Toast.makeText(context, "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                crop.setImageURL(taskSnapshot.getUploadSessionUri().toString());
                showBitmapImageIntoCropImageView();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mProgress.dismiss();
                Toast.makeText(context, "Something went wrong try uploading again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showBitmapImageIntoCropImageView() {
        if (!"crop.getImageUrl()".contains("http")) {
            Bitmap imageBitmap = decodeFromFirebaseBase64(crop.getImageURL());
            cropImageView.setImageBitmap(imageBitmap);
        } else {
            Picasso.get()
                    .load(crop.getImageURL())
                    .centerCrop()
                    .into(cropImageView);
        }
    }

    private Bitmap decodeFromFirebaseBase64(String image) {
        byte[] decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }
}
