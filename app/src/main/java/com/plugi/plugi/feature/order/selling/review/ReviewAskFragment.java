package com.plugi.plugi.feature.order.selling.review;

import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.plugi.plugi.core.views.ProductShowCaseWebView;
import com.plugi.plugi.feature.item.ItemDetailsFragment;
import com.plugi.plugi.feature.main.MainActivity;
import com.plugi.plugi.feature.main.SideMenuFragment;
import com.plugi.plugi.feature.main.interfaces.IOnBackPressed;
import com.plugi.plugi.feature.order.NoTransactionFragment;
import com.plugi.plugi.feature.order.selling.confirm.ConfirmAskFragment;
import com.plugi.plugi.models.CheckOrderPaymentStatus;
import com.plugi.plugi.models.ReviewItem;
import com.plugi.plugi.models.itemDetails.ShippingCostPerCountry;
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

public class ReviewAskFragment extends BaseFragment implements IOnBackPressed {

    private ReviewItem itemDetails;
    ProductShowCaseWebView wv;
    //ItemInfo
    private TextView productName, itemSize , itemColor , item_placed_asked , item_Tax , item_transaction_fee, item_all_fees , item_Total , shipAddress , shipCity , paymentMethod  , getPaid;
    private CheckBox cbTerms , cbShip;
    private Button btnSubmit;
    ShimmerFrameLayout shimmerFrameLayout;
    private ScrollView itemScrollView;
    ImageView ivSide , ivBack;

    private double shippingFees = 0;
    private ShippingCostPerCountry shippingCostPerCountry;
    private ImageView itemImage;
    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review_ask, container, false);


        itemDetails = ((MainActivity) getActivity()).getSelectReviewItem();

        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(((MainActivity) requireActivity()).reviewContainer.getVisibility()!= View.VISIBLE)
                {
                    Fragment fragment = null;
                    fragment = new ItemDetailsFragment();
                    Bundle args = new Bundle();
                    args.putInt(Constants.BUNDLE_ITEM_TYPE, 1);
                    fragment.setArguments(args);
                    ((MainActivity) getActivity()).replacePopUpFragments(fragment);
                }
                else {
                    ((MainActivity) requireActivity()).popupContainer.setVisibility(View.VISIBLE);
                    ((MainActivity) requireActivity()).reviewContainer.setVisibility(View.GONE);
                }
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



        itemImage = view.findViewById(R.id.itemImage);
        productName = view.findViewById(R.id.productName);
        itemSize = view.findViewById(R.id.itemSize);
        itemColor = view.findViewById(R.id.itemColor);
        item_placed_asked = view.findViewById(R.id.item_placed_asked);
        item_Tax = view.findViewById(R.id.item_Tax);
        item_transaction_fee = view.findViewById(R.id.item_transaction_fee);
        item_all_fees = view.findViewById(R.id.item_all_fees);
        item_Total = view.findViewById(R.id.item_Total);
        shipAddress = view.findViewById(R.id.shipAddress);
        shipCity = view.findViewById(R.id.shipCity);
        paymentMethod = view.findViewById(R.id.paymentMethod);
        getPaid = view.findViewById(R.id.getPaid);
        cbTerms = view.findViewById(R.id.cbTerms);
        cbShip = view.findViewById(R.id.cbShip);
        item_Total = view.findViewById(R.id.item_Total);
        btnSubmit = view.findViewById(R.id.btnSubmit);

        wv = (ProductShowCaseWebView) view.findViewById(R.id.web_view);

        if(itemDetails != null) {
            setItemDetails(itemDetails);
        }




    }




    private void setItemDetails(ReviewItem itemDetails) {


        Log.d("ItemDetails", "setItemDetails: " + itemDetails.getItemID());
        //load3DImage();
        Glide.with(getActivity()).load(itemDetails.getItemDetail().getMainImage()).placeholder(R.drawable.ic_landiing_logo).into(itemImage);

        productName.setText(itemDetails.getItemDetail().getName());
        if(((MainActivity) getActivity()).getSelectItemSize() != null) {

            itemSize.setText((((MainActivity) getActivity()).getSelectItemSize().getSizeValue()));
        }
        else
        {
            if(((MainActivity) getActivity()).getItemSizeList() != null  && ((MainActivity) getActivity()).getItemSizeList().size() > 0) {
                itemSize.setText(((MainActivity) getActivity()).getSelectItemSize().getSizeValue().toString());
            }
        }
        itemColor.setText(itemDetails.getColorName());





        itemScrollView.setVisibility(View.VISIBLE);
        shimmerFrameLayout.setVisibility(View.GONE);
        shimmerFrameLayout.stopShimmer();
        for(int i = 0; i < itemDetails.getShippingCostPerCountries().size(); i++)
        {
            if(itemDetails.getShippingCostPerCountries().get(i).getToCountryID()==(  ((MainActivity) requireActivity()).getSelectAddress().getCountryID()))//&& itemDetails.getShippingCostPerCountries().get(i).getID()==(itemDetails.getCountryID())
            {
                Log.d("SHPPING", "setItemDetails: " + itemDetails.getShippingCostPerCountries().get(i));
                shippingCostPerCountry = itemDetails.getShippingCostPerCountries().get(i);
                shippingFees = shippingCostPerCountry.getPrice();
            }
        }
        double totalFees = itemDetails.getPlugiProcessingFees() + itemDetails.getPaymentFees() + shippingFees + itemDetails.getTaxFees() - itemDetails.getDiscountCost();
        item_all_fees.setText(itemDetails.getItemDetail().getPriceCurrency()+"."+ String.valueOf(totalFees));
        double itemValue = ((MainActivity) requireActivity()).getItemValue();
        item_placed_asked.setText(String.valueOf(itemValue));
        double tax = itemDetails.getTaxFees();
        item_Tax.setText(String.valueOf(tax));
        double transcation = itemDetails.getTransactionFees();
        item_transaction_fee.setText(String.valueOf(transcation));
        double itemtotal = totalFees + transcation + itemValue;
        if(((MainActivity) getActivity()).getSelectedDiscount() != null)
        {
            itemtotal = itemtotal - ((MainActivity) getActivity()).getSelectedDiscount().getDiscountCost();

        }
        item_Total.setText(String.valueOf(String.format("%.2f",itemtotal)));
        shipAddress.setText(((MainActivity) requireActivity()).getSelectAddress().getRegionName());
        shipCity.setText(((MainActivity) requireActivity()).getSelectAddress().getCityName());
        paymentMethod.setText(((MainActivity) requireActivity()).getSelectPayment().getCardTypeName());
        getPaid.setText(((MainActivity) requireActivity()).getSelectPayment().getCardNo().toString());
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbTerms.isChecked() && cbShip.isChecked()) {
                   CheckPayment();
                }
            }
        });


    }
    private void load3DImage() {

        wv.setVerticalScrollBarEnabled(false);
        wv.setHorizontalScrollBarEnabled(false);

        String imagesTag360="";
        /*Taking images from the assert folder*/
        //imagesTag360=imagesTag360+"<img src=\"http://plugi.mawaqaademos.com/assets/images/Uploads/202082718813.jpg\"/>";
        for(int i=1;i<19;i++)
        {
            imagesTag360=imagesTag360+"<img src=\"file:///android_asset/images/image1_"+i+".jpg\"/>" ;
        }

        /* For Showing Images from image url just use the image url in the src field*/

//        for(int i=0;i<imageLength;i++)
//        {
//            imagesTag360=imagesTag360+"<img src=\"http://imageurl.com/image1_.jpg\"/>" ;
//        }


        Log.d("",imagesTag360);


        wv.loadDataWithBaseURL("",
                imagesTag360, "text/html", "UTF-8", null);
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
                        fragment = new ConfirmAskFragment();
                        ((MainActivity) requireActivity()).replaceReviewFragments(fragment, Constants.BUNDLE_ITEM_DETAILS, itemDetails);
                    }
                    else
                    {
                        Fragment fragment = null;
                        fragment = new ConfirmAskFragment();
                        ((MainActivity) requireActivity()).replaceReviewFragments(fragment, Constants.BUNDLE_ITEM_DETAILS, itemDetails);
                    }
                }
                else
                {
                    Fragment fragment = null;
                    fragment = new NoTransactionFragment();
                    ((MainActivity) requireActivity()).replaceReviewFragments(fragment, Constants.BUNDLE_ITEM_DETAILS, itemDetails);
                }
            }

            @Override
            public void onFailure(Call<CheckOrderPaymentStatus> call, Throwable t) {
                Log.d("ItemDetails", "onFailure: " + t.getLocalizedMessage());
            }
        });
    }
    @Override
    protected int layoutRes() {
        return R.layout.fragment_review_ask;
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
