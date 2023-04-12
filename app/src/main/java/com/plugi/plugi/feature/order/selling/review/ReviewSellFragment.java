package com.plugi.plugi.feature.order.selling.review;

import android.os.Bundle;
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
import com.plugi.plugi.feature.order.selling.payment.SellPaymentFragment;
import com.plugi.plugi.models.ReviewItem;
import com.plugi.plugi.models.itemDetails.ShippingCostPerCountry;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ReviewSellFragment extends BaseFragment implements IOnBackPressed {

    private ReviewItem itemDetails;
    ProductShowCaseWebView wv;
    //ItemInfo
    private TextView productName, itemSize , itemColor , item_sale_price, item_all_fees , item_Total , shipAddress , shipCity , paymentMethod  , getPaid;
    private CheckBox cbNew , cbShip;
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
        View view = inflater.inflate(R.layout.fragment_review_sell, container, false);

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
        item_sale_price = view.findViewById(R.id.item_sale_price);
        item_all_fees = view.findViewById(R.id.item_all_fees);
        item_Total = view.findViewById(R.id.item_Total);
        shipAddress = view.findViewById(R.id.shipAddress);
        shipCity = view.findViewById(R.id.shipCity);
        paymentMethod = view.findViewById(R.id.paymentMethod);
        getPaid = view.findViewById(R.id.getPaid);
        cbNew = view.findViewById(R.id.cbNew);
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
        item_sale_price.setText(String.valueOf(itemValue));
        double transcation = itemDetails.getTransactionFees();
        double itemtotal = totalFees + transcation + itemValue;
        if(((MainActivity) requireActivity()).getSelectedDiscount() != null)
        {
            itemtotal = itemtotal - ((MainActivity) requireActivity()).getSelectedDiscount().getDiscountCost();
        }

        item_Total.setText(String.valueOf(String.format("%.2f",itemtotal)));
        shipAddress.setText(((MainActivity) requireActivity()).getSelectAddress().getRegionName());
        shipCity.setText(((MainActivity) requireActivity()).getSelectAddress().getCityName());
        paymentMethod.setText(((MainActivity) requireActivity()).getSelectPayment().getCardTypeName());
        getPaid.setText(((MainActivity) requireActivity()).getSelectPayment().getCardNo().toString());
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbNew.isChecked() && cbShip.isChecked()) {
                    Fragment fragment = null;
                    fragment = new SellPaymentFragment();
                    ((MainActivity) requireActivity()).replaceReviewFragments(fragment, Constants.BUNDLE_ITEM_DETAILS, itemDetails);
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

    @Override
    protected int layoutRes() {
        return R.layout.fragment_review_sell;
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
