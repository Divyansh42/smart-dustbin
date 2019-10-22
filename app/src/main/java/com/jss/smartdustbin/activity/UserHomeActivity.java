package com.jss.smartdustbin.activity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.OnSuccessListener;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

//import com.jss.smartdustbin.Fragments.AllDustbinsFragment;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.jss.smartdustbin.API;
import com.jss.smartdustbin.R;
import com.jss.smartdustbin.utils.Config;
import com.jss.smartdustbin.utils.HttpStatus;
import com.jss.smartdustbin.utils.NotificationUtils;
import com.jss.smartdustbin.utils.SmartDustbinApplication;

import java.util.HashMap;
import java.util.Map;

public class UserHomeActivity extends AppCompatActivity {

    //TextView tvFirstName;
    //TextView tvLastName;
   // TextView tvDesignation;
    private static final String TAG =  UserHomeActivity.class.getSimpleName();
    private SharedPreferences pref;
    //TextView accessToken;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    Button logoutButton;
    View registeredDustbinCard;
    View myAccountCard;
    Intent intent;
    View registerCard;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);


       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //setTitle("Smart Dustbin");

        pref = PreferenceManager.getDefaultSharedPreferences(UserHomeActivity.this);

        registerCard = findViewById(R.id.register_card);
        registeredDustbinCard = (View) findViewById(R.id.registered_dustbin_card);
        myAccountCard = (View) findViewById(R.id.my_account_card);

        logoutButton = findViewById(R.id.bt_logout);
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
                intent = new Intent(UserHomeActivity.this, AllDustbinActivity.class);
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


                new AlertDialog.Builder(UserHomeActivity.this)
                        .setTitle("Logout")
                        .setMessage("Are you sure you want to logout?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                                SmartDustbinApplication.getInstance().getDefaultSharedPreferences().edit().clear().apply();
                                Intent intent = new Intent(UserHomeActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();

                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton("NO", null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        });



        /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();*/


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
                        sendFCMToken(FCMToken);
                    }


                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

                   // txtMessage.setText(message);
                }
            }
        };

        /*NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View view = navigationView.getHeaderView(0);
        tvFirstName = view.findViewById(R.id.nav_header_user_first_name);
        tvLastName = view.findViewById(R.id.nav_header_user_last_name);
        tvDesignation = view.findViewById(R.id.nav_header_user_designation);*/

        /*navigationView.setNavigationItemSelectedListener(this);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmartDustbinApplication.getInstance().getDefaultSharedPreferences().edit().clear().apply();
                Intent intent = new Intent(UserHomeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });*/

    }

    public void setActionBarTitle(String title){
        getSupportActionBar().setTitle(title);
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_edit_profile) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    /*@SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        Fragment fragment = null;
        Intent intent;

        if (id == R.id.nav_register) {

            intent = new Intent(UserHomeActivity.this, RegisterDustbinActivity.class);
            startActivity(intent);
        }
        if (id == R.id.nav_dustbins){
            intent = new Intent(UserHomeActivity.this, AllDustbinActivity.class);
            startActivity(intent);

        }
        if (id == R.id.my_account){

            intent = new Intent(UserHomeActivity.this, UserAccountActivity.class);
            startActivity(intent);
        }


       *//* if (fragment != null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fl_home_activity, fragment);
            fragmentTransaction.commit();
        }*//*

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
*/

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
            //startActivity(new Intent(YourRouteActivity.this, MainActivity.class));
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

}
