package com.jss.smartdustbin.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

public class Dustbin {
    private String id;
    private String bin;
    private String landmark;
    private String state, city, locality;
    private String latitude;
    private String longitude;
    private LatLng latLng;
    private String garbageLevel;
    private Date lastUpdated;
    private String comment;

    public Dustbin(String garbageLevel, Date lastUpdated) {
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

    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
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

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
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

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
