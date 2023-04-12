package com.plugi.plugi.feature.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.login.LoginManager;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.plugi.plugi.R;
import com.plugi.plugi.core.base.BaseFragment;
import com.plugi.plugi.core.views.RMSwitch.RMSwitch;
import com.plugi.plugi.feature.login.AuthFragment;
import com.plugi.plugi.feature.main.adapter.SocialAdapter;
import com.plugi.plugi.feature.main.interfaces.OnSocialClickListener;
import com.plugi.plugi.feature.menu.AboutFragment;
import com.plugi.plugi.feature.menu.ContactFragment;
import com.plugi.plugi.feature.menu.PrivacyFragment;
import com.plugi.plugi.feature.menu.TermsFragment;
import com.plugi.plugi.feature.splash.SplashActivity;
import com.plugi.plugi.models.SocialMedia;
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

public class SideMenuFragment extends BaseFragment implements OnSocialClickListener, GoogleApiClient.OnConnectionFailedListener {

    public TextView toolbarTitle , loginStatus;
    private ImageView ivClose;
    private LinearLayout aboutPanel , contactPanel , termsPanel , privacyPanel , logoutPanel;
    private List<SocialMedia.Link> linkList;
    private SocialAdapter socialAdapter;
    private RecyclerView recSocial;
    private ShimmerFrameLayout shimmerFrameLayout;
    private RMSwitch languageSwitch;
   // private ImageView Facebook , Insta , Youtube , Twitter , GooglePlus ,LinkedIn;
    //private String refFacebook = null  , refInsta = null , refYoutube = null , refTwitter = null , refGooglePlus = null ,refLinkedIn = null;
    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_side_menu, container, false);

        shimmerFrameLayout = view.findViewById(R.id.parentShimmerLayout);
        shimmerFrameLayout.startShimmer();

        toolbarTitle = view.findViewById(R.id.toolbarTitle);

        ivClose = view.findViewById(R.id.ivClose);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //((MainActivity) getActivity()).sideMenu();
                ivClose.setVisibility(View.GONE);
                ((MainActivity) getActivity()).forceHiddenSideMenu();
            }
        });
        toolbarTitle.setVisibility(View.VISIBLE);
        toolbarTitle.setText(getResources().getString(R.string.menu));
        aboutPanel = view.findViewById(R.id.aboutPanel);
        contactPanel = view.findViewById(R.id.contactPanel);
        termsPanel = view.findViewById(R.id.termsPanel);
        privacyPanel = view.findViewById(R.id.privacyPanel);
        logoutPanel = view.findViewById(R.id.logoutPanel);
        loginStatus = view.findViewById(R.id.loginStatus);
        languageSwitch = view.findViewById(R.id.languageSwitch);
        recSocial = view.findViewById(R.id.recSocial);
        AssignSocialList();



        if(SharedPrefManager.getInstance(getActivity()).isLoggedIn()) {
            loginStatus.setText(getResources().getString(R.string.logout));
        }

        languageSwitch.addSwitchObserver(new RMSwitch.RMSwitchObserver() {
            @Override
            public void onCheckStateChange(RMSwitch switchView, boolean isChecked) {
                Log.d("LANGUAGE", "onCheckStateChange: " + isChecked);
            }
        });
        logoutPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SharedPrefManager.getInstance(getActivity()).isLoggedIn()) {
                    SharedPrefManager.getInstance(getActivity()).logout();
                    LoginManager.getInstance().logOut();
                    Auth.GoogleSignInApi.signOut( ((MainActivity) getActivity()).googleApiClient).setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult(@NonNull Status status) {
                            if(status.isSuccess())
                            {
                                Log.d("GOOGLE", "onResult: " + status.getStatusMessage());
                            }
                        }
                    });
                    Intent loginIntent = new Intent(getActivity(), SplashActivity.class);
                    startActivity(loginIntent);
                    getActivity().finish();
                }
                else
                {
                    Fragment fragment = null;
                    fragment = new AuthFragment();
                    ((MainActivity) getActivity()).replaceFragments(fragment);
                    /*
                    Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(loginIntent);

                     */
                }
            }
        });
        aboutPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                fragment = new AboutFragment();
                ((MainActivity) getActivity()).replaceFragments(fragment);


            }
        });
        contactPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                fragment = new ContactFragment();
                ((MainActivity) getActivity()).replaceFragments(fragment);

            }
        });
        privacyPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                fragment = new PrivacyFragment();
                ((MainActivity) getActivity()).replaceFragments(fragment);

            }
        });
        termsPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                fragment = new TermsFragment();
                ((MainActivity) getActivity()).replaceFragments(fragment);

            }
        });


        /*
        ((MainActivity) getActivity()).replaceFragments(AboutFragment.class);

         */
        loadData();

        return view;
    }
    private void AssignSocialList(){

        linkList = new ArrayList<>();

        LinearLayoutManager mLayoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        recSocial.setLayoutManager(mLayoutManager);
        recSocial.setItemAnimator(new DefaultItemAnimator());
        recSocial.setHasFixedSize(true);
        recSocial.setNestedScrollingEnabled(false);
        /* TODO USELESS WITHOUT DATABASE
        recCategory.addOnScrollListener(new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                userPage++;
                getData(userPage);

            }
        });

         */
        socialAdapter = new SocialAdapter(linkList , this);
        recSocial.setAdapter(socialAdapter);

    }
    public void loadData()
    {
        ApiInterface service = RetrofitClient.retrofitWrite().create(ApiInterface.class);

        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("language_ID", 1);
        RequestBody bodyToken = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
        //defining the call
        Call<SocialMedia> call = service.GetAllSocialMediaLinks(
                bodyToken
        );
        call.enqueue(new Callback<SocialMedia>() {
            @Override
            public void onResponse(Call<SocialMedia> call, Response<SocialMedia> response) {

                if(response.body().getStatusMessage().equals("Success"))
                {
                    linkList.addAll(response.body().getLinks());
                    socialAdapter.notifyDataSetChanged();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    shimmerFrameLayout.stopShimmer();
                    recSocial.setVisibility(View.VISIBLE);


                }
            }

            @Override
            public void onFailure(Call<SocialMedia> call, Throwable t) {

            }
        });
    }
    @Override
    protected int layoutRes() {
        return R.layout.fragment_side_menu;
    }


    @Override
    public void onSocialClicked(SocialMedia.Link contact, int position) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(contact.getURL())));
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
