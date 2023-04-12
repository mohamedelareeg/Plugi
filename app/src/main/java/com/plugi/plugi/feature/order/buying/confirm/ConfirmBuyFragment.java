package com.plugi.plugi.feature.order.buying.confirm;

import android.os.Bundle;
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

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ConfirmBuyFragment extends BaseFragment implements IOnBackPressed {

    private ReviewItem reviewItem;
    //ItemInfo
    private ImageView item_image , ivShare;
    private TextView item_name, item_desc , item_Total , orderStatus;
    private Button btnSubmit;
    ShimmerFrameLayout shimmerFrameLayout;
    private ScrollView itemScrollView;
    ImageView ivSide , ivBack;
    CheckOrderPaymentStatus checkOrderPaymentStatus;
    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_confirm_buy, container, false);

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

    @Override
    public void onViewCreated(@NonNull View view, @androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        shimmerFrameLayout = view.findViewById(R.id.parentShimmerLayout);
        shimmerFrameLayout.startShimmer();

        itemScrollView = view.findViewById(R.id.itemScrollView);
        orderStatus = view.findViewById(R.id.orderStatus);
        item_image = view.findViewById(R.id.item_image);
        item_name = view.findViewById(R.id.item_name);
        item_desc = view.findViewById(R.id.item_desc);
        item_Total = view.findViewById(R.id.item_Total);
        ivShare = view.findViewById(R.id.ivShare);

        btnSubmit = view.findViewById(R.id.btnSubmit);



        if(reviewItem != null) {
            setItemDetails(reviewItem);
        }




    }




    private void setItemDetails(ReviewItem itemDetails) {


        Log.d("ItemDetails", "setItemDetails: " + itemDetails.getItemID());

        item_name.setText(itemDetails.getItemDetail().getName());
        item_desc.setText(itemDetails.getItemDetail().getDescription());
        // setting up image using GLIDE
        Glide.with(getActivity()).load(itemDetails.getItemDetail().getMainImage()).placeholder(R.drawable.ic_landiing_logo).into(item_image);

        double totalFees = reviewItem.getPlugiProcessingFees() + ((MainActivity) getActivity()).getShippingCost() + reviewItem.getTaxFees() - reviewItem.getDiscountCost();
        double itemValue = ((MainActivity) requireActivity()).getItemValue();
        double itemtotal = totalFees + itemValue;
        if(((MainActivity) getActivity()).getSelectedDiscount() != null)
        {
            itemtotal = itemtotal - ((MainActivity) getActivity()).getSelectedDiscount().getDiscountCost();
            ((MainActivity) getActivity()).setSelectedDiscount(null);
        }
        orderStatus.setText(checkOrderPaymentStatus.getTransactionStatus().toString());
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
        return R.layout.fragment_confirm_buy;
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
