package com.plugi.plugi.feature.profile.buying;

import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.plugi.plugi.R;
import com.plugi.plugi.core.base.BaseFragment;
import com.plugi.plugi.core.utilities.Constants;
import com.plugi.plugi.feature.main.MainActivity;
import com.plugi.plugi.feature.main.interfaces.IOnBackPressed;
import com.plugi.plugi.feature.profile.buyingselling.adapter.HistoryBuyingAdapter;
import com.plugi.plugi.feature.profile.buying.details.BuyingHistoryDetails;
import com.plugi.plugi.feature.profile.buyingselling.interfaces.OnHistoryClickListener;
import com.plugi.plugi.models.ItemDetails;
import com.plugi.plugi.models.orderDetails.HistoryList;
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

public class HistoryBuyingFragment extends BaseFragment implements IOnBackPressed , OnHistoryClickListener {

    ShimmerFrameLayout parentShimmerLayout , parentShimmerLayout2 ;
    private ScrollView itemScrollView;

    HistoryBuyingAdapter historyBuyingAdapter;
    List<HistoryList> buyingCurrentList;
    RecyclerView rec_Current_Buying;




    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buying_history, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        parentShimmerLayout = view.findViewById(R.id.parentShimmerLayout);
        parentShimmerLayout.startShimmer();

        parentShimmerLayout2 = view.findViewById(R.id.parentShimmerLayout2);
        parentShimmerLayout2.startShimmer();

        rec_Current_Buying = view.findViewById(R.id.rec_Current_Buying);
        itemScrollView = view.findViewById(R.id.itemScrollView);
        AssignAddress();
        PojoData();
        //LoadData();

    }

    private void PojoData() {
        if(((MainActivity) getActivity()).getCustomerBids()!=null) {
            buyingCurrentList.addAll(((MainActivity) getActivity()).getCustomerBids().getHistoryList());
            historyBuyingAdapter.notifyDataSetChanged();

            itemScrollView.setVisibility(View.VISIBLE);
            parentShimmerLayout.setVisibility(View.GONE);
            parentShimmerLayout.stopShimmer();

            parentShimmerLayout2.setVisibility(View.GONE);
            parentShimmerLayout2.stopShimmer();
        }


    }


    private void AssignAddress() {
        buyingCurrentList = new ArrayList<>();
        LinearLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);

        rec_Current_Buying.setLayoutManager(mLayoutManager);
        rec_Current_Buying.setItemAnimator(new DefaultItemAnimator());
        rec_Current_Buying.setHasFixedSize(true);
        rec_Current_Buying.setNestedScrollingEnabled(false);
        /* TODO USELESS WITHOUT DATABASE
        recCategory.addOnScrollListener(new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                userPage++;
                getData(userPage);

            }
        });

         */
        historyBuyingAdapter = new HistoryBuyingAdapter(getActivity(), buyingCurrentList , this);
        rec_Current_Buying.setAdapter(historyBuyingAdapter);

    }


    public void LoadData()
    {
        ApiInterface service = RetrofitClient.retrofitWrite().create(ApiInterface.class);

        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("ItemID", 0);
        jsonParams.put("language_ID", 1);
        RequestBody bodyToken = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
        //defining the call
        Call<ItemDetails> call = service.GetItemDetailbyID(
                bodyToken
        );
        call.enqueue(new Callback<ItemDetails>() {
            @Override
            public void onResponse(Call<ItemDetails> call, Response<ItemDetails> response) {

                if(response.body().getStatusMessage().equals("Success"))
                {

                   setDetails(response.body());
                }
            }

            @Override
            public void onFailure(Call<ItemDetails> call, Throwable t) {
                Log.d("ItemDetails", "onFailure: " + t.getLocalizedMessage());
            }
        });
    }

    private void setDetails(ItemDetails itemDetails) {
        Log.d("ItemDetails", "setItemDetails: " + itemDetails.getItemID());





        itemScrollView.setVisibility(View.VISIBLE);
        parentShimmerLayout.setVisibility(View.GONE);
        parentShimmerLayout.stopShimmer();

        parentShimmerLayout2.setVisibility(View.GONE);
        parentShimmerLayout2.stopShimmer();





    }


    @Override
    protected int layoutRes() {
        return R.layout.fragment_setting_buying;
    }



    @Override
    public boolean onBackPressed() {
        return false;
        /*
        if (myCondition) {
            //action not popBackStack
            return true;
        } else {
            return false;
        }

         */
    }


    @Override
    public void onHistoryClicked(HistoryList contact, int position) {
        ((MainActivity) requireActivity()).setViewOrderDetails(null);
        Fragment fragment = null;
        fragment = new BuyingHistoryDetails();
        Bundle args = new Bundle();
        args.putInt(Constants.BUNDLE_ITEM_ID, contact.getID());
        fragment.setArguments(args);
        ((MainActivity) requireActivity()).replaceReviewFragments(fragment);
    }
}
