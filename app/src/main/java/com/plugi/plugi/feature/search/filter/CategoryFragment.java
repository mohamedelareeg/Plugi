package com.plugi.plugi.feature.search.filter;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.plugi.plugi.R;
import com.plugi.plugi.core.base.BaseFragment;
import com.plugi.plugi.core.utilities.Constants;
import com.plugi.plugi.feature.main.MainActivity;
import com.plugi.plugi.feature.search.FilterFragment;
import com.plugi.plugi.feature.search.filter.adapter.CategoryItemAdapter;
import com.plugi.plugi.feature.search.filter.interfaces.OnCategoryClickListener;
import com.plugi.plugi.models.MainCategory;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends BaseFragment implements OnCategoryClickListener {

    private CategoryItemAdapter categoryItemAdapter;
    private RecyclerView rec_Category;
    private List<MainCategory.Category> categoryList;
    private ShimmerFrameLayout shimmerFrameLayout;

    private TextView  ivClose , ivApply;
    MainCategory.Category selectedCategory;
    MainCategory.Category prepCategory;
    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_select_category, container, false);
        selectedCategory =(MainCategory.Category)getArguments().getSerializable(Constants.BUNDLE_CATEGORY_ID);
        shimmerFrameLayout = view.findViewById(R.id.parentShimmerLayout);
        shimmerFrameLayout.startShimmer();
        rec_Category = view.findViewById(R.id.rec_Category);
        ivClose = view.findViewById(R.id.ivClose);
        ivApply = view.findViewById(R.id.ivApply);

        AssignSortList();
        loadData();
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                fragment = new FilterFragment();
                ((MainActivity) getActivity()).replaceSortingFragments(fragment , Constants.BUNDLE_FILTER_EDITED , "default");
            }
        });
        ivApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(prepCategory.getCategoryID() !=0) {
                    ((MainActivity) getActivity()).setSelectedCategory(prepCategory);
                    Fragment fragment = null;
                    fragment = new FilterFragment();
                    ((MainActivity) getActivity()).replaceSortingFragments(fragment , Constants.BUNDLE_FILTER_EDITED , "edited");
                }
            }
        });

        return view;
    }
    private void AssignSortList() {
        categoryList = new ArrayList<>();
        categoryList.addAll(((MainActivity) getActivity()).getCategoryList());
        LinearLayoutManager mLayoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        rec_Category.setLayoutManager(mLayoutManager);
        rec_Category.setItemAnimator(new DefaultItemAnimator());
        rec_Category.setHasFixedSize(true);
        rec_Category.setNestedScrollingEnabled(false);
        /* TODO USELESS WITHOUT DATABASE
        recCategory.addOnScrollListener(new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                userPage++;
                getData(userPage);

            }
        });

         */
        int selectedIndex = 0;
        if(selectedCategory != null) {
            Log.d("FILTER", "AssignSortList: " + selectedCategory.getName());
            selectedIndex = categoryList.indexOf(selectedCategory);
            Log.d("FILTER", "AssignSortList: " + selectedIndex);
        }
        categoryItemAdapter = new CategoryItemAdapter( getActivity() ,categoryList , selectedIndex , this);
        rec_Category.setAdapter(categoryItemAdapter);
    }
    public void loadData()
    {

        categoryItemAdapter.notifyDataSetChanged();

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
                    categoryItemAdapter.notifyDataSetChanged();

                    shimmerFrameLayout.setVisibility(View.GONE);
                    shimmerFrameLayout.stopShimmer();
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
        return R.layout.fragment_select_category;
    }


    @Override
    public void onCategoryClicked(MainCategory.Category contact, int position) {

        if(contact.getCategoryID() != selectedCategory.getCategoryID())
        {
            ivApply.setVisibility(View.VISIBLE);
        }
        else
        {
            ivApply.setVisibility(View.GONE);
        }
        prepCategory = contact;
    }
}
