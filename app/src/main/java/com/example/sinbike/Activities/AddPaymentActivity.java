package com.example.sinbike.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.sinbike.R;

import androidx.appcompat.app.AppCompatActivity;

public class AddPaymentActivity extends AppCompatActivity {

    Spinner spinner_mm;
    ArrayAdapter<CharSequence> adapter_mm;
    Spinner spinner_yyyy;
    ArrayAdapter<CharSequence> adapter_yyyy;
    Button saveBtn, cancelBttn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment);

        saveBtn = (Button)findViewById(R.id.saveBtn);
        cancelBttn = (Button)findViewById(R.id.cancelBttn);


        EditText creditCard = (EditText) findViewById(R.id.cc_no);
        creditCard.addTextChangedListener(new CreditCardNumberFormattingTextWatcher());

        spinner_mm = (Spinner)findViewById(R.id.spinner_mm);
        adapter_mm = ArrayAdapter.createFromResource(this,R.array.months,android.R.layout.simple_spinner_item);
        adapter_mm.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_mm.setAdapter(adapter_mm);


        spinner_yyyy = (Spinner)findViewById(R.id.spinner_yyyy);
        adapter_yyyy = ArrayAdapter.createFromResource(this,R.array.years,android.R.layout.simple_spinner_item);
        adapter_yyyy.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_yyyy.setAdapter(adapter_yyyy);


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View args0) {
                Intent intent = new Intent(AddPaymentActivity.this , TopUpActivity.class);
                startActivity(intent);
                finish();
            }
        });


        cancelBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View args0) {
                Intent intent = new Intent(AddPaymentActivity.this , ManageDashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });


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

