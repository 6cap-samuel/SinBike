package com.example.sinbike.POJO;

import com.example.sinbike.Repositories.common.Model;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.ServerTimestamp;

public class Payment extends Model {
    private @ServerTimestamp Timestamp paymentDate;
    private double totalAmount;

    public Payment() {
    }

    public Payment(Timestamp paymentDate, double totalAmount) {
        this.paymentDate = paymentDate;
        this.totalAmount = totalAmount;
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

