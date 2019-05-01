package com.example.sinbike.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sinbike.Observers.LoginObserver;
import com.example.sinbike.POJO.Account;
import com.example.sinbike.R;
import com.example.sinbike.ViewModels.AccountViewModel;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public class LoginActivity extends AppCompatActivity implements LoginObserver, View.OnClickListener{

    // TAG for debug purpose.
    private static final String TAG = "LoginActivity";

    /**
     * Declaration of components.
     */
    Button btnSignup, btnLogin;
    EditText etEmail, etPassword;
    ProgressBar progressBar;
    TextView etForgetPassword;

    /**
     * Declaration of ViewModel
     */
    AccountViewModel accountViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.initViewModel();
        if (this.accountViewModel.getAccount() != null){
            this.loginSuccess(this.accountViewModel.getAccount());
        }

        this.init();
    }

    public void init() {
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnSignup = findViewById(R.id.btnSignup);
        btnLogin = findViewById(R.id.btnLogin);
        progressBar = findViewById(R.id.progressBar);
        etForgetPassword = findViewById(R.id.etForgetPassword);

        btnSignup.setOnClickListener(this);
        etForgetPassword.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

    }

    public void initViewModel(){
        this.accountViewModel = ViewModelProviders.of(this).get(AccountViewModel.class);
        this.accountViewModel.setLifecycleOwner(this);
        this.accountViewModel.setLoginObserver(this);
    }


    @Override
    public void loginSuccess(Account account) {
        Intent intent = new Intent(this, ManageDashboardActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void loginFailed() {
        Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSignup){
            Intent intent = new Intent(this, RegisterAccountActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.etForgetPassword){
            Intent intent = new Intent(this, ForgotPasswordActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btnLogin){
            this.login();
        }
    }

    public void login(){
        String email = etEmail.getText().toString().toLowerCase();
        final String password = etPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please Enter Email Address", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            etPassword.setError(getString(R.string.error_invalid_password));
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        accountViewModel.loginAccount(email, password);
    }
}