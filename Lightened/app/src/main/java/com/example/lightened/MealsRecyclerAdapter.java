package com.example.lightened;

import android.content.Context;
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

public class MealsRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "MealsRecyclerAdapter";


    private ArrayList<Food> Foods;
    private Context mContext;
    private MealsFragment fragment;

    private int typeOfMeal;

    private String[] types = {"breakfast", "morning snack", "lunch", "afternoon snack", "dinner"};

    private static final int TYPE_HEADER = 1;
    private static final int TYPE_CONTENT = 2;

    public MealsRecyclerAdapter( Context mContext, ArrayList<Food> Foods, int mealType, MealsFragment m) {

        ArrayList<Food> temp = new ArrayList<>();

        for(Food f: Foods){
            if (f.meal == mealType)
                temp.add(f);
        }
        this.Foods = temp;
        this.mContext = mContext;
        this.typeOfMeal = mealType;
        this.fragment = m;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == TYPE_CONTENT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
            ContentViewHolder holder = new ContentViewHolder(view);
            return holder;
        }
        else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_headeritem, parent, false);
            HeaderViewHolder holder = new HeaderViewHolder(view);
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        if(getItemViewType(position) == TYPE_CONTENT)
            ((ContentViewHolder)holder).setContent(Foods.get(position-1));
        else if(getItemViewType(position) == TYPE_HEADER)
            ((HeaderViewHolder)holder).setContent(types[typeOfMeal]);
    }

    @Override
    public int getItemCount() {
        return Foods.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0)
            return TYPE_HEADER;
        else
            return TYPE_CONTENT;
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

                    DatabaseHandler db = new DatabaseHandler(mContext);
                    db.deleteMeal(f);

                    notifyDataSetChanged();
                    Foods.remove(Foods.indexOf(f));
                }
            });
        }
    }



    public class HeaderViewHolder extends RecyclerView.ViewHolder{

        TextView mealType;
        Button buttonDelete, buttonNote;
        LinearLayout parent;


        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);

            mealType = itemView.findViewById(R.id.header_name);
            buttonDelete = itemView.findViewById(R.id.button_delete);
            buttonNote = itemView.findViewById(R.id.button_note);
            parent = itemView.findViewById(R.id.header_layout);
        }

        public void setContent(final String mealType){

            this.mealType.setText(mealType);

            buttonNote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DatabaseHandler db = new DatabaseHandler(mContext);
                    // TODO - MANAGE INSERTION IN NEW ACTIVITY
                    Food f = new Food(1, "hello", typeOfMeal, "28/11/2019", "paprika", 50, 805, 12, 8, 3);
                    Food f2 = new Food(2, "hello", typeOfMeal, "28/11/2019", "chleba", 50, 805, 12, 8, 3);

                    db.addToJournal(f);
                    db.addToJournal(f2);
                    int size = Foods.size();
                    Foods.add(f);
                    notifyDataSetChanged();
                    Foods.add(f2);
                    notifyDataSetChanged();

                }
            });

            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });


        }
    }



}
