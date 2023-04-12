package com.plugi.plugi.feature.popup;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;
import com.plugi.plugi.R;
import com.plugi.plugi.core.base.BaseActivity;

public class ContactActivity extends BaseActivity {
    private TextView toolbarTitle , btnSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbarTitle = findViewById(R.id.toolbarTitle);
        toolbarTitle.setText(getResources().getString(R.string.contact_us));

        // Back Button
        ImageView backButton = findViewById(R.id.ivBack);
        backButton.setVisibility(View.VISIBLE);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TSnackbar.make(findViewById(android.R.id.content),"Message sent Successfuly",TSnackbar.LENGTH_LONG).show(); TODO USING BASE ACTIVITY TO STORE SNACKBAR MESSAGES
                onBackPressed();

            }
        });
    }

    @Override
    protected int layoutResource() {
        return R.layout.activity_contact;
    }
}