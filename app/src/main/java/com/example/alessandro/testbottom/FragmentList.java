package com.example.alessandro.testbottom;


import android.content.Intent;
import android.database.DataSetObserver;
import android.os.AsyncTask;
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

    public void actualiza () {
        problemList.addAll(((MainActivity)getActivity()).getProblemas());
        qAdapter.notifyDataSetChanged();
    }
}