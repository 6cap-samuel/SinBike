package com.example.sinbike.Activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.sinbike.POJO.Account;
import com.example.sinbike.POJO.Transaction;
import com.example.sinbike.R;
import com.example.sinbike.Repositories.common.Resource;
import com.example.sinbike.ViewModels.AccountViewModel;
import com.example.sinbike.ViewModels.TransactionViewModel;
import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewTransactionFragment extends Fragment {
    List<Transaction> transactionList = new ArrayList<>();
    ListView listView;
    TransactionViewModel transactionViewModel;
    AccountViewModel accountViewModel;
    Account account;
    List<String> type = new ArrayList<>();
    List<Timestamp> date = new ArrayList<>();
    List<Double> amount = new ArrayList<>();
    List<String> accountId = new ArrayList<>();
    List<Transaction> transactionList1 = new ArrayList<>();
    transactionAdapter transactionAdapter;
    List<Transaction> transactionList2 = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_transaction, container, false);

        initViewModel();
        listView = view.findViewById(R.id.transaction_listview);

        populateList();

        transactionAdapter = new transactionAdapter(transactionList1);
        listView.setAdapter(transactionAdapter);

        return view;
    }

    public void initViewModel() {
        this.accountViewModel = ViewModelProviders.of(this).get(AccountViewModel.class);
        this.account = accountViewModel.getAccount();
        this.transactionViewModel = ViewModelProviders.of(this).get(TransactionViewModel.class);
    }

    public List<Transaction> populateList() {
        transactionViewModel.getAllTransaction(account.id).observe(this, new Observer<com.example.sinbike.Repositories.common.Resource<List<Transaction>>>() {
            @Override
            public void onChanged(Resource<List<Transaction>> listResource) {
                transactionList = listResource.data();
                for (int y = 0; y < transactionList.size(); y++) {
                    type.add(transactionList.get(y).getTransactionType());
                    date.add(transactionList.get(y).gettransactionDate());
                    amount.add(transactionList.get(y).getAmount());
                    accountId.add(transactionList.get(y).getAccountId());

                    Transaction transaction = new Transaction(amount.get(y), date.get(y), accountId.get(y), type.get(y));
                    transactionList1.add(transaction);
                }
                transactionAdapter.notifyDataSetChanged();
            }
        });
        return transactionList1;
    }
}
