package com.example.lightened;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

public class MealsActivity extends AppCompatActivity {

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
