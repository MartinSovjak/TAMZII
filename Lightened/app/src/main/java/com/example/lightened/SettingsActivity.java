package com.example.lightened;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etCal, etSug, etFat, etPro;
    Button submit;
    TextView sum;

    int calories;
    float sugars, fats, protein;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        etCal = findViewById(R.id.etCal);
        etSug = findViewById(R.id.etSug);
        etFat = findViewById(R.id.etFat);
        etPro = findViewById(R.id.etPro);

        sum = findViewById(R.id.twSum);

        submit = findViewById(R.id.bChangePref);

        submit.setOnClickListener(this);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("Settings", MODE_PRIVATE);

        calories = pref.getInt("calories", 8400);
        sugars = pref.getFloat("sugars", 40f);
        fats = pref.getFloat("fats", 30f);
        protein = pref.getFloat("protein", 30f);

        etCal.setText(""+calories);
        etSug.setText(Float.toString(sugars));
        etFat.setText(Float.toString(fats));
        etPro.setText(Float.toString(protein));

        setSum();

        setListeners();
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()) {
            case R.id.bChangePref:
                changePref();
                finish();
        }
    }

    public void changePref(){

        String sumString = sum.getText().toString();

        double sumDouble = Float.parseFloat(sumString);

        if(sumDouble != 100){
            Toast.makeText(this, "sum of percentages has to be 100%", Toast.LENGTH_SHORT).show();
            return;
        }

        if(calories == 0){
            Toast.makeText(this, "Enter Calories", Toast.LENGTH_SHORT).show();
            return;
        }


        SharedPreferences pref = getApplicationContext().getSharedPreferences("Settings", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putInt("calories", calories);
        editor.putFloat("sugars", sugars);
        editor.putFloat("fats", fats);
        editor.putFloat("protein", protein);
        editor.apply();



    }

    public void setSum(){
        float finalPercent = sugars + fats + protein;

        sum.setText(Float.toString(finalPercent));
    }

    public void setListeners(){

        etCal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(etCal.hasFocus() && s.length() > 0) {
                    calories = Integer.parseInt(etCal.getText().toString());
                }
                else {
                    calories = 0;
                }
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        etSug.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(etSug.hasFocus() && s.length() > 0) {
                    sugars = Float.parseFloat(etSug.getText().toString());

                }
                else {
                    sugars = 0;
                }

                setSum();
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        etFat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(etFat.hasFocus() && s.length() > 0) {
                    fats = Float.parseFloat(etFat.getText().toString());

                }
                else {
                    fats = 0;
                }

                setSum();
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        etPro.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(etPro.hasFocus() && s.length() > 0) {
                    protein = Float.parseFloat(etPro.getText().toString());

                }
                else {
                    protein = 0;
                }

                setSum();
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });


    }
}
