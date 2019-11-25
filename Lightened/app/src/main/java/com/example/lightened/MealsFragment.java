package com.example.lightened;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MealsFragment extends Fragment {

    private TextView twDate, twCalories;
    private LinearLayout layoutMeals;


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
        layoutMeals = view.findViewById(R.id.linearMeals);
        String dateString = getArguments().getString("message");
        twDate.setText(dateString);

        String calories = "6400 / 8000";
        twCalories.setText(calories);

        RecyclerView recyclerView = new RecyclerView(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        layoutMeals.addView(recyclerView);

        return view;


    }

}
