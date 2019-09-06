/*
package com.jss.smartdustbin.Utils;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyRequestQueue extends Application {

    public static final String TAG = VolleyRequestQueue.class.getSimpleName();
    private RequestQueue mRequestQueue;
    private static VolleyRequestQueue instance;

    public static synchronized VolleyRequestQueue getInstance() {
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null)
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request) {
        request.setTag(TAG);
        getRequestQueue().add(request);
    }

    public void cancelRequest(Object tag) {
        if (mRequestQueue != null)
            mRequestQueue.cancelAll(tag);
    }
}
*/
