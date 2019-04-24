package com.example.sinbike.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.sinbike.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterAccount extends AppCompatActivity  {

    EditText signupName, signupEmail, signupPassword, signupPhone, signupDOB;
    RadioGroup rdGender;
    RadioButton rbMale, rbFemale;
    RadioButton radioButtonOptions;
    Button btnSubmit, btnClear, btnBack;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    Long no;

    String name;
    String gender;
    String email;
    String password;
    String phone;
    String DOB;
    String accountid;

    private ArrayList<String> lst = new ArrayList<>();


    // variable to hold the gender value
    private String genderOptions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_account);
        mAuth = FirebaseAuth.getInstance();


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

        //database reference pointing to root of database
        databaseReference = FirebaseDatabase.getInstance().getReference("account");

        //database reference pointing to demo node
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                    no=(dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    registerUser();
                if (checkValidation()) {
                    databaseReference.child(String.valueOf(accountid)).child("accountId").setValue(accountid);
                    databaseReference.child(String.valueOf(accountid)).child("name").setValue(name);
                    databaseReference.child(String.valueOf(accountid)).child("gender").setValue(gender);
                    databaseReference.child(String.valueOf(accountid)).child("email").setValue(email);
                    databaseReference.child(String.valueOf(accountid)).child("telephoneNumber").setValue(phone);
                    databaseReference.child(String.valueOf(accountid)).child("dateOfBirth").setValue(DOB);
                    databaseReference.child(String.valueOf(accountid)).child("gender").setValue(genderOptions);
                    Toast.makeText(RegisterAccount.this, "Successfully Registered", Toast.LENGTH_LONG).show();
                }

            }
        });

        btnClear.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                signupName.setText("");
                signupEmail.setText("");
                signupPhone.setText("");
                signupPassword.setText("");
                signupDOB.setText("");
                rdGender.clearCheck();

            }
        });


        btnBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterAccount.this , MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }



    @Override
    protected void onStart() {
        super.onStart();
        // check if user is already signed in
        if(mAuth.getCurrentUser() != null){

        }
    }

    private void registerUser(){
         name = signupName.getText().toString().trim();
         email = signupEmail.getText().toString().trim();
         password = signupPassword.getText().toString().trim();
         phone = signupPhone.getText().toString().trim();
         DOB = signupDOB.getText().toString().trim();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseDatabase.getInstance().getReference("account")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                        }

                    }
                });
        }

        public boolean checkValidation() {
            // ---- VALIDATION -----//

            // to check if name is empty
            if (name.length() <=0){
                Toast.makeText(RegisterAccount.this, "Name is Required!", Toast.LENGTH_LONG).show();
                return false;
            }

            else if (email.length() <=0){
                Toast.makeText(RegisterAccount.this, "Email is Required!", Toast.LENGTH_LONG).show();
                return false;
            }

            // to check if its a valid email
//        if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
//            signupEmail.setError("Please enter a valid email address");
//            signupEmail.requestFocus();
//            return;
//        }

           else if (password.length() <=0){
                Toast.makeText(RegisterAccount.this, "Password is Required!", Toast.LENGTH_LONG).show();
                return false;
            }

           else if (password.length() < 6){
                Toast.makeText(RegisterAccount.this, "Minimum Password Length is 6!", Toast.LENGTH_LONG).show();
                return false;
            }

           else if (phone.length() <=0){
                Toast.makeText(RegisterAccount.this, "Phone Number is Required!", Toast.LENGTH_LONG).show();
                return false;
            }

//        if (Patterns.PHONE.matcher(phone).matches()){
//            signupPhone.setError("Please enter valid phone number");
//            signupPhone.requestFocus();
//            return;
//        }

            else if (phone.length() < 8){
                Toast.makeText(RegisterAccount.this, "Minimum Phone Length is 8!", Toast.LENGTH_LONG).show();
                return false;
            }

            else if (DOB.length()<=0){
                Toast.makeText(RegisterAccount.this, "DOB is Required!", Toast.LENGTH_LONG).show();
                return false;
        }
        return true;
    }


}

