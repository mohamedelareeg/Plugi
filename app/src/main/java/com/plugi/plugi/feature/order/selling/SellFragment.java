package com.plugi.plugi.feature.order.selling;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.tabs.TabLayout;
import com.plugi.plugi.R;
import com.plugi.plugi.core.base.BaseFragment;
import com.plugi.plugi.core.utilities.Constants;
import com.plugi.plugi.feature.item.ItemDetailsFragment;
import com.plugi.plugi.feature.main.MainActivity;
import com.plugi.plugi.feature.main.SideMenuFragment;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SellFragment extends BaseFragment implements TabLayout.OnTabSelectedListener {

    FrameLayout itemContainer;
    TabLayout tlSelection;
    ImageView placeLine , nowLine;
    ImageView ivSide , ivBack;
    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sell, container, false);

        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Fragment fragment = null;
                fragment = new ItemDetailsFragment();
                Bundle args = new Bundle();
                args.putInt(Constants.BUNDLE_ITEM_TYPE, 1);
                fragment.setArguments(args);
                ((MainActivity) getActivity()).replacePopUpFragments(fragment);
            }
        });
        ivSide = view.findViewById(R.id.ivSide);
        ivSide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) requireActivity()).sidemenu.setVisibility(View.VISIBLE);
                Fragment fragment = null;
                fragment = new SideMenuFragment();
                ((MainActivity) getActivity()).replaceFragments(fragment);
            }
        });

        itemContainer = view.findViewById(R.id.itemContainer);
        tlSelection = view.findViewById(R.id.tlSelection);

        placeLine = view.findViewById(R.id.placeLine);
        nowLine = view.findViewById(R.id.nowLine);
        Fragment fragment = null;
        fragment = new PlaceAskFragment();
        replaceItemFragments(fragment);
        placeLine.setVisibility(View.VISIBLE);
        tlSelection.addOnTabSelectedListener(this);
        return view;
    }
    public void replaceItemFragments(Fragment fragmentClass) {

        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass;
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        fragmentManager.beginTransaction().replace(R.id.itemContainer, fragment)
                .addToBackStack(null)
                .setCustomAnimations( R.anim.slide_in_left,
                        R.anim.slide_out_right,
                        R.anim.slide_in_left,
                        R.anim.slide_out_right)
                .commit();

    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_sell;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switch (tab.getPosition())
        {
            case 0:
            {
                Fragment fragment = null;
                fragment = new PlaceAskFragment();
                replaceItemFragments(fragment);
                placeLine.setVisibility(View.VISIBLE);
                nowLine.setVisibility(View.INVISIBLE);
                break;
            }
            case 1 :
            {
                Fragment fragment = null;
                fragment = new SellNowFragment();
                replaceItemFragments(fragment);
                placeLine.setVisibility(View.INVISIBLE);
                nowLine.setVisibility(View.VISIBLE);
                break;
            }
        }

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
