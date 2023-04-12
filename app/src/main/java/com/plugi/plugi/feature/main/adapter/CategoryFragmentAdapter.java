package com.plugi.plugi.feature.main.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


import com.plugi.plugi.core.views.SlidingTab.IFTabAdapter;
import com.plugi.plugi.feature.category.CategoryFragment;
import com.plugi.plugi.models.MainCategory;

public class CategoryFragmentAdapter extends FragmentPagerAdapter implements IFTabAdapter {

    private MainCategory mainCategory;
    private Context context;
    public CategoryFragmentAdapter(FragmentManager fm , MainCategory mainCategory , Context context) {
        super(fm);
        this.context = context;
        this.mainCategory = mainCategory;
    }

    @Override
    public Fragment getItem(int position) {
        return CategoryFragment.newInstance(mainCategory.getCategories().get(position) , context);
    }

    @Override
    public int getCount() {
        return mainCategory.getCategories().size();
    }

    @Override
    public String getTitle(int position) {
        return mainCategory.getCategories().get(position).getName();
    }

    @Override
    public int getIcon(int position) {
        return 0;
    }

    @Override
    public boolean isEnableBadge(int position) {
        if (position == 0){
            return true;
        }
        return false;
    }
}