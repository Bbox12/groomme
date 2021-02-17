package com.zanrite.groomme.Models;

public class Bookingmodel {

    private int ID,NoofItems,OTP,IDserviceAt,isAccepted,isRunning,isCompleted,isCancelled;
    private double Discount,Payable,Parlour_latitude,Parlour_longitude,Home_latitude,Home_longitude;
    private String addressd,houseno,landmark,OrderDate,crew_name,Slot,Photo,userName,userMobile,PPhoto,parlour_mobile,parlour_name,ActualTime,crew_pic;

    public String getCrew_pic(int position) {
        return crew_pic;
    }

    public void setCrew_pic(String crew_pic) {
        this.crew_pic = crew_pic;
    }

    public int getID(int position) {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getNoofItems(int position) {
        return NoofItems;
    }

    public void setNoofItems(int noofItems) {
        NoofItems = noofItems;
    }

    public int getOTP(int position) {
        return OTP;
    }

    public void setOTP(int OTP) {
        this.OTP = OTP;
    }

    public double getDiscount(int position) {
        return Discount;
    }

    public void setDiscount(double discount) {
        Discount = discount;
    }

    public double getPayable(int position) {
        return Payable;
    }

    public void setPayable(double payable) {
        Payable = payable;
    }

    public String getAddressd(int position) {
        return addressd;
    }

    public void setAddressd(String addressd) {
        this.addressd = addressd;
    }

    public String getHouseno(int position) {
        return houseno;
    }

    public void setHouseno(String houseno) {
        this.houseno = houseno;
    }

    public String getLandmark(int position) {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getOrderDate(int position) {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }

    public String getCrew_name(int position) {
        return crew_name;
    }

    public void setCrew_name(String crew_name) {
        this.crew_name = crew_name;
    }

    public String getSlot(int position) {
        return Slot;
    }

    public void setSlot(String slot) {
        Slot = slot;
    }

    public int getIDserviceAt(int position) {
        return IDserviceAt;
    }

    public void setIDserviceAt(int IDserviceAt) {
        this.IDserviceAt = IDserviceAt;
    }

    public String getPhoto(int position) {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getUserName(int position) {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMobile(int position) {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getPPhoto(int position) {
        return PPhoto;
    }

    public void setPPhoto(String PPhoto) {
        this.PPhoto = PPhoto;
    }

    public String getParlour_mobile(int position) {
        return parlour_mobile;
    }

    public void setParlour_mobile(String parlour_mobile) {
        this.parlour_mobile = parlour_mobile;
    }

    public String getParlour_name(int position) {
        return parlour_name;
    }

    public void setParlour_name(String parlour_name) {
        this.parlour_name = parlour_name;
    }

    public String getActualTime(int position) {
        return ActualTime;
    }

    public void setActualTime(String actualTime) {
        ActualTime = actualTime;
    }

    public int getIsAccepted(int position) {
        return isAccepted;
    }

    public void setIsAccepted(int isAccepted) {
        this.isAccepted = isAccepted;
    }

    public int getIsRunning(int position) {
        return isRunning;
    }

    public void setIsRunning(int isRunning) {
        this.isRunning = isRunning;
    }

    public int getIsCompleted(int position) {
        return isCompleted;
    }

    public void setIsCompleted(int isCompleted) {
        this.isCompleted = isCompleted;
    }

    public int getIsCancelled(int position) {
        return isCancelled;
    }

    public void setIsCancelled(int isCancelled) {
        this.isCancelled = isCancelled;
    }

    public double getParlour_latitude(int position) {
        return Parlour_latitude;
    }

    public void setParlour_latitude(double parlour_latitude) {
        Parlour_latitude = parlour_latitude;
    }

    public double getParlour_longitude(int position) {
        return Parlour_longitude;
    }

    public void setParlour_longitude(double parlour_longitude) {
        Parlour_longitude = parlour_longitude;
    }

    public double getHome_latitude(int position) {
        return Home_latitude;
    }

    public void setHome_latitude(double home_latitude) {
        Home_latitude = home_latitude;
    }

    public double getHome_longitude(int position) {
        return Home_longitude;
    }

    public void setHome_longitude(double home_longitude) {
        Home_longitude = home_longitude;
    }
}
