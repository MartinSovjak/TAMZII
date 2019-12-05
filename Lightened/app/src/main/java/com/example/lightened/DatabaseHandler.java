package com.example.lightened;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "lightenedManager";
    private static final String TABLE_FOOD_JOURNAL = "foodjournal";
    private static final String TABLE_FOOD_DATABASE = "fooddatabase";
    private static final String KEY_ID = "id";
    private static final String KEY_IMG = "img";
    private static final String KEY_MEAL = "meal";
    private static final String KEY_DATE = "date";
    private static final String KEY_NAME = "name";
    private static final String KEY_GRAMS = "grams";
    private static final String KEY_CALORIES = "calories";
    private static final String KEY_SUGARS = "sugars";
    private static final String KEY_FATS = "fats";
    private static final String KEY_PROTEIN = "protein";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_FOODJOURNAL_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_FOOD_JOURNAL + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_MEAL + " INTEGER,"
                + KEY_DATE + " TEXT,"
                + KEY_NAME + " TEXT,"
                + KEY_GRAMS + " INTEGER,"
                + KEY_CALORIES + " INTEGER,"
                + KEY_SUGARS + " REAL,"
                + KEY_FATS + " REAL,"
                + KEY_PROTEIN + " REAL"
                + ")";
        db.execSQL(CREATE_FOODJOURNAL_TABLE);


        String CREATE_FOODDATABASE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_FOOD_DATABASE + "("
                + KEY_NAME + " TEXT PRIMARY KEY,"
                + KEY_IMG + " TEXT,"
                + KEY_CALORIES + " INTEGER,"
                + KEY_SUGARS + " REAL,"
                + KEY_FATS + " REAL,"
                + KEY_PROTEIN + " REAL"
                + ")";
        db.execSQL(CREATE_FOODDATABASE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOD_JOURNAL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOD_DATABASE);
        onCreate(db);
    }

    public void addToJournal(Food food) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_MEAL, food.meal);
        values.put(KEY_DATE, food.date);
        values.put(KEY_NAME, food.name);
        values.put(KEY_GRAMS, food.grams);
        values.put(KEY_CALORIES, food.calories);
        values.put(KEY_SUGARS, food.sugars);
        values.put(KEY_FATS, food.fats);
        values.put(KEY_PROTEIN, food.protein);

        db.insert(TABLE_FOOD_JOURNAL, null, values);
        db.close();
    }

    public void addToFoodDatabase(FoodDatabaseItem food){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_NAME, food.name);
        values.put(KEY_IMG, food.img);
        values.put(KEY_CALORIES, food.calories);
        values.put(KEY_SUGARS, food.sugars);
        values.put(KEY_FATS, food.fats);
        values.put(KEY_PROTEIN, food.protein);

        db.insert(TABLE_FOOD_DATABASE, null, values);
        db.close();
    }

    public ArrayList<Food> getMealsByDate(String date) {

        ArrayList<Food> mealList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_FOOD_JOURNAL
                + " WHERE date='" + date + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Food f = new Food();
                f.id = Integer.parseInt(cursor.getString(0));

                f.meal = Integer.parseInt(cursor.getString(1));
                f.date = cursor.getString(2);
                f.name = cursor.getString(3);
                f.grams = Integer.parseInt(cursor.getString(4));
                f.calories = Integer.parseInt(cursor.getString(5));
                f.sugars = Double.parseDouble(cursor.getString(6));
                f.fats = Double.parseDouble(cursor.getString(7));
                f.protein = Double.parseDouble(cursor.getString(8));

                mealList.add(f);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return mealList;
    }

    public void deleteMeal(Food f) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FOOD_JOURNAL, KEY_ID + "  = ?", new String[]{String.valueOf(f.id)});
        db.close();
    }

    public int getMealsCount() {

        String countQuery = "SELECT * FROM " + TABLE_FOOD_JOURNAL;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }

    public int getFoodDatabaseCount() {

        String countQuery = "SELECT * FROM " + TABLE_FOOD_DATABASE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }

    public List<FoodEntry> getFoods(){

        String getAllFoods = "SELECT * FROM " + TABLE_FOOD_DATABASE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(getAllFoods, null);

        List<FoodEntry> entries = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                FoodEntry f = new FoodEntry();
                f.name = cursor.getString(0);
                f.img = cursor.getString(1);
                f.calories = Integer.parseInt(cursor.getString(2));
                f.sugars = Double.parseDouble(cursor.getString(3));
                f.fats = Double.parseDouble(cursor.getString(4));
                f.protein = Double.parseDouble(cursor.getString(5));

                entries.add(f);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return entries;
    }

    public boolean foodExists(String name){

        String getFoodQuery = "SELECT * FROM " + TABLE_FOOD_DATABASE + " WHERE " + KEY_NAME + " = '" + name + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(getFoodQuery, null);


        int count = cursor.getCount();


        cursor.close();
        db.close();

        if(count > 0)
            return true;
        else {
            return false;
        }
    }

    public int getLastId(){
        String query = "SELECT MAX(" + KEY_ID + ") as id FROM " + TABLE_FOOD_JOURNAL;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        int id = 0;
        while(cursor.moveToNext()){
            id = cursor.getInt(0);
        }

        return id;
    }
}
