package com.example.sinbike.ViewModels;

import android.app.Application;
import android.util.Log;

import com.example.sinbike.Observers.ParkingLotViewModelObserver;
import com.example.sinbike.Observers.TransactionViewModelObserver;
import com.example.sinbike.POJO.Bicycle;
import com.example.sinbike.POJO.ParkingLot;
import com.example.sinbike.POJO.Transaction;
import com.example.sinbike.Services.BicycleService;
import com.example.sinbike.Services.ParkingLotService;
import com.example.sinbike.Services.TransactionService;
import com.google.firebase.firestore.GeoPoint;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

public class TransactionViewModel extends AndroidViewModel
{
    private static final String TAG = "TransactionViewModel";

    private LifecycleOwner lifecycleOwner;
    private TransactionService transactionService;
    private TransactionViewModelObserver observer;
    private String accountId;

    public TransactionViewModel(Application application){
        super(application);
        this.transactionService = new TransactionService(application);
    }

    public void setObserver(TransactionViewModelObserver observer) {
        this.observer = observer;
    }

    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner;
    }

    public void setAccountId(String accountId){
        this.accountId = accountId;
    }

    public void getAllTransactionList(){
        final LiveData<com.example.sinbike.Repositories.common.Resource<List<Transaction>>> liveobs = this.transactionService.getAllTransactionOfUser(accountId);
        Observer obs = new Observer<com.example.sinbike.Repositories.common.Resource<List<Transaction>>>(){
            @Override
            public void onChanged(com.example.sinbike.Repositories.common.Resource<List<Transaction>> transactionList) {
                List<Transaction> transactions = transactionList.data();

                Log.d(TAG, transactions.toString());
                observer.showAllTransaction(transactions);
            }
        };
        liveobs.observe(this.lifecycleOwner, obs);
    }
}
