package com.insa_lyon.restin.Views;

/**
 * Created by Pierre on 08/01/2017.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class MenuAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public MenuAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                MenuMidiFragment tab1 = new MenuMidiFragment();
                return tab1;
            case 1:
                MenuSoirFragment tab2 = new MenuSoirFragment();
                return tab2;
            case 2:
                MenuMatinFragment tab3 = new MenuMatinFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
