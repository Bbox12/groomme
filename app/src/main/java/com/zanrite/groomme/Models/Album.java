package com.zanrite.groomme.Models;

/**
 * Created by Ashok on 07/11/2016.
 */
public class Album {
    private String name;
    private String Detail;
    private String relation;
    private String thumbnailUrl;
    private int position;
    private String email;
    private int duration;
    private float price;
    private float Discount;
    private int available;
    private String service;
    private String category;
    private String first_image;
    private String second_image;
    private String third_image;
    private String note1,note2,note3;
    private String Date_,Open_time,Closing_time,Lunch_time,Open_close;
    private String Rate_i,Rate_t,Rate_s;
    private String Attended;
    private String Experts;
    private String mobileno;
    private String qoutes;
    private String Unique_Id;
    private String parlours;
    private String address;
    private String Discount_1,Discount_2,Discount_3;
    private String Date;
    private int ID;
    private Double FinalPrice;
    private boolean isSelected;

    public boolean isSelected(int position) {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public Double getFinalPrice(int position) {
        return FinalPrice;
    }

    public void setFinalPrice(Double finalPrice) {
        FinalPrice = finalPrice;
    }

    public int getID(int position) {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDiscount_1(int position) {
        return Discount_1;
    }

    public void setDiscount_1(String discount_1) {
        Discount_1 = discount_1;
    }

    public String getDiscount_2(int position) {
        return Discount_2;
    }

    public void setDiscount_2(String discount_2) {
        Discount_2 = discount_2;
    }

    public String getDiscount_3(int position) {
        return Discount_3;
    }

    public void setDiscount_3(String discount_3) {
        Discount_3 = discount_3;
    }

    public String getExperts(int position) {
        return Experts;
    }

    public void setExperts(String experts) {
        Experts = experts;
    }

    public String getmobileno(int position) {
        return mobileno;
    }

    public void setmobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getQoutes(int position) {
        return qoutes;
    }

    public void setQoutes(String qoutes) {
        this.qoutes = qoutes;
    }

    public String getUnique_Id(int position) {
        return Unique_Id;
    }

    public void setUnique_Id(String unique_Id) {
        Unique_Id = unique_Id;
    }

    public String getParlours(int position) {
        return parlours;
    }

    public void setParlours(String parlours) {
        this.parlours = parlours;
    }

    public String getAddress(int position) {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFirst_image(int position) {
        return first_image;
    }

    public void setFirst_image(String first_image) {
        this.first_image = first_image;
    }

    public String getSecond_image(int position) {
        return second_image;
    }

    public void setSecond_image(String second_image) {
        this.second_image = second_image;
    }

    public String getThird_image(int position) {
        return third_image;
    }

    public void setThird_image(String third_image) {
        this.third_image = third_image;
    }

    public String getNote2(int position) {
        return note2;
    }

    public void setNote2(String note2) {
        this.note2 = note2;
    }

    public String getNote1(int position) {
        return note1;
    }

    public void setNote1(String note1) {
        this.note1 = note1;
    }

    public String getNote3(int position) {
        return note3;
    }

    public void setNote3(String note3) {
        this.note3 = note3;
    }

    public String getDate_(int position) {
        return Date_;
    }

    public void setDate_(String date) {
        Date_ = date;
    }

    public String getOpen_time(int position) {
        return Open_time;
    }

    public void setOpen_time(String open_time) {
        Open_time = open_time;
    }

    public String getClosing_time(int position) {
        return Closing_time;
    }

    public void setClosing_time(String closing_time) {
        Closing_time = closing_time;
    }

    public String getLunch_time(int position) {
        return Lunch_time;
    }

    public void setLunch_time(String lunch_time) {
        Lunch_time = lunch_time;
    }

    public String getOpen_close(int position) {
        return Open_close;
    }

    public void setOpen_close(String open_close) {
        Open_close = open_close;
    }

    public String getRate_i(int position) {
        return Rate_i;
    }

    public void setRate_i(String rate_i) {
        Rate_i = rate_i;
    }

    public String getRate_t(int position) {
        return Rate_t;
    }

    public void setRate_t(String rate_t) {
        Rate_t = rate_t;
    }

    public String getRate_s(int position) {
        return Rate_s;
    }

    public void setRate_s(String rate_s) {
        Rate_s = rate_s;
    }

    public String getAttended(int position) {
        return Attended;
    }

    public void setAttended(String attended) {
        Attended = attended;
    }

    public String getService(int position) {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getCategory(int position) {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getAvailable(int position) {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public float getDiscount(float position) {
        return Discount;
    }

    public void setDiscount(float discount) {
        Discount = discount;
    }

    public float getPrice(int position) {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getDuration(int position) {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getEmail(int position) {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDetail(int position) {
        return Detail;
    }

    public void setDetail(String detail) {
        Detail = detail;
    }

    public int getPosition(int position) {
        return this.position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getName(int position) {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnailUrl(int position) {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getRelation(int position) {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getDate(int position) {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
