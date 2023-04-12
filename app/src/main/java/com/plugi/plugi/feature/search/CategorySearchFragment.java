package com.plugi.plugi.feature.search;

import android.content.Context;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.plugi.plugi.R;
import com.plugi.plugi.core.utilities.Constants;
import com.plugi.plugi.feature.category.adapter.HomeHighestBidsAdapter;
import com.plugi.plugi.feature.category.adapter.HomeLowestAskAdapter;
import com.plugi.plugi.feature.category.adapter.HomeMostPopularAdapter;
import com.plugi.plugi.feature.category.adapter.HomePopularBrandsAdapter;
import com.plugi.plugi.feature.category.adapter.HomeReleaseCalenderAdapter;
import com.plugi.plugi.feature.category.interfaces.OnRelatedItemClickListener;
import com.plugi.plugi.feature.item.ConditionFragment;
import com.plugi.plugi.feature.item.ItemDetailsFragment;
import com.plugi.plugi.feature.main.MainActivity;
import com.plugi.plugi.feature.search.adapter.SearchItemsAdapter;
import com.plugi.plugi.feature.search.adapter.TopResultAdapter;
import com.plugi.plugi.feature.search.adapter.VerticalItemsAdapter;
import com.plugi.plugi.feature.search.filter.SortFragment;
import com.plugi.plugi.feature.search.interfaces.onSearchAction;
import com.plugi.plugi.models.HomeScreen;
import com.plugi.plugi.models.MainCategory;
import com.plugi.plugi.models.SearchItems;
import com.plugi.plugi.retrofit.ApiInterface;
import com.plugi.plugi.retrofit.RetrofitClient;
import com.plugi.plugi.retrofit.SharedPrefManager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategorySearchFragment extends Fragment implements onSearchAction , OnRelatedItemClickListener {
    private Context mCtx;
    private MainCategory.Category category;
    private int position;

    private int brandID = 0;
    private int sizeType = 0;
    private int size = 0;
    private int price = 0;
    private int year = 0;
    private int sort = 1;

    private String tag;
    public static CategorySearchFragment newInstance(MainCategory.Category category , int position  , Context context) {
        Bundle args = new Bundle();
        CategorySearchFragment fragment = new CategorySearchFragment();
        fragment.tag = fragment.getTag();
        fragment.category = category;
        fragment.position = position;
        fragment.mCtx = context;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //SharedPrefManager.getInstance(getActivity()).CategoryTAG(category , tag); TODO
    }

    SearchItemsAdapter searchItemsAdapter;
    VerticalItemsAdapter verticalItemsAdapter;

    RecyclerView rec_searchItems ;

    ShimmerFrameLayout shimmerFrameLayout;

    List<SearchItems.Item> searchItemsList;

    LinearLayout viewNoItems;

    ImageView sortIMG , filterIMG;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_category, container, false);
        /*
        if(position != -2) {
            ((MainActivity) getActivity()).setSelectedCategory(category);
        }

         */
        return view;
    }

    @Override
    public void onResume() {
        /*
        if(position != -2) {
            ((MainActivity) getActivity()).setSelectedCategory(category);
        }

         */
        super.onResume();
    }

    private void LoadSearchInItems(int _sort , int _brandID , int _size ,int _sizeType , int _price , int _year) {

        ApiInterface service = RetrofitClient.retrofitWrite().create(ApiInterface.class);
        /* StatusID ==> NewProduct=1, MostPopular=3 , ReleaseCalender=4, 0= all */
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("CategoryID", category.getCategoryID());
        jsonParams.put("BrandId", _brandID);
        jsonParams.put("SearchKey", "");
        jsonParams.put("Filter_SizeTypeIDs", _sizeType);
        jsonParams.put("Filter_SizeIDs", _size);
        jsonParams.put("Filter_PriceIDs", _price);
        jsonParams.put("Filter_YearIDs", _year);
        jsonParams.put("SortByID", _sort);
        jsonParams.put("language_ID", 1);
        RequestBody bodyToken = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
        //defining the call
        Call<SearchItems> call = service.SearchInItems(
                bodyToken
        );
        call.enqueue(new Callback<SearchItems>() {
            @Override
            public void onResponse(Call<SearchItems> call, Response<SearchItems> response) {
                Log.d("CATEGORY", "onResponse: " + response.body().getStatusMessage());
                if(response.body().getStatusMessage().equals("Success"))
                {
                  setSearchItems(response.body());

                }
                else
                {
                    shimmerFrameLayout.setVisibility(View.GONE);
                    shimmerFrameLayout.stopShimmer();
                    viewNoItems.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<SearchItems> call, Throwable t) {
                Log.d("CATEGORY", "onFailure: " + t.getLocalizedMessage());
            }
        });
    }

    private void setSearchItems(SearchItems searchItems) {
        searchItemsList.addAll(searchItems.getItems());
        if (((MainActivity) mCtx).getToggleType() == 1) {
            verticalItemsAdapter.notifyDataSetChanged();
        }
        else {
            searchItemsAdapter.notifyDataSetChanged();
        }

        shimmerFrameLayout.setVisibility(View.GONE);
        shimmerFrameLayout.stopShimmer();

    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sortIMG = view.findViewById(R.id.sortIMG);

        filterIMG = view.findViewById(R.id.filterIMG);

        viewNoItems = view.findViewById(R.id.viewNoItems);

        shimmerFrameLayout = view.findViewById(R.id.parentShimmerLayout);

        shimmerFrameLayout.startShimmer();

        rec_searchItems = (RecyclerView) view.findViewById(R.id.rec_searchItems);

        searchItemsList = new ArrayList<>();

        if (((MainActivity) mCtx).getSelectedSort() != null) {
            sort = ((MainActivity) mCtx).getSelectedSort().getID();
        }
        if (((MainActivity) mCtx).getSelectedBrand() != null) {
            brandID = ((MainActivity) mCtx).getSelectedBrand().getCategoryID();
        }
        if (((MainActivity) mCtx).getSelectedSize() != null) {
            size = ((MainActivity) mCtx).getSelectedSize().getID();
        }
        if (((MainActivity) mCtx).getSelectedSizeType() != null) {
            sizeType = ((MainActivity) mCtx).getSelectedSizeType().getID();
        }
        if (((MainActivity) mCtx).getSelectedPrice() != null) {
            price = ((MainActivity) mCtx).getSelectedPrice().getID();
        }
        if (((MainActivity) mCtx).getSelectedReleaseYear() != null) {
            year = ((MainActivity) mCtx).getSelectedReleaseYear().getID();
        }
        Log.d("CATEGORY", "LoadMostPopular: " + category.getCategoryID() + brandID + sort + size + sizeType + price + year);
        LoadSearchInItems(sort, brandID, size, sizeType, price, year);


        if (((MainActivity) mCtx).getToggleType() == 1) {
            LinearLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);

            rec_searchItems.setLayoutManager(mLayoutManager);
            rec_searchItems.setItemAnimator(new DefaultItemAnimator());
            rec_searchItems.setHasFixedSize(true);
            rec_searchItems.setNestedScrollingEnabled(false);
        /* TODO USELESS WITHOUT DATABASE
        recCategory.addOnScrollListener(new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                userPage++;
                getData(userPage);

            }
        });

         */
            verticalItemsAdapter = new VerticalItemsAdapter(mCtx, searchItemsList, this);
            rec_searchItems.setAdapter(verticalItemsAdapter);
        } else {

            LinearLayoutManager mLayoutManager = new GridLayoutManager(mCtx, 2);
            rec_searchItems.setLayoutManager(mLayoutManager);
            searchItemsAdapter = new SearchItemsAdapter(mCtx, searchItemsList, this);
            rec_searchItems.setAdapter(searchItemsAdapter);
        }

        sortIMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((MainActivity) mCtx).getSortList().size() > 0 &&
                        ((MainActivity) mCtx).getCategoryList().size() > 0 &&
                        ((MainActivity) mCtx).getBrandsList().size() > 0 &&
                        ((MainActivity) mCtx).getSizeList().size() > 0 &&
                        ((MainActivity) mCtx).getSizeTypeList().size() > 0 &&
                        ((MainActivity) mCtx).getPriceList().size() > 0 &&
                        ((MainActivity) mCtx).getReleaseYearList().size() > 0) {
                    //mViewPager.setVisibility(View.GONE);
                    ((MainActivity) mCtx).sortingContainer.setVisibility(View.VISIBLE);
                    Fragment fragment = null;
                    fragment = new SortFragment();
                    ((MainActivity) mCtx).replaceSortingFragments(fragment, Constants.BUNDLE_SORTED_ID ,  ((MainActivity) mCtx).getSelectedSort() , Constants.BUNDLE_SORTED_TYPE  , 0);
                }
            }
        });
        filterIMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((MainActivity) mCtx).getSortList().size() > 0 &&
                        ((MainActivity) mCtx).getCategoryList().size() > 0 &&
                        ((MainActivity) mCtx).getBrandsList().size() > 0 &&
                        ((MainActivity) mCtx).getSizeList().size() > 0 &&
                        ((MainActivity) mCtx).getSizeTypeList().size() > 0 &&
                        ((MainActivity) mCtx).getPriceList().size() > 0 &&
                        ((MainActivity) mCtx).getReleaseYearList().size() > 0) {
                    //mViewPager.setVisibility(View.GONE);
                    ((MainActivity) mCtx).sortingContainer.setVisibility(View.VISIBLE);
                    Fragment fragment = null;
                    fragment = new FilterFragment();
                    ((MainActivity) mCtx).replaceSortingFragments(fragment, Constants.BUNDLE_FILTER_EDITED, "default");
                }
            }
        });


    }

    @Override
    public void onSearch() {

    }

    @Override
    public void onItemClicked(Integer itemID) {
        if(SharedPrefManager.getInstance(mCtx).getGuestCondition() == 1) {
            //((MainActivity) mCtx).mViewPager.setVisibility(View.GONE);

            Fragment fragment = null;
            fragment = new ItemDetailsFragment();
            Bundle args = new Bundle();
            args.putInt(Constants.BUNDLE_ITEM_ID, itemID);
            fragment.setArguments(args);
            ((MainActivity) mCtx).replacePopUpFragments(fragment);
        }
        else {
            //((MainActivity) mCtx).mViewPager.setVisibility(View.GONE);
            ((MainActivity) mCtx).sidemenu.setVisibility(View.VISIBLE);
            Fragment fragment = null;
            fragment = new ConditionFragment();
            Bundle args = new Bundle();
            args.putInt(Constants.BUNDLE_ITEM_ID, itemID);
            args.putInt(Constants.BUNDLE_CATEGORY_ID, ((MainActivity) mCtx).getSelectedCategory().getCategoryID());
            fragment.setArguments(args);
            ((MainActivity) mCtx).replaceFragments(fragment);
        }

    }
}