package com.plugi.plugi.retrofit;

import com.plugi.plugi.models.About;
import com.plugi.plugi.models.AddCustomerCloset;
import com.plugi.plugi.models.AutoComplete;
import com.plugi.plugi.models.Blogs;
import com.plugi.plugi.models.CategoryItems;
import com.plugi.plugi.models.CheckOrderPaymentStatus;
import com.plugi.plugi.models.Condition;
import com.plugi.plugi.models.Contact;
import com.plugi.plugi.models.DeleteCustomerCloset;
import com.plugi.plugi.models.Faqs;
import com.plugi.plugi.models.FilterIDs;
import com.plugi.plugi.models.ForgetPassword;
import com.plugi.plugi.models.GetCurrenciesList;
import com.plugi.plugi.models.GetCustomerClosets;
import com.plugi.plugi.models.GetCustomerWallet;
import com.plugi.plugi.models.GetDiscountDetails;
import com.plugi.plugi.models.GetNotificationsetting;
import com.plugi.plugi.models.HomeRibbon;
import com.plugi.plugi.models.HomeScreen;
import com.plugi.plugi.models.HowItWork;
import com.plugi.plugi.models.ItemDetails;
import com.plugi.plugi.models.MainCategory;
import com.plugi.plugi.models.Privacy;
import com.plugi.plugi.models.ResetPassword;
import com.plugi.plugi.models.ReviewItem;
import com.plugi.plugi.models.SaveNotificationsetting;
import com.plugi.plugi.models.SearchHistory;
import com.plugi.plugi.models.SearchItems;
import com.plugi.plugi.models.SlidingBanner;
import com.plugi.plugi.models.SocialMedia;
import com.plugi.plugi.models.SortIDs;
import com.plugi.plugi.models.SpecBlogDetails;
import com.plugi.plugi.models.Terms;
import com.plugi.plugi.models.UpdateBidExpire;
import com.plugi.plugi.models.UpdateCustomerCloset;
import com.plugi.plugi.models.User;
import com.plugi.plugi.models.ViewAskDetails;
import com.plugi.plugi.models.ViewBidDetails;
import com.plugi.plugi.models.customerBids;
import com.plugi.plugi.models.customerSells;
import com.plugi.plugi.models.getCustomerAddress;
import com.plugi.plugi.models.getCustomerCards;
import com.plugi.plugi.models.getMyFavouriteItems;
import com.plugi.plugi.models.orderDetails.viewOrderDetails;
import com.plugi.plugi.models.payment.SellPayment;
import com.plugi.plugi.models.payment.bidPayment;
import com.plugi.plugi.models.payment.itemAskPayment;
import com.plugi.plugi.models.payment.itemBidPayment;
import com.plugi.plugi.retrofit.response.AddToFav;
import com.plugi.plugi.retrofit.response.RemoveFromFav;
import com.plugi.plugi.retrofit.response.UserResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {



    @POST("Register")
    Call<UserResponse> createUserBody(
            @Body RequestBody params
    );
    @POST("GetLoginToken")
    Call<UserResponse> GetLoginToken(
            @Body RequestBody params
    );
    @POST("GetLoginToken")
    Call<UserResponse> GetLoginTokenEmpty(
            @Body RequestBody params
    );

    @POST("Login")
    Call<User> Login(
            @Body RequestBody params
    );

    @POST("AboutPlugi")
    Call<About> AboutPlugi(
            @Body RequestBody params
    );
    @POST("HowitsWork_Page")
    Call<HowItWork> HowitsWork_Page(
            @Body RequestBody params
    );
    @POST("PrivacyPolicy")
    Call<Privacy> PrivacyPolicy(
            @Body RequestBody params
    );

    @POST("TermsConditions")
    Call<Terms> TermsConditions(
            @Body RequestBody params
    );
    @POST("AgreeToConditionGuideline")
    Call<Condition> AgreeToConditionGuideline(
            @Body RequestBody params
    );

    @POST("Contactus")
    Call<Contact> Contactus(
            @Body RequestBody params
    );

    @POST("GetHomeSlidingbanner")
    Call<SlidingBanner> GetHomeSlidingbanner(
            @Body RequestBody params
    );

    @POST("GetMainCategories")
    Call<MainCategory> GetMainCategories(
            @Body RequestBody params
    );

    @POST("GetAllSocialMediaLinks")
    Call<SocialMedia> GetAllSocialMediaLinks(
            @Body RequestBody params
    );

    @POST("GetitemsMostPoPular")
    Call<CategoryItems> GetitemsMostPoPular(
            @Body RequestBody params
    );
    @POST("GetHomeScreen")
    Call<HomeScreen> GetHomeScreen(
            @Body RequestBody params
    );
    @POST("GetHomeMarquee")
    Call<HomeRibbon> GetHomeMarquee(
            @Body RequestBody params
    );
    @POST("SearchInItems")
    Call<SearchItems> SearchInItems(
            @Body RequestBody params
    );

    @POST("GetItemDetailbyID")
    Call<ItemDetails> GetItemDetailbyID(
            @Body RequestBody params
    );

    @POST("SearchAutoComplate")
    Call<AutoComplete> SearchAutoComplate(
            @Body RequestBody params
    );

    @POST("SearchAutoComplate")
    Call<SearchHistory> SearchHistory(
            @Body RequestBody params
    );

    @POST("ForgetPassword")
    Call<ForgetPassword> ForgetPassword(
            @Body RequestBody params
    );
    @POST("ResetPassword")
    Call<ResetPassword> ResetPassword(
            @Body RequestBody params
    );

    @POST("GetSearchSortIDs")
    Call<SortIDs> GetSearchSortIDs(
            @Body RequestBody params
    );
    @POST("GetSearchFilterIDs")
    Call<FilterIDs> GetSearchFilterIDs(
            @Body RequestBody params
    );
    @POST("GetAllBlogs")
    Call<Blogs> GetAllBlogs(
            @Body RequestBody params
    );
    @POST("GetBlogDetails")
    Call<SpecBlogDetails> GetBlogDetails(
            @Body RequestBody params
    );

    @POST("GetFAQ")
    Call<Faqs> GetFAQ(
            @Body RequestBody params
    );

    @POST("UpdateProfileInfo")
    Call<User> UpdateProfileInfo(
            @Body RequestBody params
    );

    @POST("GetItem_BuySellNow")
    Call<ReviewItem> GetItem_BuySellNow(
            @Body RequestBody params
    );
    @POST("BidNow")
    Call<bidPayment> BidNow(
            @Body RequestBody params
    );
    @POST("SellNow")
    Call<SellPayment> SellNow(
            @Body RequestBody params
    );
    @POST("AddItemBid")
    Call<itemBidPayment> AddItemBid(
            @Body RequestBody params
    );
    @POST("AddItemAsk")
    Call<itemAskPayment> AddItemAsk(
            @Body RequestBody params
    );

    @POST("GetCustomer_Bids")
    Call<customerBids> GetCustomer_Bids(
            @Body RequestBody params
    );
    @POST("GetCustomer_Sells")
    Call<customerSells> GetCustomer_Sells(
            @Body RequestBody params
    );
    @POST("GetOrderDetail")
    Call<viewOrderDetails> GetOrderDetail(
            @Body RequestBody params
    );
    @POST("GetBidDetail")
    Call<ViewBidDetails> GetBidDetail(
            @Body RequestBody params
    );
    @POST("GetASKDetail")
    Call<ViewAskDetails> GetASKDetail(
            @Body RequestBody params
    );
    @POST("UpdateBidExpireDate")
    Call<UpdateBidExpire> UpdateBidExpireDate(
            @Body RequestBody params
    );
    @POST("UpdateASKExpireDate")
    Call<UpdateBidExpire> UpdateASKExpireDate(
            @Body RequestBody params
    );
    @POST("GetCustomerAddresses")
    Call<getCustomerAddress> GetCustomerAddresses(
            @Body RequestBody params
    );
    @POST("GetCustomerCards")
    Call<getCustomerCards> GetCustomerCards(
            @Body RequestBody params
    );
    @POST("AddToMyFavorites")
    Call<AddToFav> AddToMyFavorites(
            @Body RequestBody params
    );

    @POST("RemoveMyFavorite")
    Call<RemoveFromFav> RemoveMyFavorite(
            @Body RequestBody params
    );

    @POST("GetMyFavoriteItems")
    Call<getMyFavouriteItems> GetMyFavoriteItems(
            @Body RequestBody params
    );

    @POST("GetCustomerClosets")
    Call<GetCustomerClosets> GetCustomerClosets(
            @Body RequestBody params
    );
    @POST("AddCustomerCloset")
    Call<AddCustomerCloset> AddCustomerCloset(
            @Body RequestBody params
    );
    @POST("UpdateCustomerCloset")
    Call<UpdateCustomerCloset> UpdateCustomerCloset(
            @Body RequestBody params
    );
    @POST("DeleteCustomerCloset")
    Call<DeleteCustomerCloset> DeleteCustomerCloset(
            @Body RequestBody params
    );

    @POST("SaveNotificationsetting")
    Call<SaveNotificationsetting> SaveNotificationsetting(
            @Body RequestBody params
    );

    @POST("GetNotificationsetting")
    Call<GetNotificationsetting> GetNotificationsetting(
            @Body RequestBody params
    );
    @POST("GetDiscountDetails")
    Call<GetDiscountDetails> GetDiscountDetails(
            @Body RequestBody params
    );

    @POST("GetCurrenciesList")
    Call<GetCurrenciesList> GetCurrenciesList(
            @Body RequestBody params
    );

    @POST("GetCustomerWallet")
    Call<GetCustomerWallet> GetCustomerWallet(
            @Body RequestBody params
    );
    @POST("CheckOrderPaymentStatus")
    Call<CheckOrderPaymentStatus> CheckOrderPaymentStatus(
            @Body RequestBody params
    );
}
