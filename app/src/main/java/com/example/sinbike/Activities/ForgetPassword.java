package com.example.sinbike.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sinbike.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ForgetPassword extends AppCompatActivity {

    private EditText etEmail;
    private Button resetPassword, btnBack;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        btnBack = findViewById(R.id.btnBack);
        resetPassword = findViewById(R.id.resetPassword);
        etEmail = findViewById(R.id.etEmail);
        firebaseAuth = FirebaseAuth.getInstance();

        resetPassword.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();

                if (email.equals("")){
                    Toast.makeText(ForgetPassword.this, "Please enter your registered email address!" , Toast.LENGTH_LONG).show();
                } else {
                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(ForgetPassword.this, "Password reset email sent!" , Toast.LENGTH_LONG).show();
                                finish();
                                startActivity(new Intent(ForgetPassword.this, LoginActivity.class));

                            } else {
                                Toast.makeText(ForgetPassword.this, "Error in sending email!" , Toast.LENGTH_LONG).show();

                            }
                        }
                    });
                }

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgetPassword.this, LoginActivity.class);
                startActivity(intent);
            }
        });


    }
}
