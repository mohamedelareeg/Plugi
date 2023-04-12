package com.plugi.plugi.feature.profile;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.view.ActionMode;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.plugi.plugi.R;
import com.plugi.plugi.core.base.BaseFragment;
import com.plugi.plugi.core.utilities.Constants;
import com.plugi.plugi.feature.main.MainActivity;
import com.plugi.plugi.feature.main.SideMenuFragment;
import com.plugi.plugi.feature.profile.adapter.ClosetAdapter;
import com.plugi.plugi.feature.search.interfaces.onSearchAction;
import com.plugi.plugi.models.DeleteCustomerCloset;
import com.plugi.plugi.models.GetCustomerClosets;
import com.plugi.plugi.models.User;
import com.plugi.plugi.retrofit.ApiInterface;
import com.plugi.plugi.retrofit.RetrofitClient;
import com.plugi.plugi.retrofit.SharedPrefManager;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

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

public class ClosetFragment extends BaseFragment implements MaterialSearchBar.OnSearchActionListener  , ClosetAdapter.ServiesAdapterListener {
    TextView txItemCount , txMarketValue , txGainLoss;
    RecyclerView rec_ClosetItems;
    ClosetAdapter closetAdapter;
    List<GetCustomerClosets.ClosetDetail> closetList;
    ShimmerFrameLayout shimmerFrameLayout;
    //categoryNavigation
    MaterialSearchBar searchProductTXT;
    private onSearchAction onSearchAction;
    ImageView ivSide , ivBack;
    private ActionMode actionMode;
    private GetCustomerClosets.ClosetDetail selectitem;
    boolean itemsSort = false , marketValueSort = false;
    LinearLayout itemSortingPanel , marketValueSortingPanel;

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_closet, container, false);

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
        itemSortingPanel = view.findViewById(R.id.itemSortingPanel);
        marketValueSortingPanel = view.findViewById(R.id.marketValueSortingPanel);
        txItemCount = view.findViewById(R.id.txItemCount);
        txGainLoss = view.findViewById(R.id.txGainLoss);
        txMarketValue = view.findViewById(R.id.txMarketValue);
        shimmerFrameLayout = view.findViewById(R.id.parentShimmerLayout);
        shimmerFrameLayout.startShimmer();

        rec_ClosetItems = view.findViewById(R.id.rec_ClosetItems);
        searchProductTXT = view.findViewById(R.id.searchProductTXT);
        searchProductTXT.setOnSearchActionListener(this);
        searchProductTXT.hideSuggestionsList();
        searchProductTXT.setCardViewElevation(10);
        searchProductTXT.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("LOG_TAG", getClass().getSimpleName() + " text changed " + searchProductTXT.getText());




            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });

        AssignFollowingList();
        //getFollowingData(1);
        loadData();
        itemSortingPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(itemsSort) {
                    Collections.sort(closetList, new Comparator<GetCustomerClosets.ClosetDetail>() {
                        @Override
                        public int compare(GetCustomerClosets.ClosetDetail lhs, GetCustomerClosets.ClosetDetail rhs) {
                            // ## Ascending order
                            return rhs.getItemName().compareTo(lhs.getItemName()); // To compare string values
                            // return Integer.valueOf(obj1.getId()).compareTo(obj2.getId()); // To compare integer values

                            // ## Descending order
                            // return obj2.getCompanyName().compareToIgnoreCase(obj1.getCompanyName()); // To compare string values
                            // return Integer.valueOf(obj2.getId()).compareTo(obj1.getId()); // To compare integer values
                        }
                    });
                    closetAdapter.notifyDataSetChanged();
                    itemsSort = false;
                }
                else
                {
                    Collections.sort(closetList, new Comparator<GetCustomerClosets.ClosetDetail>() {
                        @Override
                        public int compare(GetCustomerClosets.ClosetDetail lhs, GetCustomerClosets.ClosetDetail rhs) {
                            // ## Ascending order
                            return lhs.getItemName().compareTo(rhs.getItemName()); // To compare string values
                            // return Integer.valueOf(obj1.getId()).compareTo(obj2.getId()); // To compare integer values

                            // ## Descending order
                            // return obj2.getCompanyName().compareToIgnoreCase(obj1.getCompanyName()); // To compare string values
                            // return Integer.valueOf(obj2.getId()).compareTo(obj1.getId()); // To compare integer values
                        }
                    });
                    closetAdapter.notifyDataSetChanged();
                    itemsSort = true;
                }


            }
        });
        marketValueSortingPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(marketValueSort) {
                    Collections.sort(closetList, new Comparator<GetCustomerClosets.ClosetDetail>() {
                        @Override
                        public int compare(GetCustomerClosets.ClosetDetail lhs, GetCustomerClosets.ClosetDetail rhs) {
                            // ## Ascending order
                            return Double.compare(rhs.getPurchasePrice() , lhs.getPurchasePrice());
                            //return String.valueOf(rhs.getPrice()).compareTo(String.valueOf(lhs.getPrice())); // To compare string values
                            // return Integer.valueOf(obj1.getId()).compareTo(obj2.getId()); // To compare integer values

                            // ## Descending order
                            // return obj2.getCompanyName().compareToIgnoreCase(obj1.getCompanyName()); // To compare string values
                            // return Integer.valueOf(obj2.getId()).compareTo(obj1.getId()); // To compare integer values
                        }
                    });
                    closetAdapter.notifyDataSetChanged();
                    marketValueSort = false;
                }
                else
                {
                    Collections.sort(closetList, new Comparator<GetCustomerClosets.ClosetDetail>() {
                        @Override
                        public int compare(GetCustomerClosets.ClosetDetail lhs, GetCustomerClosets.ClosetDetail rhs) {
                            // ## Ascending order
                            return Double.compare(lhs.getPurchasePrice() , rhs.getPurchasePrice());
                            //return String.valueOf(lhs.getPrice()).compareTo(String.valueOf(rhs.getPrice())); // To compare string values
                            //return lhs.getLowest().compareTo(rhs.getLowest()); // To compare string values
                            // return Integer.valueOf(obj1.getId()).compareTo(obj2.getId()); // To compare integer values

                            // ## Descending order
                            // return obj2.getCompanyName().compareToIgnoreCase(obj1.getCompanyName()); // To compare string values
                            // return Integer.valueOf(obj2.getId()).compareTo(obj1.getId()); // To compare integer values
                        }
                    });
                    closetAdapter.notifyDataSetChanged();
                    marketValueSort = true;
                }


            }
        });

        return view;
    }
    private void AssignFollowingList(){

        closetList = new ArrayList<>();
        LinearLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);

        rec_ClosetItems.setLayoutManager(mLayoutManager);
        rec_ClosetItems.setItemAnimator(new DefaultItemAnimator());
        rec_ClosetItems.setHasFixedSize(true);
        rec_ClosetItems.setNestedScrollingEnabled(false);
        /* TODO USELESS WITHOUT DATABASE
        recCategory.addOnScrollListener(new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                userPage++;
                getData(userPage);

            }
        });

         */
        closetAdapter = new ClosetAdapter(getActivity() , closetList);
        rec_ClosetItems.setAdapter(closetAdapter);
        closetAdapter.setListener(this);
        ItemTouchHelper helper = new ItemTouchHelper(
                // below statement: used at move and sort
                // new ItemTouchHandler(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                //ItemTouchHelper.LEFT)
                new ItemTouchHandler(0,
                        ItemTouchHelper.LEFT)
        );
        helper.attachToRecyclerView(rec_ClosetItems);

        /*
        followingAdapter.setListener(new FollowingAdapter() {
            @Override
            public void onItemClick(int position) {
                enableActionMode(position);

            }

            @Override
            public void onItemLongClick(int position) {
                enableActionMode(position);
            }
        });

         */
    }
    public void loadData()
    {
        if(SharedPrefManager.getInstance(getActivity()).isLoggedIn()) {
            User user = SharedPrefManager.getInstance(getActivity()).getUser();
            ApiInterface service = RetrofitClient.retrofitBearerTokenWrite(user).create(ApiInterface.class);

            Map<String, Object> jsonParams = new ArrayMap<>();
            jsonParams.put("Customer_ID", user.getCustomer().getID());
            jsonParams.put("language_ID", 1);
            RequestBody bodyToken = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
            //defining the call
            Call<GetCustomerClosets> call = service.GetCustomerClosets(
                    bodyToken
            );
            call.enqueue(new Callback<GetCustomerClosets>() {
                @Override
                public void onResponse(Call<GetCustomerClosets> call, Response<GetCustomerClosets> response) {

                    if (response.body().getStatusMessage().equals("Success")) {

                        closetList.addAll(response.body().getClosetDetails());
                        closetAdapter.notifyDataSetChanged();
                        shimmerFrameLayout.setVisibility(View.GONE);
                        shimmerFrameLayout.stopShimmer();
                        txItemCount.setText(response.body().getTotalCloset().toString());
                        txGainLoss.setText(  response.body().getPriceCurrency() +" " + response.body().getTotalGainLoss().toString());

                    } else {
                        shimmerFrameLayout.setVisibility(View.GONE);
                        shimmerFrameLayout.stopShimmer();
                        // viewNoItems.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<GetCustomerClosets> call, Throwable t) {
                    Log.d("CLOSET", "onFailure: " + t.getMessage());
                    Log.d("CLOSET", "onFailure: " + t.getLocalizedMessage());
                    shimmerFrameLayout.setVisibility(View.GONE);
                    shimmerFrameLayout.stopShimmer();
                }
            });
        }
    }
    private void getFollowingData(int userPage) {

        /*
        closetList.add(new ClosetPojo(1,"Nike White Sneakers 1" ,"Blue" , "1240.000" ,"KD",900 ,"http://plugi.mawaqaademos.com/assets/images/Uploads/2020820121846.jpg"));
        closetList.add(new ClosetPojo(2,"Nike White Sneakers 2" ,"Blue" , "250.000" ,"KD",1500 ,"http://plugi.mawaqaademos.com/assets/images/Uploads/2020820121846.jpg"));
        closetList.add(new ClosetPojo(3,"Nike White Sneakers 3" ,"Blue" , "260.000" ,"KD",2500 ,"http://plugi.mawaqaademos.com/assets/images/Uploads/2020820121846.jpg"));
        closetList.add(new ClosetPojo(4,"Nike White Sneakers 4" ,"Blue" , "280.000" ,"KD",1200 ,"http://plugi.mawaqaademos.com/assets/images/Uploads/2020820121846.jpg"));
        closetList.add(new ClosetPojo(5,"Nike White Sneakers 5" ,"Blue" , "270.000" ,"KD",1800 ,"http://plugi.mawaqaademos.com/assets/images/Uploads/2020820121846.jpg"));
        closetList.add(new ClosetPojo(6,"Nike White Sneakers 6" ,"Blue" , "440.000" ,"KD",600 ,"http://plugi.mawaqaademos.com/assets/images/Uploads/2020820121846.jpg"));
        closetList.add(new ClosetPojo(7,"Nike White Sneakers 7" ,"Blue" , "310.000" ,"KD",800 ,"http://plugi.mawaqaademos.com/assets/images/Uploads/2020820121846.jpg"));
        closetList.add(new ClosetPojo(8,"Nike White Sneakers 8" ,"Blue" , "540.000" ,"KD",400 ,"http://plugi.mawaqaademos.com/assets/images/Uploads/2020820121846.jpg"));
        closetList.add(new ClosetPojo(9,"Nike White Sneakers 9" ,"Blue" , "140.000" ,"KD",300 ,"http://plugi.mawaqaademos.com/assets/images/Uploads/2020820121846.jpg"));
        //closetList.add(new ClosetPojo(10,"Nike White Sneakers 10" ,"Blue" , "40.000" ,"KD",200 ,"http://plugi.mawaqaademos.com/assets/images/Uploads/2020820121846.jpg"));
        //closetList.add(new ClosetPojo(11,"Nike White Sneakers 11" ,"Blue" , "840.000" ,"KD",100 ,"http://plugi.mawaqaademos.com/assets/images/Uploads/2020820121846.jpg"));TODO ERROR SORTING

        closetAdapter.notifyDataSetChanged();
        shimmerFrameLayout.setVisibility(View.GONE);
        shimmerFrameLayout.stopShimmer();

         */


    }
    @Override
    public void onViewCreated(@NonNull View view, @androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }



    @Override
    protected int layoutRes() {
        return R.layout.fragment_closet;
    }

    @Override
    public void onSearchStateChanged(boolean enabled) {


        if(enabled)
        {

            //TODO

        }else
        {

            if(searchProductTXT.getText().length() == 0) {

                //setupViewPager(getMainCategory()); TODo
            }

        }

    }

    @Override
    public void onResume() {
        super.onResume();

        if(searchProductTXT.getText().length() == 0) {

            //setupViewPager(getMainCategory()); TODo
        }
        //loadCategory();
    }

    @Override
    public void onSearchConfirmed(CharSequence text) {

    }

    @Override
    public void onButtonClicked(int buttonCode) {
        if (buttonCode == MaterialSearchBar.BUTTON_BACK) {
            Log.d("CATEGORY", "onButtonClicked: ");
            if(searchProductTXT.getText().length() == 0) {


                //setupViewPager(getMainCategory()); TODo
            }
        }

    }
    public void setSearchAction(onSearchAction onSearchAction)
    {
        this.onSearchAction = onSearchAction ;
    }



    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // Do your stuff here
            Log.d("LoadMostPopular", "setUserVisibleHint: ");
        }

    }

    @Override
    public void onItemClick(int position, GetCustomerClosets.ClosetDetail item) {
        ((MainActivity) requireActivity()).setSelectedCloset(item);
        Fragment fragment = null;
        fragment = new ClosetDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.BUNDLE_ITEM_ID, item.getClosetId());
        fragment.setArguments(args);
        ((MainActivity) requireActivity()).replaceReviewFragments(fragment);
    }

    @Override
    public void onItemLongClick(int position, GetCustomerClosets.ClosetDetail item) {

    }


    private class ItemTouchHandler extends ItemTouchHelper.SimpleCallback {

        public ItemTouchHandler(int dragDirs, int swipeDirs) {
            super(dragDirs, swipeDirs);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            int from = viewHolder.getAdapterPosition();
            int to = target.getAdapterPosition();

            Collections.swap(closetAdapter.getClosetList(), from, to);
            closetAdapter.notifyItemMoved(from, to);
            RemoveFromFav(1);//TODO

            return true;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            closetAdapter.getClosetList().remove(viewHolder.getAdapterPosition());
            closetAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            RemoveFromFav(1);//TODO
        }
    }

    public void RemoveFromFav(int _itemID)
    {
        Log.d("LoadData", "LoadData: " + _itemID);
        User currentUser = SharedPrefManager.getInstance(getActivity()).getUser();
        ApiInterface service = RetrofitClient.retrofitBearerTokenWrite(currentUser).create(ApiInterface.class);

        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("ClosetId", _itemID);


        RequestBody bodyToken = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
        //defining the call
        Call<DeleteCustomerCloset> call = service.DeleteCustomerCloset(
                bodyToken
        );
        call.enqueue(new Callback<DeleteCustomerCloset>() {
            @Override
            public void onResponse(Call<DeleteCustomerCloset> call, Response<DeleteCustomerCloset> response) {

                if(response.body() != null) {
                    if(response.body().getStatusMessage().equals("Success"))
                    {

                        Log.d("LoadData", "onResponse: " + response.body().getStatusMessage());
                        Toast.makeText(getActivity(), "" + getResources().getString(R.string.removed_successfully), Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        Toast.makeText(getActivity(), "" + response.body().getStatusMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("LoadData", "onResponse: " + response.body().getStatusMessage());
                    }
                }
                else
                {
                    Log.d("LoadData", "onResponse: " + response.message());
                    Log.d("LoadData", "onResponse: " + response.errorBody());
                }
                /*


                 */

            }

            @Override
            public void onFailure(Call<DeleteCustomerCloset> call, Throwable t) {
                Log.d("ItemDetails", "onFailure: " + t.getLocalizedMessage());
            }
        });
    }

    public GetCustomerClosets.ClosetDetail getSelectitem() {
        return selectitem;
    }

    public void setSelectitem(GetCustomerClosets.ClosetDetail selectitem) {
        this.selectitem = selectitem;
    }
}
