package com.plugi.plugi.feature.main;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.plugi.plugi.R;
import com.plugi.plugi.core.base.BaseFragment;
import com.plugi.plugi.core.views.materialSpinner.MaterialSpinner;
import com.plugi.plugi.feature.profile.BlogFragment;
import com.plugi.plugi.feature.profile.ClosetFragment;
import com.plugi.plugi.feature.profile.EditProfileFragment;
import com.plugi.plugi.feature.profile.FaqsFragment;
import com.plugi.plugi.feature.profile.FollowingFragment;
import com.plugi.plugi.feature.profile.HowItWorkFragment;
import com.plugi.plugi.feature.profile.WalletFragment;
import com.plugi.plugi.feature.profile.buying.BuyingContainerFragment;
import com.plugi.plugi.feature.profile.selling.SellingContainerFragment;
import com.plugi.plugi.feature.profile.setting.SettingFragment;
import com.plugi.plugi.models.GetCurrenciesList;
import com.plugi.plugi.models.User;
import com.plugi.plugi.retrofit.SharedPrefManager;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends BaseFragment {

    ImageView ivSetting;
    TextView profileView;
    LinearLayout buyingPanel , sellingPanel  , closetPanel, followingPanel ,  blogPanel , walletPanel , hiwPanel , faqsPanel , CurrencyPanel;
    TextView profileName;
    User currentUser;
    ImageView ivSide;
    public static Dialog CurrencyDialog;
    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ivSide = view.findViewById(R.id.ivSide);
        ivSide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).sideMenu();
            }
        });

        profileName = view.findViewById(R.id.profileName);
        profileView = view.findViewById(R.id.profileView);
        ivSetting = view.findViewById(R.id.ivSetting);
        if(SharedPrefManager.getInstance(getActivity()).isLoggedIn())
        {
            profileView.setVisibility(View.VISIBLE);
            ivSetting.setVisibility(View.VISIBLE);
            currentUser = SharedPrefManager.getInstance(getActivity()).getUser();
            profileName.setText(currentUser.getCustomer().getFirstName());
        }
        buyingPanel = view.findViewById(R.id.buyingPanel);
        sellingPanel = view.findViewById(R.id.sellingPanel);
        closetPanel = view.findViewById(R.id.closetPanel);
        followingPanel = view.findViewById(R.id.followingPanel);
        hiwPanel = view.findViewById(R.id.hiwPanel);
        blogPanel = view.findViewById(R.id.blogPanel);
        walletPanel = view.findViewById(R.id.walletPanel);
        faqsPanel = view.findViewById(R.id.faqsPanel);
        CurrencyPanel = view.findViewById(R.id.CurrencyPanel);

        profileView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SharedPrefManager.getInstance(getActivity()).isLoggedIn()) {
                    Fragment fragment = null;
                    fragment = new EditProfileFragment();
                    ((MainActivity) getActivity()).replacePopUpFragments(fragment);
                }
            }
        });
        ivSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SharedPrefManager.getInstance(getActivity()).isLoggedIn()) {
                    Fragment fragment = null;
                    fragment = new SettingFragment();
                    ((MainActivity) getActivity()).replacePopUpFragments(fragment);
                }
                else
                {
                    Toast.makeText(getActivity(), getResources().getString(R.string.please_login_first), Toast.LENGTH_SHORT).show();
                }
            }
        });
        buyingPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SharedPrefManager.getInstance(getActivity()).isLoggedIn()) {
                    Fragment fragment = null;
                    fragment = new BuyingContainerFragment();
                    ((MainActivity) getActivity()).replacePopUpFragments(fragment);
                }
                else
                {
                    Toast.makeText(getActivity(), getResources().getString(R.string.please_login_first), Toast.LENGTH_SHORT).show();
                }
            }
        });
        sellingPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SharedPrefManager.getInstance(getActivity()).isLoggedIn()) {
                    Fragment fragment = null;
                    fragment = new SellingContainerFragment();
                    ((MainActivity) getActivity()).replacePopUpFragments(fragment);
                }
                else
                {
                    Toast.makeText(getActivity(), getResources().getString(R.string.please_login_first), Toast.LENGTH_SHORT).show();
                }
            }
        });
        closetPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SharedPrefManager.getInstance(getActivity()).isLoggedIn()) {
                    Fragment fragment = null;
                    fragment = new ClosetFragment();
                    ((MainActivity) getActivity()).replacePopUpFragments(fragment);
                }
                else
                {
                    Toast.makeText(getActivity(), getResources().getString(R.string.please_login_first), Toast.LENGTH_SHORT).show();
                }
            }
        });
        followingPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SharedPrefManager.getInstance(getActivity()).isLoggedIn()) {
                    Fragment fragment = null;
                    fragment = new FollowingFragment();
                    ((MainActivity) getActivity()).replacePopUpFragments(fragment);
                }
                else
                {
                    Toast.makeText(getActivity(), getResources().getString(R.string.please_login_first), Toast.LENGTH_SHORT).show();
                }
            }
        });
        faqsPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                fragment = new FaqsFragment();
                ((MainActivity) getActivity()).replacePopUpFragments(fragment);
            }
        });
        blogPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                fragment = new BlogFragment();
                ((MainActivity) getActivity()).replacePopUpFragments(fragment);
            }
        });
        walletPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SharedPrefManager.getInstance(getActivity()).isLoggedIn()) {
                    Fragment fragment = null;
                    fragment = new WalletFragment();
                    ((MainActivity) getActivity()).replacePopUpFragments(fragment);
                }
                else
                {
                    Toast.makeText(getActivity(), getResources().getString(R.string.please_login_first), Toast.LENGTH_SHORT).show();
                }
            }
        });
        hiwPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                fragment = new HowItWorkFragment();
                ((MainActivity) getActivity()).replacePopUpFragments(fragment);
            }
        });
        CurrencyPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCurrencyDialog(getActivity());
            }
        });
        return view;
    }
    public void showCurrencyDialog(Activity activity){

        CurrencyDialog = new Dialog(activity);
        // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        CurrencyDialog.setCancelable(false);
        CurrencyDialog.setContentView(R.layout.dialog_currency);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(CurrencyDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        ImageView ivClose = CurrencyDialog.findViewById(R.id.ivClose);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrencyDialog.dismiss();
                CurrencyPanel.setEnabled(true);
            }
        });

        MaterialSpinner spinner = (MaterialSpinner) CurrencyDialog.findViewById(R.id.currencySpinner);
        List<GetCurrenciesList.Item> jsonresponse  = new ArrayList<>(((MainActivity) requireActivity()).getCurrecnyList());
        List<String> Values_sub = new ArrayList<>();
        for (int i = 0; i <jsonresponse.size() ; i++) {
            Values_sub.add(jsonresponse.get(i).getName().toString());
        }
        spinner.setItems(Values_sub);
        int selectedIndex = -1;
        if(SharedPrefManager.getInstance(getActivity()).isCurrencySelected())
        {
            //Log.d("FILTER", "AssignSortList: " + selectBrand.getName());
            selectedIndex = ((MainActivity) requireActivity()).getCurrecnyList().indexOf(SharedPrefManager.getInstance(getActivity()).getCurrentCurrency());

        }
        Log.d("CURR", "showCurrencyDialog: " + selectedIndex + " | " + spinner.getItems().size());
        //spinner.setSelectedIndex(selectedIndex);
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                //Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                for (int i = 0; i < jsonresponse.size(); i++) {
                    String month = jsonresponse.get(i).getName();
                    if (month.equals(item)) {
                        //Toast.makeText(AddTeacherActivity.this, "" + jsonresponse.get(i).getId() + "  " + jsonresponse.get(i).getCityName(), Toast.LENGTH_SHORT).show();
                        //getGroupCategory(jsonresponse.get(i).getId());
                        SharedPrefManager.getInstance(getActivity()).setCurrentCurrency(jsonresponse.get(i));
                        Log.d("REG", "onItemSelected: " + jsonresponse.get(i).getId());
                    }
                }
            }
        });

        Button btnSubmit = CurrencyDialog.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrencyDialog.dismiss();
                CurrencyPanel.setEnabled(true);
            }
        });

        CurrencyDialog.show();
        CurrencyDialog.getWindow().setAttributes(lp);
        CurrencyPanel.setEnabled(false);


    }

    @Override
    public void onResume() {

        super.onResume();
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_profile;
    }
}
