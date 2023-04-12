package com.plugi.plugi.feature.item;

import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.plugi.plugi.core.views.materialSpinner.MaterialSpinner;
import com.plugi.plugi.feature.main.MainActivity;
import com.plugi.plugi.feature.main.SideMenuFragment;
import com.plugi.plugi.feature.main.interfaces.IOnBackPressed;
import com.plugi.plugi.models.AddCustomerCloset;
import com.plugi.plugi.models.DeleteCustomerCloset;
import com.plugi.plugi.models.ItemDetails;
import com.plugi.plugi.models.User;
import com.plugi.plugi.retrofit.ApiInterface;
import com.plugi.plugi.retrofit.RetrofitClient;
import com.plugi.plugi.retrofit.SharedPrefManager;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddToClosetFragment extends BaseFragment implements IOnBackPressed {

    private ItemDetails itemDetails;
    ProductShowCaseWebView wv;
    private ImageView itemImage;
    //ItemInfo
    private TextView productName , item_Size , item_PurchaseDate , item_Price , tvUnWorm , tvWorm;
    private Button btnSubmit;
    ShimmerFrameLayout shimmerFrameLayout ;
    private ScrollView itemScrollView;
    ImageView ivSide , ivBack;
    LinearLayout conditionPanel;
    MaterialSpinner conditionSpinner;
    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_to_closet, container, false);

        itemDetails = ((MainActivity) getActivity()).getSelectItem();

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

        conditionPanel = view.findViewById(R.id.conditionPanel);
        itemImage = view.findViewById(R.id.itemImage);
        conditionSpinner=  (MaterialSpinner) view.findViewById(R.id.ConditionSpinner);
        conditionSpinner.setItems("New", "Used");
        conditionSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                //Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            }
        });
        itemScrollView = view.findViewById(R.id.itemScrollView);

        //item_Size , item_PurchaseDate , item_Price , tvUnWorm , tvWorm
        productName = view.findViewById(R.id.productName);
        item_Size = view.findViewById(R.id.item_Size);
        item_PurchaseDate = view.findViewById(R.id.item_PurchaseDate);
        item_Price = view.findViewById(R.id.item_Price);
        tvUnWorm = view.findViewById(R.id.tvUnWorm);
        tvWorm = view.findViewById(R.id.tvWorm);



        btnSubmit = view.findViewById(R.id.btnSubmit);

        wv = (ProductShowCaseWebView) view.findViewById(R.id.web_view);

        if(itemDetails != null) {
            setItemDetails(itemDetails);
        }




    }


    public void LoadData(int _itemID)
    {
        ApiInterface service = RetrofitClient.retrofitWrite().create(ApiInterface.class);

        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("ItemID", _itemID);
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

                   setItemDetails(response.body());
                }
            }

            @Override
            public void onFailure(Call<ItemDetails> call, Throwable t) {
                Log.d("ItemDetails", "onFailure: " + t.getLocalizedMessage());
            }
        });
    }
    Date currentTime = Calendar.getInstance().getTime();
    Date selectedDate = currentTime;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);//dd:MM:yyyy
    private void setItemDetails(ItemDetails itemDetails) {


        Log.d("ItemDetails", "setItemDetails: " + itemDetails.getItemID());
        if(itemDetails.getItemImages().size() > 0)
        {
            load3DImage(itemDetails);
            itemImage.setVisibility(View.GONE);
            wv.setVisibility(View.VISIBLE);
        }
        else
        {
            Glide.with(getActivity()).load(itemDetails.getItemDetail().getMainImage()).placeholder(R.drawable.ic_landiing_logo).into(itemImage);
        }

//        item_Size.setText(((MainActivity) requireActivity()).getSelectedSize().toString());
       // productName.setText(itemDetails.getItemDetail().getName());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(selectedDate);
        item_PurchaseDate.setText(sdf.format(selectedDate));
        itemScrollView.setVisibility(View.VISIBLE);
        shimmerFrameLayout.setVisibility(View.GONE);
        shimmerFrameLayout.stopShimmer();

        tvUnWorm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conditionPanel.setVisibility(View.GONE);
                tvUnWorm.setBackground(getResources().getDrawable(R.drawable.draw_round_border_fill));
                tvUnWorm.setTextColor(getResources().getColor(R.color.white));
                tvWorm.setBackgroundColor(getResources().getColor(R.color.white));
                tvWorm.setTextColor(getResources().getColor(R.color.black));
            }
        });
        tvWorm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conditionPanel.setVisibility(View.VISIBLE);
                tvWorm.setBackground(getResources().getDrawable(R.drawable.draw_round_border_fill));
                tvWorm.setTextColor(getResources().getColor(R.color.white));
                tvUnWorm.setBackgroundColor(getResources().getColor(R.color.white));
                tvUnWorm.setTextColor(getResources().getColor(R.color.black));
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Fragment fragment = null;
                fragment = new ConfirmBidFragment();
                ((MainActivity) requireActivity()).replaceReviewFragments(fragment , Constants.BUNDLE_ITEM_DETAILS , itemDetails);

                 */
                //TODO
                //int _itemID , int sizeID , int conditionID , String purchaseDate , int purchasePrice
                AddToCloset(itemDetails.getItemID() , 2,  37 ,  "10-10-2020" , 250);
            }
        });


    }
    private void load3DImage(ItemDetails itemDetails) {

        wv.setVerticalScrollBarEnabled(false);
        wv.setHorizontalScrollBarEnabled(false);

        String imagesTag360="";
        /*Taking images from the assert folder*/
        //imagesTag360=imagesTag360+"<img src=\"http://plugi.mawaqaademos.com/assets/images/Uploads/202082718813.jpg\"/>";
        /*
        for(int i=1;i<19;i++)
        {
            imagesTag360=imagesTag360+"<img src=\"file:///android_asset/images/image1_"+i+".jpg\"/>" ;
        }

         */

        /* For Showing Images from image url just use the image url in the src field*/

        for(int i=0;i<itemDetails.getItemImages().size();i++) {
            imagesTag360 = imagesTag360 + "<img src=\"http://plugi.mawaqaademos.com/assets/images/Uploads/"+itemDetails.getItemImages().get(i).getPropName() +"\"/>";
        }


        Log.d("",imagesTag360);


        wv.loadDataWithBaseURL("",
                imagesTag360, "text/html", "UTF-8", null);
    }
    @Override
    protected int layoutRes() {
        return R.layout.fragment_review_bid;
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

    public void AddToCloset(int _itemID , int sizeID , int conditionID , String purchaseDate , int purchasePrice)
    {
        Log.d("LoadData", "LoadData: " + _itemID);
        if(SharedPrefManager.getInstance(getActivity()).isLoggedIn()) {
            User currentUser = SharedPrefManager.getInstance(getActivity()).getUser();
            ApiInterface service = RetrofitClient.retrofitBearerTokenWrite(currentUser).create(ApiInterface.class);

            Map<String, Object> jsonParams = new ArrayMap<>();
            jsonParams.put("Item_ID", _itemID);
            jsonParams.put("Customer_ID", currentUser.getCustomer().getID());
            jsonParams.put("SizeId", sizeID);
            jsonParams.put("ConditionId", conditionID);
            jsonParams.put("PurchaseDate", purchaseDate);
            jsonParams.put("PurchasePrice", purchasePrice);
            jsonParams.put("language_ID", 1);


            RequestBody bodyToken = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
            //defining the call
            Call<AddCustomerCloset> call = service.AddCustomerCloset(
                    bodyToken
            );
            call.enqueue(new Callback<AddCustomerCloset>() {
                @Override
                public void onResponse(Call<AddCustomerCloset> call, Response<AddCustomerCloset> response) {

                    if (response.body() != null) {
                        if (response.body().getStatusMessage().equals("Success")) {

                            Log.d("LoadData", "onResponse: " + response.body().getStatusMessage());
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
                            //Toast.makeText(getActivity(), "" + getResources().getString(R.string.added_successfuly), Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getActivity(), "" + response.body().getStatusMessage(), Toast.LENGTH_SHORT).show();
                            Log.d("LoadData", "onResponse: " + response.body().getStatusMessage());
                        }
                    } else {
                        Log.d("LoadData", "onResponse: " + response.message());
                        Log.d("LoadData", "onResponse: " + response.errorBody());
                    }
                    /*


                     */

                }

                @Override
                public void onFailure(Call<AddCustomerCloset> call, Throwable t) {
                    Log.d("ItemDetails", "onFailure: " + t.getLocalizedMessage());
                }
            });
        }
    }
    public void RemoveFromCloset(int _itemID)
    {
        Log.d("LoadData", "LoadData: " + _itemID);
        if(SharedPrefManager.getInstance(getActivity()).isLoggedIn()) {
            User currentUser = SharedPrefManager.getInstance(getActivity()).getUser();
            ApiInterface service = RetrofitClient.retrofitBearerTokenWrite(currentUser).create(ApiInterface.class);

            Map<String, Object> jsonParams = new ArrayMap<>();
            jsonParams.put("ClosetId", _itemID);


            RequestBody bodyToken = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
            //defining the call
            Call<DeleteCustomerCloset> call = service.DeleteCustomerCloset(
                    bodyToken
            );
            call.enqueue(new Callback<DeleteCustomerCloset>() {
                @Override
                public void onResponse(Call<DeleteCustomerCloset> call, Response<DeleteCustomerCloset> response) {

                    if (response.body() != null) {
                        if (response.body().getStatusMessage().equals("Success")) {


                            Log.d("LoadData", "onResponse: " + response.body().getStatusMessage());
                            //Toast.makeText(getActivity(), "" + getResources().getString(R.string.added_successfuly), Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getActivity(), "" + response.body().getStatusMessage(), Toast.LENGTH_SHORT).show();
                            Log.d("LoadData", "onResponse: " + response.body().getStatusMessage());
                        }
                    } else {
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
    }

}
