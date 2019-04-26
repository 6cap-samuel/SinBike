package com.example.sinbike.ViewModels;

import android.app.Application;
import android.util.Log;

import com.example.sinbike.Observers.AccountViewModelObserver;
import com.example.sinbike.POJO.Account;
import com.example.sinbike.Repositories.Firestore.Resource;
import com.example.sinbike.Services.AccountService;

import java.util.List;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

public class AccountViewModel extends AndroidViewModel {

    private static final String TAG = "AccountViewModel";

    private LifecycleOwner lifecycleOwner;
    private AccountService accountService;
    private AccountViewModelObserver observer;

    public AccountViewModel(Application application){
        super(application);
        this.accountService = new AccountService(application);
    }

    public AccountViewModel(Application application, String userId) {
        super(application);
        this.accountService = new AccountService(application, userId);
    }

    public void setObserver(AccountViewModelObserver observer) {
        this.observer = observer;
    }

    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner;
    }

    public void loginAccount(String email, String password){
        final LiveData<com.example.sinbike.Repositories.common.Resource<List<Account>>> liveobs = this.accountService.login(email, password);
        Observer obs = new Observer<com.example.sinbike.Repositories.common.Resource<List<Account>>>(){
            @Override
            public void onChanged(com.example.sinbike.Repositories.common.Resource<List<Account>> listResource) {

                List<Account> accounts = listResource.data();
                Log.d(TAG, accounts.toString());

                if (accounts.size() != 0){
                    observer.loginSuccess(accounts.get(0));
                } else {
                    observer.loginFailed();
                }

                liveobs.removeObserver(this);
            }
        };
        liveobs.observe(this.lifecycleOwner, obs);
    }

    public void createAccount(Account account){
        if (createAccountValidation()){
            final LiveData<com.example.sinbike.Repositories.common.Resource<List<Account>>> liveobs = this.accountService.login(account.getEmail(), account.getPassword());
            Observer obs = new Observer<com.example.sinbike.Repositories.common.Resource<List<Account>>>(){
                @Override
                public void onChanged(com.example.sinbike.Repositories.common.Resource<List<Account>> listResource) {

                    List<Account> accounts = listResource.data();
                    Log.d(TAG, accounts.toString());

                    if (listResource.data().size() == 0){
                        observer.createAccountPass();
                    } else {
                        observer.createAccountFail();
                    }
                    liveobs.removeObserver(this);
                }
            };
            liveobs.observe(this.lifecycleOwner, obs);
        }
    }

    public boolean createAccountValidation(){
        // TODO: Create your own validation here.
        return true;
    }

    public LiveData<Resource<Boolean>> updateAccountStatus(String docId, Account account, int accountStatus){
        return this.accountService.updateAccountStatus(docId, account, accountStatus);
    }

    public LiveData<Resource<Boolean>> updateBillingAddress(String docId, Account account, String address){
        return this.accountService.updateBillingAddress(docId, account, address);
    }
}
