package com.zanrite.groomme.Models;

public class servicemodel {
    private int ID;
    private  String primaryservice,primaryimage;
    
    
  

    public int getID(int position) {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getPrimaryservice(int position) {
        return primaryservice;
    }

    public void setPrimaryservice(String primaryservice) {
        this.primaryservice = primaryservice;
    }

    public String getPrimaryimage(int position) {
        return primaryimage;
    }

    public void setPrimaryimage(String primaryimage) {
        this.primaryimage = primaryimage;
    }
}
