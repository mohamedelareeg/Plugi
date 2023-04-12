package com.plugi.plugi.feature.profile.buying;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

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
import com.plugi.plugi.feature.order.adapter.SelectExpirationAdapter;
import com.plugi.plugi.feature.order.interfaces.OnExpirationClickListener;
import com.plugi.plugi.feature.profile.buyingselling.adapter.CurrentBuyingAdapter;
import com.plugi.plugi.feature.profile.buying.details.BuyingCurrentDetails;
import com.plugi.plugi.feature.profile.buyingselling.interfaces.OnCurrentClickListener;
import com.plugi.plugi.feature.profile.buyingselling.interfaces.OnCurrentEditClickListener;
import com.plugi.plugi.models.ItemDetails;
import com.plugi.plugi.models.UpdateBidExpire;
import com.plugi.plugi.models.User;
import com.plugi.plugi.models.itemDetails.ExpirationDaysList;
import com.plugi.plugi.models.orderDetails.CurrentList;
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

public class CurrentBuyingFragment extends BaseFragment implements IOnBackPressed  , OnCurrentClickListener, OnExpirationClickListener , OnCurrentEditClickListener {

    ShimmerFrameLayout parentShimmerLayout , parentShimmerLayout2;
    private ScrollView itemScrollView;

    CurrentBuyingAdapter currentBuyingAdapter;
    List<CurrentList> buyingCurrentList;
    RecyclerView rec_Current_Buying;
    public static Dialog expirationDialog;
    private ExpirationDaysList selectExpiration;
    private int itemID;




    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buying_current, container, false);


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
            buyingCurrentList.addAll(((MainActivity) getActivity()).getCustomerBids().getCurrentList());
            currentBuyingAdapter.notifyDataSetChanged();

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
        currentBuyingAdapter = new CurrentBuyingAdapter(getActivity(), buyingCurrentList , this , this);
        rec_Current_Buying.setAdapter(currentBuyingAdapter);

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

        if(((MainActivity) getActivity()).getSelectExpiration() != null) {
            selectExpiration = ((MainActivity) getActivity()).getSelectExpiration();

            //expiration_value.setText(selectExpiration.getPropValue() + getActivity().getResources().getString(R.string.day));
        }
        else if(((MainActivity) getActivity()).getExpirationList() != null  && ((MainActivity) getActivity()).getExpirationList().size() > 0) {
            selectExpiration = ((MainActivity) getActivity()).getExpirationList().get(0);
            //expiration_value.setText(selectExpiration.getPropValue() + getActivity().getResources().getString(R.string.day));
            ((MainActivity) getActivity()).setSelectExpiration(selectExpiration);

        }
        else if(((MainActivity) getActivity()).getCustomerBids().getExpirationDaysList() != null  && ((MainActivity) getActivity()).getCustomerBids().getExpirationDaysList().size() > 0) {
            selectExpiration = ((MainActivity) getActivity()).getCustomerBids().getExpirationDaysList().get(0);
            //expiration_value.setText(selectExpiration.getPropValue() + getActivity().getResources().getString(R.string.day));
            ((MainActivity) getActivity()).setSelectExpiration(selectExpiration);

        }



    }


    @Override
    protected int layoutRes() {
        return R.layout.fragment_setting_buying;
    }

    public void showExpirationDialog(Activity activity , List<ExpirationDaysList> expirationList){

        expirationDialog = new Dialog(activity);
        // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        expirationDialog.setCancelable(false);
        expirationDialog.setContentView(R.layout.dialog_select_expiration);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(expirationDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        ImageView ivClose = expirationDialog.findViewById(R.id.ivClose);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expirationDialog.dismiss();
               // btnSubmit.setEnabled(true);
            }
        });
        int selectedIndex = -1;

        if(selectExpiration != null) {
            //Log.d("FILTER", "AssignSortList: " + selectBrand.getName());
            selectedIndex = expirationList.indexOf(selectExpiration);
        }

        RecyclerView recyclerView = expirationDialog.findViewById(R.id.rec_AllExpiration);
        SelectExpirationAdapter adapterRe = new SelectExpirationAdapter(activity , expirationList , selectedIndex , this);
        recyclerView.setAdapter(adapterRe);

        LinearLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        expirationDialog.show();
        expirationDialog.getWindow().setAttributes(lp);
        //btnSubmit.setEnabled(false);


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
    public void onCurrentClicked(CurrentList contact, int position) {
        ((MainActivity) requireActivity()).setViewBidDetails(null);
        Fragment fragment = null;
        fragment = new BuyingCurrentDetails();
        Bundle args = new Bundle();
        args.putInt(Constants.BUNDLE_ITEM_ID, contact.getID());
        fragment.setArguments(args);
        ((MainActivity) requireActivity()).replaceReviewFragments(fragment);
    }

    @Override
    public void onExpirationClicked(ExpirationDaysList contact, int position) {
        //expiration_value.setText(contact.getPropValue() + getActivity().getResources().getString(R.string.day));
        ((MainActivity) getActivity()).setSelectExpiration(contact);
        selectExpiration = contact;
        Log.d("LoadData", "onExpirationClicked: " + contact.getPropValue() );
        setExpireDate(getItemID());
        expirationDialog.dismiss();
        //btnSubmit.setEnabled(true);
    }
    public void setExpireDate(int _itemID)
    {
        Log.d("LoadData", "LoadData: " + _itemID);
        User currentUser = SharedPrefManager.getInstance(getActivity()).getUser();
        ApiInterface service = RetrofitClient.retrofitBearerTokenWrite(currentUser).create(ApiInterface.class);

        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("Language_ID", 1);
        jsonParams.put("CustomerID", currentUser.getCustomer().getID());
        jsonParams.put("ID", _itemID);
        jsonParams.put("ExtendExpireDay", Integer.valueOf(selectExpiration.getPropValue()));
        RequestBody bodyToken = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
        //defining the call
        Call<UpdateBidExpire> call = service.UpdateBidExpireDate(
                bodyToken
        );
        call.enqueue(new Callback<UpdateBidExpire>() {
            @Override
            public void onResponse(Call<UpdateBidExpire> call, Response<UpdateBidExpire> response) {

                if(response.body() != null) {
                    if(response.body().getStatusMessage().equals("Success"))
                    {

                        Log.d("LoadData", "onResponse: " + response.body().getNEWExpireDate());
                        Toast.makeText(getActivity(), "" + getResources().getString(R.string.updated_succ), Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        Toast.makeText(getActivity(), "" + response.body().getStatusMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("LoadData", "onResponse: " + response.body().getStatusMessage());
                    }
                }
                else
                {
                    Log.d("LoadData", "onResponse: " + response.message());
                    Log.d("LoadData", "onResponse: " + response.errorBody());
                }
                /*


                 */

            }

            @Override
            public void onFailure(Call<UpdateBidExpire> call, Throwable t) {
                Log.d("ItemDetails", "onFailure: " + t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void onCurrentEditClicked(CurrentList contact, int position) {
        setItemID(contact.getItemID());
        showExpirationDialog(getActivity(),((MainActivity) getActivity()).getCustomerBids().getExpirationDaysList());
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }
}
