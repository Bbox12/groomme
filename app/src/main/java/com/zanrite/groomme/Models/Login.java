package com.zanrite.groomme.Models;

public class Login {

    private String Name, Email, _Phone_no, Institute, Photo, Gender, Place, Bday, Password;
    private int ID;



    public int getID(int position) {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getPassword(int position) {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getName(int position) {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail(int position) {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String get_Phone_no(int position) {
        return _Phone_no;
    }

    public void set_Phone_no(String phn) {
        _Phone_no = phn;
    }

    public String getInstitute(int position) {
        return Institute;
    }

    public void setInstitute(String institute) {
        Institute = institute;
    }

    public String getPhoto(int position) {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getGender(int position) {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getPlace(int position) {
        return Place;
    }

    public void setPlace(String place) {
        Place = place;
    }

    public String getBday(int position) {
        return Bday;
    }

    public void setBday(String bday) {
        Bday = bday;
    }
}

