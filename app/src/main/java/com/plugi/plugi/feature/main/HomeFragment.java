package com.plugi.plugi.feature.main;

import android.graphics.Color;
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
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.facebook.shimmer.ShimmerFrameLayout;
import com.plugi.plugi.R;
import com.plugi.plugi.core.base.BaseFragment;
import com.plugi.plugi.feature.category.CategoryFragment;
import com.plugi.plugi.feature.main.adapter.MainCategoryAdapter;
import com.plugi.plugi.feature.main.adapter.SliderBannerAdapter;
import com.plugi.plugi.feature.main.interfaces.OnCategoryClickListener;
import com.plugi.plugi.feature.ribbon.AutoScrollRibbon;
import com.plugi.plugi.models.MainCategory;
import com.plugi.plugi.models.SlidingBanner;
import com.plugi.plugi.retrofit.ApiInterface;
import com.plugi.plugi.retrofit.RetrofitClient;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends BaseFragment implements OnCategoryClickListener {

    private static final String TAG = "HomeFragment";
    //categoryNavigation
    MainCategoryAdapter mainCategoryAdapter;
    List<MainCategory.Category> categoryList;
    RecyclerView rec_MainCategory;
    ShimmerFrameLayout shimmerFrameLayout2;
    FrameLayout  CategoryContainer;

    SliderView sliderView;
    private SliderBannerAdapter adapter;
    private ShimmerFrameLayout shimmerFrameLayout;
    private List<String> sliderItemList;

    FrameLayout ribbon_container;
    AutoScrollRibbon autoScrollRibbon;

    ImageView ivSide;

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ivSide = view.findViewById(R.id.ivSide);
        ivSide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).sideMenu();
            }
        });
        ribbon_container = view.findViewById(R.id.ribbon_container);
        shimmerFrameLayout = view.findViewById(R.id.parentShimmerLayout);
        shimmerFrameLayout.startShimmer();

        CategoryContainer = view.findViewById(R.id.CategoryContainer);

        shimmerFrameLayout2 = view.findViewById(R.id.parentShimmerLayout2);
        shimmerFrameLayout2.startShimmer();

        //SldingBanner
        sliderItemList = new ArrayList<>();
        sliderView = view.findViewById(R.id.imageSlider);
        adapter = new SliderBannerAdapter(getActivity());
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();
        sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                Log.i("GGG", "onIndicatorClicked: " + sliderView.getCurrentPagePosition());
            }
        });



        loadRibbon();
        loadBannerData();

        categoryList = new ArrayList<>();
        rec_MainCategory = view.findViewById(R.id.rec_MainCategory);
        AssignCategoryList();
        loadCategory();
        /*
        Fragment fragment = null;
        fragment = new SideMenuFragment();
        ((MainActivity) getActivity()).replaceFragments(fragment);

         */
        //((MainActivity) requireActivity()).toolbarLogo.setVisibility(View.VISIBLE); //TODO RECALL VIEW FROM THE PARENT ACTIVITY


        return view;
    }

    private void loadRibbon() {
        try {
            autoScrollRibbon = new AutoScrollRibbon();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.ribbon_container, autoScrollRibbon).commit();
        }catch (Exception e)
        {
            Log.d(TAG, "loadRibbon: " + e.getLocalizedMessage());
        }

    }

    private void AssignCategoryList() {
        categoryList = new ArrayList<>();
        LinearLayoutManager mLayoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        rec_MainCategory.setLayoutManager(mLayoutManager);
        rec_MainCategory.setItemAnimator(new DefaultItemAnimator());
        rec_MainCategory.setHasFixedSize(true);
        rec_MainCategory.setNestedScrollingEnabled(false);
        /* TODO USELESS WITHOUT DATABASE
        recCategory.addOnScrollListener(new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                userPage++;
                getData(userPage);

            }
        });

         */
        mainCategoryAdapter = new MainCategoryAdapter(categoryList , this);
        rec_MainCategory.setAdapter(mainCategoryAdapter);
    }
    private void loadCategory() {
        categoryList.addAll( ((MainActivity) getActivity()).getCategoryList());
        mainCategoryAdapter.notifyDataSetChanged();

        shimmerFrameLayout2.setVisibility(View.GONE);
        shimmerFrameLayout2.stopShimmer();
        /*
        ApiInterface service = RetrofitClient.retrofitWrite().create(ApiInterface.class);

        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("language_ID", 1);
        RequestBody bodyToken = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
        //defining the call
        Call<MainCategory> call = service.GetMainCategories(
                bodyToken
        );
        call.enqueue(new Callback<MainCategory>() {
            @Override
            public void onResponse(Call<MainCategory> call, Response<MainCategory> response) {

                if(response.body().getStatusMessage().equals("Success"))
                {
                    ((MainActivity) getActivity()).setCategoryList(response.body().getCategories());
                    categoryList.addAll(response.body().getCategories());
                    mainCategoryAdapter.notifyDataSetChanged();

                    shimmerFrameLayout2.setVisibility(View.GONE);
                    shimmerFrameLayout2.stopShimmer();

                }
            }

            @Override
            public void onFailure(Call<MainCategory> call, Throwable t) {

            }
        });

         */
    }

    @Override
    public void onResume() {

        super.onResume();
    }

    public void loadBannerData()
    {
        ApiInterface service = RetrofitClient.retrofitWrite().create(ApiInterface.class);

        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("language_ID", 1);
        RequestBody bodyToken = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
        //defining the call
        Call<SlidingBanner> call = service.GetHomeSlidingbanner(
                bodyToken
        );
        call.enqueue(new Callback<SlidingBanner>() {
            @Override
            public void onResponse(Call<SlidingBanner> call, Response<SlidingBanner> response) {

                if(response.body().getStatusMessage().equals("Success"))
                {
                    sliderItemList.addAll(response.body().getImages());
                    adapter.setList(sliderItemList);
                    shimmerFrameLayout.setVisibility(View.GONE);
                    shimmerFrameLayout.stopShimmer();
                    sliderView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<SlidingBanner> call, Throwable t) {

            }
        });
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_home;
    }

    public void replaceCategoryFragments(Fragment fragmentClass) {

        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass;
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        fragmentManager.beginTransaction().replace(R.id.CategoryContainer, fragment)
                .addToBackStack(null)
                .commit();
        CategoryContainer.setVisibility(View.VISIBLE);

    }
    @Override
    public void onCategoryClicked(MainCategory.Category contact, int position) {
        Fragment fragment = null;
        fragment = CategoryFragment.newInstance(contact , getActivity());
        replaceCategoryFragments(fragment);
        ((MainActivity) getActivity()).setSelectedCategory(contact);
    }

    @Override
    public void onCategorySortedClicked(MainCategory.Category contact, int position) {
        Fragment fragment = null;
        fragment = CategoryFragment.newInstance(contact , getActivity());
        replaceCategoryFragments(fragment);

    }
}
