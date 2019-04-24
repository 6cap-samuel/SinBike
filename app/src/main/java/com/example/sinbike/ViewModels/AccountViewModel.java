package com.example.sinbike.ViewModels;

import android.app.Application;

import com.example.sinbike.POJO.Account;
import com.example.sinbike.Repositories.Firestore.Resource;
import com.example.sinbike.Repositories.common.CompletionLiveData;
import com.example.sinbike.Repositories.common.QueryLiveData;
import com.example.sinbike.Services.AccountService;

import androidx.lifecycle.LiveData;

public class AccountViewModel {

    private AccountService accountService;

    public AccountViewModel(Application application, String userId){
        this.accountService = new AccountService(application, userId);
    }

    public QueryLiveData<Account> login(String username, String password){
        return this.accountService.login(username, password);
    }

    public CompletionLiveData create(Account account){
        return this.accountService.create(account);
    }

    public LiveData<Resource<Boolean>> update(String docId, Account account){
        return this.accountService.update(docId, account);
    }

    public LiveData<Resource<Boolean>> delete(String docId){
        return this.accountService.delete(docId);
    }

    public LiveData<Resource<Boolean>> updateAccountStatus(String docId, Account account, int accountStatus){
        return this.accountService.updateAccountStatus(docId, account, accountStatus);
    }

    public LiveData<Resource<Boolean>> updateBillingAddress(String docId, Account account, String address){
        return this.accountService.updateBillingAddress(docId, account, address);
    }
}
