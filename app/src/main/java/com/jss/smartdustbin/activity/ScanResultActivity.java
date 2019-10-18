package com.jss.smartdustbin.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jss.smartdustbin.API;
import com.jss.smartdustbin.R;
import com.jss.smartdustbin.utils.HttpStatus;
import com.jss.smartdustbin.utils.LocationTrack;
import com.jss.smartdustbin.utils.SmartDustbinApplication;

import org.json.JSONException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Timer;

public class ScanResultActivity extends AppCompatActivity {

    Button btnDone;
    String barCodeResult;
    ProgressDialog progressDialog;
    private static final String TAG =  ScanResultActivity.class.getSimpleName();
    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();

    private final static int ALL_PERMISSIONS_RESULT = 101;
    LocationTrack locationTrack;
    double longitude, latitude;

    Timer timer;
    boolean success = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_result);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        barCodeResult = getIntent().getStringExtra("code");
        setTitle("Register");

        /*permissions.add(ACCESS_FINE_LOCATION);
        permissions.add(ACCESS_COARSE_LOCATION);

        permissionsToRequest = findUnAskedPermissions(permissions);
        //get the permissions we have asked for before but are not granted..
        //we will store this in a global list to access later.


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            if (permissionsToRequest.size() > 0)
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }


        locationTrack = new LocationTrack(ScanResultActivity.this);


        if (locationTrack.canGetLocation()) {


            longitude = locationTrack.getLongitude();
            latitude = locationTrack.getLatitude();

            Toast.makeText(getApplicationContext(), "Longitude:" + Double.toString(longitude) + "\nLatitude:" + Double.toString(latitude), Toast.LENGTH_SHORT).show();
        } else {

            locationTrack.showSettingsAlert();
        }

        timer = new Timer();*/
        btnDone = findViewById(R.id.btn_done);

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(ScanResultActivity.this);
                progressDialog.setTitle("Confirming registration");
                progressDialog.setMessage("Please wait...");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setCancelable(false);
                progressDialog.show();


               confirmRegistration();


            }
        });


    }

    private void confirmRegistration() {

        final Thread fetchRegistrationConfirmationThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int count = 0;
                final String accessToken = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("access_token", "");

                while(!success){
                    try {
                        httpRequest(accessToken, barCodeResult);
                        Thread.sleep(2000);

                    }
                    catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        fetchRegistrationConfirmationThread.start();
    }

    private void httpRequest(String accessToken, String bin) throws IOException, JSONException {
        String data = "?";
        try {
            data += URLEncoder.encode("bin", "UTF-8") + "=" + URLEncoder.encode("987543222", "UTF-8");
            /*data += "&" + URLEncoder.encode("description", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8");
            data += "&" + URLEncoder.encode("remark", "UTF-8") + "=" + URLEncoder.encode(params[2], "UTF-8");
            data += "&" + URLEncoder.encode("customer", "UTF-8") + "=" + URLEncoder.encode(params[3], "UTF-8");
            data += "&" + URLEncoder.encode("salesman", "UTF-8") + "=" + URLEncoder.encode(params[4], "UTF-8");*/
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        URL url = new URL(API.BASE + API.CONFIRM_REGISTRATION + "?bin=" + bin);

        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setRequestProperty ("Authorization", "Bearer " + accessToken);
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setDoInput(false);
        httpURLConnection.setDoOutput(false);
       /* OutputStreamWriter wr = new OutputStreamWriter(httpURLConnection.getOutputStream());
        wr.write(data);
        wr.flush();
        wr.close();*/
        int responseCode = httpURLConnection.getResponseCode();
        Log.e(TAG, "response code-----------" + responseCode);
        //System.out.println("--------------" + httpURLConnection.getResponseCode() + "------------------");

        if (responseCode == HttpStatus.OK.value()) {
            Log.v(TAG, "json object created");
            success = true;
            runOnUiThread(new Runnable() {

                @Override
                public void run() {

                    // Stuff that updates the UI
                    progressDialog.hide();
                    Intent intent = new Intent(ScanResultActivity.this, RegistrationConfirmationActivity.class);
                    startActivity(intent);
                    finish();

                }
            });
        } else{
            success = false;
            onError(httpURLConnection.getResponseCode());
        }
    }

    public void onError(int status) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                if (status == HttpStatus.UNAUTHORIZED.value()) {
                    Toast.makeText(ScanResultActivity.this, "Please login to perform this action.", Toast.LENGTH_SHORT).show();
                    SmartDustbinApplication.getInstance().getDefaultSharedPreferences().edit().clear().apply();
                    Intent login = new Intent(ScanResultActivity.this, LoginActivity.class);
                    finishAffinity();
                    startActivity(login);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {

            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    /*private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList result = new ArrayList();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case ALL_PERMISSIONS_RESULT:
                for (String perms : permissionsToRequest) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }

                }

                break;
        }

    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(ScanResultActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
*/
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //locationTrack.stopListener();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


}
