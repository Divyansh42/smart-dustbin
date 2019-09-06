package com.jss.smartdustbin.Activities;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

//import com.jss.smartdustbin.Fragments.AllDustbinsFragment;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.jss.smartdustbin.R;
import com.jss.smartdustbin.Utils.SmartDustbinApplication;

public class UserHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView tvFirstName;
    TextView tvLastName;
    TextView tvDesignation;
    private static final String TAG =  UserHomeActivity.class.getSimpleName();
    private SharedPreferences pref;
    TextView accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pref = PreferenceManager.getDefaultSharedPreferences(UserHomeActivity.this);
        accessToken = findViewById(R.id.access_token);
        accessToken.setText(SmartDustbinApplication.getInstance().getAccessToken());


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        /*FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        SmartDustbinApplication.getInstance().setFCMRegTokenInPref(token);



                        // Log and toast
                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d(TAG, "token :: " + msg);
                        //Toast.makeText(NotificationActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });*/

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( UserHomeActivity.this,  new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String fcmToken = instanceIdResult.getToken();
                Log.e("fcmToken",fcmToken);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("fcmRegToken" , fcmToken);
                editor.apply();

                //notify server for the token change

            }
        });
        //SmartDustbinApplication.getInstance().getFCMRegToken();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View view = navigationView.getHeaderView(0);
        tvFirstName = view.findViewById(R.id.nav_header_user_first_name);
        tvLastName = view.findViewById(R.id.nav_header_user_last_name);
        tvDesignation = view.findViewById(R.id.nav_header_user_designation);

        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

    @SuppressWarnings("StatementWithEmptyBody")
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
        if (id == R.id.nav_localities){
           // fragment = new RegisterFragment();
        }

        if(id == R.id.logout){
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("token", "");
            editor.apply();
            intent = new Intent(UserHomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();

        }

        if (fragment != null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fl_home_activity, fragment);
            fragmentTransaction.commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
