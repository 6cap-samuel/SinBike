package com.example.sinbike.Activities;


import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.sinbike.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopUpFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_top_up, container, false);
        Button topupBtn = view.findViewById(R.id.topupBtn);

        EditText amt = view.findViewById(R.id.amt);

        amt.setFilters(new InputFilter[]{ new TopUpAmountRange("10", "50")});

        topupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),PaymentMethodActivity.class);
                startActivity(i);
            }
        });
        return view;
    }
}
