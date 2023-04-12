package com.plugi.plugi.feature.search;

import android.os.Bundle;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.plugi.plugi.R;
import com.plugi.plugi.core.base.BaseFragment;
import com.plugi.plugi.core.utilities.Constants;
import com.plugi.plugi.feature.main.MainActivity;
import com.plugi.plugi.feature.search.adapter.SearchHistoryAdapter;
import com.plugi.plugi.feature.search.adapter.SearchTrendingAdapter;
import com.plugi.plugi.feature.search.interfaces.OnSuggestClickListener;
import com.plugi.plugi.models.AutoComplete;
import com.plugi.plugi.models.SearchHistory;
import com.plugi.plugi.retrofit.ApiInterface;
import com.plugi.plugi.retrofit.RetrofitClient;

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

public class SuggestFragment extends BaseFragment implements OnSuggestClickListener {
    List<SearchHistory.RecentSearchWord> suggestList;
    List<SearchHistory.TrendingSearchWord> trendingList;

    SearchHistoryAdapter searchHistoryAdapter;
    SearchTrendingAdapter searchTrendingAdapter;
    RecyclerView rec_RecentSearch , rec_TrendingSearch;
    ShimmerFrameLayout shimmerFrameLayout , shimmerFrameLayout2;

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_suggest, container, false);

        shimmerFrameLayout = view.findViewById(R.id.parentShimmerLayout);
        shimmerFrameLayout.startShimmer();

        shimmerFrameLayout2 = view.findViewById(R.id.parentShimmerLayout2);
        shimmerFrameLayout2.startShimmer();

        rec_RecentSearch = view.findViewById(R.id.rec_RecentSearch);
        rec_TrendingSearch = view.findViewById(R.id.rec_TrendingSearch);


        AssignHistoryList();
        AssignTrendingList();

        pojoSuggestions();
        LoadSuggest();
        return view;

    }

    private void pojoSuggestions() {

     //TODO
    }

    private void AssignHistoryList() {
        suggestList = new ArrayList<>();
        LinearLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);

        rec_RecentSearch.setLayoutManager(mLayoutManager);
        rec_RecentSearch.setItemAnimator(new DefaultItemAnimator());
        rec_RecentSearch.setHasFixedSize(true);
        rec_RecentSearch.setNestedScrollingEnabled(false);
        /* TODO USELESS WITHOUT DATABASE
        recCategory.addOnScrollListener(new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                userPage++;
                getData(userPage);

            }
        });

         */
        searchHistoryAdapter = new SearchHistoryAdapter(getActivity() , suggestList , this);
        rec_RecentSearch.setAdapter(searchHistoryAdapter);
    }
    private void AssignTrendingList() {
        trendingList = new ArrayList<>();
        LinearLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);

        rec_TrendingSearch.setLayoutManager(mLayoutManager);
        rec_TrendingSearch.setItemAnimator(new DefaultItemAnimator());
        rec_TrendingSearch.setHasFixedSize(true);
        rec_TrendingSearch.setNestedScrollingEnabled(false);
        /* TODO USELESS WITHOUT DATABASE
        recCategory.addOnScrollListener(new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                userPage++;
                getData(userPage);

            }
        });

         */
        searchTrendingAdapter = new SearchTrendingAdapter(getActivity() , trendingList , this);
        rec_TrendingSearch.setAdapter(searchTrendingAdapter);
    }
    private void LoadSuggest() {
        ApiInterface service = RetrofitClient.retrofitWrite().create(ApiInterface.class);

        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("SearchKey", "");
        jsonParams.put("language_ID", 1);
        RequestBody bodyToken = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
        //defining the call
        Call<SearchHistory> call = service.SearchHistory(
                bodyToken
        );
        call.enqueue(new Callback<SearchHistory>() {
            @Override
            public void onResponse(Call<SearchHistory> call, Response<SearchHistory> response) {

                if(response.body().getStatusMessage().equals("Success"))
                {
                        suggestList.addAll(response.body().getRecentSearchWords());
                        searchHistoryAdapter.notifyDataSetChanged();

                        shimmerFrameLayout.setVisibility(View.GONE);
                        shimmerFrameLayout.stopShimmer();

                        trendingList.addAll(response.body().getTrendingSearchWords());
                        searchTrendingAdapter.notifyDataSetChanged();

                        shimmerFrameLayout2.setVisibility(View.GONE);
                        shimmerFrameLayout2.stopShimmer();

                    //SharedPrefManager.getInstance(getActivity()).CategoryList(response.body());

                }
            }

            @Override
            public void onFailure(Call<SearchHistory> call, Throwable t) {

            }
        });
    }
    @Override
    protected int layoutRes() {
        return R.layout.fragment_suggest;
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
                .commit();

    }
    @Override
    public void onSuggestClicked(String contact, int position) {
        ((MainActivity) requireActivity()).setLastSearchTXT(contact);
        Fragment fragment = null;
        //fragment = new HomeFragment();//CategoryFragment.newInstance(category);
        fragment = new SearchResultFragment();
        ((MainActivity) getActivity()).replaceFragments(fragment  , Constants.BUNDLE_SEARCH_TXT , contact);
        ((MainActivity) getActivity()).sidemenu.setVisibility(View.VISIBLE);
        //sidemenu
    }
}
