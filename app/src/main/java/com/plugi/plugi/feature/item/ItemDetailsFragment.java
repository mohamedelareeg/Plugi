package com.plugi.plugi.feature.item;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.plugi.plugi.R;
import com.plugi.plugi.core.base.BaseFragment;
import com.plugi.plugi.core.utilities.Constants;
import com.plugi.plugi.core.views.ProductShowCaseWebView;
import com.plugi.plugi.core.views.ScrollViewExt;
import com.plugi.plugi.feature.category.interfaces.OnRelatedItemClickListener;
import com.plugi.plugi.feature.item.Size.BuyBottomSheetFragment;
import com.plugi.plugi.feature.item.Size.SellBottomSheetFragment;
import com.plugi.plugi.feature.item.adapter.ItemAllAsksAdapter;
import com.plugi.plugi.feature.item.adapter.ItemAllBidsAdapter;
import com.plugi.plugi.feature.item.adapter.ItemAllSaleAdapter;
import com.plugi.plugi.feature.item.adapter.ItemLastSaleAdapter;
import com.plugi.plugi.feature.item.adapter.ItemPropertiesAdapter;
import com.plugi.plugi.feature.item.adapter.ItemSizesAdapter;
import com.plugi.plugi.feature.item.adapter.ProductImageViewPagerAdapter;
import com.plugi.plugi.feature.item.adapter.RelatedProductsAdapter;
import com.plugi.plugi.feature.item.interfaces.OnItemSizeClickListener;
import com.plugi.plugi.feature.main.MainActivity;
import com.plugi.plugi.feature.main.interfaces.IOnBackPressed;
import com.plugi.plugi.feature.order.buying.BuyFragment;
import com.plugi.plugi.feature.order.selling.SellFragment;
import com.plugi.plugi.feature.profile.BlogInfoFragment;
import com.plugi.plugi.feature.profile.ClosetDetailsFragment;
import com.plugi.plugi.feature.profile.buying.details.BuyingCurrentDetails;
import com.plugi.plugi.feature.profile.buying.details.BuyingHistoryDetails;
import com.plugi.plugi.feature.profile.buying.details.BuyingPendingDetails;
import com.plugi.plugi.feature.profile.selling.details.SellingCurrentDetails;
import com.plugi.plugi.feature.profile.selling.details.SellingHistoryDetails;
import com.plugi.plugi.feature.profile.selling.details.SellingPendingDetails;
import com.plugi.plugi.models.ItemDetails;
import com.plugi.plugi.models.SpecBlogDetails;
import com.plugi.plugi.models.User;
import com.plugi.plugi.models.itemDetails.AllItemSize;
import com.plugi.plugi.models.itemDetails.AllitemASK;
import com.plugi.plugi.models.itemDetails.AllitemBid;
import com.plugi.plugi.models.itemDetails.LastSale;
import com.plugi.plugi.models.itemDetails.Proptety;
import com.plugi.plugi.models.itemDetails.Relateditem;
import com.plugi.plugi.retrofit.ApiInterface;
import com.plugi.plugi.retrofit.RetrofitClient;
import com.plugi.plugi.retrofit.SharedPrefManager;
import com.plugi.plugi.retrofit.response.AddToFav;
import com.plugi.plugi.retrofit.response.RemoveFromFav;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemDetailsFragment extends BaseFragment implements OnRelatedItemClickListener , IOnBackPressed , OnItemSizeClickListener , ScrollViewExt.ScrollViewListener {

    int itemID;
    ProductShowCaseWebView wv;
    //=========== ImageViews ============= TODO DISCOUNT IMAGE
    private ImageView ivDiscount;
    //=========== ImageAlbums ============= TODO RECYCLEVIEW WITH DOTS INSTED OF WEBVIEW
    private ViewPager vpProductImages;
    private LinearLayout layoutDots;
    private ProductImageViewPagerAdapter productImageViewPagerAdapter;
    private TextView[] dots;
    private int currentPosition;
    List<ItemDetails> imageList = new ArrayList<>();
    RelativeLayout all_salesPanel;
    LinearLayout proceedPanel;
    Button btnAccept;

    //ItemInfo
    private TextView productName, itemCondition , itemPrice , itemPriceDesc , item_size , item_ask_title , item_bid_price , item_bid_title , item_ask_price  , item_details ;
    //RelatedProducts
    RelatedProductsAdapter relatedProductsAdapter;
    ItemPropertiesAdapter itemPropertiesAdapter;
    RecyclerView rec_RelatedProducts;
    RecyclerView rec_Properties;
    List<Relateditem> relateditemList;
    List<Proptety> proptetyList;
    ShimmerFrameLayout shimmerFrameLayout , shimmerFrameLayout2 , shimmerFrameLayout3;
    private ScrollViewExt itemScrollView;

    //PopUpDialog
    public static Dialog sizeDialog , saleDialog ,askDialog , bidDialog , followDialog;
    private TextView viewAllsizes , viewAllasks , viewAllbids , viewAllSales ;

    private LinearLayout asksPanel , bidsPanel , relatedPanel;

    private ItemDetails itemDetails;

    private ImageView itemImage;
    private int Type = 0;

    ImageView  ivBack , ivShare , ivFollow;

    public AllItemSize selectedSize;
    BuyBottomSheetFragment buyFragment = new BuyBottomSheetFragment();
    SellBottomSheetFragment sellFragment = new SellBottomSheetFragment();

    RecyclerView rev_AllSales;

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_details, container, false);

        ((MainActivity) getActivity()).setCustomerFollowStatus(false);
        Type = getArguments().getInt(Constants.BUNDLE_ITEM_TYPE , 0);
        itemID = getArguments().getInt(Constants.BUNDLE_ITEM_ID , 0);
        itemDetails = ((MainActivity) getActivity()).getSelectItem();
        ivShare = view.findViewById(R.id.ivShare);
        ivFollow = view.findViewById(R.id.ivFollow);
        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Type == 2)
                {
                    ((MainActivity) requireActivity()).sidemenu.setVisibility(View.VISIBLE);
                    ((MainActivity) getActivity()).forceHiddenPopUp(false);
                }
                else if(Type ==3)
                    {
                        SpecBlogDetails specBlogDetails = ((MainActivity) getActivity()).getSpecBlogDetails();
                        Fragment fragment = null;
                        fragment = new BlogInfoFragment();
                        Bundle args = new Bundle();
                        args.putSerializable(Constants.BUNDLE_BLOG_LIST, (Serializable)specBlogDetails.getBlog());
                        fragment.setArguments(args);
                        ((MainActivity) getActivity()).replacePopUpFragments(fragment);
                    }
                else if(Type ==4)
                {
                    Fragment fragment = null;
                    fragment = new BuyingCurrentDetails();
                    Bundle args = new Bundle();
                    args.putInt(Constants.BUNDLE_ITEM_ID,  ((MainActivity) requireActivity()).getViewBidDetails().getID());
                    fragment.setArguments(args);
                    ((MainActivity) requireActivity()).replaceReviewFragments(fragment);
                }
                else if(Type ==5)
                {
                    Fragment fragment = null;
                    fragment = new BuyingPendingDetails();
                    Bundle args = new Bundle();
                    args.putInt(Constants.BUNDLE_ITEM_ID,  ((MainActivity) requireActivity()).getViewOrderDetails().getID());
                    fragment.setArguments(args);
                    ((MainActivity) requireActivity()).replaceReviewFragments(fragment);
                }
                else if(Type ==6)
                {
                    Fragment fragment = null;
                    fragment = new BuyingHistoryDetails();
                    Bundle args = new Bundle();
                    args.putInt(Constants.BUNDLE_ITEM_ID,  ((MainActivity) requireActivity()).getViewOrderDetails().getID());
                    fragment.setArguments(args);
                    ((MainActivity) requireActivity()).replaceReviewFragments(fragment);
                }
                else if(Type ==14)
                {
                    Fragment fragment = null;
                    fragment = new SellingCurrentDetails();
                    Bundle args = new Bundle();
                    args.putInt(Constants.BUNDLE_ITEM_ID,  ((MainActivity) requireActivity()).getViewAskDetails().getID());
                    fragment.setArguments(args);
                    ((MainActivity) requireActivity()).replaceReviewFragments(fragment);
                }
                else if(Type ==15)
                {
                    Fragment fragment = null;
                    fragment = new SellingPendingDetails();
                    Bundle args = new Bundle();
                    args.putInt(Constants.BUNDLE_ITEM_ID,  ((MainActivity) requireActivity()).getViewOrderDetails().getID());
                    fragment.setArguments(args);
                    ((MainActivity) requireActivity()).replaceReviewFragments(fragment);
                }
                else if(Type ==16)
                {
                    Fragment fragment = null;
                    fragment = new SellingHistoryDetails();
                    Bundle args = new Bundle();
                    args.putInt(Constants.BUNDLE_ITEM_ID,  ((MainActivity) requireActivity()).getViewOrderDetails().getID());
                    fragment.setArguments(args);
                    ((MainActivity) requireActivity()).replaceReviewFragments(fragment);
                }
                else if(Type ==7)
                {
                    Fragment fragment = null;
                    fragment = new ClosetDetailsFragment();
                    Bundle args = new Bundle();
                    args.putInt(Constants.BUNDLE_ITEM_ID,  1);//TODO
                    fragment.setArguments(args);
                    ((MainActivity) requireActivity()).replaceReviewFragments(fragment);
                }
                else
                {
                    ((MainActivity) getActivity()).forceHiddenPopUp(false);
                }

            }
        });




        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        shimmerFrameLayout = view.findViewById(R.id.parentShimmerLayout);
        shimmerFrameLayout2 = view.findViewById(R.id.parentShimmerLayout2);
        shimmerFrameLayout3 = view.findViewById(R.id.parentShimmerLayout3);

        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout2.startShimmer();
        shimmerFrameLayout3.startShimmer();

        itemScrollView = view.findViewById(R.id.itemScrollView);
        relatedPanel = view.findViewById(R.id.relatedPanel);
        viewAllasks = view.findViewById(R.id.viewAllasks);
        viewAllbids = view.findViewById(R.id.viewAllbids);
        viewAllSales = view.findViewById(R.id.viewAllSales);
        viewAllsizes = view.findViewById(R.id.viewAllsizes);

        item_size = view.findViewById(R.id.item_size);
        item_ask_title = view.findViewById(R.id.item_ask_title);
        item_bid_price = view.findViewById(R.id.item_bid_price);
        item_bid_title = view.findViewById(R.id.item_bid_title);
        item_ask_price = view.findViewById(R.id.item_ask_price);
        item_details = view.findViewById(R.id.item_details);

        wv = (ProductShowCaseWebView) view.findViewById(R.id.web_view);
        itemImage = view.findViewById(R.id.itemImage);
        productName = view.findViewById(R.id.productName);
        itemCondition = view.findViewById(R.id.itemCondition);
        itemPrice = view.findViewById(R.id.itemPrice);
        itemPriceDesc = view.findViewById(R.id.itemPriceDesc);

        asksPanel  = view.findViewById(R.id.asksPanel);
        bidsPanel  = view.findViewById(R.id.bidsPanel);

        rev_AllSales = view.findViewById(R.id.rec_AllSales);
        all_salesPanel = view.findViewById(R.id.all_salesPanel);
        proceedPanel = view.findViewById(R.id.proceedPanel);
        btnAccept = view.findViewById(R.id.btnAccept);

        rec_RelatedProducts = (RecyclerView) view.findViewById(R.id.rec_RelatedProducts);
        rec_Properties = (RecyclerView) view.findViewById(R.id.rec_Properties);

        relateditemList = new ArrayList<>();
        proptetyList = new ArrayList<>();

        RecyclerView.LayoutManager PopularlayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rec_RelatedProducts.setLayoutManager(PopularlayoutManager);
        relatedProductsAdapter = new RelatedProductsAdapter(getContext(), relateditemList ,this);
        rec_RelatedProducts.setAdapter(relatedProductsAdapter);

        RecyclerView.LayoutManager propertiesManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rec_Properties.setLayoutManager(propertiesManager);
        itemPropertiesAdapter = new ItemPropertiesAdapter(getContext(), proptetyList);
        rec_Properties.setAdapter(itemPropertiesAdapter);
        Log.d("ItemDetails", "onViewCreated: " + itemID);
        if(Type == 1) {
            if (itemDetails != null) {
                setItemDetails(itemDetails);
            }
        }
        else if(itemID != 0) {
            LoadData(itemID);

        }



    }


    public void LoadData(int _itemID)
    {
        ApiInterface service = RetrofitClient.retrofitWrite().create(ApiInterface.class);

        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("ItemID", _itemID);
        jsonParams.put("language_ID", 1);
        if(SharedPrefManager.getInstance(getActivity()).isLoggedIn()) {
            User user = SharedPrefManager.getInstance(getActivity()).getUser();
            jsonParams.put("CustomerId", user.getCustomer().getID());
        }

        RequestBody bodyToken = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
        //defining the call
        Call<ItemDetails> call = service.GetItemDetailbyID(
                bodyToken
        );
        call.enqueue(new Callback<ItemDetails>() {
            @Override
            public void onResponse(Call<ItemDetails> call, Response<ItemDetails> response) {

                if(response.body().getStatusMessage().equals("Success"))
                {
                    ((MainActivity) getActivity()).setSelectItemSize(null);
                    ((MainActivity) getActivity()).setItemSizeList(null);
                    ((MainActivity) getActivity()).setSelectItem(response.body());
                    ((MainActivity) getActivity()).setItemSizeList(response.body().getAllItemSizes());

                    setItemDetails(response.body());

                }
            }

            @Override
            public void onFailure(Call<ItemDetails> call, Throwable t) {
                Log.d("ItemDetails", "onFailure: " + t.getLocalizedMessage());
            }
        });
    }

    private void setItemDetails(ItemDetails itemDetails) {
        Log.d("ItemDetails", "setItemDetails: " + itemDetails.getItemID());
        // setting up image using GLIDE
        if(itemDetails.getItemImages().size() > 0)
        {
            load3DImage(itemDetails);
            itemImage.setVisibility(View.GONE);
            wv.setVisibility(View.VISIBLE);
        }
        else
        {
            Glide.with(getActivity()).load(itemDetails.getItemDetail().getMainImage()).placeholder(R.drawable.ic_landiing_logo).into(itemImage);
        }


        //load3DImage();
        productName.setText(itemDetails.getItemDetail().getName());
        itemCondition.setText(itemDetails.getItemDetail().getConditionName());
        itemPrice.setText(getActivity().getResources().getString(R.string.currency_kd) + itemDetails.getItemDetail().getLastSalePrice());
        itemPriceDesc.setText(itemDetails.getItemDetail().getLastSaleDesc());

        if(((MainActivity) getActivity()).getSelectItemSize() != null) {
            selectedSize = ((MainActivity) getActivity()).getSelectItemSize();
            if(selectedSize!=null && selectedSize.getSizeValue() != null) {
                item_size.setText(selectedSize.getSizeValue().toString());
            }
        }
        else
        {
            if(((MainActivity) getActivity()).getItemSizeList() != null  && ((MainActivity) getActivity()).getItemSizeList().size() > 0) {
                selectedSize = ((MainActivity) getActivity()).getItemSizeList().get(0);
                ((MainActivity) getActivity()).setSelectItemSize(selectedSize);
                if(selectedSize!=null && selectedSize.getSizeValue() != null) {
                    item_size.setText(selectedSize.getSizeValue().toString());
                }
            }
        }
        item_ask_price.setText(getResources().getString(R.string.currency_kd) + "." + itemDetails.getItemDetail().getLowAskPrice());
        item_bid_price.setText(getResources().getString(R.string.currency_kd) + "." + itemDetails.getItemDetail().getMaxBidPrice());
        item_details.setText(itemDetails.getItemDetail().getDescription());
        ((MainActivity) getActivity()).setCustomerFollowStatus(itemDetails.getCustomerFollowStatus());
        shimmerFrameLayout3.setVisibility(View.GONE);
        shimmerFrameLayout3.stopShimmer();
        itemScrollView.setVisibility(View.VISIBLE);
        if(itemDetails.getRelateditems().size() > 0) {
            relatedPanel.setVisibility(View.VISIBLE);
            relateditemList.addAll(itemDetails.getRelateditems());
            relatedProductsAdapter.notifyDataSetChanged();
            shimmerFrameLayout.setVisibility(View.GONE);
            shimmerFrameLayout.stopShimmer();
        }
        else
        {
            relatedPanel.setVisibility(View.GONE);
            shimmerFrameLayout.setVisibility(View.GONE);
            shimmerFrameLayout.stopShimmer();
        }

        proptetyList.addAll(itemDetails.getPropteties());
        itemPropertiesAdapter.notifyDataSetChanged();
        shimmerFrameLayout2.setVisibility(View.GONE);
        shimmerFrameLayout2.stopShimmer();

        viewAllsizes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemDetails.getAllItemSizes().size() != 0) {
                    showSizeDialog(getActivity(), itemDetails);
                }
            }
        });

        viewAllSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemDetails.getAllSales().size() != 0) {
                    showSaleDialog(getActivity(), itemDetails);
                }
            }
        });
        viewAllasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemDetails.getAllitemASKs().size() != 0) {
                    showAskDialog(getActivity(), itemDetails);
                }
            }
        });
        viewAllbids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemDetails.getAllitemBids().size() != 0) {
                    showBidsDialog(getActivity(), itemDetails);
                }
            }
        });
        ivFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFollowDialog(getActivity(), itemDetails);
            }
        });
        asksPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(((MainActivity) getActivity()).getItemSizeList().size() > 0) {
                    if (itemDetails.getAllItemSizes().size() > 0 && ((MainActivity) getActivity()).getSelectItemSize() == ((MainActivity) getActivity()).getItemSizeList().get(0)) {
                        if (!buyFragment.isAdded()) {
                            buyFragment.show(getActivity().getSupportFragmentManager(), buyFragment.getTag());


                        }
                    }
                    else {

                        if (SharedPrefManager.getInstance(getActivity()).isLoggedIn()) {
                            Fragment fragment = null;
                            fragment = new BuyFragment();
                            ((MainActivity) getActivity()).replacePopUpFragments(fragment);
                        } else {
                            Toast.makeText(getActivity(), getResources().getString(R.string.please_login_first), Toast.LENGTH_SHORT).show();
                        }
                    }
                }else {
                    ((MainActivity) getActivity()).setSelectItemSize(null);
                    if (SharedPrefManager.getInstance(getActivity()).isLoggedIn()) {
                        Fragment fragment = null;
                        fragment = new BuyFragment();
                        ((MainActivity) getActivity()).replacePopUpFragments(fragment);
                    } else {
                        Toast.makeText(getActivity(), getResources().getString(R.string.please_login_first), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        bidsPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((MainActivity) getActivity()).getItemSizeList().size() > 0) {
                    Log.d("SELL", "onClick:1 ");
                    if (itemDetails.getAllItemSizes().size() > 0 && ((MainActivity) getActivity()).getSelectItemSize() == ((MainActivity) getActivity()).getItemSizeList().get(0)) {
                        if (!sellFragment.isAdded()) {
                            sellFragment.show(getActivity().getSupportFragmentManager(), sellFragment.getTag());


                        }
                    }
                    else {
                        Log.d("SELL", "onClick:2 ");
                        if (SharedPrefManager.getInstance(getActivity()).isLoggedIn()) {
                            Fragment fragment = null;
                            fragment = new SellFragment();
                            ((MainActivity) getActivity()).replacePopUpFragments(fragment);
                        } else {
                            Toast.makeText(getActivity(), getResources().getString(R.string.please_login_first), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else {
                    Log.d("SELL", "onClick:3 ");
                    ((MainActivity) getActivity()).setSelectItemSize(null);
                    if (SharedPrefManager.getInstance(getActivity()).isLoggedIn()) {
                        Fragment fragment = null;
                        fragment = new SellFragment();
                        ((MainActivity) getActivity()).replacePopUpFragments(fragment);
                    } else {
                        Toast.makeText(getActivity(), getResources().getString(R.string.please_login_first), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        ItemAllSaleAdapter adapterRe = new ItemAllSaleAdapter(getActivity() , itemDetails.getAllSales());
        rev_AllSales.setAdapter(adapterRe);
        LinearLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        rev_AllSales.setLayoutManager(mLayoutManager);
        //itemScrollView.smoothScrollTo(0,0);

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ITEM", "onClick: ");
                if(itemDetails.getAllSales().size() > 0) {
                    all_salesPanel.setVisibility(View.VISIBLE);
                }
                proceedPanel.setVisibility(View.GONE);
            }
        });

        itemScrollView.setScrollViewListener(this);
    }
    private void load3DImage(ItemDetails itemDetails) {

        wv.setVerticalScrollBarEnabled(false);
        wv.setHorizontalScrollBarEnabled(false);

        String imagesTag360="";
        /*Taking images from the assert folder*/
        //imagesTag360=imagesTag360+"<img src=\"http://plugi.mawaqaademos.com/assets/images/Uploads/202082718813.jpg\"/>";
        /*
        for(int i=1;i<19;i++)
        {
            imagesTag360=imagesTag360+"<img src=\"file:///android_asset/images/image1_"+i+".jpg\"/>" ;
        }

         */

        /* For Showing Images from image url just use the image url in the src field*/

       for(int i=0;i<itemDetails.getItemImages().size();i++) {
           imagesTag360 = imagesTag360 + "<img src=\"http://plugi.mawaqaademos.com/assets/images/Uploads/"+itemDetails.getItemImages().get(i).getPropName() +"\"/>";
       }


        Log.d("",imagesTag360);


        wv.loadDataWithBaseURL("",
                imagesTag360, "text/html", "UTF-8", null);
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_item_details;
    }
    TextView btnFollow;
    public void showFollowDialog(Activity activity , ItemDetails _itemdetails){

        followDialog = new Dialog(activity);
        // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        followDialog.setCancelable(false);
        followDialog.setContentView(R.layout.dialog_follow);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(followDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        ImageView ivClose = followDialog.findViewById(R.id.ivClose);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                followDialog.dismiss();
                ivFollow.setEnabled(true);
            }
        });

        btnFollow = (TextView) followDialog.findViewById(R.id.btnFollow);
        TextView btnCloset = (TextView) followDialog.findViewById(R.id.btnCloset);

        if(((MainActivity) getActivity()).isCustomerFollowStatus()) {
         btnFollow.setText(getResources().getString(R.string.unfollow));
        }
        else
        {
            btnFollow.setText(getResources().getString(R.string.follow));
        }
        btnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((MainActivity) getActivity()).isCustomerFollowStatus()) {
                    RemoveFromFav(_itemdetails.getItemID());
                }
                else
                {
                    AddToFav(_itemdetails.getItemID());
                }

                //TODO

            }
        });
        btnCloset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                followDialog.dismiss();
                ivFollow.setEnabled(true);
                Fragment fragment = null;
                fragment = new AddToClosetFragment();
                ((MainActivity) requireActivity()).replaceReviewFragments(fragment , Constants.BUNDLE_ITEM_DETAILS , _itemdetails);
            }
        });

        followDialog.show();
        followDialog.getWindow().setAttributes(lp);
        ivFollow.setEnabled(false);


    }
    public void showSizeDialog(Activity activity , ItemDetails _itemdetails){

        sizeDialog = new Dialog(activity);
        // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        sizeDialog.setCancelable(false);
        sizeDialog.setContentView(R.layout.dialog_view_sizes);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(sizeDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        ImageView ivClose = sizeDialog.findViewById(R.id.ivClose);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sizeDialog.dismiss();
                viewAllsizes.setEnabled(true);
            }
        });

        TextView viewChart = (TextView) sizeDialog.findViewById(R.id.viewChart);
        if(_itemdetails.getSizeChartURL()!= null) {
            viewChart.setVisibility(View.VISIBLE);
            viewChart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(_itemdetails.getSizeChartURL())));
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
            selectedIndex = _itemdetails.getAllItemSizes().indexOf(selectedSize);
        }
        RecyclerView recyclerView = sizeDialog.findViewById(R.id.rec_Allsizes);
        ItemSizesAdapter adapterRe = new ItemSizesAdapter(activity , _itemdetails.getAllItemSizes(), selectedIndex , this);
        recyclerView.setAdapter(adapterRe);
        LinearLayoutManager mLayoutManager = new GridLayoutManager(activity, 3);
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        sizeDialog.show();
        sizeDialog.getWindow().setAttributes(lp);
        viewAllsizes.setEnabled(false);


    }
    boolean sizeSort = false , priceSort = false , timeSort = false;
    public void showSaleDialog(Activity activity , ItemDetails _itemdetails){

        saleDialog = new Dialog(activity);
        // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        saleDialog.setCancelable(false);
        saleDialog.setContentView(R.layout.dialog_view_sales);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(saleDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        ImageView ivClose = saleDialog.findViewById(R.id.ivClose);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saleDialog.dismiss();
                viewAllSales.setEnabled(true);
            }
        });

        TextView sortSize = (TextView) saleDialog.findViewById(R.id.size_sort);
        TextView saleSort = (TextView) saleDialog.findViewById(R.id.sale_sort);
        TextView dateSort = (TextView) saleDialog.findViewById(R.id.date_sort);


        RecyclerView recyclerView = saleDialog.findViewById(R.id.rec_AllSales);
        ItemLastSaleAdapter adapterRe = new ItemLastSaleAdapter(activity , _itemdetails.getLastSales());
        recyclerView.setAdapter(adapterRe);
        LinearLayoutManager mLayoutManager = new GridLayoutManager(activity, 1);
        recyclerView.setLayoutManager(mLayoutManager);

        sortSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sizeSort) {
                    Collections.sort(_itemdetails.getLastSales(), new Comparator<LastSale>() {
                        @Override
                        public int compare(LastSale lhs, LastSale rhs) {
                            // ## Ascending order
                            return rhs.getSizeValue().compareTo(lhs.getSizeValue()); // To compare string values
                            // return Integer.valueOf(obj1.getId()).compareTo(obj2.getId()); // To compare integer values

                            // ## Descending order
                            // return obj2.getCompanyName().compareToIgnoreCase(obj1.getCompanyName()); // To compare string values
                            // return Integer.valueOf(obj2.getId()).compareTo(obj1.getId()); // To compare integer values
                        }
                    });
                    adapterRe.notifyDataSetChanged();
                    sizeSort = false;
                }
                else
                {
                    Collections.sort(_itemdetails.getLastSales(), new Comparator<LastSale>() {
                        @Override
                        public int compare(LastSale lhs, LastSale rhs) {
                            // ## Ascending order
                            return lhs.getSizeValue().compareTo(rhs.getSizeValue()); // To compare string values
                            // return Integer.valueOf(obj1.getId()).compareTo(obj2.getId()); // To compare integer values

                            // ## Descending order
                            // return obj2.getCompanyName().compareToIgnoreCase(obj1.getCompanyName()); // To compare string values
                            // return Integer.valueOf(obj2.getId()).compareTo(obj1.getId()); // To compare integer values
                        }
                    });
                    adapterRe.notifyDataSetChanged();
                    sizeSort = true;
                }


            }
        });
        saleSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(priceSort) {
                    Collections.sort(_itemdetails.getLastSales(), new Comparator<LastSale>() {
                        @Override
                        public int compare(LastSale lhs, LastSale rhs) {
                            // ## Ascending order
                            return rhs.getPriceValue().compareTo(lhs.getPriceValue()); // To compare string values
                            // return Integer.valueOf(obj1.getId()).compareTo(obj2.getId()); // To compare integer values

                            // ## Descending order
                            // return obj2.getCompanyName().compareToIgnoreCase(obj1.getCompanyName()); // To compare string values
                            // return Integer.valueOf(obj2.getId()).compareTo(obj1.getId()); // To compare integer values
                        }
                    });
                    adapterRe.notifyDataSetChanged();
                    priceSort = false;
                }
                else
                {
                    Collections.sort(_itemdetails.getLastSales(), new Comparator<LastSale>() {
                        @Override
                        public int compare(LastSale lhs, LastSale rhs) {
                            // ## Ascending order
                            return lhs.getPriceValue().compareTo(rhs.getPriceValue()); // To compare string values
                            // return Integer.valueOf(obj1.getId()).compareTo(obj2.getId()); // To compare integer values

                            // ## Descending order
                            // return obj2.getCompanyName().compareToIgnoreCase(obj1.getCompanyName()); // To compare string values
                            // return Integer.valueOf(obj2.getId()).compareTo(obj1.getId()); // To compare integer values
                        }
                    });
                    adapterRe.notifyDataSetChanged();
                    priceSort = true;
                }


            }
        });
        dateSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(timeSort) {
                    Collections.sort(_itemdetails.getLastSales(), new Comparator<LastSale>() {
                        @Override
                        public int compare(LastSale lhs, LastSale rhs) {
                            // ## Ascending order
                            return rhs.getPriceDate().compareTo(lhs.getPriceDate()); // To compare string values
                            // return Integer.valueOf(obj1.getId()).compareTo(obj2.getId()); // To compare integer values

                            // ## Descending order
                            // return obj2.getCompanyName().compareToIgnoreCase(obj1.getCompanyName()); // To compare string values
                            // return Integer.valueOf(obj2.getId()).compareTo(obj1.getId()); // To compare integer values
                        }
                    });
                    adapterRe.notifyDataSetChanged();
                    timeSort = false;
                }
                else
                {
                    Collections.sort(_itemdetails.getLastSales(), new Comparator<LastSale>() {
                        @Override
                        public int compare(LastSale lhs, LastSale rhs) {
                            // ## Ascending order
                            return lhs.getPriceDate().compareTo(rhs.getPriceDate()); // To compare string values
                            // return Integer.valueOf(obj1.getId()).compareTo(obj2.getId()); // To compare integer values

                            // ## Descending order
                            // return obj2.getCompanyName().compareToIgnoreCase(obj1.getCompanyName()); // To compare string values
                            // return Integer.valueOf(obj2.getId()).compareTo(obj1.getId()); // To compare integer values
                        }
                    });
                    adapterRe.notifyDataSetChanged();
                    timeSort = true;
                }


            }
        });
        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        saleDialog.show();
        saleDialog.getWindow().setAttributes(lp);
        viewAllSales.setEnabled(false);


    }
    boolean AskSizeSort = false , AskPriceSort = false;
    public void showAskDialog(Activity activity , ItemDetails _itemdetails){

        askDialog = new Dialog(activity);
        // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        askDialog.setCancelable(false);
        askDialog.setContentView(R.layout.dialog_view_asks);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(askDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        ImageView ivClose = askDialog.findViewById(R.id.ivClose);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askDialog.dismiss();
                viewAllasks.setEnabled(true);
            }
        });

        TextView itemName = (TextView) askDialog.findViewById(R.id.itemName);
        itemName.setText(_itemdetails.getItemDetail().getName());
        TextView sortSize = (TextView) askDialog.findViewById(R.id.size_sort);
        TextView price_sort = (TextView) askDialog.findViewById(R.id.price_sort);


        RecyclerView recyclerView = askDialog.findViewById(R.id.rec_AllAsks);
        ItemAllAsksAdapter adapterRe = new ItemAllAsksAdapter(activity , _itemdetails.getAllitemASKs());
        recyclerView.setAdapter(adapterRe);
        LinearLayoutManager mLayoutManager = new GridLayoutManager(activity, 1);
        recyclerView.setLayoutManager(mLayoutManager);

        sortSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(AskSizeSort) {
                    Collections.sort(_itemdetails.getAllitemASKs(), new Comparator<AllitemASK>() {
                        @Override
                        public int compare(AllitemASK lhs, AllitemASK rhs) {
                            // ## Ascending order
                            return rhs.getSizeValue().compareTo(lhs.getSizeValue()); // To compare string values
                            // return Integer.valueOf(obj1.getId()).compareTo(obj2.getId()); // To compare integer values

                            // ## Descending order
                            // return obj2.getCompanyName().compareToIgnoreCase(obj1.getCompanyName()); // To compare string values
                            // return Integer.valueOf(obj2.getId()).compareTo(obj1.getId()); // To compare integer values
                        }
                    });
                    adapterRe.notifyDataSetChanged();
                    AskSizeSort = false;
                }
                else
                {
                    Collections.sort(_itemdetails.getAllitemASKs(), new Comparator<AllitemASK>() {
                        @Override
                        public int compare(AllitemASK lhs, AllitemASK rhs) {
                            // ## Ascending order
                            return lhs.getSizeValue().compareTo(rhs.getSizeValue()); // To compare string values
                            // return Integer.valueOf(obj1.getId()).compareTo(obj2.getId()); // To compare integer values

                            // ## Descending order
                            // return obj2.getCompanyName().compareToIgnoreCase(obj1.getCompanyName()); // To compare string values
                            // return Integer.valueOf(obj2.getId()).compareTo(obj1.getId()); // To compare integer values
                        }
                    });
                    adapterRe.notifyDataSetChanged();
                    AskSizeSort = true;
                }


            }
        });
        price_sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(AskPriceSort) {
                    Collections.sort(_itemdetails.getAllitemASKs(), new Comparator<AllitemASK>() {
                        @Override
                        public int compare(AllitemASK lhs, AllitemASK rhs) {
                            // ## Ascending order
                            return rhs.getPriceValue().compareTo(lhs.getPriceValue()); // To compare string values
                            // return Integer.valueOf(obj1.getId()).compareTo(obj2.getId()); // To compare integer values

                            // ## Descending order
                            // return obj2.getCompanyName().compareToIgnoreCase(obj1.getCompanyName()); // To compare string values
                            // return Integer.valueOf(obj2.getId()).compareTo(obj1.getId()); // To compare integer values
                        }
                    });
                    adapterRe.notifyDataSetChanged();
                    AskPriceSort = false;
                }
                else
                {
                    Collections.sort(_itemdetails.getAllitemASKs(), new Comparator<AllitemASK>() {
                        @Override
                        public int compare(AllitemASK lhs, AllitemASK rhs) {
                            // ## Ascending order
                            return lhs.getPriceValue().compareTo(rhs.getPriceValue()); // To compare string values
                            // return Integer.valueOf(obj1.getId()).compareTo(obj2.getId()); // To compare integer values

                            // ## Descending order
                            // return obj2.getCompanyName().compareToIgnoreCase(obj1.getCompanyName()); // To compare string values
                            // return Integer.valueOf(obj2.getId()).compareTo(obj1.getId()); // To compare integer values
                        }
                    });
                    adapterRe.notifyDataSetChanged();
                    AskPriceSort = true;
                }


            }
        });

        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        askDialog.show();
        askDialog.getWindow().setAttributes(lp);
        viewAllasks.setEnabled(false);


    }
    boolean BidsSizeSort = false , BidsPriceSort = false;
    public void showBidsDialog(Activity activity , ItemDetails _itemdetails){

        bidDialog = new Dialog(activity);
        // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        bidDialog.setCancelable(false);
        bidDialog.setContentView(R.layout.dialog_view_bids);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(bidDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        ImageView ivClose = bidDialog.findViewById(R.id.ivClose);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bidDialog.dismiss();
                viewAllbids.setEnabled(true);
            }
        });

        TextView itemName = (TextView) bidDialog.findViewById(R.id.itemName);
        itemName.setText(_itemdetails.getItemDetail().getName());
        TextView sortSize = (TextView) bidDialog.findViewById(R.id.size_sort);
        TextView price_sort = (TextView) bidDialog.findViewById(R.id.price_sort);


        RecyclerView recyclerView = bidDialog.findViewById(R.id.rec_AllBids);
        ItemAllBidsAdapter adapterRe = new ItemAllBidsAdapter(activity , _itemdetails.getAllitemBids());
        recyclerView.setAdapter(adapterRe);
        LinearLayoutManager mLayoutManager = new GridLayoutManager(activity, 1);
        recyclerView.setLayoutManager(mLayoutManager);

        sortSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(BidsSizeSort) {
                    Collections.sort(_itemdetails.getAllitemBids(), new Comparator<AllitemBid>() {
                        @Override
                        public int compare(AllitemBid lhs, AllitemBid rhs) {
                            // ## Ascending order
                            return rhs.getSizeValue().compareTo(lhs.getSizeValue()); // To compare string values
                            // return Integer.valueOf(obj1.getId()).compareTo(obj2.getId()); // To compare integer values

                            // ## Descending order
                            // return obj2.getCompanyName().compareToIgnoreCase(obj1.getCompanyName()); // To compare string values
                            // return Integer.valueOf(obj2.getId()).compareTo(obj1.getId()); // To compare integer values
                        }
                    });
                    adapterRe.notifyDataSetChanged();
                    BidsSizeSort = false;
                }
                else
                {
                    Collections.sort(_itemdetails.getAllitemBids(), new Comparator<AllitemBid>() {
                        @Override
                        public int compare(AllitemBid lhs, AllitemBid rhs) {
                            // ## Ascending order
                            return lhs.getSizeValue().compareTo(rhs.getSizeValue()); // To compare string values
                            // return Integer.valueOf(obj1.getId()).compareTo(obj2.getId()); // To compare integer values

                            // ## Descending order
                            // return obj2.getCompanyName().compareToIgnoreCase(obj1.getCompanyName()); // To compare string values
                            // return Integer.valueOf(obj2.getId()).compareTo(obj1.getId()); // To compare integer values
                        }
                    });
                    adapterRe.notifyDataSetChanged();
                    BidsSizeSort = true;
                }


            }
        });
        price_sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(BidsPriceSort) {
                    Collections.sort(_itemdetails.getAllitemBids(), new Comparator<AllitemBid>() {
                        @Override
                        public int compare(AllitemBid lhs, AllitemBid rhs) {
                            // ## Ascending order
                            return rhs.getPriceValue().compareTo(lhs.getPriceValue()); // To compare string values
                            // return Integer.valueOf(obj1.getId()).compareTo(obj2.getId()); // To compare integer values

                            // ## Descending order
                            // return obj2.getCompanyName().compareToIgnoreCase(obj1.getCompanyName()); // To compare string values
                            // return Integer.valueOf(obj2.getId()).compareTo(obj1.getId()); // To compare integer values
                        }
                    });
                    adapterRe.notifyDataSetChanged();
                    BidsPriceSort = false;
                }
                else
                {
                    Collections.sort(_itemdetails.getAllitemBids(), new Comparator<AllitemBid>() {
                        @Override
                        public int compare(AllitemBid lhs, AllitemBid rhs) {
                            // ## Ascending order
                            return lhs.getPriceValue().compareTo(rhs.getPriceValue()); // To compare string values
                            // return Integer.valueOf(obj1.getId()).compareTo(obj2.getId()); // To compare integer values

                            // ## Descending order
                            // return obj2.getCompanyName().compareToIgnoreCase(obj1.getCompanyName()); // To compare string values
                            // return Integer.valueOf(obj2.getId()).compareTo(obj1.getId()); // To compare integer values
                        }
                    });
                    adapterRe.notifyDataSetChanged();
                    BidsPriceSort = true;
                }


            }
        });

        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        bidDialog.show();
        bidDialog.getWindow().setAttributes(lp);
        viewAllbids.setEnabled(false);


    }
    @Override
    public void onItemClicked(Integer itemID) {
        //((MainActivity) mCtx).mViewPager.setVisibility(View.GONE);

        Fragment fragment = null;
        fragment = new ItemDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.BUNDLE_ITEM_ID, itemID);
        fragment.setArguments(args);
        ((MainActivity) getActivity()).replacePopUpFragments(fragment);
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

    @Override
    public void onItemSizeClicked(AllItemSize contact, int position) {
        ((MainActivity) getActivity()).setSelectItemSize(contact);
        selectedSize = contact;
        if(selectedSize!=null && selectedSize.getSizeValue() != null) {
            item_size.setText(selectedSize.getSizeValue().toString());
        }
    }

    @Override
    public void onScrollChanged(ScrollViewExt scrollView, int x, int y, int oldx, int oldy) {
        // We take the last son in the scrollview
        View view = (View) scrollView.getChildAt(scrollView.getChildCount() - 1);
        int diff = (view.getBottom() - (scrollView.getHeight() + scrollView.getScrollY()));
        Log.d("SCROLL", "onScrollChanged: " + diff);
        // if diff is zero, then the bottom has been reached
        if (diff == 0) {
            // do stuff

        }
    }
    public void AddToFav(int _itemID)
    {
        Log.d("LoadData", "LoadData: " + _itemID);
        if(SharedPrefManager.getInstance(getActivity()).isLoggedIn()) {
            User currentUser = SharedPrefManager.getInstance(getActivity()).getUser();
            ApiInterface service = RetrofitClient.retrofitBearerTokenWrite(currentUser).create(ApiInterface.class);

            Map<String, Object> jsonParams = new ArrayMap<>();
            jsonParams.put("item_ID", _itemID);
            jsonParams.put("customer_ID", currentUser.getCustomer().getID());
            jsonParams.put("language_ID", 1);


            RequestBody bodyToken = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
            //defining the call
            Call<AddToFav> call = service.AddToMyFavorites(
                    bodyToken
            );
            call.enqueue(new Callback<AddToFav>() {
                @Override
                public void onResponse(Call<AddToFav> call, Response<AddToFav> response) {

                    if (response.body() != null) {
                        if (response.body().getStatusMessage().equals("Success")) {

                            ((MainActivity) getActivity()).setCustomerFollowStatus(true);
                            btnFollow.setText(getResources().getString(R.string.unfollow));
                            Log.d("LoadData", "onResponse: " + response.body().getStatusMessage());
                            //Toast.makeText(getActivity(), "" + getResources().getString(R.string.added_successfuly), Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getActivity(), "" + response.body().getStatusMessage(), Toast.LENGTH_SHORT).show();
                            Log.d("LoadData", "onResponse: " + response.body().getStatusMessage());
                        }
                    } else {
                        Log.d("LoadData", "onResponse: " + response.message());
                        Log.d("LoadData", "onResponse: " + response.errorBody());
                    }
                    /*


                     */

                }

                @Override
                public void onFailure(Call<AddToFav> call, Throwable t) {
                    Log.d("ItemDetails", "onFailure: " + t.getLocalizedMessage());
                }
            });
        }
    }
    public void RemoveFromFav(int _itemID)
    {
        Log.d("LoadData", "LoadData: " + _itemID);
        if(SharedPrefManager.getInstance(getActivity()).isLoggedIn()) {
            User currentUser = SharedPrefManager.getInstance(getActivity()).getUser();
            ApiInterface service = RetrofitClient.retrofitBearerTokenWrite(currentUser).create(ApiInterface.class);

            Map<String, Object> jsonParams = new ArrayMap<>();
            jsonParams.put("item_ID", _itemID);
            jsonParams.put("customer_ID", currentUser.getCustomer().getID());
            jsonParams.put("language_ID", 1);


            RequestBody bodyToken = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
            //defining the call
            Call<RemoveFromFav> call = service.RemoveMyFavorite(
                    bodyToken
            );
            call.enqueue(new Callback<RemoveFromFav>() {
                @Override
                public void onResponse(Call<RemoveFromFav> call, Response<RemoveFromFav> response) {

                    if (response.body() != null) {
                        if (response.body().getStatusMessage().equals("Success")) {

                            ((MainActivity) getActivity()).setCustomerFollowStatus(false);
                            btnFollow.setText(getResources().getString(R.string.follow));
                            Log.d("LoadData", "onResponse: " + response.body().getStatusMessage());
                            //Toast.makeText(getActivity(), "" + getResources().getString(R.string.added_successfuly), Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getActivity(), "" + response.body().getStatusMessage(), Toast.LENGTH_SHORT).show();
                            Log.d("LoadData", "onResponse: " + response.body().getStatusMessage());
                        }
                    } else {
                        Log.d("LoadData", "onResponse: " + response.message());
                        Log.d("LoadData", "onResponse: " + response.errorBody());
                    }
                    /*


                     */

                }

                @Override
                public void onFailure(Call<RemoveFromFav> call, Throwable t) {
                    Log.d("ItemDetails", "onFailure: " + t.getLocalizedMessage());
                }
            });
        }
    }
}
