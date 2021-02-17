package com.zanrite.groomme.Models;

public class Slots {

    private int ID;
    private String Slots,ActualTime;
    private boolean isSelected;

    public boolean isSelected(int position) {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getID(int position) {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getSlots(int position) {
        return Slots;
    }

    public void setSlots(String slots) {
        Slots = slots;
    }

    public String getActualTime(int position) {
        return ActualTime;
    }

    public void setActualTime(String actualTime) {
        ActualTime = actualTime;
    }
}
