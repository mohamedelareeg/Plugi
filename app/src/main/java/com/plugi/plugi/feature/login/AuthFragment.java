package com.plugi.plugi.feature.login;

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
import com.plugi.plugi.feature.main.MainActivity;
import com.plugi.plugi.feature.main.SideMenuFragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AuthFragment extends BaseFragment implements TabLayout.OnTabSelectedListener {

    FrameLayout authContainer;
    TabLayout tlSelection;
    boolean isMenu = true;
    ImageView ivSide , ivBack;
    ImageView placeLine , nowLine;
    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_auth, container, false);

        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isMenu) {

                    ((MainActivity) getActivity()).sideMenu();
                    isMenu = false;
                }
                else
                {

                    ((MainActivity) getActivity()).forceHiddenPopUp(false);
                }
            }
        });
        ivSide = view.findViewById(R.id.ivSide);
        ivSide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //((MainActivity) requireActivity()).toolbarTitle.setVisibility(View.GONE);
                ((MainActivity) requireActivity()).sidemenu.setVisibility(View.VISIBLE);
                Fragment fragment = null;
                fragment = new SideMenuFragment();
                ((MainActivity) getActivity()).replaceFragments(fragment);
            }
        });

        authContainer = view.findViewById(R.id.authContainer);
        tlSelection = view.findViewById(R.id.tlSelection);

        placeLine = view.findViewById(R.id.placeLine);
        nowLine = view.findViewById(R.id.nowLine);

        Fragment fragment = null;
        fragment = new RegisterFragment();
        replaceAuthFragments(fragment);
        tlSelection.addOnTabSelectedListener(this);
        placeLine.setVisibility(View.VISIBLE);
        return view;
    }
    public void replaceAuthFragments(Fragment fragmentClass) {

        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass;
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        fragmentManager.beginTransaction().replace(R.id.authContainer, fragment)
                .addToBackStack(null)
                .setCustomAnimations( R.anim.slide_in_left,
                        R.anim.slide_out_right,
                        R.anim.slide_in_left,
                        R.anim.slide_out_right)
                .commit();

    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_auth;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switch (tab.getPosition())
        {
            case 0:
            {
                Fragment fragment = null;
                fragment = new RegisterFragment();
                replaceAuthFragments(fragment);
                placeLine.setVisibility(View.VISIBLE);
                nowLine.setVisibility(View.INVISIBLE);
                break;
            }
            case 1 :
            {
                Fragment fragment = null;
                fragment = new LoginFragment();
                replaceAuthFragments(fragment);
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
