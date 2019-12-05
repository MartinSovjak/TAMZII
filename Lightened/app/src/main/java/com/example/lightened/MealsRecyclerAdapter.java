package com.example.lightened;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MealsRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "MealsRecyclerAdapter";


    private ArrayList<Food> Foods;
    private Context mContext;
    private MealsFragment fragment;

    private int typeOfMeal;

    private String[] types = {"breakfast", "morning snack", "lunch", "afternoon snack", "dinner"};


    private int[] countFoodsPerMeal = {0, 0, 0, 0, 0};
    private int countMeals;

    private OnHeaderListener headerListener;


    private static final int TYPE_HEADER = 1;
    private static final int TYPE_CONTENT = 2;

    public MealsRecyclerAdapter( Context mContext, ArrayList<Food> Foods,MealsFragment m, OnHeaderListener headerListener) {



        this.Foods = Foods;
        setCountFoodsPerMeal();
        sortFoods();
        this.mContext = mContext;
        this.fragment = m;
        this.headerListener = headerListener;

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

    }

    public void sortFoods(){
        Collections.sort(Foods, new Comparator<Food>(){
            public int compare(Food f1, Food f2){
                return f2.meal - f1.meal;
            }
        });
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Log.d(TAG, "onCreateViewHolder: called.");
        if(viewType == TYPE_CONTENT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
            ContentViewHolder holder = new ContentViewHolder(view);

            return holder;
        }
        else{

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_headeritem, parent, false);
            HeaderViewHolder holder = new HeaderViewHolder(view, headerListener);

            return holder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        if(getItemViewType(position) == TYPE_CONTENT) {
            ((ContentViewHolder) holder).setContent(Foods.get(position - (countMeals+1)));
        }
        else{
            ((HeaderViewHolder) holder).setContent(countMeals);
        }
    }

    @Override
    public int getItemCount() {
        return Foods.size()+5;
    }

    @Override
    public int getItemViewType(int position) {

        if(position == 0){
            countMeals = 0;
            return TYPE_HEADER;
        }
        else if(position == countFoodsPerMeal[0] + 1){
            countMeals = 1;
            return TYPE_HEADER;
        }

        else if(position == countFoodsPerMeal[0] + countFoodsPerMeal[1] + 2){
            countMeals = 2;
            return TYPE_HEADER;
        }

        else if(position == countFoodsPerMeal[0] + countFoodsPerMeal[1] + countFoodsPerMeal[2] + 3){
            countMeals = 3;
            return TYPE_HEADER;
        }
        else if(position == countFoodsPerMeal[0] + countFoodsPerMeal[1] + countFoodsPerMeal[2] + countFoodsPerMeal[3] + 4){
            countMeals = 4;
            return TYPE_HEADER;
        }
        else {
            return TYPE_CONTENT;

        }
      /*
        if(printHeader){
                if (countMeals < 4 && countFoodsPerMeal[countMeals + 1] == 0) {
                    countMeals++;
                    printHeader = true;
                } else if (countMeals == 4 && countFoodsPerMeal[countMeals] == 0) {
                    printHeader = true;
                } else if (countMeals == 4) {
                    printHeader = false;
                } else {
                    countMeals++;
                    printHeader = false;
                }

            Log.d(TAG, "HEADER" + position);

            return TYPE_HEADER;
        }
        else {


                countFoods++;

                if (countFoods == countFoodsPerMeal[countMeals]) {
                    printHeader = true;
                    countFoods = 0;
                }

            Log.d(TAG, "CONTENT");
            return TYPE_CONTENT;
            }

*/


    }



    public class ContentViewHolder extends RecyclerView.ViewHolder{

        TextView name, grams, calories, sugars, fats, protein;

        LinearLayout parent;

        public ContentViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.food_name);
            grams = itemView.findViewById(R.id.food_grams);
            calories = itemView.findViewById(R.id.food_calories);
            sugars = itemView.findViewById(R.id.food_sugars);
            fats = itemView.findViewById(R.id.food_fats);
            protein = itemView.findViewById(R.id.food_protein);

            parent = itemView.findViewById(R.id.parent_layout);
        }

        public void setContent(final Food f){

            this.name.setText(f.name);
            this.grams.setText("" + f.grams);
            this.calories.setText("" + f.calories);
            this.sugars.setText("" + f.sugars);
            this.fats.setText("" + f.fats);
            this.protein.setText("" + f.protein);


            parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: clicked on: " + f.name);

                    //DatabaseHandler db = new DatabaseHandler(mContext);
                    //db.deleteMeal(f);

                    //notifyDataSetChanged();
                    //Foods.remove(Foods.indexOf(f));
                }
            });
        }
    }



    public class HeaderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView mealType;
        Button buttonAdd;
        LinearLayout parent;
        int type;

        OnHeaderListener listener;


        public HeaderViewHolder(@NonNull View itemView, OnHeaderListener listener) {
            super(itemView);

            mealType = itemView.findViewById(R.id.header_name);
            buttonAdd = itemView.findViewById(R.id.button_add);

            parent = itemView.findViewById(R.id.header_layout);
            this.listener = listener;

            buttonAdd.setOnClickListener(this);

        }

        public void setContent(final int mealType){
            this.type = mealType;
            this.mealType.setText(types[mealType]);

           /* buttonAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseHandler db = new DatabaseHandler(mContext);
                    // TODO - MANAGE INSERTION IN NEW ACTIVITY
                   // Food f = new Food(1, "hello", typeOfMeal, "28/11/2019", "paprika", 50, 805, 12, 8, 3);
                   // Food f2 = new Food(2, "hello", typeOfMeal, "28/11/2019", "chleba", 50, 805, 12, 8, 3);
                   // db.addToJournal(f);
                   // db.addToJournal(f2);
                   // int size = Foods.size();
                   // Foods.add(f);
                   // notifyDataSetChanged();
                   // Foods.add(f2);
                    //notifyDataSetChanged();
                    //mContext.startActivityForResult(intent);
                }
            });*/
        }

        @Override
        public void onClick(View v) {
                listener.onHeaderClick(this.type);
        }
    }

    public interface OnHeaderListener{
        void onHeaderClick(int type);
    }



}
