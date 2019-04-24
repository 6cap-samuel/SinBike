package com.example.sinbike.Activities;

import android.os.Bundle;
import android.os.Handler;
import android.content.Intent;

import com.example.sinbike.R;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {



    // Waiting Duration
    //Splash screen will show for 3 seconds
    private final int SPLASH_DISPLAY_LENGTH = 3000;

    // Called when the activity is first created. //
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

}

