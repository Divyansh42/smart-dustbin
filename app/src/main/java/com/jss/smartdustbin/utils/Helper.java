package com.jss.smartdustbin.utils;

import android.graphics.Color;
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

    public static User getUserFromResponse(String response){
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(response).getAsJsonObject();
        String name = jsonObject.get("full_name").getAsString();
        String gender = jsonObject.get("gender").getAsString();
        int age = jsonObject.get("age").getAsInt();
        String email = jsonObject.get("email").getAsString();
        String contactNo = jsonObject.get("contact_no").getAsString();

        User user = new User();
        user.setName(name);
        user.setGender(gender);
        user.setAge(age);
        user.setContactNo(contactNo);
        user.setEmail(email);

        return  user;
    }

    public static Date getDateFromString(String dateString) throws ParseException {
        DateFormat formatterIST = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
        formatterIST.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = formatterIST.parse(dateString);
        Log.i(TAG, "Helper parse date {}" + formatterIST.format(date));
        return date;
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


