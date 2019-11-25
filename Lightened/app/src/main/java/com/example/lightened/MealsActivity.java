package com.example.lightened;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

public class MealsActivity extends AppCompatActivity {

    private static final String TAG = "MealsActivity";
    private ViewPager viewPager;
    private MealsFragmentCollectionAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meals);

        viewPager = findViewById(R.id.pager);
        adapter = new MealsFragmentCollectionAdapter(getSupportFragmentManager());

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(adapter.getCount()-1);


    }

}
