package com.plugi.plugi.feature.order.selling;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
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
import com.plugi.plugi.feature.item.adapter.ItemSizesAdapter;
import com.plugi.plugi.feature.item.interfaces.OnItemSizeClickListener;
import com.plugi.plugi.feature.main.MainActivity;
import com.plugi.plugi.feature.main.interfaces.IOnBackPressed;
import com.plugi.plugi.feature.order.adapter.SelectPaymentAdapter;
import com.plugi.plugi.feature.order.interfaces.OnPaymentMethodClickListener;
import com.plugi.plugi.feature.order.selling.review.ReviewSellFragment;
import com.plugi.plugi.models.GetDiscountDetails;
import com.plugi.plugi.models.ReviewItem;
import com.plugi.plugi.models.itemDetails.AllItemSize;
import com.plugi.plugi.models.itemDetails.ShippingCostPerCountry;
import com.plugi.plugi.models.profile.CustomerPaymentCard;
import com.plugi.plugi.models.profile.CustomerShippingAddress;
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

public class SellNowFragment extends BaseFragment implements IOnBackPressed , OnPaymentMethodClickListener, OnItemSizeClickListener  {

    int itemID;
    private ReviewItem reviewItem;
    private ReviewItem itemDetails;
    ProductShowCaseWebView wv;
    //ItemInfo
    private EditText enterBID;
    private TextView productName, item_size_type , item_size_gender, item_size , itemColor , highestBID , lowestASK  , itemCurrency, itemStatus , item_Proceesing , payment_proc , item_Shipping , item_Tax  , item_Discount , item_Total ,  TxtWarring , TxtٍSubmitWarring;
    private ImageView select_Size , getPaid;
    ShimmerFrameLayout shimmerFrameLayout;
    private ScrollView itemScrollView;
    private Button btnSubmit;

    //PopUpDialog
    public static Dialog sizeDialog , paymentDialog , discountDialog;

    private FrameLayout reviewContainer;
    private CustomerShippingAddress selectAddress;
    private CustomerPaymentCard selectPayment;
    private GetDiscountDetails.DiscountDetail selectDiscount;
    public AllItemSize selectedSize;

    private double sumTotal = 0;
    private int itemValue = 0;
    private double shippingFees = 0;
    private ShippingCostPerCountry shippingCostPerCountry;
    private ImageView itemImage;


    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sell_now, container, false);

        itemDetails = ((MainActivity) getActivity()).getSelectReviewItem();
        itemID = ((MainActivity) getActivity()).getSelectItem().getItemID();
        if(((MainActivity) getActivity()).getSelectedDiscount() != null)
        {
            ((MainActivity) getActivity()).setSelectedDiscount(null);
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        shimmerFrameLayout = view.findViewById(R.id.parentShimmerLayout);

        shimmerFrameLayout.startShimmer();


        itemScrollView = view.findViewById(R.id.itemScrollView);

        enterBID = view.findViewById(R.id.enterBID);
        itemImage = view.findViewById(R.id.itemImage);
        productName = view.findViewById(R.id.productName);
        item_size_type = view.findViewById(R.id.item_size_type);
        item_size_gender = view.findViewById(R.id.item_size_gender);
        item_size = view.findViewById(R.id.item_size);
        itemColor = view.findViewById(R.id.itemColor);
        highestBID = view.findViewById(R.id.highestBID);
        lowestASK = view.findViewById(R.id.lowestASK);
        itemCurrency = view.findViewById(R.id.itemCurrency);
        itemStatus = view.findViewById(R.id.itemStatus);
        item_Proceesing = view.findViewById(R.id.item_Proceesing);
        payment_proc = view.findViewById(R.id.payment_proc);
        item_Shipping = view.findViewById(R.id.item_Shipping);
        item_Tax = view.findViewById(R.id.item_Tax);
        item_Discount = view.findViewById(R.id.item_Discount);
        item_Total = view.findViewById(R.id.item_Total);
        TxtWarring = view.findViewById(R.id.TxtWarring);
        TxtٍSubmitWarring = view.findViewById(R.id.TxtٍSubmitWarring);
        select_Size = view.findViewById(R.id.select_Size);
        getPaid = view.findViewById(R.id.getPaid);
        btnSubmit = view.findViewById(R.id.btnSubmit);

        reviewContainer = view.findViewById(R.id.reviewContainer);

        wv = (ProductShowCaseWebView) view.findViewById(R.id.web_view);

        if(itemDetails != null) {
            setItemDetails(itemDetails);
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
        jsonParams.put("ItemID", _itemID);
        jsonParams.put("CustomerID", 1);
        jsonParams.put("CountryID", 1);
        RequestBody bodyToken = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
        //defining the call
        Call<ReviewItem> call = service.GetItem_BuySellNow(
                bodyToken
        );
        call.enqueue(new Callback<ReviewItem>() {
            @Override
            public void onResponse(Call<ReviewItem> call, Response<ReviewItem> response) {

                if(response.body() != null) {
                    if(response.body().getStatusMessage().equals("Success"))
                    {

                        setItemDetails(response.body());
                        ((MainActivity) getActivity()).setSelectReviewItem(response.body());
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
            public void onFailure(Call<ReviewItem> call, Throwable t) {
                Log.d("ItemDetails", "onFailure: " + t.getLocalizedMessage());
            }
        });
    }


    private void setItemDetails(ReviewItem itemDetails) {

        reviewItem = itemDetails;
        ((MainActivity) getActivity()).setExpirationList(itemDetails.getExpirationDaysList());
        ((MainActivity) getActivity()).setAddressList(itemDetails.getCustomerShippingAddress());
        ((MainActivity) getActivity()).setPaymentMethodList(itemDetails.getCustomerPaymentCards());
        AssignDefault(itemDetails);
        Log.d("ItemDetails", "setItemDetails: " + itemDetails.getItemID());
        //load3DImage();
        Glide.with(getActivity()).load(itemDetails.getItemDetail().getMainImage()).placeholder(R.drawable.ic_landiing_logo).into(itemImage);

        for(int i = 0; i < itemDetails.getShippingCostPerCountries().size(); i++)
        {
            if(itemDetails.getShippingCostPerCountries().get(i).getToCountryID()==(selectAddress.getCountryID()))//&& itemDetails.getShippingCostPerCountries().get(i).getID()==(itemDetails.getCountryID())
            {
                Log.d("SHPPING", "setItemDetails: " + itemDetails.getShippingCostPerCountries().get(i));
                shippingCostPerCountry = itemDetails.getShippingCostPerCountries().get(i);
                shippingFees = shippingCostPerCountry.getPrice();
                ((MainActivity) getActivity()).setShippingCost(shippingFees);
            }
        }
        productName.setText(itemDetails.getItemDetail().getName());
        item_size_type.setText(itemDetails.getUnitSizeType());
        item_size_gender.setText(itemDetails.getGenderSizeType());
        itemColor.setText(itemDetails.getColorName());
        itemCurrency.setText(itemDetails.getItemDetail().getPriceCurrency());
        highestBID.setText(itemDetails.getItemDetail().getMaxBidPrice().toString());
        lowestASK.setText(itemDetails.getItemDetail().getLowAskPrice().toString());
        if(selectedSize != null) {
            enterBID.setText(selectedSize.getPriceValue().toString());
            highestBID.setText(selectedSize.getPriceValue().toString());
        }
        else
        {
            enterBID.setText(itemDetails.getItemDetail().getMaxBidPrice().toString());
        }

        double transcation = itemDetails.getTransactionFees();
        double paymentfees = itemDetails.getPaymentFees();
        payment_proc.setText(itemDetails.getItemDetail().getPriceCurrency() + "." + String.valueOf(paymentfees));
        item_Proceesing.setText(itemDetails.getItemDetail().getPriceCurrency() + "." + itemDetails.getPlugiProcessingFees());
        item_Shipping.setText(itemDetails.getItemDetail().getPriceCurrency() + "." + shippingFees);
        item_Tax.setText(itemDetails.getItemDetail().getPriceCurrency() + "." + itemDetails.getTaxFees());
        sumTotal = itemValue + itemDetails.getPlugiProcessingFees() + shippingFees + + paymentfees +  transcation + itemDetails.getTaxFees() - itemDetails.getDiscountCost();
        item_Total.setText(itemDetails.getItemDetail().getPriceCurrency() + "." + String.format("%.2f",sumTotal));

        itemScrollView.setVisibility(View.VISIBLE);
        shimmerFrameLayout.setVisibility(View.GONE);
        shimmerFrameLayout.stopShimmer();

        if(((MainActivity) requireActivity()).getSelectPayment() != null && ((MainActivity) requireActivity()).getSelectAddress() != null)
        {

            TxtٍSubmitWarring.setVisibility(View.GONE);
        }
        else
        {
            if(((MainActivity) requireActivity()).getSelectAddress() == null) {
                TxtٍSubmitWarring.setText(getResources().getString(R.string.please_select_shipping_address_first));
            }else if(((MainActivity) requireActivity()).getSelectPayment() == null) {
                TxtٍSubmitWarring.setText(getResources().getString(R.string.please_select_shipping_payment_first));
            }
            else
            {
                TxtٍSubmitWarring.setText(getResources().getString(R.string.please_select_shipping_address_and_payment_first));
            }

            TxtٍSubmitWarring.setVisibility(View.VISIBLE);
            btnSubmit.setVisibility(View.GONE);
            btnSubmit.setEnabled(false);
        }
        select_Size.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemDetails.getAllItemSizes().size() != 0) {
                    showSizeDialog(getActivity(), itemDetails);
                }
            }
        });
        item_Discount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDiscountDialog(getActivity(), itemDetails);
            }
        });
        getPaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPaymentDialog(getActivity(), ((MainActivity) getActivity()).getPaymentMethodList());
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(enterBID.getText())) {
                    ((MainActivity) requireActivity()).setItemValue(itemValue);
                    Fragment fragment = null;
                    fragment = new ReviewSellFragment();
                    ((MainActivity) requireActivity()).replaceReviewFragments(fragment, Constants.BUNDLE_ITEM_DETAILS, itemDetails);
                }
            }
        });



        enterBID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {


                    String w = s.toString();
                    if (!TextUtils.isEmpty(w)) {
                        itemValue = Integer.parseInt(w);
                    } else {
                        itemValue = 0;
                    }
                    sumTotal = itemValue + itemDetails.getPlugiProcessingFees() + itemDetails.getPaymentFees() + itemDetails.getTransactionFees() + shippingFees + itemDetails.getTaxFees() - itemDetails.getDiscountCost();
                    if(((MainActivity) getActivity()).getSelectedDiscount() != null)
                    {
                        sumTotal = sumTotal - ((MainActivity) getActivity()).getSelectedDiscount().getDiscountCost();
                    }
                    item_Total.setText(itemDetails.getItemDetail().getPriceCurrency() + "." + String.format("%.2f", sumTotal));
                }
                catch (Exception e)
                {
                    e.getLocalizedMessage();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void AssignDefault(ReviewItem itemDetails) {
        if(((MainActivity) getActivity()).getSelectItemSize() != null) {
            selectedSize = ((MainActivity) getActivity()).getSelectItemSize();
            if(selectedSize!=null ) {
                if(selectedSize.getSizeValue() != null)
                {
                    item_size.setText(selectedSize.getSizeValue().toString());
                }
                else
                {
                    item_size.setText("All");
                }
            }
        }
        else
        {
            if(((MainActivity) getActivity()).getItemSizeList() != null  && ((MainActivity) getActivity()).getItemSizeList().size() > 0) {
                selectedSize = ((MainActivity) getActivity()).getItemSizeList().get(0);
                ((MainActivity) getActivity()).setSelectItemSize(selectedSize);
                if(selectedSize!=null ) {
                    if(selectedSize.getSizeValue() != null)
                    {
                        item_size.setText(selectedSize.getSizeValue().toString());
                    }
                    else
                    {
                        item_size.setText("All");
                    }
                }
                else
                {
                    item_size.setText("All");
                }
            }
            else
            {
                item_size.setText("All");
            }
        }
        if(((MainActivity) getActivity()).getSelectAddress() != null) {
            selectAddress = ((MainActivity) getActivity()).getSelectAddress();

            if(!itemDetails.getIsCountryRestricted())
            {
                btnSubmit.setVisibility(View.VISIBLE);
                btnSubmit.setEnabled(true);
                TxtWarring.setVisibility(View.GONE);

            }
            else
            {
                btnSubmit.setVisibility(View.GONE);
                btnSubmit.setEnabled(false);
                TxtWarring.setText(getResources().getString(R.string.restricted_shipping_to_your_country));
                TxtWarring.setVisibility(View.VISIBLE);
            }

            for (int i = 0; i < itemDetails.getRestrictedCountriesToBuyItem().size(); i++)//ADDITIONAL VALIDATION
            {
                Log.d("SHIPPING", "AssignDefault:1 ");
                if(itemDetails.getRestrictedCountriesToBuyItem().get(i).getCountryID().equals(selectAddress.getCountryID()))
                {
                    Log.d("SHIPPING", "AssignDefault:2 ");
                    if(itemDetails.getRestrictedCountriesToBuyItem().get(i).getHasBuy())
                    {
                        Log.d("SHIPPING", "AssignDefault:3 ");
                        btnSubmit.setVisibility(View.VISIBLE);
                        btnSubmit.setEnabled(true);
                        TxtWarring.setVisibility(View.GONE);
                    }
                    else
                    {
                        Log.d("SHIPPING", "AssignDefault:4 ");
                        btnSubmit.setVisibility(View.GONE);
                        btnSubmit.setEnabled(false);
                        TxtWarring.setText(getResources().getString(R.string.restricted_shipping_to_your_country));
                        TxtWarring.setVisibility(View.VISIBLE);
                    }
                    break;
                }
            }


        }
        else
        {
            Log.d("SHIPPING", "AssignDefault:5 ");
            selectAddress = ((MainActivity) getActivity()).getAddressList().get(0);
            ((MainActivity) getActivity()).setSelectAddress(selectAddress);
            for (int i = 0; i < itemDetails.getRestrictedCountriesToBuyItem().size(); i++)
            {
                if(itemDetails.getRestrictedCountriesToBuyItem().get(i).getCountryID().equals(selectAddress.getCountryID()))
                {
                    if(itemDetails.getRestrictedCountriesToBuyItem().get(i).getHasBuy())
                    {
                        btnSubmit.setVisibility(View.VISIBLE);
                        btnSubmit.setEnabled(true);
                        TxtWarring.setVisibility(View.GONE);
                    }
                    else
                    {
                        btnSubmit.setVisibility(View.GONE);
                        btnSubmit.setEnabled(false);
                        TxtWarring.setText(getResources().getString(R.string.restricted_shipping_to_your_country));
                        TxtWarring.setVisibility(View.VISIBLE);
                    }
                    break;
                }
            }
        }
        if(((MainActivity) getActivity()).getSelectPayment() != null) {
            selectPayment = ((MainActivity) getActivity()).getSelectPayment();
        }
        else
        {
            selectPayment = ((MainActivity) getActivity()).getPaymentMethodList().get(0);
            ((MainActivity) getActivity()).setSelectPayment(selectPayment);
        }

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
        return R.layout.fragment_sell_now;
    }
    public void showPaymentDialog(Activity activity , List<CustomerPaymentCard> paymentMethodList){

        paymentDialog = new Dialog(activity);
        // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        paymentDialog.setCancelable(false);
        paymentDialog.setContentView(R.layout.dialog_select_payment);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(paymentDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        int selectedIndex = -1;

        if(selectPayment != null) {
            //Log.d("FILTER", "AssignSortList: " + selectBrand.getName());
            selectedIndex = paymentMethodList.indexOf(selectPayment);
        }

        RecyclerView recyclerView = paymentDialog.findViewById(R.id.rec_AllPayment);
        SelectPaymentAdapter adapterRe = new SelectPaymentAdapter(activity , paymentMethodList , selectedIndex , this);
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

        Button btnSubmit = paymentDialog.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentDialog.dismiss();
                getPaid.setEnabled(true);
            }
        });
        paymentDialog.show();
        paymentDialog.getWindow().setAttributes(lp);
        getPaid.setEnabled(false);


    }
    public void showSizeDialog(Activity activity , ReviewItem _itemdetails){

        sizeDialog = new Dialog(activity);
        // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        sizeDialog.setCancelable(false);
        sizeDialog.setContentView(R.layout.dialog_view_sizes);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(sizeDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        ImageView ivClose = sizeDialog.findViewById(R.id.ivClose);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sizeDialog.dismiss();
                select_Size.setEnabled(true);
            }
        });

        TextView viewChart = (TextView) sizeDialog.findViewById(R.id.viewChart);
        viewChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(_itemdetails.getSizeChartURL())));
            }
        });

        int selectedIndex = -1;

        if(selectedSize != null) {
            //Log.d("FILTER", "AssignSortList: " + selectBrand.getName());
            selectedIndex = _itemdetails.getAllItemSizes().indexOf(selectedSize);
        }

        RecyclerView recyclerView = sizeDialog.findViewById(R.id.rec_Allsizes);
        ItemSizesAdapter adapterRe = new ItemSizesAdapter(activity , _itemdetails.getAllItemSizes() , selectedIndex , this);
        recyclerView.setAdapter(adapterRe);
        LinearLayoutManager mLayoutManager = new GridLayoutManager(activity, 3);
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        sizeDialog.show();
        sizeDialog.getWindow().setAttributes(lp);
        select_Size.setEnabled(false);


    }

    public void showDiscountDialog(Activity activity , ReviewItem _itemdetails){

        discountDialog = new Dialog(activity);
        // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        discountDialog.setCancelable(false);
        discountDialog.setContentView(R.layout.dialog_discount);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(discountDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        ImageView ivClose = discountDialog.findViewById(R.id.ivClose);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                discountDialog.dismiss();
                item_Discount.setEnabled(true);
            }
        });
        TextView TxtٍSubmitWarring = (TextView) discountDialog.findViewById(R.id.TxtٍSubmitWarring);
        EditText etDiscount = (EditText) discountDialog.findViewById(R.id.etDiscount);
        etDiscount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(etDiscount.getText().length() <= 0)
                {
                    TxtٍSubmitWarring.setText(getResources().getString(R.string.please_enter_discount_code));
                    TxtٍSubmitWarring.setVisibility(View.VISIBLE);
                }
                else
                {
                    TxtٍSubmitWarring.setVisibility(View.GONE);
                }
            }
        });


        Button btnSubmit = (Button)discountDialog.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(etDiscount.getText())) {
                    getDiscountDetails(etDiscount.getText().toString());
                }
            }
        });
        discountDialog.show();
        discountDialog.getWindow().setAttributes(lp);
        item_Discount.setEnabled(false);


    }

    public void getDiscountDetails(String code)
    {

        ApiInterface service = RetrofitClient.retrofitWrite().create(ApiInterface.class);

        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("discountCode", code);
        jsonParams.put("language_ID", 1);
        RequestBody bodyToken = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
        //defining the call
        Call<GetDiscountDetails> call = service.GetDiscountDetails(
                bodyToken
        );
        call.enqueue(new Callback<GetDiscountDetails>() {
            @Override
            public void onResponse(Call<GetDiscountDetails> call, Response<GetDiscountDetails> response) {

                if(response.body() != null) {
                    if(response.body().getStatusMessage().equals("Success"))
                    {

                        setSelectDiscount(response.body().getDiscountDetail());
                        ((MainActivity) getActivity()).setSelectedDiscount(response.body().getDiscountDetail());
                        discountDialog.dismiss();
                        item_Discount.setEnabled(true);
                        AssignDefault(reviewItem);
                        item_Discount.setText( reviewItem.getItemDetail().getPriceCurrency() + "." + response.body().getDiscountDetail().getDiscountCost());
                        item_Proceesing.setText(reviewItem.getItemDetail().getPriceCurrency() + "." + reviewItem.getPlugiProcessingFees());
                        item_Shipping.setText(reviewItem.getItemDetail().getPriceCurrency() + "." + shippingFees);
                        item_Tax.setText(reviewItem.getItemDetail().getPriceCurrency() + "." + reviewItem.getTaxFees());
                        sumTotal = itemValue + reviewItem.getPlugiProcessingFees() +  reviewItem.getPaymentFees()  + reviewItem.getTransactionFees() + shippingFees + reviewItem.getTaxFees() - reviewItem.getDiscountCost() - getSelectDiscount().getDiscountCost();
                        item_Total.setText(reviewItem.getItemDetail().getPriceCurrency() + "." +  String.format("%.2f",sumTotal));

                    }
                    else
                    {
                        Toast.makeText(getActivity(), "" + getResources().getString(R.string.invild_code), Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<GetDiscountDetails> call, Throwable t) {
                Log.d("ItemDetails", "onFailure: " + t.getLocalizedMessage());
            }
        });
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
    public void onPaymentMethodClicked(CustomerPaymentCard contact, int position) {
        ((MainActivity) getActivity()).setSelectPayment(contact);
        selectPayment = contact;

        AssignDefault(reviewItem);

        item_Proceesing.setText(reviewItem.getItemDetail().getPriceCurrency() + "." + reviewItem.getPlugiProcessingFees());
        item_Shipping.setText(reviewItem.getItemDetail().getPriceCurrency() + "." + shippingFees);
        item_Tax.setText(reviewItem.getItemDetail().getPriceCurrency() + "." + reviewItem.getTaxFees());
        sumTotal = itemValue + reviewItem.getPlugiProcessingFees() +  reviewItem.getPaymentFees()  + reviewItem.getTransactionFees() + shippingFees + reviewItem.getTaxFees() - reviewItem.getDiscountCost();
        item_Total.setText(reviewItem.getItemDetail().getPriceCurrency() + "." +  String.format("%.2f",sumTotal));


        if(((MainActivity) requireActivity()).getSelectPayment() != null && ((MainActivity) requireActivity()).getSelectAddress() != null)
        {

            TxtٍSubmitWarring.setVisibility(View.GONE);
        }
        else {
            if (((MainActivity) requireActivity()).getSelectAddress() == null) {
                TxtٍSubmitWarring.setText(getResources().getString(R.string.please_select_shipping_address_first));
            } else if (((MainActivity) requireActivity()).getSelectPayment() == null) {
                TxtٍSubmitWarring.setText(getResources().getString(R.string.please_select_shipping_payment_first));
            } else {
                TxtٍSubmitWarring.setText(getResources().getString(R.string.please_select_shipping_address_and_payment_first));
            }
        }
    }
    @Override
    public void onItemSizeClicked(AllItemSize contact, int position) {
        ((MainActivity) getActivity()).setSelectItemSize(contact);
        selectedSize = contact;
        if(selectedSize!=null) {
            if(selectedSize.getSizeValue() != null)
            {
                item_size.setText(selectedSize.getSizeValue().toString());
            }
            else
            {
                item_size.setText("All");
            }
            lowestASK.setText(reviewItem.getItemDetail().getLowAskPrice().toString());
            if(selectedSize != null) {
                enterBID.setText(selectedSize.getPriceValue().toString());
                highestBID.setText(selectedSize.getPriceValue().toString());
            }
            else
            {
                enterBID.setText(reviewItem.getItemDetail().getMaxBidPrice().toString());
            }
        }

    }


    public GetDiscountDetails.DiscountDetail getSelectDiscount() {
        return selectDiscount;
    }

    public void setSelectDiscount(GetDiscountDetails.DiscountDetail selectDiscount) {
        this.selectDiscount = selectDiscount;
    }
}
