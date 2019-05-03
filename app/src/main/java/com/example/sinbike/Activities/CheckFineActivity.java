package com.example.sinbike.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.sinbike.R;
import com.example.sinbike.RecyclerViews.Adapters.CardViewDataAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import android.view.View.OnClickListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CheckFineActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<Item> parkingFineList;

    ArrayList<String> accountId = new ArrayList<>();
    ArrayList<String> accountId1 = new ArrayList<>();
    List<String> fineDate = new ArrayList<>();
    List<String> fineLocation = new ArrayList<>();
    List<String> fineTime = new ArrayList<>();
    List<String> fineAmount = new ArrayList<>();

    Item listItem = null;

    private Button btnSelection;

    private CardView cardView;
    private Context mContext;
    private Activity mActivity;

    private TextView accountBalance;

    private PopupWindow mPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_fine);

        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Check & Pay Parking Fine");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckFineActivity.this, ManageDashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnSelection = (Button) findViewById(R.id.pay_btn);

        parkingFineList = new ArrayList<>();

        getData();

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // create an Object for Adapter
        mAdapter = new CardViewDataAdapter(parkingFineList);

        // set the adapter object to the Recyclerview
        mRecyclerView.setAdapter(mAdapter);

        // mContext = getApplicationContext();
        mActivity = CheckFineActivity.this;

        accountBalance = findViewById(R.id.textAccountBalance);

        btnSelection.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Item> data = new ArrayList<>();
                List<Item> stList = ((CardViewDataAdapter) mAdapter).getParkingList();

                for (int i = 0; i < stList.size(); i++) {
                    Item singleParkingFine = stList.get(i);
                    if (singleParkingFine.isSelected()) {

                        data.add(singleParkingFine);
                    }
                }

                cardView = (CardView) findViewById(R.id.FinePaymentLayout);
                LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View customView = inflater.inflate(R.layout.activity_fine_payment,null);
                mPopupWindow = new PopupWindow(
                        customView,
                        ConstraintLayout.LayoutParams.WRAP_CONTENT,
                        ConstraintLayout.LayoutParams.WRAP_CONTENT
                );

                mPopupWindow.showAtLocation(btnSelection, Gravity.CENTER,0,0);

                ImageButton closeButton = customView.findViewById(R.id.fine_close);

                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Dismiss the popup window
                        mPopupWindow.dismiss();
                    }
                });

                final List<String> accountID = new ArrayList<>();
                final List<String> accountBalanceList = new ArrayList<>();

                DatabaseReference accountRef = FirebaseDatabase.getInstance().getReference("account");

                accountRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot result) {
                        for (DataSnapshot dsp : result.getChildren()) {
                            accountID.add(dsp.getKey());
                            Item accountBalanceItem = dsp.getValue(Item.class);
                            if (listItem != null) {
                                assert accountBalanceItem != null;
                                accountBalanceList.add(accountBalanceItem.getAccountBalance());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    for (int u = 0; u < accountID.size(); u++) {
                        if (user.getUid().equals(accountID.get(u))){
                            accountBalance.setText(accountBalanceList.get(u));
                        }
                    }
                }
            }
        });
    }

    public void getData() {
        DatabaseReference accountRef1 = FirebaseDatabase.getInstance().getReference("account");
        accountRef1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot result) {
                for (DataSnapshot dsp : result.getChildren()) {
                    Item listItem = dsp.getValue(Item.class); //add result into array list
                    if (listItem != null) {
                        accountId.add(listItem.getAccountID());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference fineRef = FirebaseDatabase.getInstance().getReference("parkingFine");
        fineRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot result) {
                for (DataSnapshot dsp : result.getChildren()) {
                    listItem = dsp.getValue(Item.class); //add result into array list
                    if (listItem != null) {
                        accountId1.add(listItem.getAccountID());

                        fineDate.add(listItem.getFineDate());
                        fineLocation.add(listItem.getFineLocation());
                        fineTime.add(listItem.getFineTime());
                        fineAmount.add(listItem.getFineAmount());
                    }
                }

                for (int y = 0; y < accountId1.size(); y++) {
                    if (accountId.contains(accountId1.get(y))) {

                        Item st = new Item("Parking Fine " + y, "Fine Date: " + fineDate.get(y), "Fine Location: " + fineLocation.get(y),
                                "Fine Time: " + fineTime.get(y), "fineAmount: $" + fineAmount.get(y), false);

                        parkingFineList.add(st);
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}