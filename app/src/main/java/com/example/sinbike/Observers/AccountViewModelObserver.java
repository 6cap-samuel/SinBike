package com.example.sinbike.Observers;

import com.example.sinbike.POJO.Account;

public interface AccountViewModelObserver {
    void createAccountPass();
    void createAccountFail();
    void loginSuccess(Account account);
    void loginFailed();
}
