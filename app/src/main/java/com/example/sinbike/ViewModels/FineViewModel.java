package com.example.sinbike.ViewModels;

import android.app.Application;
import com.example.sinbike.POJO.Fine;
import com.example.sinbike.Repositories.Firestore.Resource;
import com.example.sinbike.Repositories.common.CompletionLiveData;
import com.example.sinbike.Repositories.common.QueryLiveData;
import com.example.sinbike.Services.FineService;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class FineViewModel extends AndroidViewModel {
    private static final String TAG = "FineViewModel";
    private FineService fineService;

    private String accountId;

    public FineViewModel(Application application){
        super(application);
        this.fineService = new FineService(application);
    }

    public void setAccountId(String accountId){
        this.accountId = accountId;
    }

    public QueryLiveData<Fine> getAllFine(String accountId) {
        return this.fineService.getAllFine(accountId);
    }

    public CompletionLiveData createFine(Fine fine){
        return this.fineService.createFine(fine);
    }

    public LiveData<Resource<Boolean>> updateFine(String docId, Fine fine){
        return this.fineService.updateFine(docId, fine);
    }

    public LiveData<Resource<Boolean>> deleteFine(String docId){
        return this.fineService.deleteFine(docId);
    }
}