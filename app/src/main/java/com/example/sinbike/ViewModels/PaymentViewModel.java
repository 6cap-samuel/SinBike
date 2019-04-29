package com.example.sinbike.ViewModels;

import android.app.Application;
import com.example.sinbike.POJO.FinePayment;
import com.example.sinbike.POJO.Payment;
import com.example.sinbike.Repositories.Firestore.Resource;
import com.example.sinbike.Repositories.common.CompletionLiveData;
import com.example.sinbike.Repositories.common.QueryLiveData;
import com.example.sinbike.Services.FinePaymentService;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class PaymentViewModel extends AndroidViewModel {

    private static final String TAG = "PaymentViewModel";
    private FinePaymentService finePaymentService;
    private String accountId;

    public PaymentViewModel(Application application){
        super(application);
        this.finePaymentService = new FinePaymentService(application);
    }

    public void setAccountId(String accountId){
        this.accountId = accountId;
    }

    public QueryLiveData<Payment> getAllPayment(String accountId) {
        return this.finePaymentService.getAllPayment(accountId);
    }

    public CompletionLiveData createFinePayment(FinePayment fine){
        return this.finePaymentService.createPayment(fine);
    }

    public LiveData<Resource<Boolean>> updateFinePayment(String docId, FinePayment fine){
        return this.finePaymentService.updatePayment(docId, fine);
    }

    public LiveData<Resource<Boolean>> deleteFinePayment(String docId){
        return this.finePaymentService.deletePayment(docId);
    }
}