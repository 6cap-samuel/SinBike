package com.example.sinbike.POJO;

import com.example.sinbike.Repositories.common.Model;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.ServerTimestamp;

public class Account extends Model {

    private static final int ACCOUNT_CLOSED = 0;
    private static final int ACCOUNT_OPEN = 1;
    private static final int ACCOUNT_SUSPENDED = 2;

    private String name;
    private String email;
    private String telephoneNumber;
    private String address;
    private String gender;
    private @ServerTimestamp Timestamp dateOfBirth;
    private String billingAddress;
    private int status;
    private double accountBalance;

    public Account() {
    }

    public Account(String name, String email, String telephoneNumber, String address, String gender, Timestamp dateOfBirth, String billingAddress, int status, double accountBalance) {
        this.name = name;
        this.email = email;
        this.telephoneNumber = telephoneNumber;
        this.address = address;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.billingAddress = billingAddress;
        this.status = status;
        this.accountBalance = accountBalance;
    }

    public static int getAccountClosed() {
        return ACCOUNT_CLOSED;
    }

    public static int getAccountOpen() {
        return ACCOUNT_OPEN;
    }

    public static int getAccountSuspended() {
        return ACCOUNT_SUSPENDED;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Timestamp getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Timestamp dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", telephoneNumber='" + telephoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", gender='" + gender + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", billingAddress='" + billingAddress + '\'' +
                ", status=" + status +
                ", accountBalance=" + accountBalance +
                '}';
    }
}
