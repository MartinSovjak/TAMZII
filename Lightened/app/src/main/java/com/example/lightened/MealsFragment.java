package com.example.lightened;


import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MealsFragment extends Fragment implements MealsRecyclerAdapter.OnHeaderListener {
    private static final String TAG = "MealsFragment";
    private TextView twDate, twCalories;

    private ArrayList<Food> Foods = new ArrayList<>();
    /*
    MealsRecyclerAdapter breakfastAdapter, morningSnackAdapter;

    private RecyclerView recyclerBreakfast, recyclerMorningSnack;
*/

    MealsRecyclerAdapter mealsAdapter;
    private RecyclerView mealsRecycler;
    String dateString;

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

        dateString = getArguments().getString("message");
        twDate.setText(dateString);

        initNames(dateString);
        initRecyclerView(view);

        return view;
    }

    private void initNames(String dateString){

        //TODO - Vezmu string s datumem, zavolam databazi a naplnim recyclerView s hodnotami danych zaznamenanych jidel

        DatabaseHandler db = new DatabaseHandler(getContext());
        Foods = db.getMealsByDate(dateString);

        countCalories();

        db.close();


    }
    private void initRecyclerView(View view){

        mealsRecycler = view.findViewById(R.id.recyclerBreakfast);
        mealsAdapter = new MealsRecyclerAdapter(getContext(), Foods,this, this);

        mealsRecycler.setAdapter(mealsAdapter);
        mealsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void countCalories(){

        DatabaseHandler db = new DatabaseHandler(getContext());

        if(mealsRecycler != null) {
            mealsRecycler.invalidate();

        }

        Foods = db.getMealsByDate(dateString);

        int cal = 0;
        for(Food f : Foods){
            cal += f.calories;
        }
        String s = cal + " / 8000";
       twCalories.setText(s);

       //TODO - NEW ACTIVITY WITH ADDING FOOD - IMPLEMENT INTERFACE
       //Intent intent = new Intent(this, AddNewMeal.class);
       //startActivityForResult(intent, 100);
    }


    @Override
    public void onHeaderClick(int position) {
       Intent intent = new Intent(getContext(), AddNewMealActivity.class);
       intent.putExtra("type", position);
       intent.putExtra("date", dateString);
       startActivity(intent);

    }
}
