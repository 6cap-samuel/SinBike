package com.example.sinbike.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sinbike.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class PaymentMethodActivity extends AppCompatActivity {

    FloatingActionButton floatingBtn;
    Button backBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);


        floatingBtn = findViewById(R.id.floatingActionButton);
        backBtn = findViewById(R.id.payment_backBtn);

        floatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View args0) {
                Intent intent = new Intent(PaymentMethodActivity.this, CardFormActivity.class);
                startActivity(intent);
                finish();
            }
        });



        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View args0) {
                Intent i = new Intent(PaymentMethodActivity.this, WalletPage.class);
                startActivity(i);
                finish();
            }
        });

    }
    }
