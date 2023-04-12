package com.plugi.plugi.feature.category.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.plugi.plugi.R;
import com.plugi.plugi.core.views.countdownview.CountdownView;
import com.plugi.plugi.feature.category.interfaces.OnItemClickListener;
import com.plugi.plugi.models.HomeScreen;

import java.util.List;

public class HomePopularBrandsAdapter extends RecyclerView.Adapter<HomePopularBrandsAdapter.MostPopularViewHolder> {

    Context context;
    List<HomeScreen.Brand> categoryItemsList;
    int categoryID;

    public HomePopularBrandsAdapter(Context context  , List<HomeScreen.Brand> categoryItemsList , int categoryID) {
        this.context = context;
        this.categoryItemsList = categoryItemsList;
        this.categoryID = categoryID;

    }
    @NonNull
    @Override
    public MostPopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_popular_brands, parent, false);

        //here we need to create a row item layout file
        return new MostPopularViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MostPopularViewHolder holder, int position) {

        //here we will bind data to our layout
        final HomeScreen.Brand categoryItems = categoryItemsList.get(position);

        // setting up image using GLIDE
        Glide.with(context).load(categoryItems.getImageURL()).placeholder(R.drawable.ic_landiing_logo).into(holder.item_image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        Log.d("RETROFIT", "onBindViewHolder: " + categoryItems.getName());

    }

    @Override
    public int getItemCount() {
        return categoryItemsList.size();

    }

    public  static class MostPopularViewHolder extends RecyclerView.ViewHolder{

        ImageView item_image;

        public MostPopularViewHolder(@NonNull View itemView) {
            super(itemView);

            item_image = itemView.findViewById(R.id.item_image);
        }
    }




}
