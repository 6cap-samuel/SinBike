package com.example.sinbike.POJO;

import com.example.sinbike.Repositories.common.Model;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.ServerTimestamp;

public class Fine extends Model {

    private String accountId;
    private @ServerTimestamp Timestamp fineDate;
    private double amount;

    public Fine() {
    }

    public Fine(String accountId, Timestamp fineDate, double amount) {
        this.accountId = accountId;
        this.fineDate = fineDate;
        this.amount = amount;
    }


    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Timestamp getFineDate() {
        return fineDate;
    }

    public void setFineDate(Timestamp fineDate) {
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
