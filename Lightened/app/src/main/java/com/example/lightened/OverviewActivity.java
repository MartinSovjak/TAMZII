package com.example.lightened;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.DonutProgress;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class OverviewActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonMeals, buttonFood, buttonSettings;
    DonutProgress progressCalories, progressSugars, progressFats, progressProtein;
    TextView twCalories, twSugars, twFats, twProtein;


    private static int SUGAR_CALORIES = 4;
    private static int PROTEIN_CALORIES = 4;
    private static int FATS_CALORIES = 9;

    float calories, sugars, fats, protein;
    int caloriesSettings, sugarsSettings, fatsSettings, proteinSettings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        buttonMeals = findViewById(R.id.buttonMeals);
        buttonMeals.setOnClickListener(this);

        buttonFood = findViewById(R.id.buttonFood);
        buttonFood.setOnClickListener(this);

        buttonSettings = findViewById(R.id.buttonSettings);
        buttonSettings.setOnClickListener(this);

        twCalories = findViewById(R.id.twCalories);
        twFats = findViewById(R.id.twFats);
        twProtein = findViewById(R.id.twProtein);
        twSugars = findViewById(R.id.twSugars);


        progressCalories = findViewById(R.id.donut_progress);
        progressSugars = findViewById(R.id.dpCarbs);
        progressFats = findViewById(R.id.dpFat);
        progressProtein = findViewById(R.id.dpProtein);
    }

    @Override
    protected void onResume() {
        super.onResume();


        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String newDate = sdf.format(cal.getTime());



        DatabaseHandler db = new DatabaseHandler(this);

        ArrayList<Food> foods = db.getMealsByDate(newDate);

        calories = 0;
        sugars = 0;
        fats = 0;
        protein = 0;

        for(Food item : foods){
            calories += item.calories;
            sugars += item.sugars;
            fats += item.fats;
            protein += item.protein;
        }

        fillOverview();
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.buttonMeals:

                Intent intentMeals = new Intent(getApplicationContext(), MealsActivity.class);
                startActivity(intentMeals /* MEALS_ACTIVITY_RESULT*/);
                break;
            case R.id.buttonFood:
                Intent intentFood = new Intent(getApplicationContext(), AddFoodActivity.class);
                startActivity(intentFood /*ADD_FOOD_ACTIVITY_RESULT*/);
                break;
            case R.id.buttonSettings:
                Intent intentSettings = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intentSettings);

        }

    }

    public void fillOverview(){

        SharedPreferences pref = getApplicationContext().getSharedPreferences("Settings", MODE_PRIVATE);



        caloriesSettings = pref.getInt("calories", 8400);
        float sugarsPreferences = pref.getFloat("sugars",40 );
        float fatsPreferences = pref.getFloat("fats", 30);
        float proteinPreferences = pref.getFloat("protein", 30);


        int percentCalories = Math.round((float)calories / caloriesSettings*100);

        float onePercent = (float) ((caloriesSettings/4.2)/100);
        sugarsSettings = Math.round(onePercent * sugarsPreferences / SUGAR_CALORIES);
        fatsSettings = Math.round(onePercent * fatsPreferences / FATS_CALORIES);
        proteinSettings = Math.round(onePercent * proteinPreferences / PROTEIN_CALORIES);

        int percentSugars = Math.round((float)sugars/sugarsSettings*100);
        int percentFats = Math.round((float)fats/fatsSettings*100);
        int percentProtein = Math.round((float)protein/proteinSettings*100);

        fillProgress(progressCalories, percentCalories);
        fillProgress(progressFats, percentFats);
        fillProgress(progressSugars, percentSugars);
        fillProgress(progressProtein, percentProtein);
    }

    public void fillProgress(DonutProgress progress, int percent){

        if(percent > 100) {

            progress.setDonut_progress("100");

            String calText = percent + ".0%";
            progress.setText(calText);
            progress.setTextColor(Color.RED);
            progress.setFinishedStrokeColor(Color.RED);

        }

        else {

            progress.setDonut_progress(Integer.toString(percent));
            progress.setTextColor(Color.BLUE);
            progress.setFinishedStrokeColor(Color.BLUE);
        }

        String twCalText = calories + " / " + caloriesSettings;
        String twSugarsText = sugars + " / "+ sugarsSettings;
        String twFatsText = fats + " / " + fatsSettings;
        String twProteinText = protein + " / " + proteinSettings;

        twCalories.setText(twCalText);
        twSugars.setText(twSugarsText);
        twFats.setText(twFatsText);
        twProtein.setText(twProteinText);
    }
}
