package com.example.sinbike.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sinbike.R;

import androidx.appcompat.app.AppCompatActivity;

public class TopUpActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnTopup, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up);

        btnTopup = findViewById(R.id.topupBtn);
        btnBack = findViewById(R.id.topupBack);

        btnTopup.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.topupBack){
            finish();
        } else {
            startActivity(new Intent(TopUpActivity.this, ManageCardActivity.class));
        }
    }
}
