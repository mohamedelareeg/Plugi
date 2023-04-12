package com.plugi.plugi.feature.profile;

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
import com.plugi.plugi.feature.order.adapter.SelectAddressAdapter;
import com.plugi.plugi.feature.order.adapter.SelectPaymentAdapter;
import com.plugi.plugi.feature.order.interfaces.OnAddressClickListener;
import com.plugi.plugi.feature.order.interfaces.OnPaymentMethodClickListener;
import com.plugi.plugi.feature.profile.selling.SellingContainerFragment;
import com.plugi.plugi.models.DeleteCustomerCloset;
import com.plugi.plugi.models.GetCustomerClosets;
import com.plugi.plugi.models.User;
import com.plugi.plugi.models.itemDetails.ShippingCostPerCountry;
import com.plugi.plugi.models.orderDetails.viewOrderDetails;
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

public class ClosetDetailsFragment extends BaseFragment implements IOnBackPressed , OnAddressClickListener, OnPaymentMethodClickListener {

    ProductShowCaseWebView wv;
    //ItemInfo
    private TextView productName, itemSize  , itemColor , itemData , itemStatus , viewProduct, itemCondition , item_PurchaseDate , item_Purchase_price , item_marketValue , item_gainLoss , TxtWarring , TxtٍSubmitWarring;
    private Button btnAsk , btnSell;
    ShimmerFrameLayout shimmerFrameLayout;
    private ScrollView itemScrollView;
    int itemID;
    ImageView ivDelete , ivEdit , ivBack;
    private double shippingFees = 0;
    private ShippingCostPerCountry shippingCostPerCountry;
    private ImageView itemImage;
    public static Dialog  paymentDialog , addressDialog ;
    private CustomerShippingAddress selectAddress;
    private CustomerPaymentCard selectPayment;
    private ImageView  paymentMethod , shippingAddress;
    private GetCustomerClosets.ClosetDetail closetDetail;
    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_closet_details, container, false);

        //closetDetailsPojo = ((MainActivity) getActivity()).getViewOrderDetails();
        itemID = getArguments().getInt(Constants.BUNDLE_ITEM_ID , 0);
        closetDetail =  ((MainActivity) getActivity()).getSelectedCloset();
        ivDelete = view.findViewById(R.id.ivDelete);
        ivEdit = view.findViewById(R.id.ivEdit);
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



        paymentMethod = view.findViewById(R.id.paymentMethod);
        shippingAddress = view.findViewById(R.id.shippingAddress);
        itemImage = view.findViewById(R.id.itemImage);
        productName = view.findViewById(R.id.productName);
        itemSize = view.findViewById(R.id.itemSize);
        itemColor = view.findViewById(R.id.itemColor);
        itemData = view.findViewById(R.id.itemData);;
        itemCondition = view.findViewById(R.id.itemCondition);
        viewProduct = view.findViewById(R.id.viewProduct);
        itemStatus = view.findViewById(R.id.itemStatus);
        item_PurchaseDate = view.findViewById(R.id.item_PurchaseDate);
        item_Purchase_price = view.findViewById(R.id.item_Purchase_price);
        item_marketValue = view.findViewById(R.id.item_marketValue);
        item_gainLoss = view.findViewById(R.id.item_gainLoss);
        btnAsk = view.findViewById(R.id.btnAsk);
        btnSell = view.findViewById(R.id.btnSell);

        TxtWarring = view.findViewById(R.id.TxtWarring);
        TxtٍSubmitWarring = view.findViewById(R.id.TxtٍSubmitWarring);
        wv = (ProductShowCaseWebView) view.findViewById(R.id.web_view);
        pojoData();
        if(closetDetail != null) {//TODO
            setItemDetails(closetDetail);
        }
        else if(itemID != 0) {
            LoadData(itemID);

        }




    }

    private void pojoData() {
        /*
        closetDetailsPojo = new ClosetDetailsPojo(1,"Nike Sport Sneakers" , "18","Red","Waiting for seller to ship" ,
                "USED" , "15-10-20" ,"25.000","25.000" ,"20.000", "KD", "http://plugi.mawaqaademos.com/assets/images/Uploads/2020820121846.jpg");

         */
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

    private void setItemDetails(GetCustomerClosets.ClosetDetail itemDetails) {

        //((MainActivity) getActivity()).setAddressList(itemDetails.getCustomerShippingAddress());TODO
        //((MainActivity) getActivity()).setPaymentMethodList(itemDetails.getCustomerPaymentCards());TODO
        //load3DImage();
        Glide.with(getActivity()).load(itemDetails.getItemImage()).placeholder(R.drawable.ic_landiing_logo).into(itemImage);

        productName.setText(itemDetails.getItemName());
        itemSize.setText(itemDetails.getSizevalue());
        itemColor.setText(itemDetails.getColorvalue());
        itemCondition.setText(itemDetails.getConditionValue());
        itemData.setText(getResources().getString(R.string.authentic));
        item_PurchaseDate.setText(itemDetails.getPurchaseDate());
        item_Purchase_price.setText(itemDetails.getPurchasePrice().toString());
        item_marketValue.setText(itemDetails.getPurchasePrice().toString());//TODo
        item_gainLoss.setText(itemDetails.getGainOrLoss().toString());
        itemStatus.setText(itemDetails.getConditionValue());//TODO

        //AssignDefault(itemDetails);
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
            btnSell.setVisibility(View.GONE);
            btnSell.setEnabled(false);
            btnAsk.setVisibility(View.GONE);
            btnAsk.setEnabled(false);
        }

        btnAsk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "API/AddItemAsk", Toast.LENGTH_SHORT).show();
                /*
                Fragment fragment = null;
                fragment = new ConfirmBidFragment();
                ((MainActivity) requireActivity()).replaceReviewFragments(fragment, Constants.BUNDLE_ITEM_DETAILS, itemDetails);

                 */
            }
        });
        btnSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "API/SellNow", Toast.LENGTH_SHORT).show();

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
                args.putInt(Constants.BUNDLE_ITEM_ID, itemDetails.getItemID());
                fragment.setArguments(args);
                ((MainActivity) getActivity()).replaceReviewFragments(fragment);
            }
        });
        paymentMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPaymentDialog(getActivity(), ((MainActivity) getActivity()).getPaymentMethodList());
            }
        });

        shippingAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showAddressDialog(getActivity(), ((MainActivity) getActivity()).getAddressList());
            }
        });
        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RemoveFromFav(itemDetails.getClosetId());
            }
        });

    }
    public void RemoveFromFav(int _itemID)
    {
        Log.d("LoadData", "LoadData: " + _itemID);
        User currentUser = SharedPrefManager.getInstance(getActivity()).getUser();
        ApiInterface service = RetrofitClient.retrofitBearerTokenWrite(currentUser).create(ApiInterface.class);

        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("ClosetId", closetDetail.getClosetId());


        RequestBody bodyToken = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
        //defining the call
        Call<DeleteCustomerCloset> call = service.DeleteCustomerCloset(
                bodyToken
        );
        call.enqueue(new Callback<DeleteCustomerCloset>() {
            @Override
            public void onResponse(Call<DeleteCustomerCloset> call, Response<DeleteCustomerCloset> response) {

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
            public void onFailure(Call<DeleteCustomerCloset> call, Throwable t) {
                Log.d("ItemDetails", "onFailure: " + t.getLocalizedMessage());
            }
        });
    }
    private void AssignDefault(GetCustomerClosets.ClosetDetail itemDetails) {


        if(((MainActivity) getActivity()).getSelectAddress() != null) {
            selectAddress = ((MainActivity) getActivity()).getSelectAddress();

            btnAsk.setVisibility(View.VISIBLE);
            btnAsk.setEnabled(true);
            btnSell.setVisibility(View.VISIBLE);
            btnSell.setEnabled(true);
            TxtWarring.setVisibility(View.GONE);



        }
        else
        {
            Log.d("SHIPPING", "AssignDefault:5 ");
            selectAddress = ((MainActivity) getActivity()).getAddressList().get(0);
            ((MainActivity) getActivity()).setSelectAddress(selectAddress);
            btnAsk.setVisibility(View.VISIBLE);
            btnAsk.setEnabled(true);
            btnSell.setVisibility(View.VISIBLE);
            btnSell.setEnabled(true);
            TxtWarring.setVisibility(View.GONE);
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

    public void showAddressDialog(Activity activity , List<CustomerShippingAddress> addressList){

        addressDialog = new Dialog(activity);
        // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        addressDialog.setCancelable(false);
        addressDialog.setContentView(R.layout.dialog_select_address);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(addressDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        int selectedIndex = -1;

        if(selectAddress != null) {
            //Log.d("FILTER", "AssignSortList: " + selectBrand.getName());
            selectedIndex = addressList.indexOf(selectAddress);
        }

        RecyclerView recyclerView = addressDialog.findViewById(R.id.rec_AllAddress);
        SelectAddressAdapter adapterRe = new SelectAddressAdapter(activity , addressList , selectedIndex , this);
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

        Button btnSubmit = addressDialog.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addressDialog.dismiss();
                shippingAddress.setEnabled(true);
            }
        });
        addressDialog.show();
        addressDialog.getWindow().setAttributes(lp);
        shippingAddress.setEnabled(false);


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
                paymentMethod.setEnabled(true);
            }
        });
        paymentDialog.show();
        paymentDialog.getWindow().setAttributes(lp);
        paymentMethod.setEnabled(false);


    }
    @Override
    protected int layoutRes() {
        return R.layout.fragment_closet_details;
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
    public void onAddressClicked(CustomerShippingAddress contact, int position) {
        ((MainActivity) getActivity()).setSelectAddress(contact);
        selectAddress = contact;
        //AssignDefault(closetDetailsPojo);


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
    public void onPaymentMethodClicked(CustomerPaymentCard contact, int position) {
        ((MainActivity) getActivity()).setSelectPayment(contact);
        selectPayment = contact;
        //AssignDefault(closetDetailsPojo);



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
}
