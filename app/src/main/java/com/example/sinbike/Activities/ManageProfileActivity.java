package com.example.sinbike.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.sinbike.POJO.Account;
import com.example.sinbike.R;
import com.example.sinbike.ViewModels.AccountViewModel;

public class ManageProfileActivity extends AppCompatActivity implements View.OnClickListener{

    AccountViewModel accountViewModel;
    Account account;

    EditText etName, etTelephoneNumber, etDob;
    RadioGroup rdGender;
    RadioButton rbMale, rbFemale;
    Button btnSubmit, btnClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_profile);

        this.initViewModel();
        this.init();
    }

    public void init(){
        etName = findViewById(R.id.signupName);
        etTelephoneNumber = findViewById(R.id.signupPhone);
        etDob = findViewById(R.id.signupDOB);
        rdGender = findViewById(R.id.rdGender);
        rbMale = findViewById(R.id.rbMale);
        rbFemale = findViewById(R.id.rbFemale);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnClear = findViewById(R.id.btnClear);

        this.initHints();

        btnSubmit.setOnClickListener(this);
        btnClear.setOnClickListener(this);
    }

    public void initHints(){
        etName.setHint(this.account.getName());
        etTelephoneNumber.setHint(this.account.getTelephoneNumber());
        etDob.setHint(this.account.getDateOfBirth());
    }

    public void initViewModel(){
        this.accountViewModel = ViewModelProviders.of(this).get(AccountViewModel.class);
        this.accountViewModel.setLifecycleOwner(this);
        this.account = accountViewModel.getAccount();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnSubmit){
            this.submit();
        } else if (v.getId() == R.id.btnClear){
            this.clear();
        } else {
            this.back();
        }
    }

    public void submit(){

        if (!etName.getText().toString().equals("")){
            this.account.setName(etName.getText().toString());
        }

        if(!etTelephoneNumber.getText().toString().equals("")){
            this.account.setTelephoneNumber(etTelephoneNumber.getText().toString());
        }

        if (!etDob.getText().toString().equals("")){
            this.account.setDateOfBirth(etDob.getText().toString());
        }

        this.accountViewModel.update(this.account);
        finish();
    }

    public void clear(){
        etName.setText("");
        etTelephoneNumber.setText("");
        etDob.setText("");
    }

    public void back(){
        finish();
    }
}
