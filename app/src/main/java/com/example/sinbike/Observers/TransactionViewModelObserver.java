package com.example.sinbike.Observers;

import com.example.sinbike.POJO.Transaction;
import java.util.List;

public interface TransactionViewModelObserver {
    public void showAllTransaction(List<Transaction> transactionList);
}
