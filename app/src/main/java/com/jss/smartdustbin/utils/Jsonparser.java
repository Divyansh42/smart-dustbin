package com.jss.smartdustbin.utils;

import com.jss.smartdustbin.model.Dustbin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Jsonparser {


    public static List<Dustbin> responseStringToDustbinsArray(String dustbinStringResponse) {
        List<Dustbin> dustbinList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(dustbinStringResponse);
            for (int j = 0; j < jsonArray.length(); j++) {
                Dustbin dustbin;
                try {
                    dustbin = toObject(jsonArray.getJSONObject(j).toString());
                    dustbinList.add(dustbin);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dustbinList;


    }

    public static String get(JSONObject jsonObject, String key) {
        if (jsonObject.has(key)) {
            try {
                String string = jsonObject.getString(key);
                return string;
            } catch (JSONException e) {
                e.printStackTrace();
                return "";
            }
        } else {
            return "";
        }
    }


    public static Dustbin toObject(String jsonString) {
        Dustbin dustbin = new Dustbin();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            dustbin.setId(get(jsonObject, "_id"));
            dustbin.setState(get(jsonObject, "state"));
            dustbin.setCity(get(jsonObject, "city"));
            dustbin.setLocality(get(jsonObject, "locality"));
           // dustbin.setLatLng(get(jsonObject, "latlang"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dustbin;
    }
}
