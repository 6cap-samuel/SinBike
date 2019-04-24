package com.example.sinbike.Services;

import android.app.Application;

import com.example.sinbike.POJO.Account;
import com.example.sinbike.Repositories.AccountRepository;
import com.example.sinbike.Repositories.Firestore.Resource;
import com.example.sinbike.Repositories.common.CompletionLiveData;
import com.example.sinbike.Repositories.common.QueryLiveData;

import androidx.lifecycle.LiveData;

public class AccountService {
    private static final String TAG = "AccountService";
    private AccountRepository accountRepository;

    public AccountService(Application application, String userId){
        this.accountRepository = new AccountRepository(application, userId);
    }

    public QueryLiveData<Account> login(String username, String password){
        return this.accountRepository.login(username, password);
    }

    public CompletionLiveData create(Account account){
        return this.accountRepository.createAccount(account);
    }

    public LiveData<Resource<Boolean>> update(String docId, Account account){
        return this.accountRepository.update(docId, account);
    }

    public LiveData<Resource<Boolean>> delete(String docId){
        return this.accountRepository.delete(docId);
    }

    public LiveData<Resource<Boolean>> updateAccountStatus(String docId, Account account, int accountStatus){
        account.setStatus(accountStatus);
        return this.accountRepository.update(docId, account);
    }

    public LiveData<Resource<Boolean>> updateBillingAddress(String docId, Account account, String address){
        account.setAddress(address);
        return this.accountRepository.update(docId, account);
    }
}
