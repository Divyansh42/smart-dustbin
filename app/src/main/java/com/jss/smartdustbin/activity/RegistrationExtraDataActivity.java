package com.jss.smartdustbin.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.jss.smartdustbin.R;

public class RegistrationExtraDataActivity extends AppCompatActivity {

    String qrCodeResult;
    Spinner wardsSpinner;
    EditText landmarkEditText;
    Button continueBt;
    String selectedWardId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_extra_data);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        qrCodeResult = getIntent().getStringExtra("code");
        setTitle("Register");

        wardsSpinner = findViewById(R.id.spinner_wards);
        landmarkEditText = findViewById(R.id.et_landmark);

        continueBt = findViewById(R.id.btn_continue);

        continueBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationExtraDataActivity.this, MapsActivity.class);
                intent.putExtra("code", qrCodeResult);
                intent.putExtra("ward_id", selectedWardId);
                intent.putExtra("landmark", landmarkEditText.toString());
                startActivity(intent);
            }
        });


    }
}
