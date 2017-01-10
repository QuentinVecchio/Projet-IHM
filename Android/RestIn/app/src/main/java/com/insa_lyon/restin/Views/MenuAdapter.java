package com.insa_lyon.restin.Views;

/**
 * Created by Pierre on 08/01/2017.
 */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.Vector;

public class MenuAdapter extends PagerAdapter {

    private Context mContext;
    private Vector<View> pages;
    private Vector<String> titles;

    public MenuAdapter(Context context, Vector<View> pages, Vector<String> titles) {
        this.mContext = context;
        this.pages = pages;
        this.titles = titles;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View page = pages.get(position);
        container.addView(page);
        return page;
    }

    @Override
    public int getCount() {
        return pages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
