package com.plugi.plugi.feature.profile.setting;

import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.plugi.plugi.R;
import com.plugi.plugi.core.base.BaseFragment;
import com.plugi.plugi.feature.profile.setting.adapter.NotificationAdapter;
import com.plugi.plugi.models.GetNotificationsetting;
import com.plugi.plugi.models.User;
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

public class NotificationFragment extends BaseFragment  {

    List<GetNotificationsetting.NotificationSetting> notificationList;
    NotificationAdapter notificationAdapter;
    RecyclerView rec_SNotification;
    LinearLayout viewNoItems;
    private ShimmerFrameLayout shimmerFrameLayout;

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting_notification, container, false);


        rec_SNotification = view.findViewById(R.id.rec_SNotification);
        viewNoItems = view.findViewById(R.id.viewNoItems);
        shimmerFrameLayout = view.findViewById(R.id.parentShimmerLayout);
        shimmerFrameLayout.startShimmer();



        AssignBlogList();
        PojoData();
        loadData();

        return view;
    }

    private void PojoData() {

        /*
        notificationList.add(new Notification(1 ,"Selling" , "Asking , New Highest Bid" , "sent when a new Highest Bid is placed on an item that is at least X precent of your Ask,",
                65,1,0));
        notificationList.add(new Notification(2 ,"Asking" , "Asking , New Lowest Bid" , "sent when a new Highest Bid is placed on an item that is at least X precent of your Ask,",
                25,1,1));
        notificationList.add(new Notification(3 ,"Asking" , "Ask Expiring" , "sent when a new Highest Bid is placed on an item that is at least X precent of your Ask,",
                70,0,1));
        notificationList.add(new Notification(4 ,"Bidding" , "Bid Expiring" , "sent when a new Highest Bid is placed on an item that is at least X precent of your Ask,",
                40,1,1));
        notificationList.add(new Notification(5 ,"Selling" , "Item Sold" , "sent when a new Highest Bid is placed on an item that is at least X precent of your Ask,",
                35,0,0));
        notificationList.add(new Notification(6 ,"Buying" , "First Seller has not shipped" , "sent when a new Highest Bid is placed on an item that is at least X precent of your Ask,",
                10,1,1));
        notificationAdapter.notifyDataSetChanged();

        shimmerFrameLayout.setVisibility(View.GONE);
        shimmerFrameLayout.stopShimmer();

         */
    }

    private void AssignBlogList() {
        notificationList = new ArrayList<>();
        RecyclerView.LayoutManager PopularbrandslayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rec_SNotification.setLayoutManager(PopularbrandslayoutManager);
        notificationAdapter = new NotificationAdapter(getActivity(), notificationList);
        rec_SNotification.setAdapter(notificationAdapter);
    }

    public void loadData()
    {
        if(SharedPrefManager.getInstance(getActivity()).isLoggedIn()) {
            User user = SharedPrefManager.getInstance(getActivity()).getUser();
            ApiInterface service = RetrofitClient.retrofitBearerTokenWrite(user).create(ApiInterface.class);

            Map<String, Object> jsonParams = new ArrayMap<>();
            jsonParams.put("language_ID", 1);
            jsonParams.put("CustomerId", user.getCustomer().getID());
            RequestBody bodyToken = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
            //defining the call
            Call<GetNotificationsetting> call = service.GetNotificationsetting(
                    bodyToken
            );
            call.enqueue(new Callback<GetNotificationsetting>() {
                @Override
                public void onResponse(Call<GetNotificationsetting> call, Response<GetNotificationsetting> response) {

                    if (response.body().getStatusMessage().equals("Success")) {

                        notificationList.addAll(response.body().getNotificationSettings());
                        notificationAdapter.notifyDataSetChanged();
                        shimmerFrameLayout.setVisibility(View.GONE);
                        shimmerFrameLayout.stopShimmer();

                    } else {
                        shimmerFrameLayout.setVisibility(View.GONE);
                        shimmerFrameLayout.stopShimmer();
                        // viewNoItems.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<GetNotificationsetting> call, Throwable t) {
                    Log.d("CLOSET", "onFailure: " + t.getMessage());
                    Log.d("CLOSET", "onFailure: " + t.getLocalizedMessage());
                    shimmerFrameLayout.setVisibility(View.GONE);
                    shimmerFrameLayout.stopShimmer();
                }
            });
        }
    }
    @Override
    protected int layoutRes() {
        return R.layout.fragment_setting_notification;
    }


}
