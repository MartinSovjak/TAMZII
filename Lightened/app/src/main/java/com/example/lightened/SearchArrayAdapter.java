package com.example.lightened;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SearchArrayAdapter extends ArrayAdapter<FoodEntry> {

    private FoodEntry foodEntry;
    private List<FoodEntry> foodEntryList;
    private TextView searchName, searchCalories, searchSugars, searchFats, searchProtein;
    private ImageView foodIcon;


    public SearchArrayAdapter(Context context, List<FoodEntry> objects){
        super(context,0, objects);

        foodEntryList = new ArrayList<>(objects);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return foodFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.autocomplete_row, parent, false
            );
        }
        searchName = (TextView) convertView.findViewById(R.id.searchName);
        searchCalories = (TextView) convertView.findViewById(R.id.searchCalories);
        searchSugars = (TextView) convertView.findViewById(R.id.searchSugars);
        searchFats = (TextView) convertView.findViewById(R.id.searchFats);
        searchProtein = (TextView) convertView.findViewById(R.id.searchProtein);

        // Get a reference to ImageView holder
        foodIcon = (ImageView) convertView.findViewById(R.id.searchImg);

        foodEntry = getItem(position);
        String img = foodEntry.img;

        if(foodEntry != null){
            searchName.setText(foodEntry.name);
            searchCalories.setText(""+foodEntry.calories);
            searchSugars.setText(""+foodEntry.sugars);
            searchFats.setText(""+foodEntry.fats);
            searchProtein.setText(""+foodEntry.protein);

            //TODO - NOT READY FOR GETTING IMAGES
            Bitmap icon = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.rohlik);
            foodIcon.setImageBitmap(icon);
        }

        return convertView;
    }

    private Filter foodFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<FoodEntry> suggestions = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                suggestions.addAll(foodEntryList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for(FoodEntry item : foodEntryList){
                    if(item.name.toLowerCase().contains(filterPattern)){
                        suggestions.add(item);
                    }
                }
            }

            results.values = suggestions;
            results.count = suggestions.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List)results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((FoodEntry) resultValue).name;
        }
    };
/*
    @Override
    public int getCount()
    {
        return this.foodEntryList.size();
    }

    @Override
    public FoodEntry getItem(int position)
    {
        foodEntry = this.foodEntryList.get(position);

        return foodEntry;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View row = convertView;
        LayoutInflater inflater = LayoutInflater.from(getContext());

        if (row == null)
        {
            row = inflater.inflate(R.layout.fragment_search, parent, false);
        }

        foodEntry = this.foodEntryList.get(position);

        searchName = (TextView) row.findViewById(R.id.searchName);
        searchName.setText(foodEntry.name);

        searchCalories = (TextView) row.findViewById(R.id.searchCalories);
        searchCalories.setText(foodEntry.calories);

        searchSugars = (TextView) row.findViewById(R.id.searchSugars);
        searchSugars.setText(""+foodEntry.sugars);

        searchFats = (TextView) row.findViewById(R.id.searchFats);
        searchFats.setText(""+foodEntry.fats);

        searchProtein = (TextView) row.findViewById(R.id.searchProtein);
        searchProtein.setText(""+foodEntry.protein);


        // Get a reference to ImageView holder
        foodIcon = (ImageView) row.findViewById(R.id.searchImg);

        String img = foodEntry.img;
        //TODO - NOT READY FOR GETTING IMAGES
        Bitmap icon = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.rohlik);
        foodIcon.setImageBitmap(icon);


        return row;
    }
    */

}
