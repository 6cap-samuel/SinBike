package com.example.sinbike.Activities;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Item {
    public String bicycleID;
    public String accountID;
    public String fineDate;
    public String fineLocation;
    public String fineTime;
    public String fineAmount;

    public String getBicycleID(){
        return bicycleID;
    }

    public String getAccountID(){
        return accountID;
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
}
