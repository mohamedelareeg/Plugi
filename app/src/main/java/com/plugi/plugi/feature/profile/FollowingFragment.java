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
import com.plugi.plugi.feature.profile.adapter.FollowingAdapter;
import com.plugi.plugi.feature.search.interfaces.onSearchAction;
import com.plugi.plugi.models.User;
import com.plugi.plugi.models.getMyFavouriteItems;
import com.plugi.plugi.retrofit.ApiInterface;
import com.plugi.plugi.retrofit.RetrofitClient;
import com.plugi.plugi.retrofit.SharedPrefManager;
import com.plugi.plugi.retrofit.response.RemoveFromFav;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowingFragment extends BaseFragment implements MaterialSearchBar.OnSearchActionListener  , FollowingAdapter.ServiesAdapterListener {

    RecyclerView rec_FollowingItems;
    FollowingAdapter followingAdapter;
    List<getMyFavouriteItems.Item> followingList;
    ShimmerFrameLayout shimmerFrameLayout;
    //categoryNavigation
    MaterialSearchBar searchProductTXT;
    private onSearchAction onSearchAction;
    ImageView ivSide , ivBack;
    private ActionMode actionMode;
    private getMyFavouriteItems.Item selectitem;

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_following, container, false);

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
        shimmerFrameLayout = view.findViewById(R.id.parentShimmerLayout);
        shimmerFrameLayout.startShimmer();

        rec_FollowingItems = view.findViewById(R.id.rec_FollowingItems);
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
        loadData();
        //getFollowingData(1);


        return view;
    }
    private void AssignFollowingList(){

        followingList = new ArrayList<>();
        LinearLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);

        rec_FollowingItems.setLayoutManager(mLayoutManager);
        rec_FollowingItems.setItemAnimator(new DefaultItemAnimator());
        rec_FollowingItems.setHasFixedSize(true);
        rec_FollowingItems.setNestedScrollingEnabled(false);
        /* TODO USELESS WITHOUT DATABASE
        recCategory.addOnScrollListener(new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                userPage++;
                getData(userPage);

            }
        });

         */
        followingAdapter = new FollowingAdapter(getActivity() , followingList);
        rec_FollowingItems.setAdapter(followingAdapter);
        followingAdapter.setListener(this);
        ItemTouchHelper helper = new ItemTouchHelper(
                // below statement: used at move and sort
                // new ItemTouchHandler(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                //ItemTouchHelper.LEFT)
                new ItemTouchHandler(0,
                        ItemTouchHelper.LEFT)
        );
        helper.attachToRecyclerView(rec_FollowingItems);

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
            jsonParams.put("customer_ID", user.getCustomer().getID());
            jsonParams.put("language_ID", 1);
            RequestBody bodyToken = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
            //defining the call
            Call<getMyFavouriteItems> call = service.GetMyFavoriteItems(
                    bodyToken
            );
            call.enqueue(new Callback<getMyFavouriteItems>() {
                @Override
                public void onResponse(Call<getMyFavouriteItems> call, Response<getMyFavouriteItems> response) {

                    if (response.body().getStatusMessage().equals("Success")) {

                        followingList.addAll(response.body().getItems());
                        followingAdapter.notifyDataSetChanged();
                        shimmerFrameLayout.setVisibility(View.GONE);
                        shimmerFrameLayout.stopShimmer();

                    } else {
                        shimmerFrameLayout.setVisibility(View.GONE);
                        shimmerFrameLayout.stopShimmer();
                       // viewNoItems.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<getMyFavouriteItems> call, Throwable t) {

                }
            });
        }
    }
    private void getFollowingData(int userPage) {

        /*
        followingList.add(new FollowingPojo(1,"Nike White Sneakers" ,"Blue" , "240.000" ,"KD","http://plugi.mawaqaademos.com/assets/images/Uploads/2020820121846.jpg"));
        followingList.add(new FollowingPojo(2,"Nike White Sneakers" ,"Blue" , "240.000" ,"KD","http://plugi.mawaqaademos.com/assets/images/Uploads/2020820121846.jpg"));
        followingList.add(new FollowingPojo(3,"Nike White Sneakers" ,"Blue" , "240.000" ,"KD","http://plugi.mawaqaademos.com/assets/images/Uploads/2020820121846.jpg"));
        followingList.add(new FollowingPojo(4,"Nike White Sneakers" ,"Blue" , "240.000" ,"KD","http://plugi.mawaqaademos.com/assets/images/Uploads/2020820121846.jpg"));
        followingList.add(new FollowingPojo(5,"Nike White Sneakers" ,"Blue" , "240.000" ,"KD","http://plugi.mawaqaademos.com/assets/images/Uploads/2020820121846.jpg"));
        followingList.add(new FollowingPojo(6,"Nike White Sneakers" ,"Blue" , "240.000" ,"KD","http://plugi.mawaqaademos.com/assets/images/Uploads/2020820121846.jpg"));
        followingList.add(new FollowingPojo(7,"Nike White Sneakers" ,"Blue" , "240.000" ,"KD","http://plugi.mawaqaademos.com/assets/images/Uploads/2020820121846.jpg"));
        followingList.add(new FollowingPojo(8,"Nike White Sneakers" ,"Blue" , "240.000" ,"KD","http://plugi.mawaqaademos.com/assets/images/Uploads/2020820121846.jpg"));
        followingList.add(new FollowingPojo(9,"Nike White Sneakers" ,"Blue" , "240.000" ,"KD","http://plugi.mawaqaademos.com/assets/images/Uploads/2020820121846.jpg"));
        followingList.add(new FollowingPojo(10,"Nike White Sneakers" ,"Blue" , "240.000" ,"KD","http://plugi.mawaqaademos.com/assets/images/Uploads/2020820121846.jpg"));
        followingList.add(new FollowingPojo(11,"Nike White Sneakers" ,"Blue" , "240.000" ,"KD","http://plugi.mawaqaademos.com/assets/images/Uploads/2020820121846.jpg"));
        followingList.add(new FollowingPojo(12,"Nike White Sneakers" ,"Blue" , "240.000" ,"KD","http://plugi.mawaqaademos.com/assets/images/Uploads/2020820121846.jpg"));
        followingList.add(new FollowingPojo(13,"Nike White Sneakers" ,"Blue" , "240.000" ,"KD","http://plugi.mawaqaademos.com/assets/images/Uploads/2020820121846.jpg"));
        followingAdapter.notifyDataSetChanged();
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
        return R.layout.fragment_search;
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
    public void onItemClick(int position, getMyFavouriteItems.Item item) {
        ((MainActivity) requireActivity()).setSelectedFavItem(item);
        setSelectitem(item);
        Fragment fragment = null;
        fragment = new FollowingDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.BUNDLE_ITEM_ID, item.getItemId());
        fragment.setArguments(args);
        ((MainActivity) requireActivity()).replaceReviewFragments(fragment);
    }

    @Override
    public void onItemLongClick(int position, getMyFavouriteItems.Item item) {
        setSelectitem(item);
    }


    private class ItemTouchHandler extends ItemTouchHelper.SimpleCallback {

        public ItemTouchHandler(int dragDirs, int swipeDirs) {
            super(dragDirs, swipeDirs);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            int from = viewHolder.getAdapterPosition();
            int to = target.getAdapterPosition();

            Collections.swap(followingAdapter.getAgentsList(), from, to);
            followingAdapter.notifyItemMoved(from, to);
            RemoveFromFav(1);//TODO

            return true;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            followingAdapter.getAgentsList().remove(viewHolder.getAdapterPosition());
            followingAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            RemoveFromFav(1);//TODO
        }
    }

    public void RemoveFromFav(int _itemID)
    {
        Log.d("LoadData", "LoadData: " + _itemID);
        User currentUser = SharedPrefManager.getInstance(getActivity()).getUser();
        ApiInterface service = RetrofitClient.retrofitBearerTokenWrite(currentUser).create(ApiInterface.class);

        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("item_ID", _itemID);
        jsonParams.put("customer_ID", currentUser.getCustomer().getID());
        jsonParams.put("language_ID", 1);


        RequestBody bodyToken = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
        //defining the call
        Call<RemoveFromFav> call = service.RemoveMyFavorite(
                bodyToken
        );
        call.enqueue(new Callback<RemoveFromFav>() {
            @Override
            public void onResponse(Call<RemoveFromFav> call, Response<RemoveFromFav> response) {

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
            public void onFailure(Call<RemoveFromFav> call, Throwable t) {
                Log.d("ItemDetails", "onFailure: " + t.getLocalizedMessage());
            }
        });
    }

    public getMyFavouriteItems.Item getSelectitem() {
        return selectitem;
    }

    public void setSelectitem(getMyFavouriteItems.Item selectitem) {
        this.selectitem = selectitem;
    }
}
