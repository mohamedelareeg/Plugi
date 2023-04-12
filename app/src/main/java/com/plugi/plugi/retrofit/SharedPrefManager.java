package com.plugi.plugi.retrofit;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.plugi.plugi.models.GetCurrenciesList;
import com.plugi.plugi.models.MainCategory;
import com.plugi.plugi.models.User;


public class SharedPrefManager {

    private static SharedPrefManager mInstance;
    private static Context mCtx;
    // Shared Preferences
    SharedPreferences pref;
    private static final String SHARED_PREF_NAME = "plugicustomer";

    private static final String KEY_SEARCH_HISTORY= "keysearchhistory";
    private static final String KEY_LAST_SEARCH = "lastsearch";
    private static final String key_CATEGORY_LIST = "keycategorylist";
    private static final String KEY_CATEGORY_TAG = "categorytag_";
    private static final String KEY_GUEST_VISIT = "guestvisit";
    private static final String KEY_GUEST_ONBOARDING = "guestonboarding";
    private static final String KEY_GUEST_CONDITION = "guestcondition";
    private static final String KEY_USER_LIST = "keyuserlist";

    private static final String KEY_USER_ID = "keyuserid";
    private static final String KEY_USER_NAME = "keyusername";
    private static final String KEY_USER_EMAIL = "keyuseremail";
    private static final String KEY_USER_THUMB = "keyuserthumb";
    private static final String KEY_USER_GENDER = "keyusergender";
    private static final String KEY_USER_GCM = "keyusergcm";

    private static final String KEY_CURRENT_CURRENCY = "keycurrentcurrency";

    public SharedPrefManager(Context context) {
        mCtx = context;
        pref = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public boolean setCurrentCurrency(GetCurrenciesList.Item currency) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(currency);
        editor.putString(KEY_CURRENT_CURRENCY, json);
        editor.apply();
        return true;
    }

    public GetCurrenciesList.Item getCurrentCurrency() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(KEY_CURRENT_CURRENCY, null );
        GetCurrenciesList.Item currentCurrency = gson.fromJson(json, GetCurrenciesList.Item.class);
        return currentCurrency;
    }
    public boolean isCurrencySelected() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(KEY_CURRENT_CURRENCY, null) != null)
            return true;
        return false;
    }
    public boolean userToken(String token) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER_GCM, token);
        editor.apply();
        return true;
    }


    public boolean CategoryTAG(MainCategory.Category category , String tag) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_CATEGORY_TAG + category.getName() , tag);
        editor.apply();
        return true;
    }

    public String geCategoryTAG(MainCategory.Category category) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_CATEGORY_TAG + category.getName(), null);
    }
    public boolean LastSearch(String lastSearch) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_LAST_SEARCH  , lastSearch);
        editor.apply();
        return true;
    }
    public boolean GuestVisit( ) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(KEY_GUEST_VISIT  , 1);
        editor.apply();
        return true;
    }

    public boolean GuestOnBoarding( ) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(KEY_GUEST_ONBOARDING  , 1);
        editor.apply();
        return true;
    }
    public boolean GuestCondition( ) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(KEY_GUEST_CONDITION  , 1);
        editor.apply();
        return true;
    }

    public boolean CategoryList(MainCategory category ) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(category);
        editor.putString(key_CATEGORY_LIST  , json);
        editor.apply();
        return true;
    }
    public boolean UserLogin(User user ) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString(KEY_USER_LIST  , json);
        editor.apply();
        return true;
    }
    public boolean isCategoryLoaded() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(key_CATEGORY_LIST , null );
        MainCategory category = gson.fromJson(json, MainCategory.class);
        if (category != null)
            return true;
        return false;
    }
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(KEY_USER_LIST , null );
        User customer = gson.fromJson(json, User.class);
        if (customer != null)
            return true;
        return false;
    }


    public MainCategory getCategory() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(key_CATEGORY_LIST , null );
        MainCategory category = gson.fromJson(json, MainCategory.class);
        return category;
    }
    public User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(KEY_USER_LIST , null );
        User customer = gson.fromJson(json, User.class);
        return customer;
    }


    public String getLastSearch() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_LAST_SEARCH , null);
    }
    public int getGuestVisit() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_GUEST_VISIT, 0);
    }

    public int getGuestOnBoarding() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_GUEST_ONBOARDING, 0);
    }

    public int getGuestCondition() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_GUEST_CONDITION, 0);
    }
    //this method will fetch the device token from shared preferences
    public String getDeviceToken(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getString(KEY_USER_GCM, null);
    }
    public boolean logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }
}
