package com.zanrite.groomme.Models;

public class service {
    private int ID,ParlourID,NoofItems;
    private String pService,Quotes,Photo,SecondID;
    private Float Price,Discount,FinalPrice;

    public int getNoofItems(int position) {
        return NoofItems;
    }

    public void setNoofItems(int noofItems) {
        NoofItems = noofItems;
    }

    public String getSecondID(int position) {
        return SecondID;
    }

    public void setSecondID(String secondID) {
        SecondID = secondID;
    }

    public int getID(int position) {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getParlourID(int position) {
        return ParlourID;
    }

    public void setParlourID(int parlourID) {
        ParlourID = parlourID;
    }

    public String getpService(int position) {
        return pService;
    }

    public void setpService(String pService) {
        this.pService = pService;
    }

    public String getQuotes(int position) {
        return Quotes;
    }

    public void setQuotes(String quotes) {
        Quotes = quotes;
    }

    public String getPhoto(int position) {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public Float getPrice(int position) {
        return Price;
    }

    public void setPrice(Float price) {
        Price = price;
    }

    public Float getDiscount(int position) {
        return Discount;
    }

    public void setDiscount(Float discount) {
        Discount = discount;
    }

    public Float getFinalPrice(int position) {
        return FinalPrice;
    }

    public void setFinalPrice(Float finalPrice) {
        FinalPrice = finalPrice;
    }
}
