package com.example.sinbike.RecyclerViews.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.sinbike.Activities.Item;
import com.example.sinbike.R;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class CardViewDataAdapter extends
        RecyclerView.Adapter<CardViewDataAdapter.ViewHolder> {

    private List<Item> stList;

    public CardViewDataAdapter(List<Item> parkingFine) {
        this.stList = parkingFine;
    }

    // Create new views
    @Override
    public CardViewDataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.cardview_row, null);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        final int pos = position;

        viewHolder.textTitle.setText(stList.get(position).getFineTitle());

        viewHolder.textFineDate.setText(stList.get(position).getFineDate());

        viewHolder.textFineTime.setText(stList.get(position).getFineTime());

        viewHolder.textFineLocation.setText(stList.get(position).getFineLocation());

        viewHolder.textFineAmount.setText(stList.get(position).getFineAmount());

        viewHolder.chkSelected.setChecked(stList.get(position).isSelected());

        viewHolder.chkSelected.setTag(stList.get(position));


        viewHolder.chkSelected.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                Item contact = (Item) cb.getTag();

                contact.setSelected(cb.isChecked());
                stList.get(pos).setSelected(cb.isChecked());
            }
        });

    }

    // Return the size arraylist
    @Override
    public int getItemCount() {
        return stList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textTitle;
        public TextView textFineDate;
        public TextView textFineTime;
        public TextView textFineLocation;
        public TextView textFineAmount;

        public CheckBox chkSelected;

        public Item singleParkingFine;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            textTitle = (TextView) itemLayoutView.findViewById(R.id.textTitle);
            textFineDate = (TextView) itemLayoutView.findViewById(R.id.textFineDate);
            textFineTime = (TextView) itemLayoutView.findViewById(R.id.textFineTime);
            textFineLocation = (TextView) itemLayoutView.findViewById(R.id.textFineLocation);
            textFineAmount = (TextView) itemLayoutView.findViewById(R.id.textFineAmount);
            chkSelected = (CheckBox) itemLayoutView.findViewById(R.id.chkSelected);
        }
    }

    // method to access in activity after updating selection
    public List<Item> getParkingList() {
        return stList;
    }

}
