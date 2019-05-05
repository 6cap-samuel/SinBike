package com.example.sinbike.ViewModels;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.sinbike.Observers.LoginObserver;
import com.example.sinbike.Observers.SignUpObserver;
import com.example.sinbike.POJO.Account;
import com.example.sinbike.Repositories.Firestore.Resource;
import com.example.sinbike.Services.AccountService;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class AccountViewModel extends AndroidViewModel {

    private static final String TAG = "AccountViewModel";

    private LifecycleOwner lifecycleOwner;
    private AccountService accountService;
    private LoginObserver loginObserver;
    private SignUpObserver signUpObserver;

    private static Account account;

    public AccountViewModel(Application application){
        super(application);
        this.accountService = new AccountService(application);
    }

    public AccountViewModel(Application application, String userId) {
        super(application);
        this.accountService = new AccountService(application, userId);
    }

    public void setLoginObserver(LoginObserver observer){
        this.loginObserver = observer;
    }

    public void setSignUpObserver(SignUpObserver observer){
        this.signUpObserver = observer;
    }

    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner;
    }

    public LiveData<com.example.sinbike.Repositories.common.Resource<List<Account>>> getAllAccount(){
        final LiveData<com.example.sinbike.Repositories.common.Resource<List<Account>>> liveobs = this.accountService.getAllBicycle();
        return liveobs;
    }

    public Account getAccount(){
        return this.account;
    }

    public void loginAccount(String email, String password){
        final LiveData<com.example.sinbike.Repositories.common.Resource<List<Account>>> liveobs = this.accountService.login(email, password);
        Observer obs = new Observer<com.example.sinbike.Repositories.common.Resource<List<Account>>>(){
            @Override
            public void onChanged(com.example.sinbike.Repositories.common.Resource<List<Account>> listResource) {

                List<Account> accounts = listResource.data();
                Log.d(TAG, accounts.toString());


                if (accounts.size() != 0){
                    account = accounts.get(0);
                    loginObserver.loginSuccess(accounts.get(0));
                } else {
                    loginObserver.loginFailed();
                }

                liveobs.removeObserver(this);
            }
        };
        liveobs.observe(this.lifecycleOwner, obs);
    }

    public void createAccount(Account account){
        if (createAccountValidation()){
            final LiveData<com.example.sinbike.Repositories.common.Resource<List<Account>>> emailExist = this.accountService.checkEmail(account.getEmail());

            Observer checkObs = new Observer<com.example.sinbike.Repositories.common.Resource<List<Account>>>() {
                @Override
                public void onChanged(com.example.sinbike.Repositories.common.Resource<List<Account>> listResource) {
                    if (listResource.data().size() == 0){
                        final LiveData<com.example.sinbike.Repositories.common.Resource<List<Account>>> liveobs = accountService.login(account.getEmail(), account.getPassword());
                        Observer obs = new Observer<com.example.sinbike.Repositories.common.Resource<List<Account>>>(){
                            @Override
                            public void onChanged(com.example.sinbike.Repositories.common.Resource<List<Account>> listResource) {

                                List<Account> accounts = listResource.data();
                                Log.d(TAG, accounts.toString());

                                if (listResource.data().size() == 0){
                                    accountService.create(account);
                                    signUpObserver.createAccountPass();
                                } else {
                                    signUpObserver.createAccountFail();
                                }
                                liveobs.removeObserver(this);
                            }
                        };
                        liveobs.observe(lifecycleOwner, obs);
                    } else {
                        signUpObserver.createAccountFail();
                    }
                    emailExist.removeObserver(this);
                }
            };
            emailExist.observe(this.lifecycleOwner, checkObs);
        }
    }

    public boolean createAccountValidation(){
        // TODO: Create your own validation here.
        return true;
    }

    public LiveData<Resource<Boolean>> updateAccountStatus(int accountStatus){
        return this.accountService.updateAccountStatus(account.id, account, accountStatus);
    }

    public LiveData<Resource<Boolean>> update(Account tempAccount){
        account = tempAccount;
        return this.accountService.update(account.id, account);
    }

    public LiveData<Resource<Boolean>> delete(Account account){
        return this.accountService.delete(account.id);
    }

    public void logout(){
        if (FirebaseAuth.getInstance().getCurrentUser() != null)
            FirebaseAuth.getInstance().signOut();
        this.account = null;
    }
}
