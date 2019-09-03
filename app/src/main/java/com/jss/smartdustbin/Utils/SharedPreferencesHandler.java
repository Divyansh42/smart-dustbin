package com.jss.smartdustbin.Utils;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.jss.smartdustbin.Activities.LoginActivity;

public class SharedPreferencesHandler extends Application {

    private static SharedPreferencesHandler instance;

    public SharedPreferences getDefaultSharedPreferences(){
        return PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static synchronized SharedPreferencesHandler getInstance() {
        return instance;
    }

    public void setAccessToken(String token){
        SharedPreferences pref = getDefaultSharedPreferences();
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("access_token", token);
        editor.apply();
    }

    public String getAccessToken() {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences();
        String token = null;
        if (sharedPreferences.contains("token")) {
            token = sharedPreferences.getString("token", "");
        }
        return token;
    }
    public void setFCMRegTokenInPref(String token) {
        SharedPreferences pref = getDefaultSharedPreferences();
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("regId", token);
        editor.commit();
    }

    public String getFCMRegToken(){
        SharedPreferences sharedPreferences = getDefaultSharedPreferences();
       // SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regToken = sharedPreferences.getString("regId", null);

        Toast.makeText(getApplicationContext(), "RegToken " + regToken, Toast.LENGTH_SHORT).show();

        return regToken;
    }





}
