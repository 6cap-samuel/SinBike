package com.example.sinbike.Observers;

import com.example.sinbike.POJO.Account;

public interface AccountViewModelObserver {
    public void createAccountPass();
    public void createAccountFail();
    public void loginSuccess(Account account);
    public void loginFailed();
}
