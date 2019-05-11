package com.example.sinbike.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.sinbike.Observers.BicycleViewModelObserver;
import com.example.sinbike.Observers.ParkingLotViewModelObserver;
import com.example.sinbike.Observers.TransactionViewModelObserver;
import com.example.sinbike.POJO.Account;
import com.example.sinbike.POJO.Bicycle;
import com.example.sinbike.POJO.Fine;
import com.example.sinbike.POJO.FinePayment;
import com.example.sinbike.POJO.ParkingLot;
import com.example.sinbike.POJO.Payment;
import com.example.sinbike.POJO.Transaction;
import com.example.sinbike.R;
import com.example.sinbike.Services.TransactionService;
import com.example.sinbike.ViewModels.AccountViewModel;
import com.example.sinbike.ViewModels.BicycleViewModel;
import com.example.sinbike.ViewModels.FaultViewModel;
import com.example.sinbike.ViewModels.FineViewModel;
import com.example.sinbike.ViewModels.ParkingLotViewModel;
import com.example.sinbike.ViewModels.TransactionViewModel;

import java.util.List;

public class SplashActivity extends AppCompatActivity implements BicycleViewModelObserver, ParkingLotViewModelObserver, TransactionViewModelObserver {

    private static final String TAG = "SplashActivity";

    AccountViewModel accountViewModel;
    FaultViewModel faultViewModel;
    BicycleViewModel bicycleViewModel;
    ParkingLotViewModel parkingLotviewModel;
    TransactionViewModel transactionViewModel;
    FineViewModel fineViewModel;

    Account account;

    private final int SPLASH_DISPLAY_LENGTH = 3000;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        this.handleSplashScreen();

//        this.init();
    }

    public void init(){
//        this.accountTest();
//        this.bicycleTest();
//        this.parkingLotTest();
        this.fineTest();

//        this.transactionTest();
    }

    public void fineTest(){
        this.fineViewModel = ViewModelProviders.of(this).get(FineViewModel.class);
        Fine fine = new Fine();
        fine.setAccountId("v7XY8cNZQxlii3geNg52");
        this.fineViewModel.createFine(fine);
    }

    public void paymentTest(){
        Payment p = new FinePayment();
        p.setTransactionid("06OJfmAODevkKth3mDP4");
        ((FinePayment) p).setFineid("CzVH1GmvqKupYxWWLe7g");


    }


    public void transactionTest(){

        TransactionService transactionService = new TransactionService(this.getApplication());

        for(int i = 0; i < 20; i++){
            Transaction t = new Transaction();
           // t.setDescription(String.valueOf(i));
            t.setAccountId("v7XY8cNZQxlii3geNg52");

            transactionService.create(t);
        }

        this.transactionViewModel = ViewModelProviders.of(this).get(TransactionViewModel.class);
        this.transactionViewModel.setLifecycleOwner(this);
        this.transactionViewModel.setObserver(this);
        this.transactionViewModel.setAccountId("v7XY8cNZQxlii3geNg52");

        this.transactionViewModel.getAllTransactionList();
    }

    public void accountTest(){
        this.accountViewModel = ViewModelProviders.of(this).get(AccountViewModel.class);
        this.accountViewModel.setLifecycleOwner(this);
//        this.accountViewModel.setObserver(this);

        // CREATION of Account DONE
        Account account = new Account();
        account.setEmail("gg4@gg.com");
        account.setPassword("hello w0r!d");
        this.accountViewModel.createAccount(account);

        String workingDocId = "v7XY8cNZQxlii3geNg52";
        this.accountViewModel.loginAccount("gg4@gg.com", "hello w0r!d");

        // Find all the Account Statuses in Constants Class
    }

    public void parkingLotTest(){
        this.parkingLotviewModel = ViewModelProviders.of(this).get(ParkingLotViewModel.class);
        this.parkingLotviewModel.setLifecycleOwner(this);
        this.parkingLotviewModel.setObserver(this);

        this.parkingLotviewModel.getAllParkingLots();
    }

    public void bicycleTest(){
        this.bicycleViewModel = ViewModelProviders.of(this).get(BicycleViewModel.class);
        this.bicycleViewModel.setLifecycleOwner(this);
        this.bicycleViewModel.setObserver(this);

        this.bicycleViewModel.getAllBicycle();
    }

    public void createAccountPass(){
        // TODO: implement function when account creation passed.
        // TODO: Encourage user to login once account has been created.
        Log.d(TAG,"Account Creation Pass");


    }

    public void createAccountFail(){
        // TODO: implement function when account creation failed.
        Log.d(TAG,"Account Creation Fail");
    }

    public void handleSplashScreen(){
                new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent mainIntent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    @Override
    public void showBicycleList(List<Bicycle> bicycleList) {
        // TODO: this will show the list of bicycle that you have around Singapore.
        Log.d(TAG, "Bicycle List");
        Log.d(TAG, bicycleList.toString());
    }

    @Override
    public void showParkingLotList(List<ParkingLot> parkingLotList) {
        Log.d(TAG, "Parking Lot List");
        Log.d(TAG, parkingLotList.toString());
    }

    @Override
    public void showAllTransaction(List<Transaction> transactionList) {
        // TODO : this function will show the list of transaction fo tne user.
        Log.d(TAG, "Transaction List");
        Log.d(TAG, transactionList.toString());
    }
}

