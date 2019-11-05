package com.jss.smartdustbin.utils;

import android.graphics.Color;
import android.text.format.DateUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jss.smartdustbin.API;
import com.jss.smartdustbin.interfaces.VolleyCallback;
import com.jss.smartdustbin.model.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class Helper {

    private static final String TAG = "Helper";

    public static void sendFcmToken(final String accessToken, final VolleyCallback volleyCallback) {
        String url = API.BASE + API.FCM_TOKEN_POST ;
        StringRequest tokenPutRequest = new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, " Helper onResponse: " + response);
                volleyCallback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, " Helper onErrorResponse: " + error.toString());
                if(error.networkResponse != null)
                    volleyCallback.onError(error.networkResponse.statusCode, new String(error.networkResponse.data));
                else
                    volleyCallback.onError(HttpStatus.SERVICE_UNAVAILABLE.value(), "");
            }
        }){
            @Override
            public Map<String, String> getHeaders() {
                Map<String,String> params = new HashMap<>();
                params.put("Authorization", "Bearer " + accessToken);
                return params;
            }
        };

        SmartDustbinApplication.getInstance().addToRequestQueue(tokenPutRequest);
    }

    public static String getDateFromString(String dateString) throws ParseException {
        /*DateFormat formatterIST = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
        formatterIST.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = formatterIST.parse(dateString);
        Log.i(TAG, "Helper parse date {}" + formatterIST.format(date));
        return date;*/

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        long time = sdf.parse(dateString).getTime();
        long now = System.currentTimeMillis();

        CharSequence ago =
                DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS);
        return ago.toString();
    }

    public static int getGarbageStatusFromLevel(String garbageLevel){
        int garbagePer = Integer.parseInt(garbageLevel);
        if(garbagePer < 25)
            return 1;
        else if(garbagePer <= 74)
           return 2;
        else return 3;
    }




}


