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
import com.plugi.plugi.feature.search.adapter.OnCompleteAdapter;
import com.plugi.plugi.feature.search.adapter.SearchHistoryAdapter;
import com.plugi.plugi.feature.search.adapter.SearchTrendingAdapter;
import com.plugi.plugi.feature.search.adapter.TopResultAdapter;
import com.plugi.plugi.feature.search.interfaces.OnSuggestClickListener;
import com.plugi.plugi.models.AutoComplete;
import com.plugi.plugi.models.MainCategory;
import com.plugi.plugi.models.PojoOnComplete;
import com.plugi.plugi.models.SearchItems;
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

public class OnCompleteFragment extends BaseFragment implements OnSuggestClickListener {
    List<PojoOnComplete> onCompleteList;
    List<AutoComplete.AutoComplateItemList> searchItemList;

    OnCompleteAdapter onCompleteAdapter;
    TopResultAdapter topResultAdapter;
    RecyclerView rec_OnComplete , rec_TopResult;
    ShimmerFrameLayout shimmerFrameLayout;
    private String searchTXT;

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_on_complete, container, false);
        searchTXT = getArguments().getString(Constants.BUNDLE_SEARCH_TXT);

        rec_OnComplete = view.findViewById(R.id.rec_OnComplete);
        rec_TopResult = view.findViewById(R.id.rec_TopResult);

        shimmerFrameLayout = view.findViewById(R.id.parentShimmerLayout);
        shimmerFrameLayout.startShimmer();

        /*
        shimmerFrameLayout.setVisibility(View.GONE);
        shimmerFrameLayout.stopShimmer();

         */

        AssignOnCompleteList();
        AssignTopResultList();

        pojoSuggestions();

        AutoComplete();
        return view;

    }

    private void pojoSuggestions() {

        onCompleteList.add(new PojoOnComplete("1" , 1 , 1 , 1 ,   searchTXT + "Test Pojo Item 1" , 150));
        onCompleteList.add(new PojoOnComplete("2" , 1 , 1 , 1 , searchTXT + "Test Pojo Item 2" , 250));
        onCompleteList.add(new PojoOnComplete("3" , 1 , 1 , 1 , searchTXT + "Test Pojo Item 3" , 81));
        onCompleteList.add(new PojoOnComplete("4" , 1 , 1 , 1 , searchTXT + "Test Pojo Item 4" , 109));
        onCompleteList.add(new PojoOnComplete("5" , 1 , 1 , 1 , searchTXT + "Test Pojo Item 5" , 75));
        onCompleteAdapter.notifyDataSetChanged();

        topResultAdapter.notifyDataSetChanged();
    }

    private void AssignOnCompleteList() {
        onCompleteList = new ArrayList<>();
        LinearLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);

        rec_OnComplete.setLayoutManager(mLayoutManager);
        rec_OnComplete.setItemAnimator(new DefaultItemAnimator());
        rec_OnComplete.setHasFixedSize(true);
        rec_OnComplete.setNestedScrollingEnabled(false);
        /* TODO USELESS WITHOUT DATABASE
        recCategory.addOnScrollListener(new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                userPage++;
                getData(userPage);

            }
        });

         */
        onCompleteAdapter = new OnCompleteAdapter(getActivity() , onCompleteList , searchTXT.length() , this);
        rec_OnComplete.setAdapter(onCompleteAdapter);
    }
    private void AssignTopResultList() {
        searchItemList = new ArrayList<>();
        LinearLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);

        rec_TopResult.setLayoutManager(mLayoutManager);
        rec_TopResult.setItemAnimator(new DefaultItemAnimator());
        rec_TopResult.setHasFixedSize(true);
        rec_TopResult.setNestedScrollingEnabled(false);
        /* TODO USELESS WITHOUT DATABASE
        recCategory.addOnScrollListener(new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                userPage++;
                getData(userPage);

            }
        });

         */
        topResultAdapter = new TopResultAdapter(getActivity() , searchItemList);
        rec_TopResult.setAdapter(topResultAdapter);

    }
    @Override
    protected int layoutRes() {
        return R.layout.fragment_on_complete;
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
    private void AutoComplete() {
        ApiInterface service = RetrofitClient.retrofitWrite().create(ApiInterface.class);

        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("SearchKey", searchTXT);
        jsonParams.put("language_ID", 1);
        RequestBody bodyToken = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
        //defining the call
        Call<AutoComplete> call = service.SearchAutoComplate(
                bodyToken
        );
        call.enqueue(new Callback<AutoComplete>() {
            @Override
            public void onResponse(Call<AutoComplete> call, Response<AutoComplete> response) {

                if(response.body().getStatusMessage().equals("Success"))
                {
                    if(response.body().getAutoComplateItemList() != null) {
                        searchItemList.addAll(response.body().getAutoComplateItemList());
                        topResultAdapter.notifyDataSetChanged();

                        shimmerFrameLayout.setVisibility(View.GONE);
                        shimmerFrameLayout.stopShimmer();
                    }
                    //SharedPrefManager.getInstance(getActivity()).CategoryList(response.body());

                }
            }

            @Override
            public void onFailure(Call<AutoComplete> call, Throwable t) {

            }
        });
    }
    @Override
    public void onSuggestClicked(String contact, int position) {
        ((MainActivity) requireActivity()).setLastSearchTXT(contact);
        Fragment fragment = null;
        //fragment = new HomeFragment();//CategoryFragment.newInstance(category);
        fragment = new SearchResultFragment();
        //replaceFragments(fragment, Constants.BUNDLE_SEARCH_TXT , contact);
        ((MainActivity) getActivity()).replaceFragments(fragment  , Constants.BUNDLE_SEARCH_TXT , contact);
        ((MainActivity) getActivity()).sidemenu.setVisibility(View.VISIBLE);
    }
}
