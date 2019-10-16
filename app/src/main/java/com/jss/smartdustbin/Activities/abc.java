/*
package com.jss.smartdustbin.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.vision.barcode.Barcode;
import com.jss.smartdustbin.API;
import com.jss.smartdustbin.R;
import com.jss.smartdustbin.Utils.HttpStatus;
import com.jss.smartdustbin.Utils.NetworkReceiver;
import com.jss.smartdustbin.Utils.SmartDustbinApplication;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import info.androidhive.barcode.BarcodeReader;

public class abc extends AppCompatActivity implements BarcodeReader.BarcodeReaderListener {

    BarcodeReader barcodeReader;
    public static final String LOG_TAG = ScanActivity.class.getSimpleName();
    private static final int REQUEST_CODE = 126;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Register");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(ScanActivity.this, new String[] {Manifest.permission.CAMERA}, REQUEST_CODE);
        // get the barcode reader instance
        barcodeReader = (BarcodeReader) getSupportFragmentManager().findFragmentById(R.id.barcode_scanner);
    }

    @Override
    public void onScanned(Barcode barcode) {

        // playing barcode reader beep sound
        barcodeReader.playBeep();
        Intent intent = new Intent(ScanActivity.this, MapsActivity.class);
        intent.putExtra("code", barcode.displayValue);
        startActivity(intent);
    }

    private void sendQrCodeResult(String barCodeResult, double latitude, double longitude) {
        final String accessToken = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("access_token", "");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, API.BASE + API.REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(LOG_TAG, " onResponse: " + response);
                Intent intent = new Intent(ScanActivity.this, ScanResultActivity.class);
                startActivity(intent);


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
            protected Map<String, String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("lat", Double.toString(latitude));
                params.put("long", Double.toString(longitude));
                params.put("din", barCodeResult);
                return params;
            }

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
            Toast.makeText(ScanActivity.this, "Please login to perform this action.", Toast.LENGTH_SHORT).show();
            SmartDustbinApplication.getInstance().getDefaultSharedPreferences().edit().clear().apply();
            Intent login = new Intent(ScanActivity.this, LoginActivity.class);
            finishAffinity();
            startActivity(login);
        } else{
            Toast.makeText(ScanActivity.this, "Please try again.", Toast.LENGTH_SHORT).show();
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
    }

    @Override
    public void onScannedMultiple(List<Barcode> list) {

    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onScanError(String s) {
        Toast.makeText(getApplicationContext(), "Error occurred while scanning " + s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCameraPermissionDenied() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
*/
