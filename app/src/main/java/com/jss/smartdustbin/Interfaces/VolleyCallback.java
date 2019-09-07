package com.jss.smartdustbin.Interfaces;

public interface VolleyCallback {

    void onSuccess(String response);

    void onError(int status, String error);
}
