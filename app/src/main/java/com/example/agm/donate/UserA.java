package com.example.agm.donate;

/**
 * Created by elega on 04-04-2018.
 */

public class UserA {
    private String foodtype;
    private String lat;
    private String lon;
    private String add;
    private String donN;

    public UserA() {
    }
    public UserA(String foodtype){

    }
    public void setFoodtype(String foodtype) {
        this.foodtype = foodtype;
    }
    public void setLat (String lat) {
        this.lat = lat;
    }
    public void setLon(String lon) {
        this.lon = lon;
    }
    public void setAdd (String add) {
        this.add = add;
    }
    public void setDonN (String donN) {
        this.donN = donN;
    }
    public String getFoodtype() {
        return foodtype;
    }
    public String getLat() {
        return lat;
    }
    public String getLon() {
        return lon;
    }
    public String getAdd() {
        return add;
    }
    public String getDonN() {
        return donN;
    }
}
