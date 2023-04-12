package com.plugi.plugi.feature.item.Size;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.plugi.plugi.R;
import com.plugi.plugi.feature.item.adapter.ItemSizesAdapter;
import com.plugi.plugi.feature.item.interfaces.OnItemSizeClickListener;
import com.plugi.plugi.feature.main.MainActivity;
import com.plugi.plugi.feature.order.selling.SellFragment;
import com.plugi.plugi.models.ItemDetails;
import com.plugi.plugi.models.itemDetails.AllItemSize;
import com.plugi.plugi.retrofit.SharedPrefManager;


public class SellBottomSheetFragment extends BottomSheetDialogFragment implements  OnItemSizeClickListener {

    @Override
    public void onItemSizeClicked(AllItemSize contact, int position) {
        ((MainActivity) getActivity()).setSelectItemSize(contact);
        selectedSize = contact;
        if(SharedPrefManager.getInstance(getActivity()).isLoggedIn()) {
            Fragment fragment = null;
            fragment = new SellFragment();
            ((MainActivity) getActivity()).replacePopUpFragments(fragment);
            dismiss();
        }
        else
        {
            Toast.makeText(getActivity(), getResources().getString(R.string.please_login_first), Toast.LENGTH_SHORT).show();
        }

    }

    public interface onSomeEventListener {
        public void PostEvent(AllItemSize post);
    }
    public AllItemSize selectedSize;
    onSomeEventListener someEventListener;
    public SellBottomSheetFragment() {
        // Required empty public constructor
    }
    private ProgressDialog mLoginProgress;
    private ItemDetails itemDetails;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Making bottom sheet expanding to full height by default
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        dialog.setOnShowListener(dialog1 -> {
            BottomSheetDialog d = (BottomSheetDialog) dialog1;

            FrameLayout bottomSheet = d.findViewById(R.id.design_bottom_sheet);
            BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
        });
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_size_bottom_sheet, container, false);
        itemDetails = ((MainActivity) getActivity()).getSelectItem();
        if(((MainActivity) getActivity()).getSelectItemSize() != null) {
            selectedSize = ((MainActivity) getActivity()).getSelectItemSize();

        }
        else
        {
            if(((MainActivity) getActivity()).getItemSizeList() != null  && ((MainActivity) getActivity()).getItemSizeList().size() > 0) {
                selectedSize = ((MainActivity) getActivity()).getItemSizeList().get(0);
                ((MainActivity) getActivity()).setSelectItemSize(selectedSize);
            }
        }
        TextView viewChart = (TextView) view.findViewById(R.id.viewChart);
        if(itemDetails.getSizeChartURL()!= null) {
            viewChart.setVisibility(View.VISIBLE);
            viewChart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(itemDetails.getSizeChartURL())));
                }
            });
        }
        else
        {
            viewChart.setVisibility(View.GONE);
        }

        int selectedIndex = -1;

        if(selectedSize != null) {
            //Log.d("FILTER", "AssignSortList: " + selectBrand.getName());
            selectedIndex = itemDetails.getAllItemSizes().indexOf(selectedSize);
        }
        RecyclerView recyclerView = view.findViewById(R.id.rec_Allsizes);
        ItemSizesAdapter adapterRe = new ItemSizesAdapter(getActivity() , itemDetails.getAllItemSizes(), selectedIndex , this);
        recyclerView.setAdapter(adapterRe);
        LinearLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 4);
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }

    public ProgressDialog mProgressDialog;
    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getContext());
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage(getActivity().getResources().getString(R.string.loading));
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    private void setBottomSheetBehavior() {

    };



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void init() {


    }

    @Override
    public void onResume() {



        super.onResume();
    }


    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        try {
        someEventListener = (onSomeEventListener) activity;
        } catch (ClassCastException e) {

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


        try {
            someEventListener = (onSomeEventListener) context;
        } catch (ClassCastException e) {

        }
    }




}
