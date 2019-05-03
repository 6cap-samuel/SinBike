package com.example.sinbike.RecyclerViews.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sinbike.Activities.AddPaymentActivity;
import com.example.sinbike.Activities.ManageCardActivity;
import com.example.sinbike.Constants;
import com.example.sinbike.POJO.Card;
import com.example.sinbike.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

/**
 * This class is the adapter class of the CardRecyclerView
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    private final LayoutInflater mInflater;
    private List<Card> cardList;

    public CardAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.cardList = new ArrayList<Card>();
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = mInflater.inflate(R.layout.recycler_view_item_card, viewGroup, false);
        return new CardViewHolder(view);
    }

    /**
     * Bind View Holder to specific index.
     */
    @Override
    public void onBindViewHolder(@NonNull CardViewHolder cardViewHolder, int position) {
        Card card = cardList.get(position);
        cardViewHolder.bindCard(card);
    }

    @Override
    public int getItemCount() {
        if (this.cardList.size() != 0)
            return this.cardList.size();
        return 0;
    }

    public void setCards(List<Card> cardList){
        this.cardList = cardList;
        notifyDataSetChanged();
    }

    /**
     * View Holder for the Card Recycler View.
     */
    class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private Card card;

        TextView txtCardNumber;
        ImageView imgCardType;

        private CardViewHolder(View itemView) {
            super(itemView);
            this.txtCardNumber = (TextView) itemView.findViewById(R.id.txt_card_number);
            this.imgCardType = (ImageView) itemView.findViewById(R.id.img_card_type);
        }

        private void bindCard(Card card){
            this.card = card;
            this.txtCardNumber.setText(this.card.getCardNumber());

            if (this.card.getCardType() == Constants.CARD_VISA) {
                imgCardType.setBackground(ContextCompat.getDrawable(itemView.getContext(), R.drawable.visa));
            } else if (this.card.getCardType() == Constants.CARD_MASTER) {
                imgCardType.setBackground(ContextCompat.getDrawable(itemView.getContext(), R.drawable.mastercard));
            } else if (this.card.getCardType() == Constants.CARD_AMEX) {
                imgCardType.setBackground(ContextCompat.getDrawable(itemView.getContext(), R.drawable.amex));
            }
        }

        @Override
        public void onClick(View v) {


        }
    }
}