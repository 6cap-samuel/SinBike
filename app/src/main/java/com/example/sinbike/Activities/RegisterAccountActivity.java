package com.example.sinbike.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sinbike.Activities.Dialogs.CustomDialog;
import com.example.sinbike.Constants;
import com.example.sinbike.Observers.SignUpObserver;
import com.example.sinbike.POJO.Account;
import com.example.sinbike.POJO.Fine;
import com.example.sinbike.R;
import com.example.sinbike.ViewModels.AccountViewModel;
import com.example.sinbike.ViewModels.FineViewModel;
import com.google.firebase.Timestamp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public class RegisterAccountActivity extends AppCompatActivity implements SignUpObserver, View.OnClickListener {

    EditText signupName, signupEmail, signupPassword, signupPhone, signupDOB;
    TextView tvTermsandCondition;
    RadioGroup rdGender;
    RadioButton rbMale, rbFemale;
    RadioButton radioButtonOptions;
    Button btnSubmit, btnClear, btnBack;
    CustomDialog customDialog;
    CheckBox checkBox;

    String name;
    String gender;
    String email;
    String password;
    String phone;
    String DOB;
    String accountid;

    AccountViewModel accountViewModel;

    // variable to hold the gender value
    private String genderOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_account);

        this.init();
        this.setListener();
    }

    public void init(){

        signupName = findViewById(R.id.signupName);
        signupEmail = findViewById(R.id.signupEmail);
        signupPassword = findViewById(R.id.signupPassword);
        signupPhone = findViewById(R.id.signupPhone);
        signupDOB = findViewById(R.id.signupDOB);
        rdGender = findViewById(R.id.rdGender);
        rbMale = findViewById(R.id.rbMale);
        rbFemale = findViewById(R.id.rbFemale);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnClear = findViewById(R.id.btnClear);
        btnBack = findViewById(R.id.btnBack);
        tvTermsandCondition = findViewById(R.id.tvTermsandCondition);
        checkBox = findViewById(R.id.cbTermsAndCondition);


        this.accountViewModel = ViewModelProviders.of(this).get(AccountViewModel.class);
        this.accountViewModel.setSignUpObserver(this);
        this.accountViewModel.setLifecycleOwner(this);
    }



    public void setListener(){

        btnClear.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

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

    private void registerUser(){
        Account account = new Account();
        account.setName(name);
        account.setEmail(email);
        account.setPassword(password);
        account.setTelephoneNumber(phone);
        account.setDateOfBirth(DOB);

        this.accountViewModel.createAccount(account);
    }

    public void sampleFunction(){
        // get current account that is being logged in.
        Account currentAccount = this.accountViewModel.getAccount();

        //updateds variables of the curreent account.
        currentAccount.setGender("Female");
        currentAccount.setName(name);
        currentAccount.setStatus(Constants.ACCOUNT_OPEN);

        // updates account to the database
        this.accountViewModel.update(currentAccount);

    }

    public void foreignKeySample(){
        FineViewModel fineViewModel = ViewModelProviders.of(this).get(FineViewModel.class);
        Fine fine = new Fine();
        fine.setAmount(20);
        fine.setFineDate("20/10-/2019");
        fine.setAccountId(this.accountViewModel.getAccount().id);

        fineViewModel.createFine(fine);

    }

    /**
     * Form validation.
     * @return
     */
    public boolean checkValidation() {

        name = signupName.getText().toString().trim();
        email = signupEmail.getText().toString().trim();
        password = signupPassword.getText().toString().trim();
        phone = signupPhone.getText().toString().trim();
        DOB = signupDOB.getText().toString().trim();


        if (name.length() <= 0){
            Toast.makeText(RegisterAccountActivity.this, "Name is Required!", Toast.LENGTH_LONG).show();
            return false;
        } else if (email.length() <= 0){
            Toast.makeText(RegisterAccountActivity.this, "Email is Required!", Toast.LENGTH_LONG).show();
            return false;
        } else if (password.length() <= 0){
            Toast.makeText(RegisterAccountActivity.this, "Password is Required!", Toast.LENGTH_LONG).show();
            return false;
        } else if (password.length() < 6){
            Toast.makeText(RegisterAccountActivity.this, "Minimum Password Length is 6!", Toast.LENGTH_LONG).show();
            return false;
        } else if (phone.length() <=0){
            Toast.makeText(RegisterAccountActivity.this, "Phone Number is Required!", Toast.LENGTH_LONG).show();
            return false;
        } else if (phone.length() < 8){
            Toast.makeText(RegisterAccountActivity.this, "Minimum Phone Length is 8!", Toast.LENGTH_LONG).show();
            return false;
        } else if (DOB.length()<=0){
            Toast.makeText(RegisterAccountActivity.this, "DOB is Required!", Toast.LENGTH_LONG).show();
            return false;
        } else if (!checkBox.isChecked()){
            Toast.makeText(RegisterAccountActivity.this, "Please accept Terms & Conditions!", Toast.LENGTH_LONG).show();
            return false;
        }


        return true;
    }


    @Override
    public void createAccountPass() {
        Toast.makeText(this, "Account creation success.", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void createAccountFail() {
        Toast.makeText(this, "Account already exist.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnClear){
            signupName.setText("");
            signupEmail.setText("");
            signupPhone.setText("");
            signupPassword.setText("");
            rdGender.clearCheck();
        } else if (v.getId() == R.id.btnBack){
            finish();
        } else if (v.getId() == R.id.btnSubmit){
            if (checkValidation()) {
                registerUser();
            }
        } else if (v.getId() == R.id.tvTermsandCondition){
            customDialog = new CustomDialog(this);
            customDialog.show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}

