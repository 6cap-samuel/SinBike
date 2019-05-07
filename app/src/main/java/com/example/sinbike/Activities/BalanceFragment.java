package com.example.sinbike.Activities;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sinbike.Constants;
import com.example.sinbike.POJO.Account;
import com.example.sinbike.R;
import com.example.sinbike.ViewModels.AccountViewModel;
/**
 * A simple {@link Fragment} subclass.
 */
public class BalanceFragment extends Fragment {

    AccountViewModel accountViewModel;
    Account account;
    TextView accountBal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_balance, container, false);
        accountBal = view.findViewById(R.id.accountBalance);


        return view;
    }

}
