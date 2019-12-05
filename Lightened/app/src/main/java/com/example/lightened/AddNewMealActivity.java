package com.example.lightened;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
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
    int type;
    String date;

    private static int GET_MEAL_INFO = 10;
    private static int NO_MEAL_INFO = 400;

    EditText Name,Grams,Calories,Fats,Sugars,Protein;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_meal);

        type = getIntent().getExtras().getInt("type", -1);
        date = getIntent().getExtras().getString("date", null);

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

        if(TextUtils.isEmpty(Name.getText()) || TextUtils.isEmpty(Sugars.getText()) || TextUtils.isEmpty(Fats.getText()) ||
                TextUtils.isEmpty(Protein.getText()) || TextUtils.isEmpty(Grams.getText()) || TextUtils.isEmpty(Calories.getText())){

            return;
        }
            Intent intent = new Intent();
            intent.putExtra("name", Name.getText().toString());
            intent.putExtra("grams", Integer.parseInt(Grams.getText().toString()) );
            intent.putExtra("calories", Integer.parseInt(Calories.getText().toString()));
            intent.putExtra("sugars", Double.parseDouble(Sugars.getText().toString()));
            intent.putExtra("fats", Double.parseDouble(Fats.getText().toString()));
            intent.putExtra("protein", Double.parseDouble(Protein.getText().toString()));
            intent.putExtra("type", type);
            intent.putExtra("date", date);

            setResult(GET_MEAL_INFO, intent);
            finish();
    }

    public void fillAutocompleteList(){
            foodEntryList = new ArrayList<>();

            DatabaseHandler db = new DatabaseHandler(this);
            foodEntryList = db.getFoods();

    }

    public void countValues(){

        double finalCalories = calories*grams/100;
        double finalSugars = sugars*grams/100;
        double finalProtein = protein * grams/100;
        double finalFats = fats * grams/100;


        Calories.setText(""+Math.round(finalCalories));
        Sugars.setText(""+finalSugars);
        Protein.setText(""+finalProtein);
        Fats.setText(""+finalFats);
    }
}
