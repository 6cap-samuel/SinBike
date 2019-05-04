package com.example.sinbike.Activities;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sinbike.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class BalanceFragment extends Fragment {

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    DatabaseReference dbref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_balance, container, false);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        TextView accBalTxt = (TextView)view.findViewById(R.id.accBalance);

        dbref = FirebaseDatabase.getInstance().getReference();


        // Inflate the layout for this fragment
        return view;

    }

}
