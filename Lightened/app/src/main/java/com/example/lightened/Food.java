package com.example.lightened;

public class Food {
    public int id;
    public String img;
    public int meal;
    public String date;
    public String name;
    public int grams;
    public int calories;
    public double sugars;
    public double fats;
    public double protein;


    public Food(int id, String img, int meal, String date, String name, int grams, int calories, double sugars, double fats, double protein) {
        this.id = id;
        this.img = img;
        this.meal = meal;
        this.date = date;
        this.name = name;
        this.grams = grams;
        this.calories = calories;
        this.sugars = sugars;
        this.fats = fats;
        this.protein = protein;
    }

    public Food(){}
}
