package com.plugi.plugi.feature.order.selling.confirm;

import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.plugi.plugi.R;
import com.plugi.plugi.core.base.BaseFragment;
import com.plugi.plugi.core.utilities.Constants;
import com.plugi.plugi.feature.item.ItemDetailsFragment;
import com.plugi.plugi.feature.main.MainActivity;
import com.plugi.plugi.feature.main.SideMenuFragment;
import com.plugi.plugi.feature.main.interfaces.IOnBackPressed;
import com.plugi.plugi.models.CheckOrderPaymentStatus;
import com.plugi.plugi.models.ReviewItem;
import com.plugi.plugi.models.payment.itemAskPayment;
import com.plugi.plugi.retrofit.ApiInterface;
import com.plugi.plugi.retrofit.RetrofitClient;
import com.plugi.plugi.retrofit.SharedPrefManager;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmAskFragment extends BaseFragment implements IOnBackPressed {

    private ReviewItem reviewItem;
    //ItemInfo
    private ImageView item_image , ivShare;
    private TextView item_name, item_desc , item_Total;
    private Button btnSubmit;
    ShimmerFrameLayout shimmerFrameLayout;
    private ScrollView itemScrollView;
    ImageView ivSide , ivBack;
    CheckOrderPaymentStatus checkOrderPaymentStatus;
    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_confirm_ask, container, false);

        reviewItem = ((MainActivity) getActivity()).getSelectReviewItem();
        checkOrderPaymentStatus =  ((MainActivity) requireActivity()).getLastOrderStatus();
        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = null;
                fragment = new ItemDetailsFragment();
                Bundle args = new Bundle();
                args.putInt(Constants.BUNDLE_ITEM_TYPE, 1);
                fragment.setArguments(args);
                ((MainActivity) getActivity()).replacePopUpFragments(fragment);
                ((MainActivity) requireActivity()).reviewContainer.setVisibility(View.GONE);
            }
        });
        ivSide = view.findViewById(R.id.ivSide);
        ivSide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) requireActivity()).sidemenu.setVisibility(View.VISIBLE);
                Fragment fragment = null;
                fragment = new SideMenuFragment();
                ((MainActivity) getActivity()).replaceFragments(fragment);
            }
        });



        return view;
    }
    public void LoadData(ReviewItem reviewItem)
    {
        double totalFees = reviewItem.getPlugiProcessingFees() + ((MainActivity) getActivity()).getShippingCost() + reviewItem.getTaxFees() - reviewItem.getDiscountCost();
        double itemValue = ((MainActivity) requireActivity()).getItemValue();
        double itemtotal = totalFees + itemValue;
        ApiInterface service = RetrofitClient.retrofitBearerTokenWrite(SharedPrefManager.getInstance(getActivity()).getUser()).create(ApiInterface.class);

        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("language_ID", 1);
        jsonParams.put("id", 0);
        jsonParams.put("item_ID", reviewItem.getItemID());
        jsonParams.put("size_ID", 1);
        jsonParams.put("units", 1);
        jsonParams.put("customer_Id", SharedPrefManager.getInstance(getActivity()).getUser().getCustomer().getID());
        jsonParams.put("release_Date", "2020-08-08");
        jsonParams.put("status", 1);
        jsonParams.put("expiry_Date", "2020-08-08");
        jsonParams.put("notes", "test");
        jsonParams.put("discount_ID", 1);
        jsonParams.put("discount_Cost", 20);
        jsonParams.put("salle_Cost", ((MainActivity) getActivity()).getItemValue());
        jsonParams.put("shipping_Cost", ((MainActivity) getActivity()).getShippingCost());
        jsonParams.put("plugi_Cost",  reviewItem.getTransactionFees());
        jsonParams.put("payment_Cost", 40);
        jsonParams.put("total_Cost", itemtotal);
        jsonParams.put("customerAddress_ID",((MainActivity) getActivity()).getSelectAddress().getID());
        jsonParams.put("customerCard_ID", ((MainActivity) getActivity()).getSelectPayment().getID());
        RequestBody bodyToken = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
        //defining the call
        Call<itemAskPayment> call = service.AddItemAsk(
                bodyToken
        );
        call.enqueue(new Callback<itemAskPayment>() {
            @Override
            public void onResponse(Call<itemAskPayment> call, Response<itemAskPayment> response) {

                if(response.body().getStatusMessage().equals("Success"))
                {

                    setItemDetails(reviewItem , response.body());
                }
            }

            @Override
            public void onFailure(Call<itemAskPayment> call, Throwable t) {
                Log.d("ItemDetails", "onFailure: " + t.getLocalizedMessage());
            }
        });
    }
    @Override
    public void onViewCreated(@NonNull View view, @androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        shimmerFrameLayout = view.findViewById(R.id.parentShimmerLayout);
        shimmerFrameLayout.startShimmer();

        itemScrollView = view.findViewById(R.id.itemScrollView);

        item_image = view.findViewById(R.id.item_image);
        item_name = view.findViewById(R.id.item_name);
        item_desc = view.findViewById(R.id.item_desc);
        item_Total = view.findViewById(R.id.item_Total);
        ivShare = view.findViewById(R.id.ivShare);

        btnSubmit = view.findViewById(R.id.btnSubmit);



        if(reviewItem != null) {
            LoadData(reviewItem);
        }





    }



    private void setItemDetails(ReviewItem itemDetails ,itemAskPayment itemAskPayment) {


        Log.d("ItemDetails", "setItemDetails: " + itemDetails.getItemID());

        item_name.setText(itemDetails.getItemDetail().getName());
        item_desc.setText(itemDetails.getItemDetail().getDescription());
        // setting up image using GLIDE
        Glide.with(getActivity()).load(itemDetails.getItemDetail().getMainImage()).placeholder(R.drawable.ic_landiing_logo).into(item_image);
        double tax = itemDetails.getTaxFees();
        double transcation = itemDetails.getTransactionFees();
        double totalFees = reviewItem.getPlugiProcessingFees() + itemDetails.getPaymentFees() + transcation + ((MainActivity) getActivity()).getShippingCost() + reviewItem.getTaxFees() - reviewItem.getDiscountCost();
        double itemValue = ((MainActivity) requireActivity()).getItemValue();

        double itemtotal = totalFees + itemValue;

        if(((MainActivity) getActivity()).getSelectedDiscount() != null)
        {
            itemtotal = itemtotal - ((MainActivity) getActivity()).getSelectedDiscount().getDiscountCost();
            ((MainActivity) getActivity()).setSelectedDiscount(null);
        }
        //String.valueOf(String.format("%.2f",itemtotal))
        item_Total.setText(String.valueOf(String.format("%.2f",checkOrderPaymentStatus.getTransationValue())));
        itemScrollView.setVisibility(View.VISIBLE);
        shimmerFrameLayout.setVisibility(View.GONE);
        shimmerFrameLayout.stopShimmer();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = null;
                fragment = new ItemDetailsFragment();
                Bundle args = new Bundle();
                args.putInt(Constants.BUNDLE_ITEM_TYPE, 1);
                fragment.setArguments(args);
                ((MainActivity) getActivity()).replacePopUpFragments(fragment);
                ((MainActivity) requireActivity()).reviewContainer.setVisibility(View.GONE);
            }
        });


    }


    @Override
    protected int layoutRes() {
        return R.layout.fragment_confirm_ask;
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

}
