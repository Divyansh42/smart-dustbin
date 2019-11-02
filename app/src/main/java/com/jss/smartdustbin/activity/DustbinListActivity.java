package com.jss.smartdustbin.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.jss.smartdustbin.API;
import com.jss.smartdustbin.R;
import com.jss.smartdustbin.adapter.DustbinsAdapter;
import com.jss.smartdustbin.model.Dustbin;
import com.jss.smartdustbin.utils.HttpStatus;
import com.jss.smartdustbin.utils.Jsonparser;
import com.jss.smartdustbin.utils.SmartDustbinApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jss.smartdustbin.activity.LoginActivity.LOG_TAG;

public class DustbinListActivity extends AppCompatActivity {

    private static final String TAG = DustbinListActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    LinearLayoutManager mLayoutManager;
    private List<Dustbin> dustbinList;
    private DustbinsAdapter dustbinsAdapter;
    ProgressBar progressBar;
    TextView defaultTv;
    ImageView mapIcon;
    ImageView filterIcon;

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
        mapIcon = findViewById(R.id.map_icon);
        filterIcon = findViewById(R.id.filter);

        mapIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DustbinListActivity.this, AllDustbinActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("dustbin_list", (ArrayList<? extends Parcelable>) dustbinList);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        filterIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] choices = {"25% and below", "25% - 75%", "75% and above"};

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DustbinListActivity.this)
                        .setTitle("Filter by Garbage level");
                alertDialogBuilder.setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        /*SmartDustbinApplication.getInstance().getDefaultSharedPreferences().edit().clear().apply();
                        Intent intent = new Intent(DustbinListActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();*/
                    }
                });
                alertDialogBuilder.setNegativeButton("Cancel", null)
                        .setSingleChoiceItems(choices, 0,null);
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });

        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        /*recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, DividerItemDecoration.VERTICAL, 36));*/
        recyclerView.setAdapter(dustbinsAdapter);
        loadDustbinList("5db97bb11a5e5700049482c1");

       /* Dustbin d1 = new Dustbin("75", "12/09/19 10:14 AM");
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

        dustbinsAdapter.notifyDataSetChanged();*/


    }

    private void loadDustbinList(String wardId){

        StringBuilder urlSb = new StringBuilder(API.BASE + API.DUSTBIN_LIST + "/?wardId=");
        urlSb.append(wardId);
        progressBar.setVisibility(View.VISIBLE);
        defaultTv.setVisibility(View.GONE);
        final String accessToken = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("access_token", "");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlSb.toString(),  new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(LOG_TAG, " onResponse: " + response);
                progressBar.setVisibility(View.GONE);
                dustbinList = Jsonparser.responseStringToDustbinList(response);
                dustbinsAdapter.setItems(dustbinList);
                dustbinsAdapter.notifyDataSetChanged();
                dustbinsAdapter.getItemCount();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(LOG_TAG, " onErrorResponse: " + error.toString());
                if(error.networkResponse != null){
                    onError(error.networkResponse.statusCode);
                }
            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                params.put("Authorization", "Bearer " + accessToken);
                return params;
            }

        };

        SmartDustbinApplication.getInstance().addToRequestQueue(stringRequest);
    }

    public void onError(int status) {
        if(status == HttpStatus.UNAUTHORIZED.value()){
            Toast.makeText(DustbinListActivity.this, "Please login to perform this action.", Toast.LENGTH_SHORT).show();
            SmartDustbinApplication.getInstance().getDefaultSharedPreferences().edit().clear().apply();
            Intent login = new Intent(DustbinListActivity.this, LoginActivity.class);
            finishAffinity();
            startActivity(login);
        } else{
            Toast.makeText(DustbinListActivity.this, "Please try again.", Toast.LENGTH_SHORT).show();
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
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
