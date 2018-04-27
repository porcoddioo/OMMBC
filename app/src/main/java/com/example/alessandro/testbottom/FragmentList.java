package com.example.alessandro.testbottom;


import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentList extends Fragment {

    private ListView lista_problemas;


    public FragmentList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        lista_problemas = (ListView) getView().findViewById(R.id.lista_prob);
        ArrayList<String> problemas = new ArrayList<String>();
        // Llena el ArrayList de prueba
        for(int i = 0 ; i < 50 ; i++) problemas.add("Problema "+(i+1));
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext() ,android.R.layout.simple_list_item_1, problemas);
        lista_problemas.setAdapter(adapter);
        lista_problemas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), Vista_Problema.class);
                intent.putExtra("numero",position);
                startActivity(intent);
            }
        });
    }
}