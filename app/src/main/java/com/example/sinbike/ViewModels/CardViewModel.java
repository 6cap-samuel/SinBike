package com.example.sinbike.ViewModels;

import android.app.Application;

import com.example.sinbike.POJO.Card;
import com.example.sinbike.Services.CardService;

import java.util.List;

import androidx.lifecycle.LiveData;

public class CardViewModel {

    private static final String TAG = "CardViewModel";
    private CardService cardService;

    public CardViewModel(Application application){
        this.cardService = new CardService(application);
    }

    public LiveData<List<Card>> getCards(){
        return this.cardService.getCard();
    }

    public void addCard(Card card){
        this.cardService.insertCard(card);
    }
}
