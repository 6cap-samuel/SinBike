package com.example.sinbike.POJO;

import com.example.sinbike.Repositories.common.Model;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.ServerTimestamp;

public class Fine extends Model {

    private String accountId;
    private String fineDate;
    private double amount;
    private String location;

    public Fine() {
    }

    public Fine(String accountId, String fineDate, double amount, String location) {
        this.accountId = accountId;
        this.fineDate = fineDate;
        this.amount = amount;
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getFineDate() {
        return fineDate;
    }

    public void setFineDate(String fineDate) {
        this.fineDate = fineDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Fine{" +
                "accountId='" + accountId + '\'' +
                ", fineDate=" + fineDate +
                ", amount=" + amount +
                ", id='" + id + '\'' +
                '}';
    }
}
