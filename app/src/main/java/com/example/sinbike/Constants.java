package com.example.sinbike;

public class Constants {

    /*-------------------------- Fire store --------------------------*/
    public static final String COLLECTION_ACCOUNT = "Account";
    public static final String COLLECTION_BICYCLE = "Bicycle";
    public static final String COLLECTION_FAULT = "Fault";
    public static final String COLLECTION_FINE = "Fine";
    public static final String COLLECTION_FINE_PAYMENT = "FinePayment";
    public static final String COLLECTION_PARKING_LOT = "ParkingLot";
    public static final String COLLECTION_PAYMENT = "Payment";
    public static final String COLLECTION_RENTAL = "Rental";
    public static final String COLLECTION_RENTAL_PAYMENT = "RentalPayment";
    public static final String COLLECTION_RESERVATION = "Reservation";
    public static final String COLLECTION_TRANSACTION = "Transaction";

    /*-------------------------- Card Type --------------------------*/
    public static final int CARD_VISA = 1;
    public static final int CARD_MASTER = 2;
    public static final int CARD_AMEX = 3;

    /*-------------------------- Account Status --------------------------*/
    public static final int ACCOUNT_CLOSED = 0;
    public static final int ACCOUNT_OPEN = 1;
    public static final int ACCOUNT_SUSPENDED = 2;

    /*-------------------------- Transaction Type --------------------------*/
    public static final int TRANSACTION_TYPE_TOPUP = 0;
    public static final int TRANSACTION_TYPE_USE = 1;
}
