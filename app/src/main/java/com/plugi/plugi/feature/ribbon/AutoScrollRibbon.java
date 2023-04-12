package com.plugi.plugi.feature.ribbon;


import android.os.Bundle;
import android.os.Handler;
import android.util.ArrayMap;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import com.plugi.plugi.R;
import com.plugi.plugi.core.base.BaseFragment;
import com.plugi.plugi.feature.ribbon.adapter.StockRibbonAdapter;
import com.plugi.plugi.models.HomeRibbon;
import com.plugi.plugi.models.HomeScreen;
import com.plugi.plugi.retrofit.ApiInterface;
import com.plugi.plugi.retrofit.RetrofitClient;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AutoScrollRibbon extends BaseFragment {
    RecyclerView rvTickerList;
    List<HomeRibbon.Item> stockListModels = new ArrayList<>();
    private StockRibbonAdapter stockRibbonAdapter;
    int scrollCount = 0;

    public AutoScrollRibbon() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ribbon, container, false);
        rvTickerList = view.findViewById(R.id.rec_scroll_stock);
        LoadRibbon();
        return view;
    }

    private void LoadRibbon() {

        ApiInterface service = RetrofitClient.retrofitWrite().create(ApiInterface.class);

        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("categoryID", 1);
        jsonParams.put("language_ID", 1);
        RequestBody bodyToken = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
        //defining the call
        Call<HomeRibbon> call = service.GetHomeMarquee(
                bodyToken
        );
        call.enqueue(new Callback<HomeRibbon>() {
            @Override
            public void onResponse(Call<HomeRibbon> call, Response<HomeRibbon> response) {
                Log.d("CATEGORY", "onResponse: " + response.body().getStatusMessage());
                if(response.body().getStatusMessage().equals("Success"))
                {
                    autoScroll(response.body());


                }
            }

            @Override
            public void onFailure(Call<HomeRibbon> call, Throwable t) {
                Log.d("CATEGORY", "onFailure: " + t.getLocalizedMessage());
            }
        });
    }
    private void autoScroll(HomeRibbon homeRibbon) {
        stockListModels = new ArrayList<>();
        stockListModels.addAll(homeRibbon.getItems());
        stockRibbonAdapter = new StockRibbonAdapter( getActivity() ,stockListModels);
        rvTickerList.setAdapter(stockRibbonAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext()) {

            @Override
            public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
                try {
                    LinearSmoothScroller smoothScroller = new LinearSmoothScroller(getActivity()) {
                        private static final float SPEED = 3500f;// Change this value (default=25f)

                        @Override
                        protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                            return SPEED / displayMetrics.densityDpi;
                        }
                    };
                    smoothScroller.setTargetPosition(position);
                    startSmoothScroll(smoothScroller);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        //  LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        autoScrollAnother();
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvTickerList.setLayoutManager(layoutManager);
        rvTickerList.setHasFixedSize(true);
        rvTickerList.setItemViewCacheSize(1000);
        rvTickerList.setDrawingCacheEnabled(true);
        rvTickerList.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        rvTickerList.setAdapter(stockRibbonAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(stockListModels.size() > 0) {
            autoScrollAnother();
        }

    }

    /**
     * Autoscroll detected from here, where counter, time and runnable is declared.
     */
    public void autoScrollAnother() {
        scrollCount = 0;
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                rvTickerList.smoothScrollToPosition((scrollCount++));
                if (scrollCount == stockListModels.size() - 2) {
                    stockListModels.addAll(stockListModels);
                    stockRibbonAdapter.notifyDataSetChanged();
                }
                handler.postDelayed(this, 2000);
            }
        };
        handler.postDelayed(runnable, 2000);
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_ribbon;
    }
}
