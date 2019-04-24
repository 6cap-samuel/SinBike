package com.example.sinbike.POJO;

public class Card {

    private final int CARD_VISA = 1;
    private final int CARD_MASTER = 2;
    private final int CARD_AMEX = 3;

    private String cardType;
    private String cardNumber;
    private String expiryMonth;
    private String expiryYear;

    public Card(String cardType, String cardNumber, String expiryMonth, String expiryYear) {
        this.cardType = cardType;
        this.cardNumber = cardNumber;
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpiryMonth() {
        return expiryMonth;
    }

    public void setExpiryMonth(String expiryMonth) {
        this.expiryMonth = expiryMonth;
    }

    public String getExpiryYear() {
        return expiryYear;
    }

    public void setExpiryYear(String expiryYear) {
        this.expiryYear = expiryYear;
    }
}
