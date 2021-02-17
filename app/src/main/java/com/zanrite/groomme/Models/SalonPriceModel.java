package com.zanrite.groomme.Models;

public class SalonPriceModel {


    private String Name, Service, Price_salon, Time_salon, Price_home, Time_home,mobileno;
    private int ID,PrimaryServiceID;
    private boolean isSelected;

    public boolean isSelected(int position) {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getMobileno(int position) {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getName(int position) {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getService(int position) {
        return Service;
    }

    public void setService(String service) {
        Service = service;
    }

    public String getPrice_salon(int position) {
        return Price_salon;
    }

    public void setPrice_salon(String price_salon) {
        Price_salon = price_salon;
    }

    public String getTime_salon(int position) {
        return Time_salon;
    }

    public void setTime_salon(String time_salon) {
        Time_salon = time_salon;
    }

    public String getPrice_home(int position) {
        return Price_home;
    }

    public void setPrice_home(String price_home) {
        Price_home = price_home;
    }

    public String getTime_home(int position) {
        return Time_home;
    }

    public void setTime_home(String time_home) {
        Time_home = time_home;
    }

    public int getID(int position) {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getPrimaryServiceID(int position) {
        return PrimaryServiceID;
    }

    public void setPrimaryServiceID(int primaryServiceID) {
        PrimaryServiceID = primaryServiceID;
    }
}
