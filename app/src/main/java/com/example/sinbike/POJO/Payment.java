package com.example.sinbike.POJO;

import com.example.sinbike.Repositories.common.Model;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.ServerTimestamp;

public class Payment extends Model {
    private @ServerTimestamp Timestamp paymentDate;
    private double totalAmount;
    private String transactionId;

    public Payment() {
    }

    public Payment(Timestamp paymentDate, double totalAmount, String transactionId) {
        this.paymentDate = paymentDate;
        this.totalAmount = totalAmount;
        this.transactionId = transactionId;
    }

    public String getTransactionid() {
        return transactionId;
    }

    public void setTransactionid(String transactionId) {
        this.transactionId = transactionId;
    }

    public Timestamp getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Timestamp paymentDate) {
        this.paymentDate = paymentDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentDate=" + paymentDate +
                ", totalAmount=" + totalAmount +
                ", id='" + id + '\'' +
                '}';
    }
}

