package com.example.agm.donate;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by elega on 01-04-2018.
 */

public class MyMarkerData {
    LatLng latLng;
    String title;

    String address;
    String DonerMobile;
    String foodType;
    String quanityt;
    String DonationStatus;

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(Double lat,Double longi) {
        LatLng location = new LatLng(lat, longi);
        this.latLng = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title ) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDonerMobile() {
        return DonerMobile;
    }

    public void setDonerMobile(String DonerMobile) {
        this.DonerMobile = DonerMobile;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public String getQuanityt() {
        return quanityt;
    }

    public void setQuanityt(String quanityt) {
        this.quanityt = quanityt;
    }

    public String getDonationStatus() {
        return DonationStatus;
    }

    public void setDonationStatus(String DonationStatus) {
        this.DonationStatus = DonationStatus;
    }
}
