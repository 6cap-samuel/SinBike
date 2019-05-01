package com.example.sinbike.POJO;

import com.example.sinbike.Constants;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Card {

    @PrimaryKey
    private int cardId;
    private int cardType;
    private String cardNumber;
    private String expiryMonth;
    private String expiryYear;

    public Card(){
        this.cardType = Constants.CARD_VISA;
        this.cardNumber = "";
        this.expiryMonth = "";
        this.expiryYear = "";
    }

    @Ignore
    public Card(int cardType, String cardNumber, String expiryMonth, String expiryYear) {
        this.cardType = cardType;
        this.cardNumber = cardNumber;
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public int getCardType() {
        return cardType;
    }

    public void setCardType(int cardType) {
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
