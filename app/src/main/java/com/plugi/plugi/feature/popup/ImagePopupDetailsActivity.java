package com.plugi.plugi.feature.popup;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.plugi.plugi.R;
import com.plugi.plugi.core.base.BaseActivity;
import com.plugi.plugi.core.utilities.Constants;
import com.plugi.plugi.core.utilities.StringUtils;
import com.plugi.plugi.core.views.TouchImageView;


/**
 * Created by Mohamed El Sayed
 */
public class ImagePopupDetailsActivity extends BaseActivity {
    private static final String TAG = ImagePopupDetailsActivity.class.getName();

    //private Message message;
    private String imgURL;
    private String product_name;
//    private FloatingActionButton mBtnShare;
//    private FloatingActionButton mBtnDownload;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_popup_details);

        imgURL = getIntent().getExtras().getString(Constants.BUNDLE_PRODUCTS_IMAGE);
        product_name = getIntent().getExtras().getString(Constants.BUNDLE_PRODUCTS_NAME);
        // ### begin toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(product_name);
        // ### end toolbar


        registerViews();
        try {
            // ### begin image
            setImage(imgURL);
            // ### end image
        }
        catch (Exception e)
        {
            Toast.makeText(this, "" + e, Toast.LENGTH_SHORT).show();
        }

        // ### begin title
        String title = product_name;
        if (StringUtils.isValid(title)) {
            TextView mTitle = findViewById(R.id.image_title);
            mTitle.setText(title);
        }
        // ### end title


        // ### end sender

        // ### end timestamp


//        // change the statusbar color
//        ThemeUtils.changeStatusBarColor(this, getResources().getColor(R.color.black));

//        initListeners();
    }


    private void registerViews() {
        Log.i(TAG, "registerViews");


//        mBtnShare = (FloatingActionButton) findViewById(R.id.share);
//        mBtnDownload = (FloatingActionButton) findViewById(R.id.download);
    }


//    private void initListeners() {
//        mBtnShare.setOnClickListener(onShareClickListener);
//        mBtnDownload.setOnClickListener(onDownloadClickListener);
//    }
//
//    private View.OnClickListener onShareClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            Snackbar.make(findViewById(R.id.coordinator), "share pressed", Snackbar.LENGTH_LONG).show();
//        }
//    };
//
//    private View.OnClickListener onDownloadClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            Snackbar.make(findViewById(R.id.coordinator), "download pressed", Snackbar.LENGTH_LONG).show();
//        }
//    };




    private void setImage(String imgUrl) {
        Log.i(TAG, "setImage");

        final TouchImageView mImage = findViewById(R.id.image);

        mImage.setOnTouchImageViewListener(new TouchImageView.OnTouchImageViewListener() {
            @Override
            public void onMove() {
//                RectF rect = mImage.getZoomedRect();
//                float currentZoom = mImage.getCurrentZoom();
//                boolean isZoomed = mImage.isZoomed();
            }
        });


        // https://github.com/MikeOrtiz/TouchImageView/issues/135
        Glide.with(this)
                .asBitmap()
                .load(imgUrl)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        mImage.setImageBitmap(resource);
                    }

                });

//                // make the imageview zoomable
//                // source : https://github.com/chrisbanes/PhotoView
//                PhotoViewAttacher mAttacher = new PhotoViewAttacher(mImage);
//                mAttacher.update();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int layoutResource() {
        return R.layout.activity_image_popup_details;
    }
}
