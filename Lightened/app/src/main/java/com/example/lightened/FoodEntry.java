package com.example.lightened;

import android.graphics.Bitmap;

public class FoodEntry {

    public String name;
    public String img;
    public int calories;
    public double sugars, fats, protein;

    public FoodEntry(String name, String img, int calories, double sugars, double fats, double protein) {
        this.name = name;
        this.img = img;
        this.calories = calories;
        this.sugars = sugars;
        this.fats = fats;
        this.protein = protein;
    }

    public FoodEntry(){}


}

