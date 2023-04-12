package com.plugi.plugi.feature.profile;

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
import android.widget.Toast;

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
import com.plugi.plugi.feature.main.interfaces.IOnBackPressed;
import com.plugi.plugi.feature.profile.selling.SellingContainerFragment;
import com.plugi.plugi.models.User;
import com.plugi.plugi.models.getMyFavouriteItems;
import com.plugi.plugi.models.itemDetails.ShippingCostPerCountry;
import com.plugi.plugi.models.orderDetails.viewOrderDetails;
import com.plugi.plugi.retrofit.ApiInterface;
import com.plugi.plugi.retrofit.RetrofitClient;
import com.plugi.plugi.retrofit.SharedPrefManager;
import com.plugi.plugi.retrofit.response.RemoveFromFav;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowingDetailsFragment extends BaseFragment implements IOnBackPressed {

    ProductShowCaseWebView wv;
    //ItemInfo
    private TextView productName, itemSize  , itemColor , itemData  , viewProduct ,  shipAddress , shipCity , paymentMethod;
    private Button btnBid , btnBuy;
    ShimmerFrameLayout shimmerFrameLayout;
    private ScrollView itemScrollView;
    private getMyFavouriteItems.Item itemFollowing;
    int itemID;
    ImageView ivDelete , ivBack;
    private double shippingFees = 0;
    private ShippingCostPerCountry shippingCostPerCountry;
    private ImageView itemImage;
    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_following_details, container, false);

        //closetDetailsPojo = ((MainActivity) getActivity()).getViewOrderDetails();
        itemID = getArguments().getInt(Constants.BUNDLE_ITEM_ID , 0);
        itemFollowing =  ((MainActivity) getActivity()).getSelectedFavItem();
        ivDelete = view.findViewById(R.id.ivDelete);
        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(((MainActivity) requireActivity()).reviewContainer.getVisibility()!= View.VISIBLE)
                {
                    Fragment fragment = null;
                    fragment = new SellingContainerFragment();
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




        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        shimmerFrameLayout = view.findViewById(R.id.parentShimmerLayout);
        shimmerFrameLayout.startShimmer();

        itemScrollView = view.findViewById(R.id.itemScrollView);


// shipAddress , shipCity , paymentMethod
        paymentMethod = view.findViewById(R.id.paymentMethod);
        shipAddress = view.findViewById(R.id.shipAddress);
        shipCity = view.findViewById(R.id.shipCity);
        itemImage = view.findViewById(R.id.itemImage);
        productName = view.findViewById(R.id.productName);
        itemSize = view.findViewById(R.id.itemSize);
        itemColor = view.findViewById(R.id.itemColor);
        itemData = view.findViewById(R.id.itemData);;
        viewProduct = view.findViewById(R.id.viewProduct);
        btnBid = view.findViewById(R.id.btnBid);
        btnBuy = view.findViewById(R.id.btnBuy);

        wv = (ProductShowCaseWebView) view.findViewById(R.id.web_view);
        pojoData();
        if(itemFollowing != null) {//TODO
            setItemDetails(itemFollowing);
        }
        else if(itemID != 0) {
            LoadData(itemID);

        }




    }

    private void pojoData() {
       // followingDetailsPojo = new FollowingDetailsPojo(1,"Nike White Sneakers"  , "18","Blue" , "240.000" ,"KD","http://plugi.mawaqaademos.com/assets/images/Uploads/2020820121846.jpg" , "Flat No :301 , FLoor : 3 , Al Reem Towers , Mirqab," , "Kuwait City Main Road , Kuwait City" , "Visa");
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

                        //setItemDetails(response.body());
                        //((MainActivity) getActivity()).setViewOrderDetails(response.body());

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

    private void setItemDetails(getMyFavouriteItems.Item itemDetails) {

        //((MainActivity) getActivity()).setAddressList(itemDetails.getCustomerShippingAddress());TODO
        //((MainActivity) getActivity()).setPaymentMethodList(itemDetails.getCustomerPaymentCards());TODO
        //load3DImage();
        Glide.with(getActivity()).load(itemDetails.getImage()).placeholder(R.drawable.ic_landiing_logo).into(itemImage);

        productName.setText(itemDetails.getName());
        itemSize.setText(itemDetails.getDescription2());
        itemColor.setText(itemDetails.getDescription1());
        itemData.setText(getResources().getString(R.string.authentic));

        /* TODO
        shipAddress.setText(itemDetails.getAddress());
        shipCity.setText(itemDetails.getCity());
        paymentMethod.setText(itemDetails.getPayment());

         */

        //AssignDefault(itemDetails);
        itemScrollView.setVisibility(View.VISIBLE);
        shimmerFrameLayout.setVisibility(View.GONE);
        shimmerFrameLayout.stopShimmer();



        btnBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "API/AddItemBid", Toast.LENGTH_SHORT).show();
                /*
                Fragment fragment = null;
                fragment = new ConfirmBidFragment();
                ((MainActivity) requireActivity()).replaceReviewFragments(fragment, Constants.BUNDLE_ITEM_DETAILS, itemDetails);

                 */
            }
        });
        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "API/BidNow", Toast.LENGTH_SHORT).show();

            }
        });
        viewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //((MainActivity) requireActivity()).reviewContainer.setVisibility(View.GONE);
                Fragment fragment = null;
                fragment = new ItemDetailsFragment();
                Bundle args = new Bundle();
                args.putInt(Constants.BUNDLE_ITEM_TYPE, 7);
                args.putInt(Constants.BUNDLE_ITEM_ID, itemDetails.getItemId());
                fragment.setArguments(args);
                ((MainActivity) getActivity()).replaceReviewFragments(fragment);
            }
        });

        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RemoveFromFav(itemDetails.getItemId());
            }
        });


    }

    public void RemoveFromFav(int _itemID)
    {
        Log.d("LoadData", "LoadData: " + _itemID);
        User currentUser = SharedPrefManager.getInstance(getActivity()).getUser();
        ApiInterface service = RetrofitClient.retrofitBearerTokenWrite(currentUser).create(ApiInterface.class);

        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("item_ID", _itemID);
        jsonParams.put("customer_ID", currentUser.getCustomer().getID());
        jsonParams.put("language_ID", 1);


        RequestBody bodyToken = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
        //defining the call
        Call<RemoveFromFav> call = service.RemoveMyFavorite(
                bodyToken
        );
        call.enqueue(new Callback<RemoveFromFav>() {
            @Override
            public void onResponse(Call<RemoveFromFav> call, Response<RemoveFromFav> response) {

                if(response.body() != null) {
                    if(response.body().getStatusMessage().equals("Success"))
                    {

                        Log.d("LoadData", "onResponse: " + response.body().getStatusMessage());
                        Toast.makeText(getActivity(), "" + getResources().getString(R.string.removed_successfully), Toast.LENGTH_SHORT).show();

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
            public void onFailure(Call<RemoveFromFav> call, Throwable t) {
                Log.d("ItemDetails", "onFailure: " + t.getLocalizedMessage());
            }
        });
    }
    @Override
    protected int layoutRes() {
        return R.layout.fragment_following_details;
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
