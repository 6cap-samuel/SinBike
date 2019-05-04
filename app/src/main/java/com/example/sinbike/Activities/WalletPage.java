package com.example.sinbike.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.sinbike.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class WalletPage extends AppCompatActivity {

    Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_page);

        backBtn = findViewById(R.id.wallet_backBtn);

        BottomNavigationView walletNav = findViewById(R.id.wallet_nav);
        walletNav.setOnNavigationItemSelectedListener(navListener);
        walletNav.setItemIconTintList(null);

        getSupportFragmentManager().beginTransaction().replace(R.id.wallet_cont, new BalanceFragment()).commit();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View args0) {
                Intent intent = new Intent(WalletPage.this , ManageDashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()){
                        case R.id.nav_bal:
                            selectedFragment = new BalanceFragment();
                            break;
                        case R.id.nav_topup:
                            selectedFragment = new TopUpFragment();
                            break;
                        case R.id.nav_transaction:
                            selectedFragment = new ViewTransactionFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.wallet_cont,
                            selectedFragment).commit();

                    return true;
                }
            };
}