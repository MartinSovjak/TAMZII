package com.example.lightened;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MealsFragment extends Fragment {



    private TextView twDate, twCalories;

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImages = new ArrayList<>();



    public MealsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meals, container, false);

        twDate = view.findViewById(R.id.txt_display);
        twCalories = view.findViewById(R.id.mealsCalories);

        String dateString = getArguments().getString("message");
        twDate.setText(dateString);

        String calories = "6400 / 8000";
        twCalories.setText(calories);

        initNames();
        initRecyclerView(view);


        return view;


    }

    private void initNames(){

        //TODO - Vezmu string s datumem, zavolam databazi a naplnim recyclerView s hodnotami danych zaznamenanych jidel
        mNames.add("hello");
        mNames.add("Washington");
        mNames.add("I want");
        mNames.add("To help you");
        mNames.add("Get Famous");
        mImages.add("Hello aswell");
    }
    private void initRecyclerView(View view){


        RecyclerView recyclerView = view.findViewById(R.id.recyclerBreakfast);
        MealsRecyclerAdapter breakfastAdapter = new MealsRecyclerAdapter(getContext(), mNames, mImages);
        recyclerView.setAdapter(breakfastAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


    }



}
