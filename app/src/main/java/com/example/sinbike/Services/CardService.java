package com.example.sinbike.Services;

import android.app.Application;

import com.example.sinbike.POJO.Card;
import com.example.sinbike.Repositories.CardRepository;

import java.util.List;

import androidx.lifecycle.LiveData;

public class CardService {

    private static final String TAG = "CardService";
    private CardRepository cardRepository;

    public CardService(Application application){
        this.cardRepository = new CardRepository(application);
    }

    public LiveData<List<Card>> getCard(){
        return this.cardRepository.get();
    }

    public void insertCard(Card card){
        this.cardRepository.insert(card);

    }
}
