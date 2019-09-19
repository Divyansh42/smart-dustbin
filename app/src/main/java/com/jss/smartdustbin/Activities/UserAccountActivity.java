package com.jss.smartdustbin.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.jss.smartdustbin.R;

public class UserAccountActivity extends AppCompatActivity {
    TextView userFullName;
    TextView userGender;
    TextView userAge;
    TextView userContactNo;
    TextView userEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My Account");

        userFullName = findViewById(R.id.user_name);
        userGender = findViewById(R.id.user_gender);
        userAge = findViewById(R.id.user_age);
        userContactNo = findViewById(R.id.user_phoneNo);
        userEmail = findViewById(R.id.user_email);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
