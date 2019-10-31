package com.jss.smartdustbin.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.jss.smartdustbin.R;
import com.jss.smartdustbin.adapter.DustbinsAdapter;
import com.jss.smartdustbin.model.Dustbin;

import java.util.ArrayList;
import java.util.List;

public class DustbinListActivity extends AppCompatActivity {

    private static final String TAG = DustbinListActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    LinearLayoutManager mLayoutManager;
    private List<Dustbin> dustbinList;
    private DustbinsAdapter dustbinsAdapter;
    ProgressBar progressBar;
    TextView defaultTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dustbin_list);

        Toolbar toolbar = findViewById(R.id.toolbar_top);
        toolbar.setTitle("Dustbins");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setDisplayShowTitleEnabled(true);
        //getSupportActionBar().setTitle("Dustbins");
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));

        recyclerView = findViewById(R.id.rv_dustbins);
        dustbinList = new ArrayList<>();
        dustbinsAdapter = new DustbinsAdapter(this, dustbinList);
        progressBar = findViewById(R.id.progressBar);
        defaultTv = findViewById(R.id.default_tv);

        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        /*recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, DividerItemDecoration.VERTICAL, 36));*/
        recyclerView.setAdapter(dustbinsAdapter);
        defaultTv.setVisibility(View.GONE);

        Dustbin d1 = new Dustbin("75", "12/09/19 10:14 AM");
        Dustbin d2 = new Dustbin("25", "12/09/19 10:14 AM");
        Dustbin d3 = new Dustbin("50", "12/09/19 10:14 AM");
        Dustbin d4 = new Dustbin("40", "12/09/19 10:14 AM");
        Dustbin d5 = new Dustbin("10", "12/09/19 10:14 AM");
        Dustbin d6 = new Dustbin("80", "12/09/19 10:14 AM");

        dustbinList.add(d1);
        dustbinList.add(d2);
        dustbinList.add(d3);
        dustbinList.add(d4);
        dustbinList.add(d5);
        dustbinList.add(d6);

        dustbinsAdapter.notifyDataSetChanged();


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
