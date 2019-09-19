package com.jss.smartdustbin.Utils;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jss.smartdustbin.API;
import com.jss.smartdustbin.Interfaces.VolleyCallback;
import com.jss.smartdustbin.Models.User;

import java.util.HashMap;
import java.util.Map;

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


}


