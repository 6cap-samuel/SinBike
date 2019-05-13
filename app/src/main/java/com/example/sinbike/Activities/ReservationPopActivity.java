package com.example.sinbike.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sinbike.R;
import com.google.firebase.FirebaseApp;

import java.util.ArrayList;

public class ReservationPopActivity extends Activity {

    Button btnunlock;
    ImageView icanchor;
    Animation roundingalone;

    private ArrayList<String> lst = new ArrayList<>();
    int count=0;

    private TextView timer;

    private CountDownTimer countDownTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_reservation_pop);

        DisplayMetrics dn = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dn);

        int width = dn.widthPixels;
        int height = dn.heightPixels;

        getWindow().setLayout((int)(width*.8), (int)(height*.7));

        btnunlock = findViewById(R.id.btnunlock);
        icanchor = findViewById(R.id.icanchor);
        timer = findViewById(R.id.timer);
        roundingalone = AnimationUtils.loadAnimation(this, R.anim.roundingalone);
        icanchor.startAnimation(roundingalone);



        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                timer.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                timer.setText("done!");
            }
        }.start();


        btnunlock.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //  startActivity(new Intent(ReservationPopActivity.this, RentalPaymentBarcodeActivity.class));
            }
        });
    }

    /* public boolean getQrResult (){
           Bundle qrcodes = getIntent().getExtras();
           if(qrcodes!=null) {
               for (int i = 0; i < lst.size(); i++) {
                   String scanResult = qrcodes.getString("barcode");
                   if (scanResult != null) {
                       if (scanResult.trim().equals(lst.get(i).trim())) {
                           Intent P = new Intent(getApplicationContext(), PopActivity.class);
                           startActivity(P);
                           return true;
                       } else
                           Toast.makeText(ReservationPopActivity.this, "Invalid Qrcode. Please scan again!",
                                   Toast.LENGTH_SHORT).show();
                       return false;
                   }
               }
           } return false;
       }
   */
    private void initViews() {

        btnunlock = findViewById(R.id.btnunlock);
        //btnget.setOnClickListener((View.OnClickListener) this);
    }
}

