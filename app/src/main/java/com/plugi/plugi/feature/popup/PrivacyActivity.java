package com.plugi.plugi.feature.popup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.plugi.plugi.R;
import com.plugi.plugi.core.base.BaseActivity;

public class PrivacyActivity extends BaseActivity {

    private TextView toolbarTitle;
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
        toolbarTitle.setText(getResources().getString(R.string.privacy_policy));
    }

    @Override
    protected int layoutResource() {
        return R.layout.activity_privacy;
    }
}