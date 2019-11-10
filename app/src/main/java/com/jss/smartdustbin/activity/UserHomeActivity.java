package com.jss.smartdustbin.activity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.OnSuccessListener;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//import com.jss.smartdustbin.Fragments.AllDustbinsFragment;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.jss.smartdustbin.API;
import com.jss.smartdustbin.R;
import com.jss.smartdustbin.model.User;
import com.jss.smartdustbin.utils.Config;
import com.jss.smartdustbin.utils.HttpStatus;
import com.jss.smartdustbin.utils.Jsonparser;
import com.jss.smartdustbin.utils.NetworkReceiver;
import com.jss.smartdustbin.utils.NotificationUtils;
import com.jss.smartdustbin.utils.SmartDustbinApplication;

import java.util.HashMap;
import java.util.Map;

import static com.jss.smartdustbin.activity.LoginActivity.LOG_TAG;

public class UserHomeActivity extends AppCompatActivity {

    TextView tvFirstName;
    private static final String TAG =  UserHomeActivity.class.getSimpleName();
    private SharedPreferences pref;
    //TextView accessToken;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    Button logoutButton;
    View registeredDustbinCard;
    View myAccountCard;
    Intent intent;
    View registerCard;
    View progressBar;
    NetworkReceiver receiver;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        setContentView(R.layout.activity_user_home);
        pref = PreferenceManager.getDefaultSharedPreferences(UserHomeActivity.this);

        registerCard = findViewById(R.id.register_card);
        registeredDustbinCard = (View) findViewById(R.id.registered_dustbin_card);
        myAccountCard = (View) findViewById(R.id.my_account_card);
        progressBar = findViewById(R.id.progress_bar);
        tvFirstName = findViewById(R.id.user_first_name_tv);

        logoutButton = findViewById(R.id.bt_logout);
        receiver = new NetworkReceiver();
        if(receiver.isConnected()){
            fetchUserData();
        } else {
            Toast.makeText(UserHomeActivity.this, "No internet connection!", Toast.LENGTH_SHORT);
        }
        registerCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(UserHomeActivity.this, RegisterDustbinActivity.class);
                startActivity(intent);
            }
        });

        registeredDustbinCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(UserHomeActivity.this, DustbinListActivity.class);
                startActivity(intent);
            }
        });

        myAccountCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(UserHomeActivity.this, UserAccountActivity.class);
                startActivity(intent);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    final androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(UserHomeActivity.this);
                    builder.setTitle("Logout");
                    builder.setMessage("Are you sure you want to logout?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int whichButton) {
                            SmartDustbinApplication.getInstance().getDefaultSharedPreferences().edit().clear().apply();
                            Intent intent = new Intent(UserHomeActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });

                    builder.setNegativeButton("No", null);
                    builder.setIcon(android.R.drawable.ic_dialog_alert);
                    AlertDialog alert1 = builder.create();
                    alert1.show();

            }
        });


        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( UserHomeActivity.this,  new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String fcmToken = instanceIdResult.getToken();
                Log.e("FCM_token",fcmToken);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("FCM_token" , fcmToken);
                editor.apply();
                Intent registrationComplete = new Intent(Config.REGISTRATION_COMPLETE);
                registrationComplete.putExtra("token",fcmToken);
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(registrationComplete);

                //notify server for the token change

            }
        });

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);
                    String FCMToken = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("FCM_token", null);
                    //Toast.makeText(getApplicationContext(), "FCM Token :" + FCMToken, Toast.LENGTH_SHORT).show();
                    if(FCMToken != null){
                        if(receiver.isConnected()){
                            sendFCMToken(FCMToken);
                        } else {
                            Toast.makeText(UserHomeActivity.this, "No internet Connection!", Toast.LENGTH_SHORT).show();
                        }

                    }


                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

                   // txtMessage.setText(message);
                }
            }
        };

    }

    public void setActionBarTitle(String title){
        getSupportActionBar().setTitle(title);
    }

    public void sendFCMToken(String FCMToken) {
        //final String FCMToken = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("FCM_token", "");
        final String accessToken = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("access_token", "");
        StringRequest fcmTokenPostReq = new StringRequest(Request.Method.POST,API.BASE + API.FCM_TOKEN_POST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, " onResponse: " + response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, " onErrorResponse: " + error.toString());
                Toast.makeText(UserHomeActivity.this, "Something went wrong! Please login again", Toast.LENGTH_SHORT).show();
                SmartDustbinApplication.getInstance().getDefaultSharedPreferences().edit().clear().apply();
                pref.edit().clear().apply();
                Intent login = new Intent(UserHomeActivity.this, LoginActivity.class);
                finishAffinity();
                startActivity(login);
               /* if(error.networkResponse != null){
                    onError(error.networkResponse.statusCode);
                }*/
            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("token", FCMToken);
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

        SmartDustbinApplication.getInstance().addToRequestQueue(fcmTokenPostReq);
    }

    private void fetchUserData() {
        final String accessToken = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("access_token", "");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, API.BASE + API.USER_INFO , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(LOG_TAG, " onResponse: " + response);
                user = Jsonparser.getUserFromResponse(response);
                tvFirstName.setText(user.getFirstName());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(LOG_TAG, " onErrorResponse: " + error.toString());
                progressBar.setVisibility(View.GONE);

                /*if(error.networkResponse != null){
                    onError(error.networkResponse.statusCode);
                }*/
                tvFirstName.setText("Welcome User");
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
            Toast.makeText(UserHomeActivity.this, "Please login to perform this action.", Toast.LENGTH_SHORT).show();
            SmartDustbinApplication.getInstance().getDefaultSharedPreferences().edit().clear().apply();
            pref.edit().clear().apply();
            Intent login = new Intent(UserHomeActivity.this, LoginActivity.class);
            finishAffinity();
            startActivity(login);
        } else{
            Toast.makeText(UserHomeActivity.this, "Error fetching data, Please try again.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(UserHomeActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        this.registerReceiver(receiver, filter);
    }

    @Override
    public void onStop() {
        super.onStop();
        UserHomeActivity.this.unregisterReceiver(receiver);
    }

}
