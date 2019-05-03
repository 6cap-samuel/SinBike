package com.example.sinbike.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.sinbike.Constants;
import com.example.sinbike.POJO.Card;
import com.example.sinbike.R;
import com.example.sinbike.ViewModels.CardViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public class AddPaymentActivity extends AppCompatActivity implements View.OnClickListener{

    Spinner spinner_mm;
    ArrayAdapter<CharSequence> adapter_mm;
    Spinner spinner_yyyy;
    ArrayAdapter<CharSequence> adapter_yyyy;
    Button saveBtn, cancelBtn;


    EditText etCardNumber, etCVV;


    CardViewModel cardViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment);

        this.initViewModels();
        this.init();
    }

    public void init(){
        saveBtn = (Button) findViewById(R.id.saveBtn);
        cancelBtn = (Button) findViewById(R.id.cancelBttn);

        etCardNumber = (EditText) findViewById(R.id.cc_no);
        etCardNumber.addTextChangedListener(new CreditCardNumberFormattingTextWatcher());

        etCVV = (EditText) findViewById(R.id.etCVV);

        spinner_mm = (Spinner)findViewById(R.id.spinner_mm);
        adapter_mm = ArrayAdapter.createFromResource(this,R.array.months,android.R.layout.simple_spinner_item);
        adapter_mm.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_mm.setAdapter(adapter_mm);

        spinner_yyyy = (Spinner)findViewById(R.id.spinner_yyyy);
        adapter_yyyy = ArrayAdapter.createFromResource(this,R.array.years,android.R.layout.simple_spinner_item);
        adapter_yyyy.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_yyyy.setAdapter(adapter_yyyy);

        saveBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
    }

    public void initViewModels(){
        this.cardViewModel = ViewModelProviders.of(this).get(CardViewModel.class);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.saveBtn){
            this.addNewCard();
        } else if (v.getId() == R.id.cancelBttn){
            this.cancel();
        }
    }

    public void addNewCard(){
        Card card = new Card();
        card.setCardNumber(etCardNumber.getText().toString());

        int monthPostion = spinner_mm.getSelectedItemPosition();
        String monthItem = adapter_mm.getItem(monthPostion).toString();

        int yearPosition = spinner_yyyy.getSelectedItemPosition();
        String yearItem = adapter_yyyy.getItem(yearPosition).toString();

        // TODO : get the card type and set into card
//         card.setCardType(Constants.CARD_VISA);
//         card.setCardType(Constants.CARD_MASTER);
//         card.setCardType(Constants.CARD_AMEX);

        card.setExpiryMonth(monthItem);
        card.setExpiryYear(yearItem);
        card.setCvvNumber(this.etCVV.getText().toString());

        this.cardViewModel.addCard(card);

        finish();
    }

    public void cancel(){
        finish();
    }

    public static class CreditCardNumberFormattingTextWatcher implements TextWatcher {
        private boolean lock;
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
        @Override
        public void afterTextChanged(Editable s) {
            {
                if (lock || s.length() > 19) {
                    return;
                }
                lock = true;
                for (int i = 4; i < s.length(); i += 5) {
                    if (s.toString().charAt(i) != ' ') {
                        s.insert(i, " ");
                    }
                }
                lock = false;
            }

        }
    }
}

