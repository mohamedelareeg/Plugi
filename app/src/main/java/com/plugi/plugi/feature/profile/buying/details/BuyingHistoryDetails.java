package com.plugi.plugi.feature.profile.buying.details;

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
import com.plugi.plugi.core.views.ProductShowCaseWebView;
import com.plugi.plugi.feature.item.ItemDetailsFragment;
import com.plugi.plugi.feature.main.MainActivity;
import com.plugi.plugi.feature.main.SideMenuFragment;
import com.plugi.plugi.feature.main.interfaces.IOnBackPressed;
import com.plugi.plugi.feature.profile.buying.BuyingContainerFragment;
import com.plugi.plugi.models.itemDetails.ShippingCostPerCountry;
import com.plugi.plugi.models.orderDetails.viewOrderDetails;
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

public class BuyingHistoryDetails extends BaseFragment implements IOnBackPressed {

    ProductShowCaseWebView wv;
    //ItemInfo
    private TextView productName, itemSize  , itemColor , itemData, itemCondition ,itemOrderNo , itemStatus , viewProduct , item_Purchase_price , item_Proceesing , item_Shipping , item_Tax , item_Total , item_bidAmount  , item_PurchaseDate , item_HighestBid , item_PaidCredit , shipAddress , shipCity;
    private Button btnSubmit;
    ShimmerFrameLayout shimmerFrameLayout;
    private ScrollView itemScrollView;
    private viewOrderDetails viewOrderDetails;
    int itemID;
    ImageView ivSide , ivBack;
    private double shippingFees = 0;
    private ShippingCostPerCountry shippingCostPerCountry;
    private ImageView itemImage;

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buying_history_details, container, false);

        viewOrderDetails = ((MainActivity) getActivity()).getViewOrderDetails();
        itemID = getArguments().getInt(Constants.BUNDLE_ITEM_ID , 0);
        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(((MainActivity) requireActivity()).reviewContainer.getVisibility()!= View.VISIBLE)
                {
                    Fragment fragment = null;
                    fragment = new BuyingContainerFragment();
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



        //productName, itemSize  , itemColor , itemData, itemCondition , viewProduct , item_yourBid , item_Proceesing , item_Shipping , item_Tax ,
        // item_Total , item_bidAmount , item_HighestBid , item_LowestAsk , item_BidDate , item_ExpireDate , itemOrderNo , itemStatus , shipAddress , shipCity
        itemImage = view.findViewById(R.id.itemImage);
        productName = view.findViewById(R.id.productName);
        itemSize = view.findViewById(R.id.itemSize);
        itemColor = view.findViewById(R.id.itemColor);
        itemData = view.findViewById(R.id.itemData);;
        itemCondition = view.findViewById(R.id.itemCondition);
        itemOrderNo = view.findViewById(R.id.itemOrderNo);
        itemStatus = view.findViewById(R.id.itemStatus);
        viewProduct = view.findViewById(R.id.viewProduct);
        item_Purchase_price = view.findViewById(R.id.item_Purchase_price);
        item_Proceesing = view.findViewById(R.id.item_Proceesing);
        item_Shipping = view.findViewById(R.id.item_Shipping);
        item_Tax = view.findViewById(R.id.item_Tax);
        item_Total = view.findViewById(R.id.item_Total);
        shipAddress = view.findViewById(R.id.shipAddress);
        shipCity = view.findViewById(R.id.shipCity);
        btnSubmit = view.findViewById(R.id.btnSubmit);

        item_bidAmount = view.findViewById(R.id.item_bidAmount);
        item_PurchaseDate = view.findViewById(R.id.item_PurchaseDate);
        item_PaidCredit = view.findViewById(R.id.item_PaidCredit);
        item_HighestBid = view.findViewById(R.id.item_HighestBid);

        wv = (ProductShowCaseWebView) view.findViewById(R.id.web_view);
        if(viewOrderDetails != null) {
            setItemDetails(viewOrderDetails);
        }
        else if(itemID != 0) {
            LoadData(itemID);

        }




    }
    public void LoadData(int _itemID)
    {
        Log.d("LoadData", "LoadData: " + _itemID);
        ApiInterface service = RetrofitClient.retrofitBearerTokenWrite(SharedPrefManager.getInstance(getActivity()).getUser()).create(ApiInterface.class);

        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("Language_ID", 1);
        jsonParams.put("CustomerID", 1);
        jsonParams.put("ID", _itemID);
        RequestBody bodyToken = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
        //defining the call
        Call<viewOrderDetails> call = service.GetOrderDetail(
                bodyToken
        );
        call.enqueue(new Callback<viewOrderDetails>() {
            @Override
            public void onResponse(Call<viewOrderDetails> call, Response<viewOrderDetails> response) {

                if(response.body() != null) {
                    if(response.body().getStatusMessage().equals("Success"))
                    {
                        if(response.body().getItemDetail() != null) {
                            setItemDetails(response.body());
                            ((MainActivity) getActivity()).setViewOrderDetails(response.body());
                        }

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
            public void onFailure(Call<viewOrderDetails> call, Throwable t) {
                Log.d("ItemDetails", "onFailure: " + t.getLocalizedMessage());
            }
        });
    }

    private void setItemDetails(viewOrderDetails itemDetails) {


        Log.d("ItemDetails", "setItemDetails: " + itemDetails.getItemDetail().getItemID());
        //load3DImage();
        Glide.with(getActivity()).load(itemDetails.getItemDetail().getMainImage()).placeholder(R.drawable.ic_landiing_logo).into(itemImage);

        productName.setText(itemDetails.getItemDetail().getName());
        itemSize.setText(itemDetails.getUnitSizeType() +" "+ itemDetails.getGenderSizeType());
        itemColor.setText(itemDetails.getColorName());
        itemCondition.setText(itemDetails.getCondationName());
        itemOrderNo.setText(itemDetails.getOrderNo().toString());
        itemStatus.setText(itemDetails.getOrderStatus().toString());
        itemData.setText(getResources().getString(R.string.authentic));
        item_Purchase_price.setText(itemDetails.getPurchasePrice().toString() + "." + itemDetails.getPriceCurrency());
        item_Proceesing.setText(itemDetails.getTransactionFees().toString() + "." + itemDetails.getPriceCurrency());
        item_Shipping.setText(itemDetails.getShippingCost().toString() + "." + itemDetails.getPriceCurrency());
        item_Tax.setText(itemDetails.getTaxFees().toString() + "." + itemDetails.getPriceCurrency());
        item_Total.setText(itemDetails.getTotalCost().toString() + "." + itemDetails.getPriceCurrency());
        item_PurchaseDate.setText(itemDetails.getOrderDate());
        item_HighestBid.setText(itemDetails.getHighestBid().toString() + "." + itemDetails.getPriceCurrency());

        itemScrollView.setVisibility(View.VISIBLE);
        shimmerFrameLayout.setVisibility(View.GONE);
        shimmerFrameLayout.stopShimmer();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Fragment fragment = null;
                fragment = new ConfirmBidFragment();
                ((MainActivity) requireActivity()).replaceReviewFragments(fragment, Constants.BUNDLE_ITEM_DETAILS, itemDetails);

                 */
            }
        });
        viewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //((MainActivity) requireActivity()).reviewContainer.setVisibility(View.GONE);
                Fragment fragment = null;
                fragment = new ItemDetailsFragment();
                Bundle args = new Bundle();
                args.putInt(Constants.BUNDLE_ITEM_TYPE, 6);
                args.putInt(Constants.BUNDLE_ITEM_ID, itemDetails.getItemDetail().getItemID());
                fragment.setArguments(args);
                ((MainActivity) getActivity()).replaceReviewFragments(fragment);
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

    @Override
    protected int layoutRes() {
        return R.layout.fragment_buying_history_details;
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
