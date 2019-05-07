package com.example.sinbike.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.example.sinbike.POJO.Account;
import com.example.sinbike.R;
import com.example.sinbike.ViewModels.AccountViewModel;

public class ManageProfileActivity extends AppCompatActivity implements View.OnClickListener{

    AccountViewModel accountViewModel;
    Account account;

    EditText etName, etTelephoneNumber, etDob;
    RadioGroup rdGender;
    RadioButton rbMale, rbFemale;
    RadioButton radioButtonOptions;
    Button btnSubmit, btnClear;

    // variable to hold the gender value
    private String genderOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_profile);

        this.initViewModel();
        this.init();
        setListener();
        this.back();
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
        genderOptions = this.account.getGender();
        if(this.account.getGender().equals("Male")) {
            rbMale.setChecked(true);
        } else if (this.account.getGender().equals("Female")) {
            rbFemale.setChecked(true);
        }


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

        this.account.setGender(genderOptions);

        this.accountViewModel.update(this.account);
        Intent intent = new Intent(ManageProfileActivity.this , ManageDashboardActivity.class);
        Toast.makeText(this, "Account Detailed Updated!", Toast.LENGTH_SHORT).show();
        startActivity(intent);
        finish();
    }

    public void clear(){
        etName.setText("");
        etTelephoneNumber.setText("");
        etDob.setText("");
    }

    public void back(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Manage Profile");

        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageProfileActivity.this , ManageDashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void setListener(){

        // to get the gender from the radio button
        rdGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButtonOptions = rdGender.findViewById(checkedId);

                switch (checkedId){
                    case R.id.rbMale:
                        genderOptions = radioButtonOptions.getText().toString();
                        break;

                    case R.id.rbFemale:
                        genderOptions = radioButtonOptions.getText().toString();
                        break;
                }
            }
        });
    }
}
