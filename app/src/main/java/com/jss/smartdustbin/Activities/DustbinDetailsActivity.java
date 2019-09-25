package com.jss.smartdustbin.Activities;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jss.smartdustbin.R;
import com.jss.smartdustbin.Utils.SmartDustbinApplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class DustbinDetailsActivity extends AppCompatActivity {

    Toolbar mToolbar;
    TextView id;
    ProgressBar dustbinLevelPb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dustbin_details);
        id = findViewById(R.id.SNo_text_view);
        dustbinLevelPb = findViewById(R.id.dustbin_progressbar);
        String FCMToken = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("FCM_token", "");

       //id.setText(FCMToken);

       /* mToolbar = (Toolbar) findViewById(R.id.map_toolbar);
        //setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("All Details");

        dustbinLevelPb.setProgress(100);
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
