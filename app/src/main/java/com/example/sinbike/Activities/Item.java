package com.example.sinbike.Activities;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties

public class Item implements Serializable {

    private static final long serialVersionUID = 1L;

    private String bicycleID;
    private String accountID;
    private String accountBalance;
    private String fineDate;
    private String fineLocation;
    private String fineTime;
    private String fineAmount;
    private String fineTitle;
    private boolean isSelected;

    public Item() {

    }

    public Item(String fineTitle, String fineDate, String fineLocation, String fineTime, String fineAmount, boolean isSelected) {

        this.fineTitle = fineTitle;
        this.fineDate = fineDate;
        this.fineLocation = fineLocation;
        this.fineTime = fineTime;
        this.fineAmount = fineAmount;
        this.isSelected = isSelected;

    }

    public String getFineTitle(){
        return fineTitle;
    }

    public String getBicycleID(){
        return bicycleID;
    }

    public String getAccountID(){
        return accountID;
    }

    public String getAccountBalance(){
        return accountBalance;
    }

    public String getFineDate(){
        return fineDate;
    }

    public String getFineLocation(){
        return fineLocation;
    }

    public String getFineTime(){
        return fineTime;
    }

    public String getFineAmount(){
        return fineAmount;
    }

    public void setFineTitle(String fineTitle) {
        this.fineTitle = fineTitle;
    }

    public void setFineDate(String fineDate) {
        this.fineDate = fineDate;
    }

    public void setFineLocation(String fineLocation) {
        this.fineLocation = fineLocation;
    }

    public void setFineTime(String fineTime) {
        this.fineTime = fineTime;
    }

    public void setFineAmount(String fineAmount) {
        this.fineAmount = fineAmount;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}