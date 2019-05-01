package com.example.sinbike.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.sinbike.POJO.Card;
import com.example.sinbike.R;
import com.example.sinbike.RecyclerViews.Adapters.CardAdapter;
import com.example.sinbike.ViewModels.AccountViewModel;
import com.example.sinbike.ViewModels.CardViewModel;

import java.util.ArrayList;
import java.util.List;

public class ManageCardActivity extends AppCompatActivity {

    private static final String TAG = "ManageCardActivity";

    RecyclerView cardRecyclerView;
    CardAdapter cardAdapter;

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
        this.cardRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Card> cards = new ArrayList<Card>();
        for (int i = 0 ; i < 20; i++){
            Card card = new Card();
            card.setCardNumber("testing");

            cards.add(card);
        }

        this.cardAdapter = new CardAdapter(this);
        this.cardAdapter.setCards(cards);
        this.cardRecyclerView.setAdapter(this.cardAdapter);

        Log.d(TAG, String.valueOf(this.cardAdapter.getItemCount()));

//        this.cardViewModel.getCards().observe(this, new Observer<List<Card>>() {
//            @Override
//            public void onChanged(List<Card> cards) {
//                Log.d(TAG, String.valueOf(cards.size()));
//
//                cardAdapter.setCards(cards);
//                cardRecyclerView.setAdapter(cardAdapter);
//            }
//        });
    }

    public void initViewModel(){
        this.accountViewModel = ViewModelProviders.of(this).get(AccountViewModel.class);
        this.cardViewModel = ViewModelProviders.of(this).get(CardViewModel.class);
    }
}
