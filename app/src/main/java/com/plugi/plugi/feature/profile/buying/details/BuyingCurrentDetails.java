package com.plugi.plugi.feature.profile.buying.details;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.plugi.plugi.R;
import com.plugi.plugi.core.base.BaseFragment;
import com.plugi.plugi.core.utilities.Constants;
import com.plugi.plugi.core.views.ProductShowCaseWebView;
import com.plugi.plugi.feature.item.ItemDetailsFragment;
import com.plugi.plugi.feature.main.MainActivity;
import com.plugi.plugi.feature.main.interfaces.IOnBackPressed;
import com.plugi.plugi.feature.order.adapter.SelectExpirationAdapter;
import com.plugi.plugi.feature.order.interfaces.OnExpirationClickListener;
import com.plugi.plugi.feature.profile.buying.BuyingContainerFragment;
import com.plugi.plugi.models.UpdateBidExpire;
import com.plugi.plugi.models.User;
import com.plugi.plugi.models.ViewBidDetails;
import com.plugi.plugi.models.itemDetails.ExpirationDaysList;
import com.plugi.plugi.models.itemDetails.ShippingCostPerCountry;
import com.plugi.plugi.retrofit.ApiInterface;
import com.plugi.plugi.retrofit.RetrofitClient;
import com.plugi.plugi.retrofit.SharedPrefManager;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuyingCurrentDetails extends BaseFragment implements IOnBackPressed , OnExpirationClickListener {

    ProductShowCaseWebView wv;
    //ItemInfo
    private TextView productName, itemSize  , itemColor , itemData, itemCondition , viewProduct , item_yourBid , item_Proceesing , item_Shipping , item_Tax , item_Total , item_bidAmount , item_HighestBid , item_LowestAsk , item_BidDate , item_ExpireDate;
    private Button btnSubmit;
    ShimmerFrameLayout shimmerFrameLayout;
    private ScrollView itemScrollView;
    private ViewBidDetails viewBidDetails;
    int itemID;
    ImageView ivDelete , ivBack;
    private double shippingFees = 0;
    private ShippingCostPerCountry shippingCostPerCountry;
    private ImageView itemImage;
    public static Dialog expirationDialog;
    private ExpirationDaysList selectExpiration;

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buying_current_details, container, false);

        viewBidDetails = ((MainActivity) getActivity()).getViewBidDetails();
        itemID = getArguments().getInt(Constants.BUNDLE_ITEM_ID , 0);
        ivDelete = view.findViewById(R.id.ivDelete);
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




        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        shimmerFrameLayout = view.findViewById(R.id.parentShimmerLayout);
        shimmerFrameLayout.startShimmer();

        itemScrollView = view.findViewById(R.id.itemScrollView);



        //productName, itemSize  , itemColor , itemData, itemCondition , viewProduct , item_yourBid , item_Proceesing , item_Shipping , item_Tax ,
        // item_Total , item_bidAmount , item_HighestBid , item_LowestAsk , item_BidDate , item_ExpireDate
        itemImage = view.findViewById(R.id.itemImage);
        productName = view.findViewById(R.id.productName);
        itemSize = view.findViewById(R.id.itemSize);
        itemColor = view.findViewById(R.id.itemColor);
        itemData = view.findViewById(R.id.itemData);;
        itemCondition = view.findViewById(R.id.itemCondition);
        viewProduct = view.findViewById(R.id.viewProduct);
        item_yourBid = view.findViewById(R.id.item_yourBid);
        item_Proceesing = view.findViewById(R.id.item_Proceesing);
        item_Shipping = view.findViewById(R.id.item_Shipping);
        item_Tax = view.findViewById(R.id.item_Tax);
        item_Total = view.findViewById(R.id.item_Total);
        btnSubmit = view.findViewById(R.id.btnSubmit);

        item_bidAmount = view.findViewById(R.id.item_bidAmount);
        item_HighestBid = view.findViewById(R.id.item_HighestBid);
        item_LowestAsk = view.findViewById(R.id.item_LowestAsk);
        item_BidDate = view.findViewById(R.id.item_BidDate);
        item_ExpireDate = view.findViewById(R.id.item_ExpireDate);

        wv = (ProductShowCaseWebView) view.findViewById(R.id.web_view);
        if(viewBidDetails != null) {
            setItemDetails(viewBidDetails);
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
        Call<ViewBidDetails> call = service.GetBidDetail(
                bodyToken
        );
        call.enqueue(new Callback<ViewBidDetails>() {
            @Override
            public void onResponse(Call<ViewBidDetails> call, Response<ViewBidDetails> response) {

                if(response.body() != null) {
                    if(response.body().getStatusMessage().equals("Success"))
                    {
                        if(response.body().getItemDetail() != null) {
                            setItemDetails(response.body());
                            ((MainActivity) getActivity()).setViewBidDetails(response.body());
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
            public void onFailure(Call<ViewBidDetails> call, Throwable t) {
                Log.d("ItemDetails", "onFailure: " + t.getLocalizedMessage());
            }
        });
    }

    private void setItemDetails(ViewBidDetails itemDetails) {


        Log.d("ItemDetails", "setItemDetails: " + itemDetails.getItemDetail().getItemID());
        //load3DImage();
        Glide.with(getActivity()).load(itemDetails.getItemDetail().getMainImage()).placeholder(R.drawable.ic_landiing_logo).into(itemImage);

        productName.setText(itemDetails.getItemDetail().getName());
        itemSize.setText(itemDetails.getUnitSizeType() +" "+ itemDetails.getGenderSizeType());
        itemColor.setText(itemDetails.getColorName());
        itemCondition.setText(itemDetails.getCondationName());
        itemData.setText(getResources().getString(R.string.authentic));
        item_yourBid.setText(itemDetails.getPrice().toString() + "." + itemDetails.getPriceCurrency());
        item_Proceesing.setText(itemDetails.getProcessingFees().toString() + "." + itemDetails.getPriceCurrency());
        item_Shipping.setText(itemDetails.getShippingCost().toString() + "." + itemDetails.getPriceCurrency());
        item_Tax.setText(itemDetails.getTaxFees().toString() + "." + itemDetails.getPriceCurrency());
        item_Total.setText(itemDetails.getTotalCost().toString() + "." + itemDetails.getPriceCurrency());

        item_HighestBid.setText(itemDetails.getHighestBid().toString() + "." + itemDetails.getPriceCurrency());
        item_LowestAsk.setText(itemDetails.getLowestASK().toString() + "." + itemDetails.getPriceCurrency());
        item_BidDate.setText(itemDetails.getExpireDate());


        itemScrollView.setVisibility(View.VISIBLE);
        shimmerFrameLayout.setVisibility(View.GONE);
        shimmerFrameLayout.stopShimmer();

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
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showExpirationDialog(getActivity(),((MainActivity) getActivity()).getCustomerBids().getExpirationDaysList());
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
                args.putInt(Constants.BUNDLE_ITEM_TYPE, 4);
                args.putInt(Constants.BUNDLE_ITEM_ID, itemDetails.getItemDetail().getItemID());
                fragment.setArguments(args);
                ((MainActivity) getActivity()).replaceReviewFragments(fragment);
            }
        });


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
                btnSubmit.setEnabled(true);
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
        btnSubmit.setEnabled(false);


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
        return R.layout.fragment_buying_current_details;
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
    public void onExpirationClicked(ExpirationDaysList contact, int position) {
        //expiration_value.setText(contact.getPropValue() + getActivity().getResources().getString(R.string.day));
        ((MainActivity) getActivity()).setSelectExpiration(contact);
        selectExpiration = contact;
        Log.d("LoadData", "onExpirationClicked: " + contact.getPropValue() );
        setExpireDate(itemID);
        expirationDialog.dismiss();
        btnSubmit.setEnabled(true);


    }

    public void setExpireDate(int _itemID)
    {
        Log.d("LoadData", "LoadData: " + _itemID);
        User  currentUser =SharedPrefManager.getInstance(getActivity()).getUser();
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
}
