package com.plugi.plugi.feature.main;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.plugi.plugi.R;
import com.plugi.plugi.core.base.BaseFragment;
import com.plugi.plugi.core.utilities.Constants;
import com.plugi.plugi.feature.main.adapter.MainCategoryAdapter;
import com.plugi.plugi.feature.main.interfaces.OnCategoryClickListener;
import com.plugi.plugi.feature.search.CategorySearchFragment;
import com.plugi.plugi.feature.search.OnCompleteFragment;
import com.plugi.plugi.feature.search.SearchResultFragment;
import com.plugi.plugi.feature.search.SuggestFragment;
import com.plugi.plugi.feature.search.interfaces.onSearchAction;
import com.plugi.plugi.models.MainCategory;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends BaseFragment implements MaterialSearchBar.OnSearchActionListener , OnCategoryClickListener  {

    MainCategoryAdapter mainCategoryAdapter;
    List<MainCategory.Category> categoryList;
    RecyclerView rec_MainCategory;
    ShimmerFrameLayout shimmerFrameLayout;
    //categoryNavigation
    MaterialSearchBar searchProductTXT;
    private onSearchAction onSearchAction;
    private FrameLayout suggestContainer , CategoryContainer;

    ImageView ivSide;


    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        ivSide = view.findViewById(R.id.ivSide);
        ivSide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).sideMenu();
            }
        });
        shimmerFrameLayout = view.findViewById(R.id.parentShimmerLayout);
        shimmerFrameLayout.startShimmer();

        CategoryContainer = view.findViewById(R.id.CategorySearchContainer);
        suggestContainer = view.findViewById(R.id.suggestContainer);
        searchProductTXT = view.findViewById(R.id.searchProductTXT);
        searchProductTXT.setOnSearchActionListener(this);
        searchProductTXT.hideSuggestionsList();
        searchProductTXT.setCardViewElevation(10);
        searchProductTXT.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("LOG_TAG", getClass().getSimpleName() + " text changed " + searchProductTXT.getText());



                if(searchProductTXT.getText().length() != 0)
                {
                    //TODo
                    if(rec_MainCategory.getVisibility() == View.VISIBLE)
                    {
                        CategoryContainer.setVisibility(View.GONE);
                        rec_MainCategory.setVisibility(View.GONE);
                    }
                    Fragment fragment = null;
                    //fragment = new HomeFragment();//CategoryFragment.newInstance(category);
                    fragment = new OnCompleteFragment();
                    replaceFragments(fragment, Constants.BUNDLE_SEARCH_TXT , searchProductTXT.getText());
                }
                else{
                    Fragment fragment = null;
                    //fragment = new HomeFragment();//CategoryFragment.newInstance(category);
                    fragment = new SuggestFragment();
                    replaceFragments(fragment);

                }


                /* TODO
                if(SharedPrefManager.getInstance(getActivity()).isCategoryLoaded()) {
                    MainCategory mainCategory = SharedPrefManager.getInstance(getActivity()).getCategory();
                    CategorySearchFragment fragment = (CategorySearchFragment)getActivity().getSupportFragmentManager().findFragmentByTag(SharedPrefManager.getInstance(getActivity()).geCategoryTAG(mainCategory.getCategories().get(1)));
                    ((CategorySearchFragment) fragment).setSearchTXT(searchProductTXT.getText(), getActivity());
                }

                 */

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });
        //categoryNavigation
        categoryList = new ArrayList<>();
        rec_MainCategory = view.findViewById(R.id.rec_MainSearchCategory);
        AssignCategoryList();

        loadCategory();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


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

        shimmerFrameLayout.setVisibility(View.GONE);
        shimmerFrameLayout.stopShimmer();
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

                    categoryList.addAll(response.body().getCategories());
                    mainCategoryAdapter.notifyDataSetChanged();

                    shimmerFrameLayout.setVisibility(View.GONE);
                    shimmerFrameLayout.stopShimmer();
                    //SharedPrefManager.getInstance(getActivity()).CategoryList(response.body());

                }
            }

            @Override
            public void onFailure(Call<MainCategory> call, Throwable t) {

            }
        });

         */
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_search;
    }
    public void replaceFragments(Fragment fragmentClass) {

        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass;
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        fragmentManager.beginTransaction().replace(R.id.suggestContainer, fragment)
                .addToBackStack(null)
                .setCustomAnimations( R.anim.slide_in_left,
                        R.anim.slide_out_right,
                        R.anim.slide_in_left,
                        R.anim.slide_out_right)
                .commit();

    }
    public void replaceFragments(Fragment fragmentClass , String Key , String Value) {

        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass;
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        Bundle args = new Bundle();
        args.putString(Key, Value);
        fragment.setArguments(args);
        fragmentManager.beginTransaction().replace(R.id.suggestContainer, fragment)
                .addToBackStack(null)
                .setCustomAnimations( R.anim.slide_in_left,
                        R.anim.slide_out_right,
                        R.anim.slide_in_left,
                        R.anim.slide_out_right)
                .commit();

    }

    public void replaceCategoryFragments(Fragment fragmentClass) {

        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass;
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        fragmentManager.beginTransaction().replace(R.id.CategorySearchContainer, fragment)
                .addToBackStack(null)
                .setCustomAnimations( R.anim.slide_in_left,
                        R.anim.slide_out_right,
                        R.anim.slide_in_left,
                        R.anim.slide_out_right)
                .commit();
        CategoryContainer.setVisibility(View.VISIBLE);

    }
    @Override
    public void onSearchStateChanged(boolean enabled) {


        if(enabled)
        {
            suggestContainer.setVisibility(View.VISIBLE);
            Fragment fragment = null;
            //fragment = new HomeFragment();//CategoryFragment.newInstance(category);
            fragment = new SuggestFragment();
            replaceFragments(fragment);
             //TODO
            if(rec_MainCategory.getVisibility() == View.VISIBLE)
            {
                CategoryContainer.setVisibility(View.GONE);
                rec_MainCategory.setVisibility(View.GONE);
            }


        }else
        {
            suggestContainer.setVisibility(View.GONE);
            if(searchProductTXT.getText().length() == 0) {
                CategoryContainer.setVisibility(View.VISIBLE);
                rec_MainCategory.setVisibility(View.VISIBLE);
                //setupViewPager(getMainCategory()); TODo
            }

        }
        /*
        Log.d("SEARCH", "onSearchStateChanged: " + enabled);

        else
        {

        }

         */
    }

    @Override
    public void onResume() {
        super.onResume();

        if(searchProductTXT.getText().length() == 0) {
            CategoryContainer.setVisibility(View.VISIBLE);
            rec_MainCategory.setVisibility(View.VISIBLE);

            //setupViewPager(getMainCategory()); TODo
        }
        //loadCategory();
    }

    @Override
    public void onSearchConfirmed(CharSequence text) {
        if(categoryList.size() != 0) {
            //suggestContainer.setVisibility(View.GONE);
            Fragment fragment = null;
            //fragment = new HomeFragment();//CategoryFragment.newInstance(category);
            fragment = new SearchResultFragment();
            //replaceFragments(fragment, Constants.BUNDLE_SEARCH_TXT , contact);
            ((MainActivity) getActivity()).replaceFragments(fragment  , Constants.BUNDLE_SEARCH_TXT , searchProductTXT.getText());
            ((MainActivity) getActivity()).sidemenu.setVisibility(View.VISIBLE);
            Log.d("CATEGORY", "onSearchConfirmed: " + ((MainActivity) getActivity()).getSelectedCategory());
        }
    }

    @Override
    public void onButtonClicked(int buttonCode) {
        if (buttonCode == MaterialSearchBar.BUTTON_BACK) {
            Log.d("CATEGORY", "onButtonClicked: ");
            if(searchProductTXT.getText().length() == 0) {
                CategoryContainer.setVisibility(View.VISIBLE);
                rec_MainCategory.setVisibility(View.VISIBLE);

                //setupViewPager(getMainCategory()); TODo
            }
        }

    }
    public void setSearchAction(onSearchAction onSearchAction)
    {
        this.onSearchAction = onSearchAction ;
    }


    @Override
    public void onCategoryClicked(MainCategory.Category contact, int position) {
        //CategorySearchFragment.newInstance(mainCategory.getCategories().get(position) , position  , context)

        Fragment fragment = null;
        fragment = CategorySearchFragment.newInstance(contact , position  , getActivity());
        replaceCategoryFragments(fragment);
        ((MainActivity) getActivity()).setSelectedCategory(contact);
        Log.d("LoadMostPopular", "onCategoryClicked: 1 ");

    }

    @Override
    public void onCategorySortedClicked(MainCategory.Category contact, int position) {
        Fragment fragment = null;
        fragment = CategorySearchFragment.newInstance(contact , position  , getActivity());
        replaceCategoryFragments(fragment);

        Log.d("LoadMostPopular", "onCategoryClicked: 2");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // Do your stuff here
            Log.d("LoadMostPopular", "setUserVisibleHint: ");
        }

    }
    public void setFilterResult()
    {
        Log.d("onBackPressed", "setFilterResult: ");
        if(((MainActivity) getActivity()).getSelectedCategory() != null) {
            //Log.d("FILTER", "AssignSortList: " + selectBrand.getName());
            int selectedIndex = ((MainActivity) getActivity()).getCategoryList().indexOf(((MainActivity) getActivity()).getSelectedCategory());
            mainCategoryAdapter.setPosition(selectedIndex , true);
            rec_MainCategory.scrollToPosition(selectedIndex);
            mainCategoryAdapter.notifyDataSetChanged();

            Fragment fragment = null;
            fragment = CategorySearchFragment.newInstance( ((MainActivity) getActivity()).getSelectedCategory() , -2  , getActivity());
            replaceCategoryFragments(fragment);
        }

    }
    /*
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResultReceived(String result) {

        Fragment fragment = null;
        fragment = CategorySearchFragment.newInstance( ((MainActivity) getActivity()).getSelectedCategory() , 0  , getActivity());
        replaceCategoryFragments(fragment);
        Log.d("LoadMostPopular", "PostEvent: " + result);
    }
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }
    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

     */
}
