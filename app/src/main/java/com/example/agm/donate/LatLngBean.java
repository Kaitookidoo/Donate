package com.example.agm.donate;


public class LatLngBean
{
    private String Title="";
    String address;
    String DonerMobile;
    String foodType;
    String quanityt;
    String DonationStatus;
    private String Latitude="";
    private String  Longitude="";

    public String getTitle() {
        return Title;
    }
    public void setTitle(String title) {
        Title = title;
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

    public String getLatitude() {
        return Latitude;
    }
    public void setLatitude(String latitude) {
        Latitude = latitude;
    }
    public String getLongitude() {
        return Longitude;
    }
    public void setLongitude(String longitude) {
        Longitude = longitude;
    }


}