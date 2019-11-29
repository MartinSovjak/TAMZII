package com.example.lightened;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

public class AddFoodActivity extends AppCompatActivity implements View.OnClickListener  {

    Button takePicture, getFromLibrary, addFood;
    EditText name, calories, sugars, fats, protein;
    Bitmap imgBitmap;
    private static final int PICK_PHOTO_FROM_GALLERY = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        takePicture = findViewById(R.id.bTake);
        getFromLibrary = findViewById(R.id.bGet);
        addFood = findViewById(R.id.bAdd);

        takePicture.setOnClickListener(this);
        getFromLibrary.setOnClickListener(this);
        addFood.setOnClickListener(this);

        name = findViewById(R.id.etName);
        calories = findViewById(R.id.etCalories);
        sugars = findViewById(R.id.etSugars);
        fats = findViewById(R.id.etFats);
        protein = findViewById(R.id.etProtein);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_PHOTO_FROM_GALLERY && resultCode == Activity.RESULT_OK){
            if(data == null){
                Toast.makeText(this, "You didn't choose a picture", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                InputStream inputStream = getApplicationContext().getContentResolver().openInputStream(data.getData());
                imgBitmap = BitmapFactory.decodeStream(inputStream);
                ImageView v = findViewById(R.id.imgFinal);
                v.setImageBitmap(imgBitmap);
                /*
                File imgFile = new  File(getFilesDir() + "hello.png");
                if(imgFile.exists()){
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    ImageView view = findViewById(R.id.imgFinal);
                    view.setImageBitmap(myBitmap);
                }
                */
            } catch (FileNotFoundException e) {
                Toast.makeText(this, "File not found.", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.bTake:
                getImgFromCamera();
                break;
            case R.id.bGet:
                getImgFromLibrary();
                break;
            case R.id.bAdd:
                sendItemToDatabase();
                break;
        }

    }

    public void getImgFromCamera(){
        File imgFile = new  File(getFilesDir() + name.getText().toString());
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            ImageView view = findViewById(R.id.imgFinal);
            view.setImageBitmap(myBitmap);
    }

    }

    public void getImgFromLibrary(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_PHOTO_FROM_GALLERY);
    }

    public void sendItemToDatabase(){
        FileOutputStream out;
        if(TextUtils.isEmpty(calories.getText()) || TextUtils.isEmpty(sugars.getText()) || TextUtils.isEmpty(fats.getText()) ||
                TextUtils.isEmpty(protein.getText()) || TextUtils.isEmpty(name.getText())){
            Toast.makeText(this, "Fill all columns.", Toast.LENGTH_SHORT).show();
            return;
        }
        // TODO - UNABLE TO MATCH FILES
        String foodName = name.getText().toString();
        String foodImg = name.getText().toString() + ".png";
        int foodCalories = Integer.parseInt(calories.getText().toString());
        double foodSugars = Double.parseDouble(sugars.getText().toString());
        double foodFats = Double.parseDouble(fats.getText().toString());
        double foodProtein = Double.parseDouble(protein.getText().toString());

        try {
            out = new FileOutputStream(getFilesDir() + name.getText().toString());
            imgBitmap.compress(Bitmap.CompressFormat.PNG, 90, out);

            FoodDatabaseItem food = new FoodDatabaseItem(foodName, foodImg, foodCalories, foodSugars, foodFats, foodProtein);

            DatabaseHandler db = new DatabaseHandler(this);
            // TODO - CHECK IF FOOD EXISTS - NO DUPLICATES
            //db.addToFoodDatabase(food);
            int count = db.getFoodDatabaseCount();
            Toast.makeText(this, "" + count, Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(this, "Unable to save a picture or insert into database.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}

