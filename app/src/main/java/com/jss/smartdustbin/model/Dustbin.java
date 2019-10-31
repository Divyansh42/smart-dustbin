package com.jss.smartdustbin.model;

import com.google.android.gms.maps.model.LatLng;

public class Dustbin {
    private String id;
    private String state, city, locality;
    private LatLng latLng;
    private String garbageLevel;
    private String lastUpdated;

    public Dustbin(String garbageLevel, String lastUpdated) {
        this.garbageLevel = garbageLevel;
        this.lastUpdated = lastUpdated;
    }
    public Dustbin() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getGarbageLevel() {
        return garbageLevel;
    }

    public void setGarbageLevel(String garbageLevel) {
        this.garbageLevel = garbageLevel;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
