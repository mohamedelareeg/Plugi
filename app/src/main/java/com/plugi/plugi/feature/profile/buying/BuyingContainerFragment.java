package com.plugi.plugi.feature.profile.buying;

import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
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
import com.plugi.plugi.models.User;
import com.plugi.plugi.models.customerBids;
import com.plugi.plugi.retrofit.ApiInterface;
import com.plugi.plugi.retrofit.RetrofitClient;
import com.plugi.plugi.retrofit.SharedPrefManager;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuyingContainerFragment extends BaseFragment implements TabLayout.OnTabSelectedListener {

    FrameLayout itemContainer;
    TabLayout tlSelection;
    ImageView currentLine , pendingLine , historyLine;
    ImageView ivSide , ivBack;
    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buying, container, false);

        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MainActivity) getActivity()).forceHiddenPopUp(false);
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

        currentLine = view.findViewById(R.id.currentLine);
        pendingLine = view.findViewById(R.id.pendingLine);
        historyLine = view.findViewById(R.id.historyLine);
        LoadData();
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
        return R.layout.fragment_buying;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switch (tab.getPosition())
        {
            case 0:
            {
                Fragment fragment = null;
                fragment = new CurrentBuyingFragment();
                replaceItemFragments(fragment);
                currentLine.setVisibility(View.VISIBLE);
                pendingLine.setVisibility(View.INVISIBLE);
                historyLine.setVisibility(View.INVISIBLE);
                break;
            }
            case 1 :
            {
                Fragment fragment = null;
                fragment = new PendingBuyingFragment();
                replaceItemFragments(fragment);
                currentLine.setVisibility(View.INVISIBLE);
                pendingLine.setVisibility(View.VISIBLE);
                historyLine.setVisibility(View.INVISIBLE);
                break;
            }
            case 2 :
            {
                Fragment fragment = null;
                fragment = new HistoryBuyingFragment();
                replaceItemFragments(fragment);
                currentLine.setVisibility(View.INVISIBLE);
                pendingLine.setVisibility(View.INVISIBLE);
                historyLine.setVisibility(View.VISIBLE);
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

    public void LoadData()
    {

        if (SharedPrefManager.getInstance(getActivity()).isLoggedIn()) {
            User currentUser = SharedPrefManager.getInstance(getActivity()).getUser();
            ApiInterface service = RetrofitClient.retrofitBearerTokenWrite(currentUser).create(ApiInterface.class);

            Map<String, Object> jsonParams = new ArrayMap<>();
            jsonParams.put("Language_ID", 1);
            jsonParams.put("CustomerID", currentUser.getCustomer().getID());//TODO
            RequestBody bodyToken = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
            //defining the call
            Call<customerBids> call = service.GetCustomer_Bids(
                    bodyToken
            );
            call.enqueue(new Callback<customerBids>() {
                @Override
                public void onResponse(Call<customerBids> call, Response<customerBids> response) {

                    if(response.body() != null) {
                        if (response.body().getStatusMessage().equals("Success")) {
                            ((MainActivity) getActivity()).setCustomerBids(response.body());
                            Fragment fragment = null;
                            fragment = new CurrentBuyingFragment();
                            replaceItemFragments(fragment);
                            currentLine.setVisibility(View.VISIBLE);


                        } else {
                            Fragment fragment = null;
                            fragment = new CurrentBuyingFragment();
                            replaceItemFragments(fragment);
                            currentLine.setVisibility(View.VISIBLE);
                        }
                    }
                    else
                    {
                        Fragment fragment = null;
                        fragment = new CurrentBuyingFragment();
                        replaceItemFragments(fragment);
                        currentLine.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<customerBids> call, Throwable t) {
                    Log.d("ItemDetails", "onFailure: " + t.getLocalizedMessage());
                    Fragment fragment = null;
                    fragment = new CurrentBuyingFragment();
                    replaceItemFragments(fragment);
                    currentLine.setVisibility(View.VISIBLE);
                }
            });
        }
        else
        {
            Fragment fragment = null;
            fragment = new CurrentBuyingFragment();
            replaceItemFragments(fragment);
            currentLine.setVisibility(View.VISIBLE);
        }
    }
}
