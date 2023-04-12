package com.plugi.plugi.feature.search;

import android.graphics.Color;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.plugi.plugi.R;
import com.plugi.plugi.core.base.BaseFragment;
import com.plugi.plugi.core.utilities.Constants;
import com.plugi.plugi.feature.main.MainActivity;
import com.plugi.plugi.feature.search.filter.BrandFragment;
import com.plugi.plugi.feature.search.filter.CategoryFragment;
import com.plugi.plugi.feature.search.filter.PriceFragment;
import com.plugi.plugi.feature.search.filter.ReleaseYearFragment;
import com.plugi.plugi.feature.search.filter.SizeFragment;
import com.plugi.plugi.feature.search.filter.SizeTypeFragment;
import com.plugi.plugi.feature.search.filter.SortFragment;
import com.plugi.plugi.models.FilterIDs;
import com.plugi.plugi.models.MainCategory;
import com.plugi.plugi.models.SocialMedia;
import com.plugi.plugi.models.SortIDs;
import com.plugi.plugi.retrofit.ApiInterface;
import com.plugi.plugi.retrofit.RetrofitClient;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterFragment extends BaseFragment {


    TextView clearAll;
    RelativeLayout sortPanel , categoryPanel , brandPanel , sizeTypePanel , sizePanel , pricePanel , releasePanel , belowDetailPanel;
    TextView sortTXT , categoryTXT , brandTXT , sizeTypeTXT , sizeTXT , priceTXT , releaseTXT;
    TextView sortDesc , categoryDesc , brandDesc , sizeTypeDesc , sizeDesc , priceDesc , releaseDesc;
    ImageView ivBack , gridView , verticalView , belowDetailIMG;
    Button btnSeeResult;
    SortIDs.SortListID selectedSort;
    MainCategory.Category selectedCategory;
    FilterIDs.BrandsList selectedBrand;
    FilterIDs.SizeList selectedSize;
    FilterIDs.SizeTypeList selectedSizeType;
    FilterIDs.PriceList selectedPrice;
    FilterIDs.ReleaseYearList selectedReleaseYear;
    private String status;



    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_filter, container, false);
        status = getArguments().getString(Constants.BUNDLE_FILTER_EDITED);

        clearAll = view.findViewById(R.id.clearAll);
        sortPanel = view.findViewById(R.id.sortPanel);
        categoryPanel = view.findViewById(R.id.categoryPanel);
        brandPanel = view.findViewById(R.id.brandPanel);
        sizeTypePanel = view.findViewById(R.id.sizeTypePanel);
        sizePanel = view.findViewById(R.id.sizePanel);
        pricePanel = view.findViewById(R.id.pricePanel);
        releasePanel = view.findViewById(R.id.releasePanel);
        belowDetailPanel = view.findViewById(R.id.belowDetailPanel);
        sortTXT = view.findViewById(R.id.sortTXT);
        categoryTXT = view.findViewById(R.id.categoryTXT);
        brandTXT = view.findViewById(R.id.brandTXT);
        sizeTypeTXT = view.findViewById(R.id.sizeTypeTXT);
        sizeTXT = view.findViewById(R.id.sizeTXT);
        priceTXT = view.findViewById(R.id.priceTXT);
        releaseTXT = view.findViewById(R.id.releaseTXT);
        sortDesc = view.findViewById(R.id.sortDesc);
        categoryDesc = view.findViewById(R.id.categoryDesc);
        brandDesc = view.findViewById(R.id.brandDesc);
        sizeTypeDesc = view.findViewById(R.id.sizeTypeDesc);
        sizeDesc = view.findViewById(R.id.sizeDesc);
        priceDesc = view.findViewById(R.id.priceDesc);
        releaseDesc = view.findViewById(R.id.releaseDesc);
        ivBack = view.findViewById(R.id.ivBack);
        gridView = view.findViewById(R.id.gridView);
        verticalView = view.findViewById(R.id.verticalView);
        belowDetailIMG = view.findViewById(R.id.belowDetailIMG);
        btnSeeResult = view.findViewById(R.id.btnSeeResult);

        gridView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).setToggleType(0);
            }
        });
        verticalView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).setToggleType(1);
            }
        });
        if(status.equals("edited"))
        {
            clearAll.setVisibility(View.VISIBLE);
        }
        if(((MainActivity) getActivity()).getSelectedCategory() != null)
        {
            selectedCategory =  ((MainActivity) getActivity()).getSelectedCategory();
            categoryTXT.setTextColor(Color.parseColor("#000000"));
            categoryDesc.setText(selectedCategory.getName());
        }
        else
        {
            selectedCategory =  ((MainActivity) getActivity()).getCategoryList().get(0);
            categoryTXT.setTextColor(Color.parseColor("#000000"));
            categoryDesc.setText(selectedCategory.getName());
        }
        if(((MainActivity) getActivity()).getSelectedSort() != null)
        {
            selectedSort =  ((MainActivity) getActivity()).getSelectedSort();
            sortTXT.setTextColor(Color.parseColor("#000000"));
            sortDesc.setText(selectedSort.getName());
        }
        else
        {
            selectedSort =  ((MainActivity) getActivity()).getSortList().get(0);
            sortTXT.setTextColor(Color.parseColor("#000000"));
            sortDesc.setText(selectedSort.getName());
        }

        if(((MainActivity) getActivity()).getSelectedBrand() != null)
        {
            selectedBrand =  ((MainActivity) getActivity()).getSelectedBrand();
            brandTXT.setTextColor(Color.parseColor("#000000"));
            brandDesc.setText(selectedBrand.getName());
            brandDesc.setVisibility(View.VISIBLE);
        }
        if(((MainActivity) getActivity()).getSelectedSize() != null)
        {
            selectedSize =  ((MainActivity) getActivity()).getSelectedSize();
            sizeTXT.setTextColor(Color.parseColor("#000000"));
            sizeDesc.setText(selectedSize.getName());
            sizeDesc.setVisibility(View.VISIBLE);
        }
        if(((MainActivity) getActivity()).getSelectedSizeType() != null)
        {
            selectedSizeType =  ((MainActivity) getActivity()).getSelectedSizeType();
            sizeTypeTXT.setTextColor(Color.parseColor("#000000"));
            sizeTypeDesc.setText(selectedSizeType.getName());
            sizeTypeDesc.setVisibility(View.VISIBLE);
        }
        if(((MainActivity) getActivity()).getSelectedPrice() != null)
        {
            selectedPrice =  ((MainActivity) getActivity()).getSelectedPrice();
            priceTXT.setTextColor(Color.parseColor("#000000"));
            priceDesc.setText(selectedPrice.getName());
            priceDesc.setVisibility(View.VISIBLE);
        }
        if(((MainActivity) getActivity()).getSelectedReleaseYear() != null)
        {
            selectedReleaseYear =  ((MainActivity) getActivity()).getSelectedReleaseYear();
            releaseTXT.setTextColor(Color.parseColor("#000000"));
            releaseDesc.setText(selectedReleaseYear.getName());
            releaseDesc.setVisibility(View.VISIBLE);
        }
        if(((MainActivity) getActivity()).isBelowRetail())
        {
            belowDetailIMG.setVisibility(View.VISIBLE);
        }
        loadData();

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(status.equals("searchResult"))
                {
                    ((MainActivity) getActivity()).forceHiddenSearchSorting(true);
                }
                else {
                    ((MainActivity) getActivity()).forceHiddenSorting(false);
                }
            }
        });
        sortPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                fragment = new SortFragment();
                ((MainActivity) getActivity()).replaceSortingFragments(fragment , Constants.BUNDLE_SORTED_ID , selectedSort , Constants.BUNDLE_SORTED_TYPE  , 1);
            }
        });
        categoryPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                fragment = new CategoryFragment();
                ((MainActivity) getActivity()).replaceSortingFragments(fragment, Constants.BUNDLE_CATEGORY_ID , selectedCategory);
            }
        });
        brandPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                fragment = new BrandFragment();
                ((MainActivity) getActivity()).replaceSortingFragments(fragment, Constants.BUNDLE_BRAND_ID , selectedBrand);
            }
        });
        sizeTypePanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                fragment = new SizeTypeFragment();
                ((MainActivity) getActivity()).replaceSortingFragments(fragment, Constants.BUNDLE_SIZETYPE_ID , selectedSizeType);
            }
        });
        sizePanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                fragment = new SizeFragment();
                ((MainActivity) getActivity()).replaceSortingFragments(fragment, Constants.BUNDLE_SIZE_ID , selectedSize);
            }
        });
        pricePanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                fragment = new PriceFragment();
                ((MainActivity) getActivity()).replaceSortingFragments(fragment, Constants.BUNDLE_PRICE_ID , selectedPrice);
            }
        });
        releasePanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                fragment = new ReleaseYearFragment();
                ((MainActivity) getActivity()).replaceSortingFragments(fragment, Constants.BUNDLE_RELEASEYEAR_ID , selectedReleaseYear);
            }
        });
        belowDetailPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(!((MainActivity) getActivity()).isBelowRetail())
               {
                   belowDetailIMG.setVisibility(View.VISIBLE);
                   ((MainActivity) getActivity()).setBelowRetail(true);

               }
               else
               {
                   belowDetailIMG.setVisibility(View.GONE);
                   ((MainActivity) getActivity()).setBelowRetail(false);
               }
            }
        });
        btnSeeResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(status.equals("searchResult"))
                {
                    ((MainActivity) getActivity()).forceHiddenSearchSorting(true );
                }
                else {
                    //EventBus.getDefault().post("filter_result");

                    ((MainActivity) getActivity()).forceHiddenSorting(true);
                }
            }
        });

        return view;
    }
    public void loadData()
    {
        ApiInterface service = RetrofitClient.retrofitWrite().create(ApiInterface.class);

        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("language_ID", 1);
        RequestBody bodyToken = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
        //defining the call
        Call<SocialMedia> call = service.GetAllSocialMediaLinks(
                bodyToken
        );
        call.enqueue(new Callback<SocialMedia>() {
            @Override
            public void onResponse(Call<SocialMedia> call, Response<SocialMedia> response) {

                if(response.body().getStatusMessage().equals("Success"))
                {

                }
            }

            @Override
            public void onFailure(Call<SocialMedia> call, Throwable t) {

            }
        });
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_filter;
    }

}
