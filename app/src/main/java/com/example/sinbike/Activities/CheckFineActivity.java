package com.example.sinbike.Activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sinbike.R;
import com.example.sinbike.RecyclerViews.Adapters.CardViewDataAdapter;
import com.google.firebase.auth.FirebaseAuth;
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
//    private RecyclerView.LayoutManager mLayoutManager;

    private List<Item> parkingFineList, data, stList;

    ArrayList<String> accountId = new ArrayList<>();
    ArrayList<String> accountId1 = new ArrayList<>();
    List<String> fineDate = new ArrayList<>();
    List<String> fineLocation = new ArrayList<>();
    List<String> fineTime = new ArrayList<>();
    List<String> fineAmount = new ArrayList<>();
    List<String> fineId = new ArrayList<>();
    List<String> email = new ArrayList<>();
    List<String> accountBalanceList = new ArrayList<>();

    Item listItem = null;
    Item singleParkingFine;

    Button btnSelection, btnPay;

    CardView cardView;
    Activity mActivity;

    CheckBox check;

    TextView accountBalance, textAmount;

    PopupWindow mPopupWindow;
    int totalAmount;
    String amount, fineStatus;

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

        if(!checkParkingFineList()) {
            // create an Object for Adapter
            mAdapter = new CardViewDataAdapter(parkingFineList);
        }

        // set the adapter object to the Recyclerview
        mRecyclerView.setAdapter(mAdapter);

        // mContext = getApplicationContext();
        mActivity = CheckFineActivity.this;

        data = new ArrayList<>();

        stList = ((CardViewDataAdapter) mAdapter).getParkingList();

        cardView = (CardView) findViewById(R.id.FinePaymentLayout);

        btnSelection.setOnClickListener(new OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                checkParkingFineList();
                for (int i = 0; i < stList.size(); i++) {
                    singleParkingFine = stList.get(i);
                    if (singleParkingFine.isSelected()) {
                        data.add(singleParkingFine);
                    }
                }
                displayFinePaymentPopup();
            }
        });
    }

                        /*String b = String.valueOf(accountBalance.getText());
                        String c = b.replace("Account Balance: $", "");

                        String d = String.valueOf(textAmount.getText());
                        String e = d.replace("Total Amount: $", "");


                        final int difference = Integer.parseInt(c) - Integer.parseInt(e);

                        final DatabaseReference accountRef1 = FirebaseDatabase.getInstance().getReference("account");

                        accountRef1.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot result) {
                                for (DataSnapshot dsp : result.getChildren()) {
                                    Item accountItem = dsp.getValue(Item.class);
                                    Map<String, Object> updates = new HashMap<String,Object>();

                                    for (int u = 0; u < email.size(); u++) {
                                        assert accountEmail != null;
                                        if (accountEmail.equalsIgnoreCase(email.get(u))) {
                                            updates.put(Integer.toString(difference), accountBalance);
                                           // accountItem.setAccountBalance(Integer.toString(difference));
                                            accountRef1.updateChildren(updates);
                                        }
                                    }
                                }
                            }
                            @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });*/

                /*for (int h =0; h<data.size(); h++ ){
                    String fineDate = data.get(h).getFineDate();
                    String fineLocation = data.get(h).getFineLocation();
                    String fineTime = data.get(h).getFineTime();
                    String fineAmount = data.get(h).getFineAmount();
                    Toast.makeText(CheckFine.this, fineDate + "\n" + fineLocation + "\n" + fineTime + "\n" + fineAmount, Toast.LENGTH_LONG).show();
                }*/

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
                        fineId.add(dsp.getKey());
                    }
                }

                for (int y = 0; y < accountId1.size(); y++) {
                    if (accountId.contains(accountId1.get(y))) {

                        Item st = new Item("Parking Fine " + fineId.get(y), "Date: " + fineDate.get(y), "Location: " + fineLocation.get(y),
                                "Time: " + fineTime.get(y), "Amount: $" + fineAmount.get(y), false);

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

    @SuppressLint("SetTextI18n")
    public void displayFinePaymentPopup() {
        for (int i = 0; i < stList.size(); i++) {
            singleParkingFine = stList.get(i);
            if (singleParkingFine.isSelected()) {
                LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View customView = inflater.inflate(R.layout.activity_fine_payment, null);
                mPopupWindow = new PopupWindow(
                        customView,
                        ConstraintLayout.LayoutParams.WRAP_CONTENT,
                        ConstraintLayout.LayoutParams.WRAP_CONTENT
                );

                mPopupWindow.showAtLocation(btnSelection, Gravity.CENTER, 0, 0);

                btnPay = customView.findViewById(R.id.confirmBtn);

                btnPay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (int i = 0; i < stList.size(); i++) {
                            singleParkingFine = stList.get(i);
                            if (singleParkingFine.isSelected()) {
                                final DatabaseReference fineRef = FirebaseDatabase.getInstance().getReference("parkingFine");
                                fineRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot result) {
                                        for (DataSnapshot dsp : result.getChildren()) {
                                            String fineTitle = singleParkingFine.getFineTitle();
                                            String fineId = fineTitle.replace("Parking Fine ", "");
                                            if (fineId.equals(dsp.getKey())) {
                                                fineRef.child(fineId).removeValue();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                        }
                        fineStatus = "Paid";
                        data.clear();
                        totalAmount = 0;
                        checkFinePaid();
                        mPopupWindow.dismiss();
                    }
                });

                accountBalance = customView.findViewById(R.id.textAccountBalance);

                DatabaseReference accountRef = FirebaseDatabase.getInstance().getReference("account");

                accountRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot result) {
                        for (DataSnapshot dsp : result.getChildren()) {
                            Item accountItem = dsp.getValue(Item.class);
                            email.add(accountItem.getEmail());
                            accountBalanceList.add(accountItem.getAccountBalance());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                final String accountEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

                for (int u = 0; u < email.size(); u++) {
                    assert accountEmail != null;
                    if (accountEmail.equalsIgnoreCase(email.get(u))) {
                        accountBalance.setText("Account Balance: $" + accountBalanceList.get(u));
                    }
                }

                textAmount = customView.findViewById(R.id.textAmount);

                for (int b = 0; b < data.size(); b++) {
                    String amount2 = data.get(b).getFineAmount();
                    String newAmount = amount2.replace("Amount: $", "");
                    totalAmount += Integer.parseInt(newAmount);
                    amount = String.valueOf(totalAmount);
                }
                textAmount.setText("Total Amount: $" + amount);

                ImageButton closeButton = customView.findViewById(R.id.fine_close);

                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        data.clear();
                        totalAmount = 0;

                                /*LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                                final View fineView = inflater.inflate(R.layout.cardview_row, null);

                                fineView.setOnClickListener(new OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        for(int r=0; r<parkingFineList.size(); r++) {
                                            check = fineView.findViewById(R.id.chkSelected);
                                            parkingFineList.get(r).setSelected(false);
                                            //check.setChecked(false);
                                        }
                                        mAdapter.notifyDataSetChanged();
                                    }
                                });*/

                        // Dismiss the popup window
                        mPopupWindow.dismiss();
                    }
                });
                break;
            } else
                Toast.makeText(CheckFineActivity.this, "Please select a parking fine!", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean checkFinePaid(){
        for (int i = 0; i < stList.size(); i++) {
            singleParkingFine = stList.get(i);
            if(singleParkingFine.isSelected()) {
                if (fineStatus.equals("Paid")) {
                    stList.remove(i);
                    i--;
                }
            }
        }mAdapter.notifyDataSetChanged();
        return false;
    }

    public boolean checkParkingFineList(){
        for(int q=0; q<parkingFineList.size();q++){
            if(parkingFineList.size()<0){
                Toast.makeText(CheckFineActivity.this, "You have no parking fine!", Toast.LENGTH_SHORT).show();
                return true;
            }
        } return false;
    }
}