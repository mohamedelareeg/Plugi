package com.plugi.plugi.feature.profile;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.plugi.plugi.R;
import com.plugi.plugi.core.base.BaseFragment;
import com.plugi.plugi.core.utilities.Constants;
import com.plugi.plugi.feature.category.interfaces.OnRelatedItemClickListener;
import com.plugi.plugi.feature.item.ItemDetailsFragment;
import com.plugi.plugi.feature.item.adapter.RelatedProductsAdapter;
import com.plugi.plugi.feature.main.MainActivity;
import com.plugi.plugi.feature.main.SideMenuFragment;
import com.plugi.plugi.models.Blog;
import com.plugi.plugi.models.SpecBlogDetails;
import com.plugi.plugi.models.itemDetails.Relateditem;
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

public class BlogInfoFragment extends BaseFragment implements OnRelatedItemClickListener {

    ImageView blogIMG  ,blogitemIMG;
    WebView videoWebView;
    LinearLayout relatedPanel , viewNoItems;
    RelativeLayout relatedPanelInfo , contentPanel;
    TextView blogTitle ,blogOwner ,blogDate,blogDesc, blogDesc2 ,blogItemName,blogItemType,blogItemPrice;
    private ShimmerFrameLayout shimmerFrameLayout , shimmerFrameLayout2;

    Blog blog;
    RelatedProductsAdapter relatedProductsAdapter;
    List<Relateditem> relateditemList;
    RecyclerView rec_RelatedProducts;
    ImageView ivSide , ivBack;
    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info_blog, container, false);
        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = null;
                fragment = new BlogFragment();
                ((MainActivity) getActivity()).replacePopUpFragments(fragment);
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
        shimmerFrameLayout2 = view.findViewById(R.id.parentShimmerLayout2);
        shimmerFrameLayout2.startShimmer();

        blogIMG = view.findViewById(R.id.blogIMG);
        blogitemIMG = view.findViewById(R.id.blogitemIMG);
        videoWebView = (WebView) view.findViewById(R.id.videoWebView);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            videoWebView.getSettings().setJavaScriptEnabled(true);
            videoWebView.setWebChromeClient(new WebChromeClient() {
            });
        }
        contentPanel = view.findViewById(R.id.contentPanel);
        relatedPanel = view.findViewById(R.id.relatedPanel);
        relatedPanelInfo = view.findViewById(R.id.relatedPanelInfo);
        rec_RelatedProducts = view.findViewById(R.id.rec_RelatedProducts);
        blogTitle = view.findViewById(R.id.blogTitle);
        blogOwner = view.findViewById(R.id.blogOwner);
        blogDate = view.findViewById(R.id.blogDate);
        blogDesc = view.findViewById(R.id.blogDesc);
        blogDesc2 = view.findViewById(R.id.blogDesc2);
        blogItemName = view.findViewById(R.id.blogItemName);
        blogItemType = view.findViewById(R.id.blogItemType);
        blogItemPrice = view.findViewById(R.id.blogItemPrice);

        relateditemList = new ArrayList<>();
        RecyclerView.LayoutManager PopularlayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rec_RelatedProducts.setLayoutManager(PopularlayoutManager);
        relatedProductsAdapter = new RelatedProductsAdapter(getContext(), relateditemList ,this);
        rec_RelatedProducts.setAdapter(relatedProductsAdapter);

        blog = (Blog)getArguments().getSerializable(Constants.BUNDLE_BLOG_LIST);
        if(blog != null)
        {

            loadData(blog);

        }
        else
        {
            shimmerFrameLayout.setVisibility(View.GONE);
            shimmerFrameLayout.stopShimmer();
            viewNoItems.setVisibility(View.VISIBLE);
        }




        return view;
    }


    private String splitLink(String link) {
        if (link.contains("https://youtu.be/")) {
            return link.split("https://youtu.be/")[1];
        } else {
            return link;
        }
    }
    public void loadData(Blog _blog)
    {
        ApiInterface service = RetrofitClient.retrofitWrite().create(ApiInterface.class);

        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("blogID", _blog.getBlogID());
        jsonParams.put("language_ID", 1);
        RequestBody bodyToken = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
        //defining the call
        Call<SpecBlogDetails> call = service.GetBlogDetails(
                bodyToken
        );
        call.enqueue(new Callback<SpecBlogDetails>() {
            @Override
            public void onResponse(Call<SpecBlogDetails> call, Response<SpecBlogDetails> response) {

                if(response.body().getStatusMessage().equals("Success"))
                {

                    SpecBlogDetails specBlogDetails = response.body();
                    ((MainActivity) getActivity()).setSpecBlogDetails(specBlogDetails);
                    blogTitle.setText(specBlogDetails.getBlog().getTitle());
                    blogOwner.setText(specBlogDetails.getBlog().getCreatedBy());
                    blogDate.setText(specBlogDetails.getBlog().getDate());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        blogDesc.setText(Html.fromHtml(String.valueOf(specBlogDetails.getBlog().getDescription()), Html.FROM_HTML_MODE_COMPACT));
                        blogDesc.setMovementMethod(LinkMovementMethod.getInstance());
                    }
                    else
                    {
                        blogDesc.setText(specBlogDetails.getBlog().getDescription());
                        blogDesc.setMovementMethod(LinkMovementMethod.getInstance());
                    }

                    Glide.with(getActivity()).load(specBlogDetails.getBlog().getImage()).into(blogIMG);
                    String pojoURL = "https://www.youtube.com/embed/nceUSuYjNXM";
                    String URL = "https://www.youtube.com/embed/";
                    String SplitedURL = splitLink(specBlogDetails.getBlog().getVideoURL());
                    Log.d("URL", "onResponse:" + URL + SplitedURL);
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        if(specBlogDetails.getBlog().getVideoURL() != null) {
                            videoWebView.loadData("<iframe width=\"100%\" height=\"100%\" src=\"" + URL + splitLink(specBlogDetails.getBlog().getVideoURL()) + "\" frameborder=\"0\" allowfullscreen></iframe>", "text/html", "utf-8");
                        }
                        else
                        {
                            videoWebView.loadData("<iframe width=\"100%\" height=\"100%\" src=\"" + pojoURL + "\" frameborder=\"0\" allowfullscreen></iframe>", "text/html", "utf-8");
                        }
                    }
                    if(specBlogDetails.getRelateditems().size() > 0) {
                        relatedPanel.setVisibility(View.VISIBLE);
                        relateditemList.addAll(specBlogDetails.getRelateditems());
                        relatedProductsAdapter.notifyDataSetChanged();
                        shimmerFrameLayout2.setVisibility(View.GONE);
                        shimmerFrameLayout2.stopShimmer();
                    }
                    else
                    {
                        relatedPanel.setVisibility(View.GONE);
                        shimmerFrameLayout2.setVisibility(View.GONE);
                        shimmerFrameLayout2.stopShimmer();
                    }
                    contentPanel.setVisibility(View.VISIBLE);
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
            public void onFailure(Call<SpecBlogDetails> call, Throwable t) {

            }
        });
    }
    @Override
    protected int layoutRes() {
        return R.layout.fragment_info_blog;
    }

    @Override
    public void onItemClicked(Integer itemID) {
        Fragment fragment = null;
        fragment = new ItemDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.BUNDLE_ITEM_TYPE, 3);
        args.putInt(Constants.BUNDLE_ITEM_ID, itemID);
        fragment.setArguments(args);
        ((MainActivity) getActivity()).replacePopUpFragments(fragment);
    }
}
