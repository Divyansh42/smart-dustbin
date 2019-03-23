package com.jss.smartdustbin.Utils;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreferencesHandler extends Application {

    public SharedPreferences getDefaultSharedPreferences(){
        return PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    }

    public String getAccessToken() {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences();
        String token = null;
        if (sharedPreferences.contains("token")) {
            token = sharedPreferences.getString("token", "");
        }
        return token;
    }
}
