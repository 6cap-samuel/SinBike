package com.example.sinbike.POJO;

public class FinePayment extends Payment {
    private String fineid;

    public FinePayment() {
    }

    public FinePayment(String fineid) {
        this.fineid = fineid;
    }

    public String getFineid() {
        return fineid;
    }

    public void setFineid(String fineid) {
        this.fineid = fineid;
    }
}
