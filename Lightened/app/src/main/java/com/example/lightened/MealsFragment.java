package com.example.lightened;


import android.content.Intent;
import android.content.SharedPreferences;
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
import java.util.Collections;
import java.util.Comparator;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class MealsFragment extends Fragment implements MealsRecyclerAdapter.OnHeaderListener, MealsRecyclerAdapter.OnItemListener {
    private static final String TAG = "MealsFragment";
    private TextView twDate, twCalories;

    private ArrayList<Food> Foods = new ArrayList<>();
    private int[] countFoodsPerMeal = {0, 0, 0, 0, 0};
    /*
    MealsRecyclerAdapter breakfastAdapter, morningSnackAdapter;

    private RecyclerView recyclerBreakfast, recyclerMorningSnack;
*/
    private static int GET_MEAL_INFO = 10;
    private static int NO_MEAL_INFO = 400;
    MealsRecyclerAdapter mealsAdapter;
    private RecyclerView mealsRecycler;
    String dateString;
    int selectedMeal;

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
        mealsAdapter = new MealsRecyclerAdapter(getContext(), Foods,this, this, this);

        setCountFoodsPerMeal();
        mealsRecycler.setAdapter(mealsAdapter);
        mealsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void countCalories(){


        int cal = 0;
        for(Food f : Foods){
            cal += f.calories;
        }

        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("Settings", MODE_PRIVATE);

        int finalCal = pref.getInt("calories", 8400);

        String s = cal + " / " + finalCal;
       twCalories.setText(s);


       //Intent intent = new Intent(this, AddNewMeal.class);
       //startActivityForResult(intent, 100);
    }


    @Override
    public void onHeaderClick(int meal) {
       Intent intent = new Intent(getContext(), AddNewMealActivity.class);
       intent.putExtra("type", meal);
       intent.putExtra("date", dateString);


       startActivityForResult(intent, GET_MEAL_INFO);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try{
            if(requestCode == NO_MEAL_INFO){
                return;
            }
            if(requestCode == GET_MEAL_INFO){
                String name = data.getStringExtra("name");
                int grams = data.getIntExtra("grams", -1);
                int calories = data.getIntExtra("calories", -1);
                double sugars = data.getDoubleExtra("sugars", -1);
                double fats = data.getDoubleExtra("fats", -1);
                double protein = data.getDoubleExtra("protein", -1);
                int type = data.getIntExtra("type", -1);
                String date = data.getStringExtra("date");

                Food f = new Food();
                f.name = name;
                f.meal = type;
                f.date = date;
                f.grams = grams;
                f.calories = calories;
                f.sugars = sugars;
                f.fats = fats;
                f.protein = protein;

                try {
                    DatabaseHandler db = new DatabaseHandler(getContext());
                    db.addToJournal(f);

                    f.id = db.getLastId();

                    Foods.add(f);
                    sortFoods();
                    setCountFoodsPerMeal();
                    countCalories();
                    mealsAdapter.notifyDataSetChanged();

                }
                catch (Exception e){
                    Log.e(TAG, "onActivityResult: ", e );
                }
            }
        }
        catch (Exception e){

        }
    }

    public void sortFoods(){
        Collections.sort(Foods, new Comparator<Food>(){
            public int compare(Food f1, Food f2){
                return f1.meal - f2.meal;
            }
        });
    }

    public void setCountFoodsPerMeal(){

        for(int i = 0; i < 5; i++){
            int count = 0;

            for(Food f : Foods){
                if (f.meal == i){
                    count++;
                }
            }
            this.countFoodsPerMeal[i] = count;
        }
        mealsAdapter.setCountFoodsPerMeal(this.countFoodsPerMeal);
    }

    @Override
    public void onItemClick(int position, int type) {
        int finalpos = position - (type+1);
        Food f = Foods.get(finalpos);
        Foods.remove(finalpos);

        DatabaseHandler db = new DatabaseHandler(getContext());
        db.deleteMeal(f);

        mealsAdapter.notifyDataSetChanged();
        countCalories();
        setCountFoodsPerMeal();
    }
}
