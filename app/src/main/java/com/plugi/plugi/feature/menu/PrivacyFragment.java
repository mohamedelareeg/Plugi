package com.plugi.plugi.feature.menu;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.plugi.plugi.R;
import com.plugi.plugi.core.base.BaseFragment;
import com.plugi.plugi.feature.main.MainActivity;
import com.plugi.plugi.feature.main.SideMenuFragment;
import com.plugi.plugi.models.Privacy;
import com.plugi.plugi.retrofit.ApiInterface;
import com.plugi.plugi.retrofit.RetrofitClient;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrivacyFragment extends BaseFragment {

    private TextView privacyContent , privacyTitle;
    private ShimmerFrameLayout shimmerFrameLayout;
    private LinearLayout contentPanel;

    ImageView ivSide , ivBack;
    TextView toolbarTitle;
    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_privacy, container, false);
        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((MainActivity) getActivity()).sidemenu.getVisibility()!= View.VISIBLE)
                {
                    ((MainActivity) getActivity()).forceHiddenPopUp(false);
                }
                else {
                    ((MainActivity) getActivity()).forceHiddenSideMenu();
                }
            }
        });
        ivSide = view.findViewById(R.id.ivSide);
        ivSide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).sidemenu.setVisibility(View.VISIBLE);
                Fragment fragment = null;
                fragment = new SideMenuFragment();
                ((MainActivity) getActivity()).replaceFragments(fragment);

            }
        });
        toolbarTitle  = view.findViewById(R.id.toolbarTitle);
        toolbarTitle.setText(getResources().getString(R.string.privacy_policy));

        privacyTitle = view.findViewById(R.id.privacyTitle);
        privacyContent = view.findViewById(R.id.privacyContent);
        contentPanel = view.findViewById(R.id.contentPanel);
        shimmerFrameLayout = view.findViewById(R.id.parentShimmerLayout);
        shimmerFrameLayout.startShimmer();

        loadData();

        return view;
    }
    public void loadData()
    {
        ApiInterface service = RetrofitClient.retrofitWrite().create(ApiInterface.class);

        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("language_ID", 1);
        RequestBody bodyToken = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
        //defining the call
        Call<Privacy> call = service.PrivacyPolicy(
                bodyToken
        );
        call.enqueue(new Callback<Privacy>() {
            @Override
            public void onResponse(Call<Privacy> call, Response<Privacy> response) {

                if(response.body().getStatusMessage().equals("Success"))
                {

                    privacyTitle.setText(response.body().getTitle());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        privacyContent.setText(Html.fromHtml(String.valueOf(response.body().getContent()), Html.FROM_HTML_MODE_COMPACT));
                    }
                    else
                    {
                        privacyContent.setText(response.body().getContent());
                    }
                    shimmerFrameLayout.setVisibility(View.GONE);
                    shimmerFrameLayout.stopShimmer();
                    contentPanel.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Privacy> call, Throwable t) {

            }
        });
    }
    @Override
    protected int layoutRes() {
        return R.layout.fragment_privacy;
    }
}
