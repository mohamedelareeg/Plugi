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
import com.plugi.plugi.feature.search.filter.adapter.BrandItemAdapter;
import com.plugi.plugi.feature.search.filter.adapter.CategoryItemAdapter;
import com.plugi.plugi.feature.search.filter.interfaces.OnBrandClickListener;
import com.plugi.plugi.feature.search.filter.interfaces.OnCategoryClickListener;
import com.plugi.plugi.models.FilterIDs;
import com.plugi.plugi.models.MainCategory;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BrandFragment extends BaseFragment implements OnBrandClickListener {

    private BrandItemAdapter brandItemAdapter;
    private RecyclerView rec_FilterItem;
    private List<FilterIDs.BrandsList> brandsList;
    private ShimmerFrameLayout shimmerFrameLayout;

    private TextView  ivClose , ivApply , filterName;
    FilterIDs.BrandsList selectBrand;
    FilterIDs.BrandsList prepBrand;
    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_filter_item, container, false);
        selectBrand =(FilterIDs.BrandsList) getArguments().getSerializable(Constants.BUNDLE_BRAND_ID);
        shimmerFrameLayout = view.findViewById(R.id.parentShimmerLayout);
        shimmerFrameLayout.startShimmer();
        filterName = view.findViewById(R.id.filterName);
        rec_FilterItem = view.findViewById(R.id.rec_FilterItem);
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
                if(prepBrand.getCategoryID() !=0) {
                    ((MainActivity) getActivity()).setSelectedBrand(prepBrand);
                    Fragment fragment = null;
                    fragment = new FilterFragment();
                    ((MainActivity) getActivity()).replaceSortingFragments(fragment , Constants.BUNDLE_FILTER_EDITED , "edited");
                }
            }
        });

        return view;
    }
    private void AssignSortList() {
        brandsList = new ArrayList<>();
        brandsList.addAll(((MainActivity) getActivity()).getBrandsList());
        LinearLayoutManager mLayoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        rec_FilterItem.setLayoutManager(mLayoutManager);
        rec_FilterItem.setItemAnimator(new DefaultItemAnimator());
        rec_FilterItem.setHasFixedSize(true);
        rec_FilterItem.setNestedScrollingEnabled(false);
        /* TODO USELESS WITHOUT DATABASE
        recCategory.addOnScrollListener(new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                userPage++;
                getData(userPage);

            }
        });

         */

        int selectedIndex = -1;
        if(selectBrand != null) {
            //Log.d("FILTER", "AssignSortList: " + selectBrand.getName());
            selectedIndex = brandsList.indexOf(selectBrand);
        }

        brandItemAdapter = new BrandItemAdapter( getActivity() ,brandsList , selectedIndex , this);
        rec_FilterItem.setAdapter(brandItemAdapter);
    }
    public void loadData()
    {

        brandItemAdapter.notifyDataSetChanged();

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
        return R.layout.fragment_filter_item;
    }



    @Override
    public void onBrandClicked(FilterIDs.BrandsList contact, int position) {
        Log.d("FILTER", "onBrandClicked: " + contact.getName() + " , " + contact.getCategoryID() );

        if(selectBrand != null) {
            if (contact.getCategoryID() != selectBrand.getCategoryID()) {
                ivApply.setVisibility(View.VISIBLE);
            } else {
                ivApply.setVisibility(View.GONE);
            }
        }
        else
        {
            ivApply.setVisibility(View.VISIBLE);
        }
        prepBrand = contact;
    }
}
