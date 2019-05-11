package com.example.sinbike.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sinbike.POJO.Card;
import com.example.sinbike.R;
import com.example.sinbike.RecyclerViews.Adapters.CardAdapter;
import com.example.sinbike.ViewModels.AccountViewModel;
import com.example.sinbike.ViewModels.CardViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ManageCardActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "ManageCardActivity";

    RecyclerView cardRecyclerView;
    CardAdapter cardAdapter;
    FloatingActionButton fabAdd;

    AccountViewModel accountViewModel;
    CardViewModel cardViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_card);

        this.initViewModel();
        this.init();
    }

    public void init(){
        this.cardRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_card);
        this.fabAdd = findViewById(R.id.floatingActionButton);
        this.cardRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        this.cardAdapter = new CardAdapter(this);
        this.cardRecyclerView.setAdapter(this.cardAdapter);

        this.cardViewModel.getCards().observe(this, new Observer<List<Card>>() {
            @Override
            public void onChanged(List<Card> cards) {
                cardAdapter.setCards(cards);
                cardRecyclerView.setAdapter(cardAdapter);
            }
        });

        this.fabAdd.setOnClickListener(this);
    }

    public void initViewModel(){
        this.accountViewModel = ViewModelProviders.of(this).get(AccountViewModel.class);
        this.cardViewModel = ViewModelProviders.of(this).get(CardViewModel.class);
    }

    public void add(){
        Bundle extras = getIntent().getExtras();
        double amount = extras.getDouble("amount");
        Intent intent = new Intent(this, CardFormActivity.class);
        intent.putExtra("amount", amount);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.floatingActionButton){
            this.add();
        }
    }
}
