package com.example.alessandro.testbottom;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAccount extends Fragment {

    public Button cerrar;

    public FragmentAccount() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_fragment_account, container, false);
        cerrar = (Button)v.findViewById(R.id.button_cerrar);
        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContext().getSharedPreferences("label", 0).edit().clear().commit();
                Intent myIntent = new Intent(getActivity(),ActivityLogin.class);
                startActivity(myIntent);
                getActivity().finish();
            }
        });
        return v;

    }

}
