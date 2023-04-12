package com.plugi.plugi.feature.profile;

import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.plugi.plugi.R;
import com.plugi.plugi.core.base.BaseFragment;
import com.plugi.plugi.feature.main.MainActivity;
import com.plugi.plugi.feature.main.SideMenuFragment;
import com.plugi.plugi.models.HowItWork;
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

public class HowItWorkFragment extends BaseFragment  {

    private LinearLayout contentPanel , viewNoItems;
    private TextView hiwDesc;
    WebView videoWebView;
    private ShimmerFrameLayout shimmerFrameLayout;
    ImageView ivSide , ivBack;
    TextView toolbarTitle;
    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_howitwork, container, false);
        toolbarTitle  = view.findViewById(R.id.toolbarTitle);
        toolbarTitle.setText(getResources().getString(R.string.how_its_work));
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
        contentPanel = view.findViewById(R.id.contentPanel);
        viewNoItems = view.findViewById(R.id.viewNoItems);
        hiwDesc = view.findViewById(R.id.hiwDesc);
        videoWebView = (WebView) view.findViewById(R.id.videoWebView);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            videoWebView.getSettings().setJavaScriptEnabled(true);
            videoWebView.setWebChromeClient(new WebChromeClient() {
            });
        }
        loadData();

        return view;
    }

    private void loadPojo() {
        String pojoURL = "https://www.youtube.com/embed/BUqEOLQ7jm4";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            videoWebView.loadData("<iframe width=\"100%\" height=\"100%\" src=\"" + pojoURL + "\" frameborder=\"0\" allowfullscreen></iframe>", "text/html", "utf-8");
        }
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_howitwork;
    }
    public void loadData()
    {
        ApiInterface service = RetrofitClient.retrofitWrite().create(ApiInterface.class);

        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("language_ID", 1);
        RequestBody bodyToken = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
        //defining the call
        Call<HowItWork> call = service.HowitsWork_Page(
                bodyToken
        );
        call.enqueue(new Callback<HowItWork>() {
            @Override
            public void onResponse(Call<HowItWork> call, Response<HowItWork> response) {

                if(response.body().getStatusMessage().equals("Success"))
                {

                    shimmerFrameLayout.setVisibility(View.GONE);
                    shimmerFrameLayout.stopShimmer();
                    contentPanel.setVisibility(View.VISIBLE);
                    loadPojo();
                    //loadPojo();
                }
                else
                {
                    shimmerFrameLayout.setVisibility(View.GONE);
                    shimmerFrameLayout.stopShimmer();
                    viewNoItems.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<HowItWork> call, Throwable t) {
                Log.d("HIW", "onFailure: " + t.getLocalizedMessage());
            }
        });
    }

}
