package com.example.sinbike.Repositories;

import android.icu.text.Replaceable;

import com.example.sinbike.POJO.Card;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.REPLACE;

public interface CardDao {

    @Insert(onConflict = REPLACE)
    void insert(Card card);

    @Query("SELECT * FROM card_database")
    LiveData<List<Card>> get();
}
