package com.plugi.plugi.feature.main;

import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.moos.navigation.BottomBarLayout;
import com.moos.navigation.BottomTabView;
import com.plugi.plugi.R;
import com.plugi.plugi.core.base.BaseJActivity;
import com.plugi.plugi.core.utilities.SectionsPagerAdapter;
import com.plugi.plugi.core.views.CustomTabLayout;
import com.plugi.plugi.core.views.CustomViewPager;
import com.plugi.plugi.feature.search.SearchResultFragment;
import com.plugi.plugi.models.CheckOrderPaymentStatus;
import com.plugi.plugi.models.CustomerAddress;
import com.plugi.plugi.models.CustomerCard;
import com.plugi.plugi.models.FilterIDs;
import com.plugi.plugi.models.GetCurrenciesList;
import com.plugi.plugi.models.GetCustomerClosets;
import com.plugi.plugi.models.GetDiscountDetails;
import com.plugi.plugi.models.ItemDetails;
import com.plugi.plugi.models.MainCategory;
import com.plugi.plugi.models.ReviewItem;
import com.plugi.plugi.models.SortIDs;
import com.plugi.plugi.models.SpecBlogDetails;
import com.plugi.plugi.models.ViewAskDetails;
import com.plugi.plugi.models.ViewBidDetails;
import com.plugi.plugi.models.customerBids;
import com.plugi.plugi.models.customerSells;
import com.plugi.plugi.models.getMyFavouriteItems;
import com.plugi.plugi.models.itemDetails.AllItemSize;
import com.plugi.plugi.models.itemDetails.ExpirationDaysList;
import com.plugi.plugi.models.orderDetails.viewOrderDetails;
import com.plugi.plugi.models.payment.Order;
import com.plugi.plugi.models.profile.CustomerPaymentCard;
import com.plugi.plugi.models.profile.CustomerShippingAddress;
import com.plugi.plugi.retrofit.ApiInterface;
import com.plugi.plugi.retrofit.RetrofitClient;
import com.plugi.plugi.retrofit.SharedPrefManager;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseJActivity  implements GoogleApiClient.OnConnectionFailedListener  {

    private static final String TAG = "MainActivity";
    //BottomNavigation
    public CustomViewPager mViewPager;
    public CustomTabLayout tabLayout;

    public FrameLayout sidemenu , sortingContainer , popupContainer , reviewContainer;
    public boolean isMenuExist = false;

    private MainCategory.Category SelectedCategory;
    private SortIDs.SortListID selectedSort;
    private FilterIDs.BrandsList selectedBrand;
    private FilterIDs.SizeList selectedSize;
    private FilterIDs.SizeTypeList selectedSizeType;
    private FilterIDs.PriceList selectedPrice;
    private FilterIDs.ReleaseYearList selectedReleaseYear;
    private boolean belowRetail = false;

    private List<MainCategory.Category> categoryList = new ArrayList<>();
    private List<SortIDs.SortListID> sortList = new ArrayList<>();
    private List<FilterIDs.BrandsList> brandsList = new ArrayList<>();
    private List<FilterIDs.PriceList> priceList = new ArrayList<>();
    private List<FilterIDs.ReleaseYearList> releaseYearList = new ArrayList<>();
    private List<FilterIDs.SizeList> sizeList = new ArrayList<>();
    private List<FilterIDs.SizeTypeList> sizeTypeList = new ArrayList<>();

    private int lastSelectedTab;

    public int toggleType = 0;

    public String LastSearchTXT;
    LinearLayout viewNoItems;

    public ItemDetails selectItem;
    public ReviewItem selectReviewItem;
    public ExpirationDaysList selectExpiration;
    List<ExpirationDaysList> expirationList = new ArrayList<>();

    public CustomerShippingAddress selectAddress;
    List<CustomerShippingAddress> addressList = new ArrayList<>();

    public CustomerPaymentCard selectPayment;
    List<CustomerPaymentCard> paymentMethodList = new ArrayList<>();

    public AllItemSize selectItemSize;
    List<AllItemSize> itemSizeList = new ArrayList<>();

    private String selectedCurrency;
    private double itemValue;
    private double shippingCost;
    private Order selectedOrder;
    private SpecBlogDetails specBlogDetails;

    public GoogleApiClient googleApiClient;

    public customerBids customerBids;
    public customerSells customerSells;

    public viewOrderDetails viewOrderDetails;
    public ViewBidDetails viewBidDetails;
    public ViewAskDetails viewAskDetails;
    public List<CustomerAddress> customerAddressList;
    public List<CustomerCard> customerCardList;
    public GetDiscountDetails.DiscountDetail selectedDiscount;
    public boolean customerFollowStatus;
    public getMyFavouriteItems.Item selectedFavItem;
    public GetCustomerClosets.ClosetDetail selectedCloset;
    public List<GetCurrenciesList.Item> currecnyList;
    public CheckOrderPaymentStatus lastOrderStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewNoItems = findViewById(R.id.viewNoItems);
        //Toolbar
        /*
        toolbarHome = findViewById(R.id.toolbarHome);
        ivBack = findViewById(R.id.ivBack);
        toolbarLogo = findViewById(R.id.toolbarLogo);
        toolbarLine = findViewById(R.id.toolbarLine);
        toolbarTitle = findViewById(R.id.toolbarTitle);
        ivSide = findViewById(R.id.ivSide);
        ivSubSide = findViewById(R.id.ivSubSide);
        ivSide.setVisibility(View.VISIBLE);
        searchTXT = findViewById(R.id.searchTXT);
        toolbarCenter = findViewById(R.id.toolbarCenter);
        ivSort = findViewById(R.id.ivSort);

         */

        //BottomNavigation
        mViewPager = (CustomViewPager) findViewById(R.id.viewpager_container);
        sidemenu = findViewById(R.id.contentContainer);
        popupContainer = findViewById(R.id.popupContainer);
        sortingContainer = findViewById(R.id.sortingContainer);
        reviewContainer = findViewById(R.id.reviewContainer);



        pojoValues();
        Log.d(TAG, "onResponse: " );
        loadCategory();

        //replaceFragments(SideMenuFragment.class);

        /*
        ivSide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sideMenu();
            }
        });

         */

        /*
        sidemenu.setVisibility(View.VISIBLE);
        MainCategory.Category category = new MainCategory.Category("1", 1, "Test", 0, "");
        Fragment fragment = null;
        //fragment = new HomeFragment();//CategoryFragment.newInstance(category);
        fragment = CategoryFragment.newInstance(category , MainActivity.this);//CategoryFragment.newInstance(category);
        replaceFragments(fragment);

         */
        loadSortData();
        /*
        ivSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sortList.size() > 0 &&
                        categoryList.size() > 0 &&
                        brandsList.size() > 0 &&
                        sizeList.size() > 0 &&
                        sizeTypeList.size() > 0 &&
                        priceList.size() > 0 &&
                        releaseYearList.size() > 0) {
                    //mViewPager.setVisibility(View.GONE);
                    sortingContainer.setVisibility(View.VISIBLE);
                    Fragment fragment = null;
                    fragment = new FilterFragment();
                    replaceSortingFragments(fragment, Constants.BUNDLE_FILTER_EDITED, "searchResult");
                }


            }
        });

         */


    }

    private void pojoValues() {
        /*
        expirationList.add(new Expiration(1 ,1));
        expirationList.add(new Expiration(2 ,3));
        expirationList.add(new Expiration(3 ,7));
        expirationList.add(new Expiration(4 ,14));
        expirationList.add(new Expiration(5 ,30));
        expirationList.add(new Expiration(6 ,60));

        addressList.add(new Address(1 , "Home Address" , "Flat No : 301, Floor : 3, Al Reem Tower , Mirqab" , "Kuwait City Main Road ,KuwaitCity"));
        addressList.add(new Address(2 , "Work Address" , "Flat No : 301, Floor : 3, Al Reem Tower , Mirqab" , "Kuwait City Main Road ,KuwaitCity"));
        paymentMethodList.add(new PaymentMethod(1 ,
                "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2a/Mastercard-logo.svg/1280px-Mastercard-logo.svg.png",
                "Master Card",
                "1321 0574 2657 0276",
                "12 june 2027"));
        paymentMethodList.add(new PaymentMethod(2 ,
                "https://cdn.iconscout.com/icon/free/png-512/visa-3-226460.png",
                "Visa Card",
                "1321 0574 2657 0276",
                "12 june 2027"));

         */
    }

    public void sideMenu() {
        if(!isMenuExist) {
            sidemenu.setVisibility(View.VISIBLE);
            Fragment fragment = null;
            fragment = new SideMenuFragment();
            replaceFragments(fragment);
            isMenuExist = true;
        }
        else
        {
            sidemenu.setVisibility(View.GONE);
            isMenuExist = false;


        }
    }
    public void forceHiddenSideMenu()
    {
        if(sidemenu.getVisibility() == View.VISIBLE) {
            sidemenu.setVisibility(View.GONE);
            isMenuExist = false;
        }
    }
    public void forceHiddenSorting(boolean setResult)
    {
        if(sortingContainer.getVisibility() == View.VISIBLE) {
            isMenuExist = false;
            sortingContainer.setVisibility(View.GONE);
            Log.d(TAG, "onBackPressed: ");
            if(setResult) {
                try {
                    SearchFragment firstFragment = (SearchFragment) getSupportFragmentManager().getFragments().get(1);
                    firstFragment.setFilterResult();
                }
                catch (Exception e)
                {
                    Log.d(TAG, "forceHiddenSorting: " + e.getLocalizedMessage());
                }
            }
        }
    }
    public void forceHiddenSearchSorting(boolean setResult)
    {
        if(sortingContainer.getVisibility() == View.VISIBLE) {
            isMenuExist = false;
            sortingContainer.setVisibility(View.GONE);
            Log.d(TAG, "onBackPressed: ");
            if(setResult) {
                try {
                    SearchResultFragment firstFragment = (SearchResultFragment) getSupportFragmentManager().getFragments().get(1);
                    firstFragment.setFilterResult(getLastSearchTXT());
                }
                catch (Exception e)
                {
                    Log.d(TAG, "forceHiddenSorting: " + e.getLocalizedMessage());
                }
            }
        }
    }
    public void forceHiddenPopUp(boolean setResult)
    {
        if(popupContainer.getVisibility() == View.VISIBLE) {
            popupContainer.setVisibility(View.GONE);
            isMenuExist = false;
            Log.d(TAG, "onBackPressed: ");
            if(setResult) {
                SearchFragment firstFragment = (SearchFragment) getSupportFragmentManager().getFragments().get(1);
                firstFragment.setFilterResult();
            }
        }
        else if(sidemenu.getVisibility() == View.VISIBLE) {
            sidemenu.setVisibility(View.GONE);
            isMenuExist = false;
        }
        else
        {
            sidemenu.setVisibility(View.GONE);
            popupContainer.setVisibility(View.GONE);
            reviewContainer.setVisibility(View.GONE);
        }
    }

    public void Listing(MainCategory.Category selectedCategory)
    {
        SearchFragment firstFragment = (SearchFragment) getSupportFragmentManager().getFragments().get(1);
        firstFragment.setFilterResult();
    }
    public void replaceFragments(Fragment fragmentClass) {

        //popupContainer.setVisibility(View.GONE);
        //ivSort.setVisibility(View.GONE);

            Fragment fragment = null;
            try {
                fragment = (Fragment) fragmentClass;
            } catch (Exception e) {
                e.printStackTrace();
            }
            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction().replace(R.id.contentContainer, fragment)
                    .addToBackStack(null)
                    .setCustomAnimations( R.anim.slide_in_left,
                            R.anim.slide_out_right,
                            R.anim.slide_in_left,
                            R.anim.slide_out_right)
                    .commit();

    }

    public void replaceFragments(Fragment fragmentClass , String Key , String Value) {
        //ivSort.setVisibility(View.GONE);

        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass;
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        Bundle args = new Bundle();
        args.putString(Key, Value);
        fragment.setArguments(args);
        fragmentManager.beginTransaction().replace(R.id.contentContainer, fragment)
                .addToBackStack(null)
                .setCustomAnimations( R.anim.slide_in_left,
                        R.anim.slide_out_right,
                        R.anim.slide_in_left,
                        R.anim.slide_out_right)
                .commit();

    }
    public void replaceSortingFragments(Fragment fragmentClass) {
        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass;
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.sortingContainer, fragment)
                .addToBackStack(null)
                .setCustomAnimations( R.anim.slide_in_left,
                        R.anim.slide_out_right,
                        R.anim.slide_in_left,
                        R.anim.slide_out_right)
                .commit();

    }
    public void replaceSortingFragments(Fragment fragmentClass , String Key , String Value) {

        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass;
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        Bundle args = new Bundle();
        args.putString(Key, Value);
        fragment.setArguments(args);
        fragmentManager.beginTransaction().replace(R.id.sortingContainer, fragment)
                .addToBackStack(null)
                .setCustomAnimations( R.anim.slide_in_left,
                        R.anim.slide_out_right,
                        R.anim.slide_in_left,
                        R.anim.slide_out_right)
                .commit();

    }
    public void replaceSortingFragments(Fragment fragmentClass , String Key , int Value) {

        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass;
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        Bundle args = new Bundle();
        args.putInt(Key, Value);
        fragment.setArguments(args);
        fragmentManager.beginTransaction().replace(R.id.sortingContainer, fragment)
                .addToBackStack(null)
                .setCustomAnimations( R.anim.slide_in_left,
                        R.anim.slide_out_right,
                        R.anim.slide_in_left,
                        R.anim.slide_out_right)
                .commit();

    }
    public void replaceSortingFragments(Fragment fragmentClass , String Key , Object Value) {

        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass;
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        Bundle args = new Bundle();
        args.putSerializable(Key, (Serializable)Value);
        fragment.setArguments(args);
        fragmentManager.beginTransaction().replace(R.id.sortingContainer, fragment)
                .addToBackStack(null)
                .setCustomAnimations( R.anim.slide_in_left,
                        R.anim.slide_out_right,
                        R.anim.slide_in_left,
                        R.anim.slide_out_right)
                .commit();

    }
    public void replaceSortingFragments(Fragment fragmentClass , String Key , Object Value , String Key2 , int Value2) {

        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass;
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        Bundle args = new Bundle();
        args.putSerializable(Key, (Serializable)Value);
        args.putInt(Key2, Value2);
        fragment.setArguments(args);
        fragmentManager.beginTransaction().replace(R.id.sortingContainer, fragment)
                .addToBackStack(null)
                .setCustomAnimations( R.anim.slide_in_left,
                        R.anim.slide_out_right,
                        R.anim.slide_in_left,
                        R.anim.slide_out_right)
                .commit();

    }
    public void replacePopUpFragments(Fragment fragmentClass) {
        //sidemenu.setVisibility(View.GONE);
        //ivSort.setVisibility(View.GONE);
        popupContainer.setVisibility(View.VISIBLE);
        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass;
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction().replace(R.id.popupContainer, fragment)
                .addToBackStack(null)
                .setCustomAnimations( R.anim.slide_in_left,
                        R.anim.slide_out_right,
                        R.anim.slide_in_left,
                        R.anim.slide_out_right)
                .commit();

    }

    public void replacePopUpFragments(Fragment fragmentClass , String Key , String Value) {
        //sidemenu.setVisibility(View.GONE);
        //ivSort.setVisibility(View.GONE);
        popupContainer.setVisibility(View.VISIBLE);
        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass;
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        Bundle args = new Bundle();
        args.putString(Key, Value);
        fragment.setArguments(args);
        fragmentManager.beginTransaction().replace(R.id.popupContainer, fragment)
                .addToBackStack(null)
                .setCustomAnimations( R.anim.slide_in_left,
                        R.anim.slide_out_right,
                        R.anim.slide_in_left,
                        R.anim.slide_out_right)
                .commit();

    }
    public void replacePopUpFragments(Fragment fragmentClass , String Key , Object Value) {
        //sidemenu.setVisibility(View.GONE);
        //ivSort.setVisibility(View.GONE);
        popupContainer.setVisibility(View.VISIBLE);
        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass;
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        Bundle args = new Bundle();
        args.putSerializable(Key, (Serializable)Value);
        fragment.setArguments(args);
        fragmentManager.beginTransaction().replace(R.id.popupContainer, fragment)
                .addToBackStack(null)
                .setCustomAnimations( R.anim.slide_in_left,
                        R.anim.slide_out_right,
                        R.anim.slide_in_left,
                        R.anim.slide_out_right)
                .commit();

    }
    public void replaceReviewFragments(Fragment fragmentClass ) {
        //sidemenu.setVisibility(View.GONE);
        //ivSort.setVisibility(View.GONE);
        popupContainer.setVisibility(View.GONE);
        reviewContainer.setVisibility(View.VISIBLE);
        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass;
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager =getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.reviewContainer, fragment)
                .addToBackStack(null)
                .setCustomAnimations( R.anim.slide_in_left,
                        R.anim.slide_out_right,
                        R.anim.slide_in_left,
                        R.anim.slide_out_right)
                .commit();

    }
    public void replaceReviewFragments(Fragment fragmentClass , String Key , Object Value) {
        //sidemenu.setVisibility(View.GONE);
        //ivSort.setVisibility(View.GONE);
        popupContainer.setVisibility(View.GONE);
        reviewContainer.setVisibility(View.VISIBLE);
        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass;
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager =getSupportFragmentManager();
        Bundle args = new Bundle();
        args.putSerializable(Key, (Serializable)Value);
        fragment.setArguments(args);
        fragmentManager.beginTransaction().replace(R.id.reviewContainer, fragment)
                .addToBackStack(null)
                .setCustomAnimations( R.anim.slide_in_left,
                        R.anim.slide_out_right,
                        R.anim.slide_in_left,
                        R.anim.slide_out_right)
                .commit();

    }
    private void setupViewPager(){
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment()); //index 0
        adapter.addFragment(new SearchFragment()); //index 1
        adapter.addFragment(new ProfileFragment()); //index 2



        tabLayout = (CustomTabLayout) findViewById(R.id.tabs);

        tabLayout.bindViewPager(mViewPager);
        BottomTabView tab_home = new BottomTabView(this);
        tab_home.setBackgroundColor(getResources().getColor(R.color.black));
        tab_home.setSelectedColor(getResources().getColor(R.color.white));
        tab_home.setTabIcon(R.drawable.ic_nav_home);
        tab_home.setTabTitle(getResources().getString(R.string.nav_home));

        BottomTabView tab_look = new BottomTabView(this);
        tab_look.setBackgroundColor(getResources().getColor(R.color.black));
        tab_look.setSelectedColor(getResources().getColor(R.color.white));
        tab_look.setTabIcon(R.drawable.ic_nav_search);
        tab_look.setTabTitle(getResources().getString(R.string.nav_search));

        BottomTabView tab_mine = new BottomTabView(this);
        tab_mine.setBackgroundColor(getResources().getColor(R.color.black));
        tab_mine.setSelectedColor(getResources().getColor(R.color.white));
        tab_mine.setTabIcon(R.drawable.ic_nav_account);
        tab_mine.setTabTitle(getResources().getString(R.string.nav_account));
        tab_mine.setUnreadCount(4);

        tabLayout
                .addTab(tab_home)
                .addTab(tab_look)
                .addTab(tab_mine)
                .create(new BottomBarLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(BottomTabView tab) {
                        //you can switch the fragment here
                        Log.e(TAG, "onTabSelected: =====" + tab.getTabPosition());
                        forceHiddenPopUp(false);
                        reviewContainer.setVisibility(View.GONE);
                        /*
                        setLastSelectedTab(tab.getTabPosition());

                        if (tab.getTabPosition() == 1) {
                            forceHiddenSorting(false);
                            ivSide.setVisibility(View.GONE);
                            ivSort.setVisibility(View.VISIBLE);
                        } else {
                            forceHiddenPopUp(false);
                            ivSide.setVisibility(View.VISIBLE);
                            ivSort.setVisibility(View.GONE);
                        }

                         */

                    }

                    @Override
                    public void onTabUnselected(BottomTabView tab) {
                        forceHiddenPopUp(false);
                        reviewContainer.setVisibility(View.GONE);
                        /*
                        setLastSelectedTab(tab.getTabPosition());

                        if (tab.getTabPosition() == 1) {
                            forceHiddenSorting(false);
                            ivSide.setVisibility(View.GONE);
                            ivSort.setVisibility(View.VISIBLE);
                        } else {
                            forceHiddenPopUp(false);
                            ivSide.setVisibility(View.VISIBLE);
                            ivSort.setVisibility(View.GONE);
                        }

                         */

                    }

                    @Override
                    public void onTabReselected(BottomTabView tab) {
                        forceHiddenPopUp(false);
                        reviewContainer.setVisibility(View.GONE);
                        /*
                        setLastSelectedTab(tab.getTabPosition());
                        if (tab.getTabPosition() == 1) {
                            forceHiddenSorting(false);
                            ivSide.setVisibility(View.GONE);
                            ivSort.setVisibility(View.VISIBLE);
                        } else {
                            forceHiddenPopUp(false);
                            ivSide.setVisibility(View.VISIBLE);
                            ivSort.setVisibility(View.GONE);
                        }

                         */

                    }
                });
        mViewPager.setPagingEnabled(false);
        mViewPager.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {

        /*
        Fragment fragmentContainer = getSupportFragmentManager().findFragmentById(R.id.contentContainer);
        if (!(fragmentContainer instanceof IOnBackPressed) || !((IOnBackPressed) fragmentContainer).onBackPressed()) {
            super.onBackPressed();
        }

         */
    // Catch back action and pops from backstack
        // (if you called previously to addToBackStack() in your transaction)

        if (sidemenu.getVisibility() == View.VISIBLE && popupContainer.getVisibility() != View.VISIBLE){

/*
            ivBack.setVisibility(View.GONE);
            toolbarTitle.setVisibility(View.GONE);
            forceHiddenSideMenu();

 */





        }
        else if (sidemenu.getVisibility() == View.VISIBLE && popupContainer.getVisibility() == View.VISIBLE){
            //forceHiddenSideMenu();

        }
        else if(sortingContainer.getVisibility() == View.VISIBLE){
            /*
            ivBack.setVisibility(View.GONE);
            toolbarTitle.setVisibility(View.GONE);
            forceHiddenSorting(false);

             */




        }
        else if(popupContainer.getVisibility() == View.VISIBLE){

            /*
            ivBack.setVisibility(View.GONE);
            toolbarTitle.setVisibility(View.GONE);
            forceHiddenPopUp(false);

             */






        }
        else
        {
            attemptToExitIfRoot(null);
        }

    }


    public void loadSortData()
    {
        ApiInterface service = RetrofitClient.retrofitWrite().create(ApiInterface.class);

        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("language_ID", 1);
        RequestBody bodyToken = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
        //defining the call
        Call<SortIDs> call = service.GetSearchSortIDs(
                bodyToken
        );
        call.enqueue(new Callback<SortIDs>() {
            @Override
            public void onResponse(Call<SortIDs> call, Response<SortIDs> response) {

                if(response.body().getStatusMessage().equals("Success"))
                {
                    setSortList(response.body().getSortListIDs());
                }
            }

            @Override
            public void onFailure(Call<SortIDs> call, Throwable t) {

            }
        });
    }
    public void loadFilterData(int categoryID)
    {
        ApiInterface service = RetrofitClient.retrofitWrite().create(ApiInterface.class);

        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("CategoryID", categoryID);
        jsonParams.put("language_ID", 1);
        RequestBody bodyToken = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
        //defining the call
        Call<FilterIDs> call = service.GetSearchFilterIDs(
                bodyToken
        );
        call.enqueue(new Callback<FilterIDs>() {
            @Override
            public void onResponse(Call<FilterIDs> call, Response<FilterIDs> response) {

                if(response.body().getStatusMessage().equals("Success"))
                {
                    setBrandsList(response.body().getBrandsList());
                    setPriceList(response.body().getPriceList());
                    setSizeList(response.body().getSizeList());
                    setSizeTypeList(response.body().getSizeTypeList());
                    setReleaseYearList(response.body().getReleaseYearList());
                }
            }

            @Override
            public void onFailure(Call<FilterIDs> call, Throwable t) {

            }
        });
    }
    private void loadCategory() {
        ApiInterface service = RetrofitClient.retrofitWrite().create(ApiInterface.class);

        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("language_ID", 1);
        RequestBody bodyToken = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
        //defining the call
        Call<MainCategory> call = service.GetMainCategories(
                bodyToken
        );
        call.enqueue(new Callback<MainCategory>() {
            @Override
            public void onResponse(Call<MainCategory> call, Response<MainCategory> response) {

                if(response.body().getStatusMessage().equals("Success"))
                {
                    Log.d(TAG, "onResponse: " + response.body().getStatusMessage());
                    setCategoryList(response.body().getCategories());
                    loadCurrencyData();
                    setupViewPager();
                    //SharedPrefManager.getInstance(getActivity()).CategoryList(response.body());

                }
                else
                {
                    setupViewPager();
                    Log.d(TAG, "onResponse: " + response.body().getStatusMessage());
                }
            }

            @Override
            public void onFailure(Call<MainCategory> call, Throwable t) {
                setupViewPager();
                Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
            }
        });
    }

    public void loadCurrencyData()
    {

            ApiInterface service = RetrofitClient.retrofitWrite().create(ApiInterface.class);

            Map<String, Object> jsonParams = new ArrayMap<>();
            jsonParams.put("language_ID", 1);
            RequestBody bodyToken = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
            //defining the call
            Call<GetCurrenciesList> call = service.GetCurrenciesList(
                    bodyToken
            );
            call.enqueue(new Callback<GetCurrenciesList>() {
                @Override
                public void onResponse(Call<GetCurrenciesList> call, Response<GetCurrenciesList> response) {

                    if (response.body().getStatusMessage().equals("Success")) {

                        setCurrecnyList(response.body().getItems());
                        if(!SharedPrefManager.getInstance(getApplicationContext()).isCurrencySelected())
                        {
                            SharedPrefManager.getInstance(getApplicationContext()).setCurrentCurrency(response.body().getItems().get(0));
                        }

                    } else {

                        // viewNoItems.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<GetCurrenciesList> call, Throwable t) {
                    Log.d("CUR", "onFailure: " + t.getMessage());
                    Log.d("CUR", "onFailure: " + t.getLocalizedMessage());

                }
            });
    }
    public List<MainCategory.Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<MainCategory.Category> categoryList) {
        this.categoryList = categoryList;
    }



    public int getLastSelectedTab() {
        return lastSelectedTab;
    }

    public void setLastSelectedTab(int lastSelectedTab) {
        this.lastSelectedTab = lastSelectedTab;
    }

    public MainCategory.Category getSelectedCategory() {
        return SelectedCategory;
    }

    public void setSelectedCategory(MainCategory.Category selectedCategory) {
        SelectedCategory = selectedCategory;
        setSelectedBrand(null);
        setSelectedSize(null);
        setSelectedSizeType(null);
        setSelectedPrice(null);
        setSelectedReleaseYear(null);
        loadFilterData(selectedCategory.getCategoryID());
    }

    public SortIDs.SortListID getSelectedSort() {
        return selectedSort;
    }

    public void setSelectedSort(SortIDs.SortListID selectedSort) {
        this.selectedSort = selectedSort;
    }

    public List<SortIDs.SortListID> getSortList() {
        return sortList;
    }

    public void setSortList(List<SortIDs.SortListID> sortList) {
        this.sortList = sortList;
    }

    public List<FilterIDs.BrandsList> getBrandsList() {
        return brandsList;
    }

    public void setBrandsList(List<FilterIDs.BrandsList> brandsList) {
        this.brandsList = brandsList;
    }

    public List<FilterIDs.PriceList> getPriceList() {
        return priceList;
    }

    public void setPriceList(List<FilterIDs.PriceList> priceList) {
        this.priceList = priceList;
    }

    public List<FilterIDs.ReleaseYearList> getReleaseYearList() {
        return releaseYearList;
    }

    public void setReleaseYearList(List<FilterIDs.ReleaseYearList> releaseYearList) {
        this.releaseYearList = releaseYearList;
    }

    public List<FilterIDs.SizeList> getSizeList() {
        return sizeList;
    }

    public void setSizeList(List<FilterIDs.SizeList> sizeList) {
        this.sizeList = sizeList;
    }

    public List<FilterIDs.SizeTypeList> getSizeTypeList() {
        return sizeTypeList;
    }

    public void setSizeTypeList(List<FilterIDs.SizeTypeList> sizeTypeList) {
        this.sizeTypeList = sizeTypeList;
    }

    public FilterIDs.BrandsList getSelectedBrand() {
        return selectedBrand;
    }

    public void setSelectedBrand(FilterIDs.BrandsList selectedBrand) {
        this.selectedBrand = selectedBrand;
    }

    public FilterIDs.SizeList getSelectedSize() {
        return selectedSize;
    }

    public void setSelectedSize(FilterIDs.SizeList selectedSize) {
        this.selectedSize = selectedSize;
    }

    public FilterIDs.SizeTypeList getSelectedSizeType() {
        return selectedSizeType;
    }

    public void setSelectedSizeType(FilterIDs.SizeTypeList selectedSizeType) {
        this.selectedSizeType = selectedSizeType;
    }

    public FilterIDs.PriceList getSelectedPrice() {
        return selectedPrice;
    }

    public void setSelectedPrice(FilterIDs.PriceList selectedPrice) {
        this.selectedPrice = selectedPrice;
    }

    public FilterIDs.ReleaseYearList getSelectedReleaseYear() {
        return selectedReleaseYear;
    }

    public void setSelectedReleaseYear(FilterIDs.ReleaseYearList selectedReleaseYear) {
        this.selectedReleaseYear = selectedReleaseYear;
    }

    public boolean isBelowRetail() {
        return belowRetail;
    }

    public void setBelowRetail(boolean belowRetail) {
        this.belowRetail = belowRetail;
    }

    public int getToggleType() {
        return toggleType;
    }

    public void setToggleType(int toggleType) {
        this.toggleType = toggleType;
    }

    public String getLastSearchTXT() {
        return LastSearchTXT;
    }

    public void setLastSearchTXT(String lastSearchTXT) {
        LastSearchTXT = lastSearchTXT;
    }

    public ItemDetails getSelectItem() {
        return selectItem;
    }

    public void setSelectItem(ItemDetails selectItem) {
        this.selectItem = selectItem;
    }

    public ExpirationDaysList getSelectExpiration() {
        return selectExpiration;
    }

    public void setSelectExpiration(ExpirationDaysList selectExpiration) {
        this.selectExpiration = selectExpiration;
    }

    public List<ExpirationDaysList> getExpirationList() {
        return expirationList;
    }

    public void setExpirationList(List<ExpirationDaysList> expirationList) {
        this.expirationList = expirationList;
    }

    public CustomerShippingAddress getSelectAddress() {
        return selectAddress;
    }

    public void setSelectAddress(CustomerShippingAddress selectAddress) {
        this.selectAddress = selectAddress;
    }

    public List<CustomerShippingAddress> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<CustomerShippingAddress> addressList) {
        this.addressList = addressList;
    }

    public CustomerPaymentCard getSelectPayment() {
        return selectPayment;
    }

    public void setSelectPayment(CustomerPaymentCard selectPayment) {
        this.selectPayment = selectPayment;
    }

    public List<CustomerPaymentCard> getPaymentMethodList() {
        return paymentMethodList;
    }

    public void setPaymentMethodList(List<CustomerPaymentCard> paymentMethodList) {
        this.paymentMethodList = paymentMethodList;
    }

    public AllItemSize getSelectItemSize() {
        return selectItemSize;
    }

    public void setSelectItemSize(AllItemSize selectItemSize) {
        this.selectItemSize = selectItemSize;
    }

    public List<AllItemSize> getItemSizeList() {
        return itemSizeList;
    }

    public void setItemSizeList(List<AllItemSize> itemSizeList) {
        this.itemSizeList = itemSizeList;
    }

    public ReviewItem getSelectReviewItem() {
        return selectReviewItem;
    }

    public void setSelectReviewItem(ReviewItem selectReviewItem) {
        this.selectReviewItem = selectReviewItem;
    }

    public String getSelectedCurrency() {
        return selectedCurrency;
    }

    public void setSelectedCurrency(String selectedCurrency) {
        this.selectedCurrency = selectedCurrency;
    }

    public double getItemValue() {
        return itemValue;
    }

    public void setItemValue(double itemValue) {
        this.itemValue = itemValue;
    }

    public double getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(double shippingCost) {
        this.shippingCost = shippingCost;
    }

    public Order getSelectedOrder() {
        return selectedOrder;
    }

    public void setSelectedOrder(Order selectedOrder) {
        this.selectedOrder = selectedOrder;
    }

    public SpecBlogDetails getSpecBlogDetails() {
        return specBlogDetails;
    }

    public void setSpecBlogDetails(SpecBlogDetails specBlogDetails) {
        this.specBlogDetails = specBlogDetails;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onStart() {
        GoogleSignInOptions gso = new  GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API , gso).build();
        super.onStart();
    }

    public com.plugi.plugi.models.customerBids getCustomerBids() {
        return customerBids;
    }

    public void setCustomerBids(com.plugi.plugi.models.customerBids customerBids) {
        this.customerBids = customerBids;
    }

    public com.plugi.plugi.models.customerSells getCustomerSells() {
        return customerSells;
    }

    public void setCustomerSells(com.plugi.plugi.models.customerSells customerSells) {
        this.customerSells = customerSells;
    }

    public com.plugi.plugi.models.orderDetails.viewOrderDetails getViewOrderDetails() {
        return viewOrderDetails;
    }

    public void setViewOrderDetails(com.plugi.plugi.models.orderDetails.viewOrderDetails viewOrderDetails) {
        this.viewOrderDetails = viewOrderDetails;
    }

    public List<CustomerAddress> getCustomerAddressList() {
        return customerAddressList;
    }

    public void setCustomerAddressList(List<CustomerAddress> customerAddressList) {
        this.customerAddressList = customerAddressList;
    }

    public List<CustomerCard> getCustomerCardList() {
        return customerCardList;
    }

    public void setCustomerCardList(List<CustomerCard> customerCardList) {
        this.customerCardList = customerCardList;
    }

    public ViewBidDetails getViewBidDetails() {
        return viewBidDetails;
    }

    public void setViewBidDetails(ViewBidDetails viewBidDetails) {
        this.viewBidDetails = viewBidDetails;
    }

    public ViewAskDetails getViewAskDetails() {
        return viewAskDetails;
    }

    public void setViewAskDetails(ViewAskDetails viewAskDetails) {
        this.viewAskDetails = viewAskDetails;
    }

    public GetDiscountDetails.DiscountDetail getSelectedDiscount() {
        return selectedDiscount;
    }

    public void setSelectedDiscount(GetDiscountDetails.DiscountDetail selectedDiscount) {
        this.selectedDiscount = selectedDiscount;
    }

    public boolean isCustomerFollowStatus() {
        return customerFollowStatus;
    }

    public void setCustomerFollowStatus(boolean customerFollowStatus) {
        this.customerFollowStatus = customerFollowStatus;
    }

    public getMyFavouriteItems.Item getSelectedFavItem() {
        return selectedFavItem;
    }

    public void setSelectedFavItem(getMyFavouriteItems.Item selectedFavItem) {
        this.selectedFavItem = selectedFavItem;
    }

    public GetCustomerClosets.ClosetDetail getSelectedCloset() {
        return selectedCloset;
    }


    public void setSelectedCloset(GetCustomerClosets.ClosetDetail selectedCloset) {

        this.selectedCloset = selectedCloset;
    }

    public List<GetCurrenciesList.Item> getCurrecnyList() {
        return currecnyList;
    }

    public void setCurrecnyList(List<GetCurrenciesList.Item> currecnyList) {
        this.currecnyList = currecnyList;
    }

    public CheckOrderPaymentStatus getLastOrderStatus() {
        return lastOrderStatus;
    }

    public void setLastOrderStatus(CheckOrderPaymentStatus lastOrderStatus) {
        this.lastOrderStatus = lastOrderStatus;
    }
}