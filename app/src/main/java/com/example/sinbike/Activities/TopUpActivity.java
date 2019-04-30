package com.example.sinbike.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sinbike.R;

import androidx.appcompat.app.AppCompatActivity;

public class TopUpActivity extends AppCompatActivity {

    private Button topUpBttn, topupBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up);

        topUpBttn = findViewById(R.id.topupBtn);
        topupBack = findViewById(R.id.topupBack);

        topUpBttn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(TopUpActivity.this, AddPaymentActivity.class));
            }
        });

        topupBack.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(TopUpActivity.this, ManageDashboardActivity.class));
            }
        });





    }
}
