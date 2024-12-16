package com.example.universityapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.universityapp.ui.AdminAdmissionsActivity;
import com.example.universityapp.ui.AdminAlumniCareersActivity;
import com.example.universityapp.ui.AdminNewsEventsActivity;
import com.example.universityapp.ui.AdminAttendance;

public class AdminDashboardActivity extends AppCompatActivity {

    private Button btnAdmissions;
    private Button btnNewsEvents;
    private Button btnAlumniCareers;

    private Button btnAttendance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        btnAdmissions = findViewById(R.id.btnAdmissions);
        btnNewsEvents = findViewById(R.id.btnNewsEvents);
        btnAlumniCareers = findViewById(R.id.btnAlumniCareers);
        btnAttendance = findViewById(R.id.btnAttendance);

        btnAdmissions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboardActivity.this, AdminAdmissionsActivity.class));
            }
        });

        btnNewsEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboardActivity.this, AdminNewsEventsActivity.class));
            }
        });

        btnAlumniCareers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboardActivity.this, AdminAlumniCareersActivity.class));
            }
        });

        btnAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboardActivity.this, AdminAttendance.class));
            }
        });
    }
}