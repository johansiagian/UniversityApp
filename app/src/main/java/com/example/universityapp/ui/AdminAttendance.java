package com.example.universityapp.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.universityapp.Nextactivity;
import com.example.universityapp.R;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
public class AdminAttendance extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int CAMERA_PERMISSION_CODE = 100;

    private ImageView imageView;
    private EditText editTextName;
    private Bitmap capturedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_attendance);

        // Inisialisasi komponen UI
        Button btnSnap = findViewById(R.id.btnSnap);
        Button btnSubmit = findViewById(R.id.btnSubmit);
        imageView = findViewById(R.id.imageView);
        editTextName = findViewById(R.id.editTextName);

        // Tombol untuk membuka kamera
        btnSnap.setOnClickListener(v -> {
            if (checkCameraPermission()) {
                openCamera();
            } else {
                requestCameraPermission();
            }
        });

        // Tombol untuk submit data
        btnSubmit.setOnClickListener(v -> {
            String name = editTextName.getText().toString().trim();
            if (capturedImage != null && !name.isEmpty()) {
                submitData(name, capturedImage);
            } else {
                Toast.makeText(this, "Please capture an image and enter a name", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Mengecek izin kamera
    private boolean checkCameraPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;
    }

    // Meminta izin kamera jika belum diberikan
    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
    }

    // Membuka kamera untuk menangkap gambar
    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
        } else {
            Toast.makeText(this, "Camera app is not available", Toast.LENGTH_SHORT).show();
        }
    }

    // Menghandle hasil gambar dari kamera
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null) {
            capturedImage = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(capturedImage);
        }
    }

    // Mengirim data ke server
    private void submitData(String name, Bitmap image) {
        // Mengubah Bitmap menjadi String Base64
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        // URL endpoint server Anda
        String url = "http://192.168.124.20/absen/upload.php";

        // Membuat request POST ke server menggunakan Volley
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    Toast.makeText(this, "Data submitted successfully", Toast.LENGTH_SHORT).show();
                    imageView.setImageResource(android.R.color.transparent);
                    editTextName.setText("");
                    capturedImage = null;

                    // Intent untuk berpindah ke activity berikutnya
                    Intent intent = new Intent(AdminAttendance.this, Nextactivity.class);
                    intent.putExtra("name", name); // Mengirim data nama ke halaman berikutnya
                    intent.putExtra("image", encodedImage); // Mengirim gambar dalam format Base64
                    startActivity(intent);
                },
                error -> {
                    Toast.makeText(this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("image", encodedImage);
                return params;
            }
        };

        // Menambahkan request ke queue
        Volley.newRequestQueue(this).add(stringRequest);
    }

    // Menghandle hasil request izin kamera
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, "Camera permission is required", Toast.LENGTH_SHORT).show();
            }
        }
    }
}