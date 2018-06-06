package com.example.alessandro.testbottom;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentFavoritos extends Fragment {

    private List<Problema> favoritos = new ArrayList<Problema>();
    private RecyclerView recyclerview;
    private ProblemAdapter qAdapter;
    private SharedPreferences mPrefs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_fragment_favoritos, container, false);
        recyclerview = (RecyclerView) v.findViewById(R.id.recycler_view2);
        qAdapter = new ProblemAdapter(favoritos);
        RecyclerView.LayoutManager qLayoutManager = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(qLayoutManager);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerview.setAdapter(qAdapter);
        recyclerview.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), recyclerview ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent myIntent = new Intent(getActivity(),Vista_Problema.class);
                        myIntent.putExtra("Problema",favoritos.get(position));
                        startActivity(myIntent);
                    }
                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
        actualiza();
        return v;
    }

    @Override
    public void onResume() {
        actualiza();
        super.onResume();
    }

    public void actualiza () {
        Log.i("actualizaa", "se actualiza");
        mPrefs = getActivity().getSharedPreferences("label", 0);
        String mString = mPrefs.getString("favoritos", null);
        Type type = new TypeToken<ArrayList<Problema>>() {}.getType();
        if (mString != null) {
            favoritos.clear();
            ArrayList<Problema> apoyo = new Gson().fromJson(mString, type);
            favoritos.addAll(apoyo);
            Log.i("mess",String.valueOf(apoyo.size()));
            qAdapter.notifyDataSetChanged();
        } else {
            Log.i("favoritus", "nofav");
        }
    }

    }