package com.example.sinbike.Activities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sinbike.POJO.Transaction;
import com.example.sinbike.R;

import java.util.List;

public class transactionAdapter extends BaseAdapter {

    //private Context context;
    private List<Transaction> transactionList;

    public transactionAdapter(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }
    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getCount() {
        return transactionList.size();
    }

    @Override
    public Object getItem(int position) {
        return transactionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();

            //LayoutInflater inflater = (LayoutInflater) context
              //      .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_transaction, null, true);

            holder.type_transaction = (TextView) convertView.findViewById(R.id.type_transaction);
            holder.date_transaction = (TextView) convertView.findViewById(R.id.date_transaction);
            holder.amount_transaction = (TextView) convertView.findViewById(R.id.amount_transaction);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }

        holder.type_transaction.setText(transactionList.get(position).getTransactionType());
        holder.date_transaction.setText(String.valueOf(transactionList.get(position).gettransactionDate()));
        holder.amount_transaction.setText(String.valueOf(transactionList.get(position).getAmount()));

        return convertView;
    }

    private class ViewHolder {

        protected TextView type_transaction, date_transaction, amount_transaction;

    }

}
