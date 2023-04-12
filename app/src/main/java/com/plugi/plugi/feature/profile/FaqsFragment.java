package com.plugi.plugi.feature.profile;

import android.os.Bundle;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.plugi.plugi.R;
import com.plugi.plugi.core.base.BaseFragment;
import com.plugi.plugi.feature.main.MainActivity;
import com.plugi.plugi.feature.main.SideMenuFragment;
import com.plugi.plugi.feature.profile.adapter.FaqsAdapter;
import com.plugi.plugi.models.Faqs;
import com.plugi.plugi.retrofit.ApiInterface;
import com.plugi.plugi.retrofit.RetrofitClient;

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

public class FaqsFragment extends BaseFragment  {

    List<Faqs.FAQItem> faqItemList;
    FaqsAdapter faqsAdapter;
    RecyclerView rec_Faqs;
    LinearLayout viewNoItems;
    private ShimmerFrameLayout shimmerFrameLayout;
    ImageView ivSide , ivBack;
    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_faqs, container, false);

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

        rec_Faqs = view.findViewById(R.id.rec_Faqs);
        viewNoItems = view.findViewById(R.id.viewNoItems);
        shimmerFrameLayout = view.findViewById(R.id.parentShimmerLayout);
        shimmerFrameLayout.startShimmer();



        AssignBlogList();
        loadData();

        return view;
    }

    private void AssignBlogList() {
        faqItemList = new ArrayList<>();
        RecyclerView.LayoutManager PopularbrandslayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rec_Faqs.setLayoutManager(PopularbrandslayoutManager);
        faqsAdapter = new FaqsAdapter(getActivity(), faqItemList);
        rec_Faqs.setAdapter(faqsAdapter);
    }

    public void loadData()
    {
        ApiInterface service = RetrofitClient.retrofitWrite().create(ApiInterface.class);

        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("language_ID", 1);
        RequestBody bodyToken = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
        //defining the call
        Call<Faqs> call = service.GetFAQ(
                bodyToken
        );
        call.enqueue(new Callback<Faqs>() {
            @Override
            public void onResponse(Call<Faqs> call, Response<Faqs> response) {

                if(response.body().getStatusMessage().equals("Success"))
                {

                    faqItemList.addAll(response.body().getFAQItems());
                    faqsAdapter.notifyDataSetChanged();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    shimmerFrameLayout.stopShimmer();

                }
                else
                {
                    shimmerFrameLayout.setVisibility(View.GONE);
                    shimmerFrameLayout.stopShimmer();
                    viewNoItems.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Faqs> call, Throwable t) {

            }
        });
    }
    @Override
    protected int layoutRes() {
        return R.layout.fragment_faqs;
    }


}
