package com.plugi.plugi.feature.profile.setting;

import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.plugi.plugi.R;
import com.plugi.plugi.core.base.BaseFragment;
import com.plugi.plugi.feature.main.MainActivity;
import com.plugi.plugi.feature.main.interfaces.IOnBackPressed;
import com.plugi.plugi.feature.profile.setting.adapter.AddressAdapter;
import com.plugi.plugi.feature.profile.setting.adapter.PaymentMethodAdapter;
import com.plugi.plugi.feature.profile.setting.interfaces.OnAddressClickListener;
import com.plugi.plugi.feature.profile.setting.interfaces.OnPaymentMethodClickListener;
import com.plugi.plugi.models.CustomerAddress;
import com.plugi.plugi.models.CustomerCard;
import com.plugi.plugi.models.ItemDetails;
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

public class SellingFragment extends BaseFragment implements IOnBackPressed , OnAddressClickListener , OnPaymentMethodClickListener {

    ShimmerFrameLayout shimmerFrameLayout , shimmerFrameLayout2 , shimmerFrameLayout3;
    private ScrollView itemScrollView;

    private TextView ivAddress , ivPaymentDetails;
    AddressAdapter addressAdapter;
    List<CustomerAddress> addressList;
    RecyclerView rec_Address;

    PaymentMethodAdapter paymentMethodAdapter;
    List<CustomerCard> paymentMethodList;
    RecyclerView rec_PaymentDetails;




    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting_selling, container, false);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        shimmerFrameLayout = view.findViewById(R.id.parentShimmerLayout);
        shimmerFrameLayout2 = view.findViewById(R.id.parentShimmerLayout2);
        shimmerFrameLayout3 = view.findViewById(R.id.parentShimmerLayout3);

        ivAddress = view.findViewById(R.id.ivAddress);
        ivPaymentDetails = view.findViewById(R.id.ivPaymentDetails);
        shimmerFrameLayout.startShimmer();

        rec_Address = view.findViewById(R.id.rec_Address);
        rec_PaymentDetails = view.findViewById(R.id.rec_PaymentDetails);
        itemScrollView = view.findViewById(R.id.itemScrollView);
        AssignAddress();
        AssignPayment();
        PojoData();
        ivAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                fragment = new AddAddressFragment();
                ((MainActivity) getActivity()).replacePopUpFragments(fragment);
            }
        });
        //LoadData();

    }

    private void PojoData() {

        if(((MainActivity) getActivity()).getCustomerAddressList() != null) {
            if (((MainActivity) getActivity()).getCustomerAddressList().size() > 0) {
                addressList.addAll(((MainActivity) getActivity()).getCustomerAddressList());
            } else {
                //String $id, Integer iD, Integer customerID, Integer countryID, String countryName,
                // String address1, String address2, Integer cityId, String cityName, String regionName, String zipCode,
                // String phoneNumber, Integer languageID, Integer currencyID
            /*
            addressList.add(new CustomerShippingAddress("1" , 1 , 3  , 1 , "Kuwait City" , "Home Address" ,
                    "Kuwait City Main Road" , 1 , "Kuwait City" , "Flat No: 301 Floor : 3, Al Reem Tower , Mirqab , Kuwait City Main Road" , "15231" , "01277637646" , 1 , 1  ));
            addressList.add(new CustomerShippingAddress("2" , 2 , 3  , 1 , "Kuwait City" , "Home Address" ,
                    "Kuwait City Main Road" , 1 , "Kuwait City" , "Flat No: 301 Floor : 3, Al Reem Tower , Mirqab , Kuwait City Main Road" , "15231" , "01277637646" , 1 , 1  ));


             */
            }

        }
        addressAdapter.notifyDataSetChanged();
        if(((MainActivity) getActivity()).getCustomerCardList() != null) {
            if (((MainActivity) getActivity()).getCustomerCardList().size() > 0) {
                paymentMethodList.addAll(((MainActivity) getActivity()).getCustomerCardList());
            } else {
            /*
            //String $id, Integer iD, Integer customerID, Integer cardType, String cardTypeName, Integer cardNo, Integer cardCVV, String expirationDate, String holdingName, Integer languageID, Integer currencyID
            paymentMethodList.add(new CustomerPaymentCard("1" , 1 , 3  , 1 , "Visa" , 1253 , 532 , "12 June 2027" , "Mohamed" , 1 , 1  ));
            paymentMethodList.add(new CustomerPaymentCard("2" , 2 , 3  , 1 , "Visa" , 1253 , 532 , "12 June 2027" , "Mohamed" , 1 , 1  ));


             */
            }
        }
        paymentMethodAdapter.notifyDataSetChanged();

        shimmerFrameLayout2.setVisibility(View.GONE);
        shimmerFrameLayout2.stopShimmer();

        shimmerFrameLayout3.setVisibility(View.GONE);
        shimmerFrameLayout3.stopShimmer();
    }

    private void AssignPayment() {
        paymentMethodList = new ArrayList<>();
        LinearLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);

        rec_PaymentDetails.setLayoutManager(mLayoutManager);
        rec_PaymentDetails.setItemAnimator(new DefaultItemAnimator());
        rec_PaymentDetails.setHasFixedSize(true);
        rec_PaymentDetails.setNestedScrollingEnabled(false);
        /* TODO USELESS WITHOUT DATABASE
        recCategory.addOnScrollListener(new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                userPage++;
                getData(userPage);

            }
        });

         */
        paymentMethodAdapter = new PaymentMethodAdapter(getActivity(), paymentMethodList, this);
        rec_PaymentDetails.setAdapter(paymentMethodAdapter);
    }

    private void AssignAddress() {
        addressList = new ArrayList<>();
        LinearLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);

        rec_Address.setLayoutManager(mLayoutManager);
        rec_Address.setItemAnimator(new DefaultItemAnimator());
        rec_Address.setHasFixedSize(true);
        rec_Address.setNestedScrollingEnabled(false);
        /* TODO USELESS WITHOUT DATABASE
        recCategory.addOnScrollListener(new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                userPage++;
                getData(userPage);

            }
        });

         */
        addressAdapter = new AddressAdapter(getActivity(), addressList, this);
        rec_Address.setAdapter(addressAdapter);

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
        shimmerFrameLayout.setVisibility(View.GONE);
        shimmerFrameLayout.stopShimmer();






    }


    @Override
    protected int layoutRes() {
        return R.layout.fragment_setting_selling;
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
    public void onAddressClicked(CustomerAddress contact, int position) {

    }

    @Override
    public void onPaymentMethodClicked(CustomerCard contact, int position) {

    }
}
