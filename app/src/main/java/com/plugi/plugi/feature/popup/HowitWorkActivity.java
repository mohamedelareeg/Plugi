package com.plugi.plugi.feature.popup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.plugi.plugi.R;
import com.plugi.plugi.core.base.BaseActivity;

public class HowitWorkActivity extends BaseActivity {

    private TextView toolbarTitle;
    WebView videoWeb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Back Button
        ImageView backButton = findViewById(R.id.ivBack);
        backButton.setVisibility(View.VISIBLE);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        toolbarTitle = findViewById(R.id.toolbarTitle);
        toolbarTitle.setText(getResources().getString(R.string.how_its_work));

        videoWeb = (WebView) findViewById(R.id.videoWebView);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            videoWeb.getSettings().setJavaScriptEnabled(true);
            videoWeb.setWebChromeClient(new WebChromeClient() {
            });
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            String pojoURL = "x3c1ih2NJEg";
            videoWeb.loadData("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/" + pojoURL + "\" frameborder=\"0\" allowfullscreen></iframe>", "text/html", "utf-8");
        }
    }

    @Override
    protected int layoutResource() {
        return R.layout.activity_howit_work;
    }
}