package com.example.androidwritefile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

public class WriteFileActivity extends AppCompatActivity {

    Button btnCamera;
    Button btnSaveFile;
    ImageView imagePerson;
    static int CAMERA_STATUS_CODE = 200;
    OutputStream fileOutputStream;
    static int WRITE_STATUS_CODE = 201;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnCamera = findViewById(R.id.take_btn);
        btnSaveFile = findViewById(R.id.save_btn);
        imagePerson = findViewById(R.id.person_avatar);
        btnCamera.setOnClickListener(view -> {
            Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            try {
                startActivityForResult(intentCamera, CAMERA_STATUS_CODE);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, "Terjasi masalah saat membuka kamera", Toast.LENGTH_LONG).show();
            }
        });

        btnSaveFile.setOnClickListener(v -> {
            requestPermission();
        });
    }

    private boolean checkPremission() {
        return ContextCompat.checkSelfPermission(
                this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    public void requestPermission() {
        // buka request permision
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, WRITE_STATUS_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            // succes take image
            if (requestCode == CAMERA_STATUS_CODE) {
//                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                imagePerson.setImageBitmap(bitmap);
                btnSaveFile.setVisibility(View.VISIBLE);
            }
        }
    }

    // dijalankan ketika request permision selesai
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == WRITE_STATUS_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //  saveImage();
                // check permision , jika permision oke maka save image
                boolean premission = checkPremission();
                if (premission) {
                    saveImage();
                } else {
                    requestPermission();
                }
            } else {
                Toast.makeText(this, "Check permission storage", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // save image to folder root android/data/andridwritefile
    private void saveImage() {
        // create directory
        // return file
        File file = createDir("foto-zam");
        BitmapDrawable bitmapDrawable = (BitmapDrawable) imagePerson.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        String imageName = System.currentTimeMillis() + ".jpg";
        File fileImage = new File(file, imageName);
        try {
            fileOutputStream = new FileOutputStream(fileImage);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.out.println("file not found");
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
        Toast.makeText(this, "Gambar tersimpan", Toast.LENGTH_SHORT).show();
        try {
            if (fileOutputStream == null) {
                Toast.makeText(this, "null file outputstream", Toast.LENGTH_SHORT).show();
            } else {
                fileOutputStream.flush();
            }
        } catch (IOException e) {
            System.out.println("flush error");
            System.out.println(e.getMessage());
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        try {
            fileOutputStream.close();
        } catch (IOException e) {
            System.out.println("close error");
            e.printStackTrace();
        }
    }
    public File createDir(String name) {
        File file = new File(getExternalFilesDir(null), "/" + name);
        if (!file.exists()) {
            file.mkdir();
        }
        return file;
    }

    // to save image in picture folder
    public void saveImageSecond() {
        Uri images;
        ContentResolver contentResolver = getContentResolver();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            images = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
        } else {
            images = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, "zamjago" + System.currentTimeMillis() + ".jpg");
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "images/*");
        Uri uri = contentResolver.insert(images, contentValues);
        try {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) imagePerson.getDrawable();
            Bitmap bitmap = bitmapDrawable.getBitmap();
            fileOutputStream = contentResolver.openOutputStream(Objects.requireNonNull(uri));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 200, fileOutputStream);
            Objects.requireNonNull(fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}