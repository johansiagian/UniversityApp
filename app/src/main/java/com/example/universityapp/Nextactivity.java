package com.example.universityapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Nextactivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nextactivity);

        // Ambil data dari Intent
        String name = getIntent().getStringExtra("name");
        String encodedImage = getIntent().getStringExtra("image");

        // Konversi Base64 ke Bitmap
        Bitmap bitmap = null;
        if (encodedImage != null) {
            byte[] decodedBytes = Base64.decode(encodedImage, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
        }

        // Tampilkan nama di halaman berikutnya
        TextView textView = findViewById(R.id.textViewName);
        textView.setText("Present, " + name + "!");

        // Tampilkan gambar di ImageView
        ImageView imageView = findViewById(R.id.imageViewCaptured);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        }
    }

}