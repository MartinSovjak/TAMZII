package com.example.lightened;


import android.database.DataSetObserver;
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

    private ArrayList<Food> Foods = new ArrayList<>();

    MealsRecyclerAdapter breakfastAdapter, morningSnackAdapter;

    private RecyclerView recyclerBreakfast, recyclerMorningSnack;

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

        initNames(dateString);
        initRecyclerView(view);

        return view;
    }

    private void initNames(String dateString){

        //TODO - Vezmu string s datumem, zavolam databazi a naplnim recyclerView s hodnotami danych zaznamenanych jidel

        DatabaseHandler db = new DatabaseHandler(getContext());
        Foods = db.getMealsByDate(dateString);

        countCalories(Foods);

        db.close();


    }
    private void initRecyclerView(View view){
        recyclerBreakfast = view.findViewById(R.id.recyclerBreakfast);
        breakfastAdapter = new MealsRecyclerAdapter(getContext(), Foods, 0, this);
        recyclerBreakfast.setAdapter(breakfastAdapter);
        recyclerBreakfast.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerMorningSnack = view.findViewById(R.id.recyclerMorningSnack);
        morningSnackAdapter = new MealsRecyclerAdapter(getContext(), Foods, 1, this);
        recyclerMorningSnack.setAdapter(morningSnackAdapter);
        recyclerMorningSnack.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void countCalories(ArrayList<Food> foods){
        Foods = foods;

        int cal = 0;
        for(Food f : Foods){
            cal += f.calories;
        }
        String s = cal + " / 8000";
       twCalories.setText(s);
    }





}
