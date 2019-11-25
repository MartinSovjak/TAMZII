package com.example.lightened;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MealsFragmentCollectionAdapter extends FragmentStatePagerAdapter {
    String newDate;

    public MealsFragmentCollectionAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        MealsFragment mealsFragment = new MealsFragment();
        Bundle bundle = new Bundle();

        position = position+1;

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, position - getCount());


        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        newDate = sdf.format(cal.getTime());
        bundle.putString("message", newDate);

        mealsFragment.setArguments(bundle);

        return mealsFragment;
    }

    @Override
    public int getCount() {
        return 365;
    }

    public String getDate(){
        return newDate;
    }
}
