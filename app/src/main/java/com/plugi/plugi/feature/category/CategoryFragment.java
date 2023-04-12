package com.plugi.plugi.feature.category;

import android.content.Context;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.plugi.plugi.R;
import com.plugi.plugi.core.utilities.Constants;
import com.plugi.plugi.feature.category.adapter.HomeHighestBidsAdapter;
import com.plugi.plugi.feature.category.adapter.HomeLowestAskAdapter;
import com.plugi.plugi.feature.category.adapter.HomeMostPopularAdapter;
import com.plugi.plugi.feature.category.adapter.HomePopularBrandsAdapter;
import com.plugi.plugi.feature.category.adapter.HomeReleaseCalenderAdapter;
import com.plugi.plugi.feature.category.interfaces.OnItemClickListener;
import com.plugi.plugi.feature.item.ConditionFragment;
import com.plugi.plugi.feature.item.ItemDetailsFragment;
import com.plugi.plugi.feature.main.MainActivity;
import com.plugi.plugi.models.HomeScreen;
import com.plugi.plugi.models.MainCategory;
import com.plugi.plugi.retrofit.ApiInterface;
import com.plugi.plugi.retrofit.RetrofitClient;
import com.plugi.plugi.retrofit.SharedPrefManager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryFragment extends Fragment implements OnItemClickListener {
    private Context mCtx;
    private MainCategory.Category category;
    public static CategoryFragment newInstance(MainCategory.Category category , Context context) {
        Bundle args = new Bundle();
        CategoryFragment fragment = new CategoryFragment();
        fragment.category = category;
        fragment.mCtx = context;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    HomeMostPopularAdapter mostPopularAdapter;
    HomeLowestAskAdapter lowestAskAdapter;
    HomePopularBrandsAdapter popularBrandsAdapter;
    HomeHighestBidsAdapter homeHighestBidsAdapter;
    HomeReleaseCalenderAdapter homeReleaseCalenderAdapter;

    RecyclerView rec_MostPopular , rec_LowestAsk , rec_PopularBrand , rec_highestBids , rec_releaseCalender;
    ShimmerFrameLayout shimmerFrameLayout , shimmerFrameLayout2 ,  shimmerFrameLayout3 ,  shimmerFrameLayout4 ,  shimmerFrameLayout5;

    List<HomeScreen.MostPoPularItem> mostPopularList;
    List<HomeScreen.LowestASKsItem> lowestAskList;
    List<HomeScreen.Brand> brandList;
    List<HomeScreen.HightestBidsItem> hightestBidsList;
    List<HomeScreen.ReleaseCalenderItem> releaseCalenderList;

    ImageView bannerIMG;

    TextView viewPopularBrands , viewPopularProducts , viewLowestAsks ,  viewHighestBids , viewReleaseCalender;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        return view;
    }

    @Override
    public void onResume() {

        super.onResume();
    }

    private void LoadHomeScreen() {
        Log.d("CATEGORY", "LoadMostPopular: " + category.getCategoryID());
        ApiInterface service = RetrofitClient.retrofitWrite().create(ApiInterface.class);

        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("categoryID", category.getCategoryID());
        jsonParams.put("language_ID", 1);
        RequestBody bodyToken = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
        //defining the call
        Call<HomeScreen> call = service.GetHomeScreen(
                bodyToken
        );
        call.enqueue(new Callback<HomeScreen>() {
            @Override
            public void onResponse(Call<HomeScreen> call, Response<HomeScreen> response) {
                Log.d("CATEGORY", "onResponse: " + response.body().getStatusMessage());
                if(response.body().getStatusMessage().equals("Success"))
                {
                  setHomeScreen(response.body());

                }
            }

            @Override
            public void onFailure(Call<HomeScreen> call, Throwable t) {
                Log.d("CATEGORY", "onFailure: " + t.getLocalizedMessage());
            }
        });
    }

    private void setHomeScreen(HomeScreen homeScreen) {

        brandList.addAll(homeScreen.getBrands());
        popularBrandsAdapter.notifyDataSetChanged();

        mostPopularList.addAll(homeScreen.getMostPoPularItems());
        mostPopularAdapter.notifyDataSetChanged();

        lowestAskList.addAll(homeScreen.getLowestASKsItems());
        lowestAskAdapter.notifyDataSetChanged();

        hightestBidsList.addAll(homeScreen.getHightestBidsItems());
        homeHighestBidsAdapter.notifyDataSetChanged();

        releaseCalenderList.addAll(homeScreen.getReleaseCalenderItems());
        homeReleaseCalenderAdapter.notifyDataSetChanged();

        shimmerFrameLayout.setVisibility(View.GONE);
        shimmerFrameLayout.stopShimmer();

        shimmerFrameLayout2.setVisibility(View.GONE);
        shimmerFrameLayout2.stopShimmer();

        shimmerFrameLayout3.setVisibility(View.GONE);
        shimmerFrameLayout3.stopShimmer();

        shimmerFrameLayout4.setVisibility(View.GONE);
        shimmerFrameLayout4.stopShimmer();

        shimmerFrameLayout5.setVisibility(View.GONE);
        shimmerFrameLayout5.stopShimmer();

        // setting up image using GLIDE
        Glide.with(mCtx).load(homeScreen.getBannerImage()).into(bannerIMG);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        viewPopularBrands = view.findViewById(R.id.viewPopularBrands);
        viewPopularProducts = view.findViewById(R.id.viewPopularProducts);
        viewLowestAsks = view.findViewById(R.id.viewLowestAsks);
        viewHighestBids = view.findViewById(R.id.viewHighestBids);
        viewReleaseCalender = view.findViewById(R.id.viewReleaseCalender);

        bannerIMG = view.findViewById(R.id.bannerIMG);
        shimmerFrameLayout = view.findViewById(R.id.parentShimmerLayout);
        shimmerFrameLayout2 = view.findViewById(R.id.parentShimmerLayout2);
        shimmerFrameLayout3 = view.findViewById(R.id.parentShimmerLayout3);
        shimmerFrameLayout4 = view.findViewById(R.id.parentShimmerLayout4);
        shimmerFrameLayout5 = view.findViewById(R.id.parentShimmerLayout5);

        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout2.startShimmer();
        shimmerFrameLayout3.startShimmer();
        shimmerFrameLayout4.startShimmer();
        shimmerFrameLayout5.startShimmer();

        rec_PopularBrand = (RecyclerView) view.findViewById(R.id.rec_PopularBrand);
        rec_MostPopular = (RecyclerView) view.findViewById(R.id.rec_MostPopular);
        rec_LowestAsk = (RecyclerView) view.findViewById(R.id.rec_LowestAsk);
        rec_highestBids = (RecyclerView) view.findViewById(R.id.rec_highestBids);
        rec_releaseCalender = (RecyclerView) view.findViewById(R.id.rec_releaseCalender);

        brandList = new ArrayList<>();
        mostPopularList = new ArrayList<>();
        lowestAskList = new ArrayList<>();
        hightestBidsList = new ArrayList<>();
        releaseCalenderList = new ArrayList<>();

        LoadHomeScreen();

        RecyclerView.LayoutManager PopularbrandslayoutManager = new LinearLayoutManager(mCtx, LinearLayoutManager.HORIZONTAL, false);
        rec_PopularBrand.setLayoutManager(PopularbrandslayoutManager);
        popularBrandsAdapter = new HomePopularBrandsAdapter(mCtx, brandList , category.getCategoryID());
        rec_PopularBrand.setAdapter(popularBrandsAdapter);

        RecyclerView.LayoutManager PopularlayoutManager = new LinearLayoutManager(mCtx, LinearLayoutManager.HORIZONTAL, false);
        rec_MostPopular.setLayoutManager(PopularlayoutManager);
        mostPopularAdapter = new HomeMostPopularAdapter(mCtx, mostPopularList , category.getCategoryID() ,this);
        rec_MostPopular.setAdapter(mostPopularAdapter);

        RecyclerView.LayoutManager LowestlayoutManager = new LinearLayoutManager(mCtx, LinearLayoutManager.HORIZONTAL, false);
        rec_LowestAsk.setLayoutManager(LowestlayoutManager);
        lowestAskAdapter = new HomeLowestAskAdapter(mCtx, lowestAskList , category.getCategoryID() , this);
        rec_LowestAsk.setAdapter(lowestAskAdapter);

        RecyclerView.LayoutManager highestlayoutManager = new LinearLayoutManager(mCtx, LinearLayoutManager.HORIZONTAL, false);
        rec_highestBids.setLayoutManager(highestlayoutManager);
        homeHighestBidsAdapter = new HomeHighestBidsAdapter(mCtx, hightestBidsList , category.getCategoryID() , this);
        rec_highestBids.setAdapter(homeHighestBidsAdapter);


        RecyclerView.LayoutManager releaselayoutManager = new LinearLayoutManager(mCtx, LinearLayoutManager.HORIZONTAL, false);
        rec_releaseCalender.setLayoutManager(releaselayoutManager);
        homeReleaseCalenderAdapter = new HomeReleaseCalenderAdapter(mCtx, releaseCalenderList , category.getCategoryID() , this);
        rec_releaseCalender.setAdapter(homeReleaseCalenderAdapter);

        /*
         viewPopularBrands = view.findViewById(R.id.viewPopularBrands);
        viewPopularProducts = view.findViewById(R.id.viewPopularProducts);
        viewLowestAsks = view.findViewById(R.id.viewLowestAsks);
        viewHighestBids = view.findViewById(R.id.viewHighestBids);
        viewReleaseCalender = view.findViewById(R.id.viewReleaseCalender);
         */
        viewPopularBrands.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                ((MainActivity) mCtx).tabLayout.setCustomScrollPosition(1,0f,true);
                ((MainActivity) mCtx).mViewPager.setCurrentItem(1);
                ((MainActivity) mCtx).setSelectedSort(((MainActivity) mCtx).getSortList().get(0));
                ((MainActivity) mCtx).Listing(category);
                }catch (Exception e) {
                    Log.d("LIST", "onClick: " + e.getLocalizedMessage());
                }
            }
        });
        viewPopularProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {


                ((MainActivity) mCtx).tabLayout.setCustomScrollPosition(1,0f,true);
                ((MainActivity) mCtx).mViewPager.setCurrentItem(1);
                ((MainActivity) mCtx).setSelectedSort(((MainActivity) mCtx).getSortList().get(1));
                ((MainActivity) mCtx).Listing(category);
                }catch (Exception e) {
                    Log.d("LIST", "onClick: " + e.getLocalizedMessage());
                }
            }
        });
        viewLowestAsks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {


                ((MainActivity) mCtx).tabLayout.setCustomScrollPosition(1,0f,true);
                ((MainActivity) mCtx).mViewPager.setCurrentItem(1);
                ((MainActivity) mCtx).setSelectedSort(((MainActivity) mCtx).getSortList().get(2));
                ((MainActivity) mCtx).Listing(category);
                }catch (Exception e) {
                    Log.d("LIST", "onClick: " + e.getLocalizedMessage());
                }
            }
        });
        viewHighestBids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ((MainActivity) mCtx).tabLayout.setCustomScrollPosition(1,0f,true);
                    ((MainActivity) mCtx).mViewPager.setCurrentItem(1);
                    ((MainActivity) mCtx).setSelectedSort(((MainActivity) mCtx).getSortList().get(3));
                    ((MainActivity) mCtx).Listing(category);
                }catch (Exception e)
                {
                    Log.d("LIST", "onClick: " + e.getLocalizedMessage());
                }

            }
        });
        viewReleaseCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {


                ((MainActivity) mCtx).tabLayout.setCustomScrollPosition(1,0f,true);
                ((MainActivity) mCtx).mViewPager.setCurrentItem(1);
                ((MainActivity) mCtx).setSelectedSort(((MainActivity) mCtx).getSortList().get(9));
                ((MainActivity) mCtx).Listing(category);
                }catch (Exception e) {
                    Log.d("LIST", "onClick: " + e.getLocalizedMessage());
                }
            }
        });




    }

    @Override
    public void onItemClicked(Integer itemID , Integer categoryID) {
        if(SharedPrefManager.getInstance(mCtx).getGuestCondition() == 1) {
            //((MainActivity) mCtx).mViewPager.setVisibility(View.GONE);

            Fragment fragment = null;
            fragment = new ItemDetailsFragment();
            Bundle args = new Bundle();
            args.putInt(Constants.BUNDLE_ITEM_ID, itemID);
            fragment.setArguments(args);
            ((MainActivity) mCtx).replacePopUpFragments(fragment);
        }
        else
        {
            //((MainActivity) mCtx).mViewPager.setVisibility(View.GONE);

            Fragment fragment = null;
            fragment = new ConditionFragment();
            Bundle args = new Bundle();
            args.putInt(Constants.BUNDLE_ITEM_ID, itemID);
            args.putInt(Constants.BUNDLE_CATEGORY_ID, categoryID);
            fragment.setArguments(args);
            ((MainActivity) mCtx).replacePopUpFragments(fragment);
        }
    }
}