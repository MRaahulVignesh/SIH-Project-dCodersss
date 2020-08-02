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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class CropDetailsActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 100;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 200;
    private Button uploadNewImageBtn;
    private ImageView cropImageView;
    private StorageReference mStorage;
    private Context context;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_details);
        context = this;

        mStorage = FirebaseStorage.getInstance().getReference();

        uploadNewImageBtn = findViewById(R.id.upload_new_image_btn);
        cropImageView = findViewById(R.id.crop_image_view);

        //TODO: to get url and other data and load into imageview and other views

        mProgress = new ProgressDialog(this);

        uploadNewImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                } else {
                    ActivityCompat.requestPermissions(CropDetailsActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
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
            //TODO:decide naming convention for image
            StorageReference filepath = mStorage.child("CropPhotos").child(uri.getLastPathSegment());
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    mProgress.dismiss();
                    Toast.makeText(context, "Image uploaded successfully", Toast.LENGTH_SHORT).show();

                    Uri imageURL = taskSnapshot.getUploadSessionUri();

                    //TODO:update the imageurl in database

                    Picasso.get().load(imageURL)
                            .centerCrop()
                            .into(cropImageView);
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

    private void encodeBitmapAndSaveToFirebase(final Bitmap imageBitmap) {
        //TODO:find url and retrieve and pass
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        //TODO: decide naming convention
        StorageReference filepath = mStorage.child("CropPhotos").child("");
        filepath.putBytes(baos.toByteArray()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                mProgress.dismiss();
                Toast.makeText(context, "Image uploaded successfully", Toast.LENGTH_SHORT).show();
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
            try {
                Bitmap imageBitmap = decodeFromFirebaseBase64("crop.getImageUrl()");
                cropImageView.setImageBitmap(imageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Picasso.get()
                    .load("crop.getImageUrl()")
                    .centerCrop()
                    .into(cropImageView);
        }
    }

    private Bitmap decodeFromFirebaseBase64(String image) throws IOException {
        byte[] decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }
}
