package com.plugi.plugi.feature.item.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.plugi.plugi.R;
import com.plugi.plugi.core.utilities.Constants;
import com.plugi.plugi.feature.popup.ImageViewerActivity;
import com.plugi.plugi.models.itemDetails.Image;

import java.util.ArrayList;
import java.util.List;



public class ProductImageViewPagerAdapter extends PagerAdapter {
    public static List<Image> list = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private Activity activity;
    private       int                      id;
    private       int                      imagewidth, imageHeight;

    public ProductImageViewPagerAdapter(Activity activity, int id) {
        this.activity = activity;
        this.id = id;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    public void addAll(List<Image> list) {
        this.list = list;
        notifyDataSetChanged();

    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_top_banner, container, false);
        final ImageView imageView = view.findViewById(R.id.ivBanner);
        final ImageView iv_play = view.findViewById(R.id.iv_play);
        final ProgressBar progress_bar = view.findViewById(R.id.progress_bar);
        container.addView(view);

        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(activity, ImageViewerActivity.class);
                intent.putExtra(Constants.IMAGE_POSITION, position);
                intent.putExtra(Constants.IMAGE_CATEGORYID, id);
                activity.startActivity(intent);

            }
        });

        progress_bar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#04d39f"), android.graphics.PorterDuff.Mode.MULTIPLY);



        Glide.with(activity)
                .asBitmap()
                .load(list.get(position).getSrc())
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        imageView.setImageBitmap(resource);
                        progress_bar.setVisibility(View.GONE);
                    }

                });
        iv_play.setVisibility(View.GONE);


        ViewTreeObserver vto = imageView.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                imageView.getViewTreeObserver().removeOnPreDrawListener(this);
                imagewidth = imageView.getMeasuredHeight();
                imageHeight = imageView.getMeasuredHeight();
//                Log.e("Product Image  Height: " + imageView.getMeasuredHeight(), "Product Image  Width: " + imageView.getMeasuredWidth());

                return true;
            }
        });


        return view;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}
