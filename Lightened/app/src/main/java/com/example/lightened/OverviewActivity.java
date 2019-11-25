package com.example.lightened;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OverviewActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonMeals;

    int MEALS_ACTIVITY_RESULT = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        buttonMeals = findViewById(R.id.buttonMeals);

        buttonMeals.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.buttonMeals:
                Intent intentMeals = new Intent(getApplicationContext(), MealsActivity.class);
                startActivityForResult(intentMeals, MEALS_ACTIVITY_RESULT);
                break;
        }

    }
}
