package com.plugi.plugi.feature.search.filter;

import android.os.Bundle;
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
import com.plugi.plugi.feature.search.filter.adapter.SortItemAdapter;
import com.plugi.plugi.feature.search.filter.interfaces.OnSortClickListener;
import com.plugi.plugi.models.SortIDs;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SortFragment extends BaseFragment implements OnSortClickListener {

    private SortItemAdapter sortItemAdapter;
    private RecyclerView rec_Sort;
    private List<SortIDs.SortListID> sortListIDS;
    private ShimmerFrameLayout shimmerFrameLayout;

    private TextView  ivClose , ivApply;
    SortIDs.SortListID selectedSort;
    SortIDs.SortListID prepSort;
    int viewedType = 0;
    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sort, container, false);

        selectedSort = (SortIDs.SortListID) getArguments().getSerializable(Constants.BUNDLE_SORTED_ID);
        if(selectedSort == null)
        {
            selectedSort =  ((MainActivity) getActivity()).getSortList().get(0);
        }
        viewedType = getArguments().getInt(Constants.BUNDLE_SORTED_TYPE , 0);
        shimmerFrameLayout = view.findViewById(R.id.parentShimmerLayout);
        shimmerFrameLayout.startShimmer();
        rec_Sort = view.findViewById(R.id.rec_Sort);
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
                if(prepSort.getID() !=0) {

                    ((MainActivity) getActivity()).setSelectedSort(prepSort);

                    if(viewedType == 1) {
                        Fragment fragment = null;
                        fragment = new FilterFragment();
                        ((MainActivity) getActivity()).replaceSortingFragments(fragment, Constants.BUNDLE_FILTER_EDITED, "edited");

                    }
                    else {


                        ((MainActivity) getActivity()).forceHiddenSorting(true);
                    }



                }
            }
        });

        return view;
    }
    private void AssignSortList() {
        sortListIDS = new ArrayList<>();
        sortListIDS.addAll(((MainActivity) getActivity()).getSortList());
        LinearLayoutManager mLayoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        rec_Sort.setLayoutManager(mLayoutManager);
        rec_Sort.setItemAnimator(new DefaultItemAnimator());
        rec_Sort.setHasFixedSize(true);
        rec_Sort.setNestedScrollingEnabled(false);
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
        if(selectedSort != null) {
            //Log.d("FILTER", "AssignSortList: " + selectBrand.getName());
            selectedIndex = sortListIDS.indexOf(selectedSort);
        }

        sortItemAdapter = new SortItemAdapter( getActivity() ,sortListIDS , selectedIndex , this);
        rec_Sort.setAdapter(sortItemAdapter);
    }
    public void loadData()
    {

        sortItemAdapter.notifyDataSetChanged();

        shimmerFrameLayout.setVisibility(View.GONE);
        shimmerFrameLayout.stopShimmer();
        /*
        ApiInterface service = RetrofitClient.retrofitWrite().create(ApiInterface.class);

        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("language_ID", 1);
        RequestBody bodyToken = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
        //defining the call
        Call<SortIDs> call = service.GetSearchSortIDs(
                bodyToken
        );
        call.enqueue(new Callback<SortIDs>() {
            @Override
            public void onResponse(Call<SortIDs> call, Response<SortIDs> response) {

                if(response.body().getStatusMessage().equals("Success"))
                {
                    sortListIDS.addAll(response.body().getSortListIDs());
                    sortItemAdapter.notifyDataSetChanged();

                    shimmerFrameLayout.setVisibility(View.GONE);
                    shimmerFrameLayout.stopShimmer();
                }
            }

            @Override
            public void onFailure(Call<SortIDs> call, Throwable t) {

            }
        });

         */
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_sort;
    }

    @Override
    public void onSortClicked(SortIDs.SortListID contact, int position) {


        if(contact.getID() != selectedSort.getID())
        {
            ivApply.setVisibility(View.VISIBLE);
        }
        else
        {
            ivApply.setVisibility(View.GONE);
        }
       prepSort = contact;

    }
}
