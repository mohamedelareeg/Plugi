package com.plugi.plugi.feature.item;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.plugi.plugi.R;
import com.plugi.plugi.core.base.BaseFragment;
import com.plugi.plugi.core.utilities.Constants;
import com.plugi.plugi.feature.main.MainActivity;
import com.plugi.plugi.feature.main.SideMenuFragment;
import com.plugi.plugi.models.Condition;
import com.plugi.plugi.retrofit.ApiInterface;
import com.plugi.plugi.retrofit.RetrofitClient;
import com.plugi.plugi.retrofit.SharedPrefManager;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConditionFragment extends BaseFragment {

    int itemID;
    int CategoryID;
    private TextView conditionTitle , conditionContent;
    private Button btnAgree;
    private ShimmerFrameLayout shimmerFrameLayout;
    private LinearLayout contentPanel;
    ImageView ivSide , ivBack;
    TextView toolbarTitle;
    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_condition, container, false);

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

        toolbarTitle = view.findViewById(R.id.toolbarTitle);
        toolbarTitle.setText(getResources().getString(R.string.agree_to_condition_guide_line));
        itemID = getArguments().getInt(Constants.BUNDLE_ITEM_ID , 0);
        CategoryID = getArguments().getInt(Constants.BUNDLE_CATEGORY_ID , 0);
        conditionTitle = view.findViewById(R.id.conditionTitle);
        conditionContent = view.findViewById(R.id.conditionContent);
        btnAgree = view.findViewById(R.id.btnAgree);
        contentPanel = view.findViewById(R.id.contentPanel);
        shimmerFrameLayout = view.findViewById(R.id.parentShimmerLayout);
        shimmerFrameLayout.startShimmer();

        loadData();
        btnAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                fragment = new ItemDetailsFragment();
                Bundle args = new Bundle();
                args.putInt(Constants.BUNDLE_ITEM_ID, itemID);
                fragment.setArguments(args);
                ((MainActivity) getActivity()).replacePopUpFragments(fragment);
                SharedPrefManager.getInstance(getActivity()).GuestCondition();
            }
        });
        return view;
    }
    public void loadData()
    {
        ApiInterface service = RetrofitClient.retrofitWrite().create(ApiInterface.class);

        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("CategoryID", CategoryID);
        jsonParams.put("language_ID", 1);
        RequestBody bodyToken = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
        //defining the call
        Call<Condition> call = service.AgreeToConditionGuideline(
                bodyToken
        );
        call.enqueue(new Callback<Condition>() {
            @Override
            public void onResponse(Call<Condition> call, Response<Condition> response) {

                if(response.body().getStatusMessage().equals("Success"))
                {

                    conditionTitle.setText(response.body().getTitle());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        conditionContent.setText(Html.fromHtml(String.valueOf(response.body().getContent()), Html.FROM_HTML_MODE_COMPACT));
                    }
                    else
                    {
                        conditionContent.setText(response.body().getContent());
                    }
                    shimmerFrameLayout.setVisibility(View.GONE);
                    shimmerFrameLayout.stopShimmer();
                    contentPanel.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Condition> call, Throwable t) {

            }
        });
    }
    @Override
    protected int layoutRes() {
        return R.layout.fragment_condition;
    }
}
