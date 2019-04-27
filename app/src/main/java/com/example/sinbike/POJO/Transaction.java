package com.example.sinbike.POJO;

import com.example.sinbike.Repositories.common.Model;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ServerTimestamp;

import org.w3c.dom.Document;

import static com.example.sinbike.Constants.COLLECTION_ACCOUNT;

public class Transaction extends Model {
    private String description;
    private @ServerTimestamp Timestamp dateOfReporting;
    private String accountId;
    private int transactionType;

    public Transaction() {
    }

    public Transaction(String description, Timestamp dateOfReporting, String accountId, int transactionType) {
        this.description = description;
        this.dateOfReporting = dateOfReporting;
        this.accountId = accountId;
        this.transactionType = transactionType;
    }

    public int getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(int transactionType) {
        this.transactionType = transactionType;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getDateOfReporting() {
        return dateOfReporting;
    }

    public void setDateOfReporting(Timestamp dateOfReporting) {
        this.dateOfReporting = dateOfReporting;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "description='" + description + '\'' +
                ", dateOfReporting=" + dateOfReporting +
                ", id='" + id + '\'' +
                '}';
    }
}
