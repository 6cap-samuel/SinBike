package com.example.sinbike.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sinbike.POJO.Account;
import com.example.sinbike.R;
import com.example.sinbike.ViewModels.AccountViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProviders;

public class ManageDashboardActivity extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = "ManageDashboardActivity";
    public CardView rentabikeid, walletid, reportafaultid, transactionid, manageprofileid, payfinesid;
    Button logout;
    TextView idUserName;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    AccountViewModel accountViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        accountViewModel = ViewModelProviders.of(this).get(AccountViewModel.class);

        Account account = accountViewModel.getAccount();
        Log.d(TAG, account.id);
        Log.d(TAG, account.toString());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_dashboard);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();


        rentabikeid = findViewById(R.id.rentalbike_id);
        rentabikeid = findViewById(R.id.rentalbike_id);
        walletid = findViewById(R.id.wallet_id);
        reportafaultid = findViewById(R.id.reportafault_id);
        transactionid = findViewById(R.id.transaction_id);
        manageprofileid = findViewById(R.id.manageprofile_id);
        payfinesid = findViewById(R.id.checkfine_id);
        logout = findViewById(R.id.btnLogout);
        idUserName = findViewById(R.id.idUserName);

        FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    String name = firebaseUser.getDisplayName();
                    idUserName.setText(name);
                }
            }
        };


        rentabikeid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ManageDashboardActivity.this, Rental.class);
                startActivity(i);
                finish();
            }
        });


        walletid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View args0) {
                Intent intent = new Intent(ManageDashboardActivity.this , TopUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        reportafaultid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(ManageDashboardActivity.this, ReportFaults.class);
                    startActivity(intent);
                    finish();
            }
        });

        payfinesid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageDashboardActivity.this, CheckFine.class);
                startActivity(intent);
                finish();
            }
        });

        transactionid.setOnClickListener(this);
        manageprofileid.setOnClickListener(this);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAuth.getCurrentUser() !=null)
                mAuth.signOut();
                Intent intent = new Intent (ManageDashboardActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });




    }


    @Override
    public void onClick(View v) {

    }
}
