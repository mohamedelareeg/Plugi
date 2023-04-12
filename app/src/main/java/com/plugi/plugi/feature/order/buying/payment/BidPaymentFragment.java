package com.plugi.plugi.feature.order.buying.payment;

import android.os.Bundle;
import android.os.Handler;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.plugi.plugi.R;
import com.plugi.plugi.core.base.BaseFragment;
import com.plugi.plugi.core.utilities.Constants;
import com.plugi.plugi.feature.item.ItemDetailsFragment;
import com.plugi.plugi.feature.main.MainActivity;
import com.plugi.plugi.feature.main.SideMenuFragment;
import com.plugi.plugi.feature.main.interfaces.IOnBackPressed;
import com.plugi.plugi.feature.order.NoTransactionFragment;
import com.plugi.plugi.feature.order.buying.confirm.ConfirmBidFragment;
import com.plugi.plugi.models.CheckOrderPaymentStatus;
import com.plugi.plugi.models.ReviewItem;
import com.plugi.plugi.models.payment.bidPayment;
import com.plugi.plugi.retrofit.ApiInterface;
import com.plugi.plugi.retrofit.RetrofitClient;
import com.plugi.plugi.retrofit.SharedPrefManager;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BidPaymentFragment extends BaseFragment implements IOnBackPressed {

    private ReviewItem reviewItem;
    //ItemInfo

    WebView myWebView;
    ShimmerFrameLayout shimmerFrameLayout;
    private ScrollView itemScrollView;
    ImageView ivSide , ivBack;
    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_confirm_payment, container, false);

        reviewItem = ((MainActivity) getActivity()).getSelectReviewItem();

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

    @Override
    public void onViewCreated(@NonNull View view, @androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        shimmerFrameLayout = view.findViewById(R.id.parentShimmerLayout);
        shimmerFrameLayout.startShimmer();

        itemScrollView = view.findViewById(R.id.itemScrollView);
        myWebView = view.findViewById(R.id.myWebView);

        if(reviewItem != null) {
            LoadData(reviewItem);
        }




    }


    public void LoadData(ReviewItem _reviewItem)
    {
        double totalFees = reviewItem.getPlugiProcessingFees() + ((MainActivity) getActivity()).getShippingCost() + reviewItem.getTaxFees() - reviewItem.getDiscountCost();
        double itemValue = ((MainActivity) requireActivity()).getItemValue();
        double itemtotal = totalFees + itemValue;
        ApiInterface service = RetrofitClient.retrofitBearerTokenWrite(SharedPrefManager.getInstance(getActivity()).getUser()).create(ApiInterface.class);
        JSONObject order = new JSONObject();
        try {
            order.put("item_ID", _reviewItem.getItemID());
            order.put("size_ID",  3);
            order.put("customer_ID", SharedPrefManager.getInstance(getActivity()).getUser().getCustomer().getID());
            order.put("bider_ID",  1);
            order.put("bider_Cost",  ((MainActivity) getActivity()).getItemValue());
            order.put("transaction_Fees",  _reviewItem.getTransactionFees());
            order.put("payment_Cost",  _reviewItem.getPaymentFees());
            order.put("shipping_Cost",  ((MainActivity) getActivity()).getShippingCost());
            order.put("discount_ID",  0);
            order.put("discount_Cost",  0);
            order.put("total_Payment",  itemtotal);
            order.put("shipping_Address_ID",  ((MainActivity) getActivity()).getSelectAddress().getID());
            order.put("paymentCard_ID",  ((MainActivity) getActivity()).getSelectPayment().getID());
            order.put("status",  0);
            order.put("transaction_Type",  1);
            //jsonArray.put(billing);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("order", order);
        jsonParams.put("language_ID", 1);
        RequestBody bodyToken = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
        //defining the call
        Call<bidPayment> call = service.BidNow(
                bodyToken
        );
        call.enqueue(new Callback<bidPayment>() {
            @Override
            public void onResponse(Call<bidPayment> call, Response<bidPayment> response) {

                if(response.body().getStatusMessage().equals("Success"))
                {
                    ((MainActivity) requireActivity()).setSelectedOrder(response.body().getOrder());
                    setItemDetails(_reviewItem , response.body());

                }
            }

            @Override
            public void onFailure(Call<bidPayment> call, Throwable t) {
                Log.d("ItemDetails", "onFailure: " + t.getLocalizedMessage());
            }
        });
    }
    private void setItemDetails(ReviewItem itemDetails , bidPayment payment) {


        Log.d("ItemDetails", "setItemDetails: " + itemDetails.getItemID());


        myWebView.setWebViewClient(new MyWebViewClient());
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.loadUrl(payment.getPaymentURL());





    }


    @Override
    protected int layoutRes() {
        return R.layout.fragment_confirm_payment;
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
    private static int TIME_OUT = 3000; //Time to launch the another activity
    public class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.contains("https://plugi.mawaqaademos.com/")) {

                Toast.makeText(getActivity(), "Thank You", Toast.LENGTH_SHORT).show();
                // perform your action here
                return true;
            } else {
                view.loadUrl(url);
                return true;
            }
        }

        @Override
        public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
            if(shimmerFrameLayout.getVisibility() == View.VISIBLE)
            {
                itemScrollView.setVisibility(View.VISIBLE);
                shimmerFrameLayout.setVisibility(View.GONE);
                shimmerFrameLayout.stopShimmer();
            }
            Log.d("web", "doUpdateVisitedHistory: " + url);
            if (url.contains("paymentrouter.htm")) {
                new Handler().postDelayed(new Runnable() {

                    /*
                     * Showing splash screen with a timer. This will be useful when you
                     * want to show case your app logo / company
                     */

                    @Override
                    public void run() {
                        // This method will be executed once the timer is over
                        // Start your app main activity
                      CheckPayment();
                    }
                }, TIME_OUT);
            }
                // perform your action here
            super.doUpdateVisitedHistory(view, url, isReload);
        }
    }
    public void CheckPayment()
    {
        ApiInterface service = RetrofitClient.retrofitBearerTokenWrite(SharedPrefManager.getInstance(getActivity()).getUser()).create(ApiInterface.class);

        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("Language_ID", 1);
        jsonParams.put("CustomerID", SharedPrefManager.getInstance(getActivity()).getUser().getCustomer().getID());
        jsonParams.put("ItemID", ((MainActivity) requireActivity()).getSelectedOrder().getItemID());
        jsonParams.put("OrderID", ((MainActivity) requireActivity()).getSelectedOrder().getID());
        RequestBody bodyToken = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
        //defining the call
        Call<CheckOrderPaymentStatus> call = service.CheckOrderPaymentStatus(
                bodyToken
        );
        call.enqueue(new Callback<CheckOrderPaymentStatus>() {
            @Override
            public void onResponse(Call<CheckOrderPaymentStatus> call, Response<CheckOrderPaymentStatus> response) {

                if(response.body().getStatusMessage().equals("Success"))
                {
                    ((MainActivity) requireActivity()).setLastOrderStatus(response.body());
                    if(response.body().getIsPaidStatus()) {
                        Fragment fragment = null;
                        fragment = new ConfirmBidFragment();
                        ((MainActivity) requireActivity()).replaceReviewFragments(fragment, Constants.BUNDLE_ITEM_DETAILS, reviewItem);
                    }
                    else
                    {
                        Fragment fragment = null;
                        fragment = new ConfirmBidFragment();
                        ((MainActivity) requireActivity()).replaceReviewFragments(fragment, Constants.BUNDLE_ITEM_DETAILS, reviewItem);
                    }
                }
                else
                {
                    Fragment fragment = null;
                    fragment = new NoTransactionFragment();
                    ((MainActivity) requireActivity()).replaceReviewFragments(fragment, Constants.BUNDLE_ITEM_DETAILS, reviewItem);
                }
            }

            @Override
            public void onFailure(Call<CheckOrderPaymentStatus> call, Throwable t) {
                Log.d("ItemDetails", "onFailure: " + t.getLocalizedMessage());
            }
        });
    }
}
