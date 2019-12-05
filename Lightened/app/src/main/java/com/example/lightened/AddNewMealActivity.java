package com.example.lightened;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class AddNewMealActivity extends AppCompatActivity implements View.OnClickListener{
    Button fill, addFood;
    AutoCompleteTextView filter;
    private List<FoodEntry> foodEntryList;

    private int calories, grams;
    private double sugars,fats,protein;
    private String name;

    EditText Name,Grams,Calories,Fats,Sugars,Protein;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_meal);

        int type = getIntent().getExtras().getInt("type", -1);
        String date = getIntent().getExtras().getString("date", null);

        fillAutocompleteList();

        filter = findViewById(R.id.autoCompleteSearch);
        Name = findViewById(R.id.etName);
        Grams = findViewById(R.id.etGrams);
        Calories = findViewById(R.id.etCalories);
        Sugars = findViewById(R.id.etSugars);
        Fats = findViewById(R.id.etFats);
        Protein = findViewById(R.id.etProtein);

        SearchArrayAdapter adapter = new SearchArrayAdapter(this, foodEntryList);
        filter.setAdapter(adapter);

        Grams.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(Grams.hasFocus() && s.length() > 0) {
                    grams = Integer.parseInt(s.toString());


                    countValues();
                }
                if (s.length() == 0) {
                    grams = 0;
                    countValues();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 0){
                    Name.setFocusableInTouchMode(true);
                    Calories.setFocusable(true);

                    Sugars.setFocusable(true);
                    Sugars.setBackgroundColor(Color.TRANSPARENT);
                    Protein.setFocusable(true);
                    Protein.setBackgroundColor(Color.TRANSPARENT);
                    Fats.setFocusable(true);
                    Fats.setBackgroundColor(Color.TRANSPARENT);

                    grams = 0;
                    Grams.setText("");
                    Name.setText("");
                    countValues();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        filter.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
                Object item = parent.getItemAtPosition(position);
                if (item instanceof FoodEntry){
                    FoodEntry foodEntry=(FoodEntry) item;
                    name = foodEntry.name;
                    grams = 100;
                    calories = foodEntry.calories;
                    sugars = foodEntry.sugars;
                    protein = foodEntry.protein;
                    fats = foodEntry.fats;

                    Name.setText(name);
                    Grams.setText(""+grams);

                    countValues();
                    Name.setFocusable(false);
                    Name.setBackgroundColor(Color.TRANSPARENT);
                    Calories.setFocusable(false);
                    Calories.setBackgroundColor(Color.TRANSPARENT);
                    Sugars.setFocusable(false);
                    Sugars.setBackgroundColor(Color.TRANSPARENT);
                    Protein.setFocusable(false);
                    Protein.setBackgroundColor(Color.TRANSPARENT);
                    Fats.setFocusable(false);
                    Fats.setBackgroundColor(Color.TRANSPARENT);
                }
            }
        });

        addFood = findViewById(R.id.bAddMeal);

        addFood.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.bAddMeal:
                addMeal();
                break;
        }

    }

    public void addMeal(){

    }

    public void fillAutocompleteList(){
            foodEntryList = new ArrayList<>();

          //  DatabaseHandler db = new DatabaseHandler(this);
            //foodEntryList = db.getFoods();
        // TODO - DATABASE CONNECTION
        foodEntryList.add(new FoodEntry("rohlik","img", 500, 30, 20 , 10));
        foodEntryList.add(new FoodEntry("chlebik","img", 400, 40, 50 , 60));
    }

    public void countValues(){

        double finalCalories = calories*grams/100;
        double finalSugars = sugars*grams/100;
        double finalProtein = protein * grams/100;
        double finalFats = fats * grams/100;


        Calories.setText(""+finalCalories);
        Sugars.setText(""+finalSugars);
        Protein.setText(""+finalProtein);
        Fats.setText(""+finalFats);
    }
}
