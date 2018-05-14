package com.example.alessandro.testbottom;


import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentList extends Fragment {

    private List<Problema> problemList = new ArrayList<>();
    private RecyclerView recyclerview;
    private ProblemAdapter qAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_fragment_list, container, false);

        recyclerview = (RecyclerView) v.findViewById(R.id.recycler_view);
        qAdapter = new ProblemAdapter(problemList);
        RecyclerView.LayoutManager qLayoutManager = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(qLayoutManager);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerview.setAdapter(qAdapter);
        recyclerview.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), recyclerview ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent myIntent = new Intent(getActivity(),Vista_Problema.class);
                        myIntent.putExtra("Problema",problemList.get(position));
                        startActivity(myIntent);
                    }
                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        try{
            problemList.clear();
            prepareProbData();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /*
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
    }*/

    private Problema genProb(String name){
       Problema qNew = new Problema(name, "Descripcion Test");
        return qNew;
    }

    private void prepareProbData() throws Exception{

        try{
            problemList.add(genProb("Problema 1"));
            //problemList.add(genProb("Problema 2"));
            //problemList.add(genProb("Problema 3"));

            qAdapter.notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}