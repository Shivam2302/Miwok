package com.example.android.miwok;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by shivam on 3/1/18.
 */

public class CategoryAdapter extends FragmentPagerAdapter {

    public CategoryAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0)
            return new NumberFragment();
        else if(position == 1)
            return new ColorFragment();
        else if(position == 2)
            return new FamilyFragment();
        else
            return new PhrasesFragment();
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0)
            return "Numbers";
        else if(position == 1)
            return "Colors";
        else if(position == 2)
            return "Family";
        else
            return "Phrases";
    }
}
