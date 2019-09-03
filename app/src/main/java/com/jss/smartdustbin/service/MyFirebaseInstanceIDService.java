package com.jss.smartdustbin.service;


import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.jss.smartdustbin.Utils.Config;
import com.jss.smartdustbin.Utils.SharedPreferencesHandler;

public class MyFirebaseInstanceIDService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();

    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        SharedPreferencesHandler.getInstance().setFCMRegTokenInPref(token);
        sendRegistrationToServer(token);
        Intent registrationComplete = new Intent(Config.REGISTRATION_COMPLETE);
        registrationComplete.putExtra("token", token);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }


    private void sendRegistrationToServer(final String token) {
        // sending gcm token to server
        Log.e(TAG, "sendRegistrationToServer: " + token);
    }

}
