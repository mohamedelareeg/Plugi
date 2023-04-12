package com.plugi.plugi.feature.profile;

import android.os.Bundle;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.plugi.plugi.R;
import com.plugi.plugi.core.base.BaseFragment;
import com.plugi.plugi.feature.main.MainActivity;
import com.plugi.plugi.feature.main.SideMenuFragment;
import com.plugi.plugi.feature.profile.adapter.WalletAdapter;
import com.plugi.plugi.feature.profile.interfaces.OnWalletClickListener;
import com.plugi.plugi.models.GetCustomerWallet;
import com.plugi.plugi.models.User;
import com.plugi.plugi.retrofit.ApiInterface;
import com.plugi.plugi.retrofit.RetrofitClient;
import com.plugi.plugi.retrofit.SharedPrefManager;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletFragment extends BaseFragment implements OnWalletClickListener {

    LinearLayout WalletPanel , viewNoItems;
    RelativeLayout WalletTransactionInfo;
    TextView walletValue;
    private ShimmerFrameLayout shimmerFrameLayout2;

    WalletAdapter WalletAdapter;
    List<GetCustomerWallet.Detail> walletList;
    RecyclerView rec_Wallet;
    ImageView ivSide , ivBack;
    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wallet, container, false);
        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MainActivity) getActivity()).forceHiddenPopUp(false);
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



        shimmerFrameLayout2 = view.findViewById(R.id.parentShimmerLayout2);
        shimmerFrameLayout2.startShimmer();





        WalletPanel = view.findViewById(R.id.WalletPanel);
        WalletTransactionInfo = view.findViewById(R.id.WalletTransactionInfo);
        walletValue = view.findViewById(R.id.walletValue);
        rec_Wallet = view.findViewById(R.id.rec_Wallet);

        walletList = new ArrayList<>();
        RecyclerView.LayoutManager PopularlayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rec_Wallet.setLayoutManager(PopularlayoutManager);
        WalletAdapter = new WalletAdapter(getContext(), walletList ,this);
        rec_Wallet.setAdapter(WalletAdapter);
        loadData();




        return view;
    }


    private String splitLink(String link) {
        if (link.contains("https://youtu.be/")) {
            return link.split("https://youtu.be/")[1];
        } else {
            return link;
        }
    }
    public void loadData()
    {
        if(SharedPrefManager.getInstance(getActivity()).isLoggedIn()) {
            User user = SharedPrefManager.getInstance(getActivity()).getUser();
            ApiInterface service = RetrofitClient.retrofitBearerTokenWrite(user).create(ApiInterface.class);

            Map<String, Object> jsonParams = new ArrayMap<>();
            jsonParams.put("CustomerID", user.getCustomer().getID());
            jsonParams.put("language_ID", 1);
            RequestBody bodyToken = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
            //defining the call
            Call<GetCustomerWallet> call = service.GetCustomerWallet(
                    bodyToken
            );
            call.enqueue(new Callback<GetCustomerWallet>() {
                @Override
                public void onResponse(Call<GetCustomerWallet> call, Response<GetCustomerWallet> response) {

                    if (response.body().getStatusMessage().equals("Success")) {

                        walletValue.setText(response.body().getCurrencePrice() + "." + response.body().getTotalBalance());
                        walletList.addAll(response.body().getDetails());
                        WalletAdapter.notifyDataSetChanged();
                        shimmerFrameLayout2.setVisibility(View.GONE);
                        shimmerFrameLayout2.stopShimmer();

                    } else {

                        shimmerFrameLayout2.setVisibility(View.GONE);
                        shimmerFrameLayout2.stopShimmer();
                        // viewNoItems.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<GetCustomerWallet> call, Throwable t) {
                    walletValue.setText(getResources().getString(R.string.pojo_price));
                }
            });
        }
    }
    @Override
    protected int layoutRes() {
        return R.layout.fragment_wallet;
    }


    @Override
    public void onWalletClicked(GetCustomerWallet.Detail contact, int position) {

    }
}
