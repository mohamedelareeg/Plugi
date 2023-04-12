package com.plugi.plugi.feature.search;

import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.plugi.plugi.R;
import com.plugi.plugi.core.base.BaseFragment;
import com.plugi.plugi.core.utilities.Constants;
import com.plugi.plugi.feature.category.interfaces.OnRelatedItemClickListener;
import com.plugi.plugi.feature.item.ConditionFragment;
import com.plugi.plugi.feature.item.ItemDetailsFragment;
import com.plugi.plugi.feature.main.MainActivity;
import com.plugi.plugi.feature.search.adapter.SearchResultAdapter;
import com.plugi.plugi.feature.search.interfaces.OnSuggestClickListener;
import com.plugi.plugi.models.SearchItems;
import com.plugi.plugi.retrofit.ApiInterface;
import com.plugi.plugi.retrofit.RetrofitClient;
import com.plugi.plugi.retrofit.SharedPrefManager;

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

public class SearchResultFragment extends BaseFragment implements OnSuggestClickListener , OnRelatedItemClickListener {
    List<SearchItems.Item> searchItemList;

    SearchResultAdapter searchResultAdapter;
    RecyclerView  rec_SearchResult;
    ShimmerFrameLayout shimmerFrameLayout;
    private String _search;
    int userPage = 1;
    ImageView ivBack , ivSort;
    TextView searchTXT;

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_result, container, false);
        _search = getArguments().getString(Constants.BUNDLE_SEARCH_TXT);

        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // ((MainActivity) requireActivity()).onBackPressed();//TODO
                ((MainActivity) getActivity()).forceHiddenSideMenu();
            }
        });
        searchTXT = view.findViewById(R.id.searchTXT);
        if(_search != null && !_search.equals("")) {
            searchTXT.setText(_search);
        }
        ivSort = view.findViewById(R.id.ivSort);
        ivSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( ((MainActivity) getActivity()).getSortList().size() > 0 &&
                        ((MainActivity) getActivity()).getCategoryList().size() > 0 &&
                        ((MainActivity) getActivity()).getBrandsList().size() > 0 &&
                        ((MainActivity) getActivity()).getSizeList().size() > 0 &&
                        ((MainActivity) getActivity()).getSizeTypeList().size() > 0 &&
                        ((MainActivity) getActivity()).getPriceList().size() > 0 &&
                        ((MainActivity) getActivity()).getReleaseYearList().size() > 0) {
                    //mViewPager.setVisibility(View.GONE);
                    ((MainActivity) getActivity()).sortingContainer.setVisibility(View.VISIBLE);
                    Fragment fragment = null;
                    fragment = new FilterFragment();
                    ((MainActivity) getActivity()).replaceSortingFragments(fragment, Constants.BUNDLE_FILTER_EDITED, "searchResult");
                }
            }
        });
        rec_SearchResult = view.findViewById(R.id.rec_SearchResult);

        shimmerFrameLayout = view.findViewById(R.id.parentShimmerLayout);
        shimmerFrameLayout.startShimmer();

        /*
        shimmerFrameLayout.setVisibility(View.GONE);
        shimmerFrameLayout.stopShimmer();

         */


        AssignTopResultList();

        pojoSuggestions();
        LoadSearchInItems();
        return view;

    }

    private void pojoSuggestions() {

        searchResultAdapter.notifyDataSetChanged();
    }
    private void AssignTopResultList() {
        searchItemList = new ArrayList<>();
        LinearLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);

        rec_SearchResult.setLayoutManager(mLayoutManager);
        rec_SearchResult.setItemAnimator(new DefaultItemAnimator());
        rec_SearchResult.setHasFixedSize(true);
        rec_SearchResult.setNestedScrollingEnabled(false);
        /* TODO USELESS WITHOUT DATABASE
        recCategory.addOnScrollListener(new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                userPage++;
                getData(userPage);

            }
        });

         */
        searchResultAdapter = new SearchResultAdapter(getActivity() , searchItemList , this);
        rec_SearchResult.setAdapter(searchResultAdapter);
    }
    private void LoadSearchInItems() {

        ApiInterface service = RetrofitClient.retrofitWrite().create(ApiInterface.class);
        /* StatusID ==> NewProduct=1, MostPopular=3 , ReleaseCalender=4, 0= all */
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("CategoryID", ((MainActivity) getActivity()).getSelectedCategory());
        jsonParams.put("BrandId", 0);
        jsonParams.put("SearchKey", "");
        jsonParams.put("Filter_SizeTypeIDs", "");
        jsonParams.put("Filter_SizeIDs", "");
        jsonParams.put("Filter_PriceIDs", "");
        jsonParams.put("Filter_YearIDs", "");
        jsonParams.put("SortByID", 1);
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
                    searchItemList.addAll(response.body().getItems());
                    searchResultAdapter.notifyDataSetChanged();

                    shimmerFrameLayout.setVisibility(View.GONE);
                    shimmerFrameLayout.stopShimmer();
                    //setSearchItems(response.body());

                }
            }

            @Override
            public void onFailure(Call<SearchItems> call, Throwable t) {
                Log.d("CATEGORY", "onFailure: " + t.getLocalizedMessage());
            }
        });
    }
    @Override
    protected int layoutRes() {
        return R.layout.fragment_on_complete;
    }

    @Override
    public void onSuggestClicked(String contact, int position) {

    }

    public void setFilterResult(String searchTXT)
    {
        Log.d("onBackPressed", "setFilterResult: ");
        if(((MainActivity) getActivity()).getSelectedCategory() != null) {
            //Log.d("FILTER", "AssignSortList: " + selectBrand.getName());
            Fragment fragment = new SearchResultFragment();
            //replaceFragments(fragment, Constants.BUNDLE_SEARCH_TXT , contact);
            ((MainActivity) getActivity()).replaceFragments(fragment  , Constants.BUNDLE_SEARCH_TXT , searchTXT);
            ((MainActivity) getActivity()).sidemenu.setVisibility(View.VISIBLE);
        }

    }
    @Override
    public void onItemClicked(Integer itemID) {

        /*
        if(SharedPrefManager.getInstance(getActivity()).getGuestCondition() == 1) {
            //((MainActivity) mCtx).mViewPager.setVisibility(View.GONE);

            Fragment fragment = null;
            fragment = new ItemDetailsFragment();
            Bundle args = new Bundle();
            args.putInt(Constants.BUNDLE_ITEM_ID, itemID);
            fragment.setArguments(args);
            ((MainActivity) getActivity()).replacePopUpFragments(fragment , Constants.BUNDLE_SEARCH_RESULT , "searchResult");
            ((MainActivity) requireActivity()).sidemenu.setVisibility(View.GONE);
        }
        else
        {
            //((MainActivity) mCtx).mViewPager.setVisibility(View.GONE);
            ((MainActivity) getActivity()).sidemenu.setVisibility(View.VISIBLE);
            Fragment fragment = null;
            fragment = new ConditionFragment();
            Bundle args = new Bundle();
            args.putInt(Constants.BUNDLE_ITEM_ID, itemID);
            args.putInt(Constants.BUNDLE_CATEGORY_ID, ((MainActivity) getActivity()).getSelectedCategory().getCategoryID() );
            fragment.setArguments(args);
            ((MainActivity) getActivity()).replaceFragments(fragment);
            ((MainActivity) requireActivity()).sidemenu.setVisibility(View.GONE);
        }

         */
        if(SharedPrefManager.getInstance(getActivity()).getGuestCondition() == 1) {
            //((MainActivity) mCtx).mViewPager.setVisibility(View.GONE);

            Fragment fragment = null;
            fragment = new ItemDetailsFragment();
            Bundle args = new Bundle();
            args.putInt(Constants.BUNDLE_ITEM_ID, itemID);
            args.putInt(Constants.BUNDLE_ITEM_TYPE, 2);
            fragment.setArguments(args);
            ((MainActivity) getActivity()).replacePopUpFragments(fragment);
            ((MainActivity) requireActivity()).sidemenu.setVisibility(View.GONE);
        }
        else
        {
            //((MainActivity) mCtx).mViewPager.setVisibility(View.GONE);

            Fragment fragment = null;
            fragment = new ConditionFragment();
            Bundle args = new Bundle();
            args.putInt(Constants.BUNDLE_ITEM_ID, itemID);
            args.putInt(Constants.BUNDLE_ITEM_TYPE, 2);
            args.putInt(Constants.BUNDLE_CATEGORY_ID, ((MainActivity) getActivity()).getSelectedCategory().getCategoryID());
            fragment.setArguments(args);
            ((MainActivity) getActivity()).replacePopUpFragments(fragment);
            ((MainActivity) requireActivity()).sidemenu.setVisibility(View.GONE);
        }


    }
}
