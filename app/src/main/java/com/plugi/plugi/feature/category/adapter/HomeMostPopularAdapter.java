package com.plugi.plugi.feature.category.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.plugi.plugi.R;
import com.plugi.plugi.core.views.countdownview.CountdownView;
import com.plugi.plugi.feature.category.interfaces.OnItemClickListener;
import com.plugi.plugi.feature.item.ItemDetailsFragment;
import com.plugi.plugi.feature.main.MainActivity;
import com.plugi.plugi.feature.menu.ContactFragment;
import com.plugi.plugi.models.CategoryItems;
import com.plugi.plugi.models.HomeScreen;

import java.util.List;

public class HomeMostPopularAdapter extends RecyclerView.Adapter<HomeMostPopularAdapter.MostPopularViewHolder> {

    Context context;
    List<HomeScreen.MostPoPularItem> categoryItemsList;

    int categoryID;
    OnItemClickListener onItemClickListener;
    public HomeMostPopularAdapter(Context context  , List<HomeScreen.MostPoPularItem> categoryItemsList  , int categoryID , OnItemClickListener onItemClickListener) {
        this.context = context;
        this.categoryItemsList = categoryItemsList;
        this.categoryID = categoryID;
        this.onItemClickListener = onItemClickListener;
    }
    @NonNull
    @Override
    public MostPopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_search_result, parent, false);

        //here we need to create a row item layout file
        return new MostPopularViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MostPopularViewHolder holder, int position) {

        //here we will bind data to our layout
        final HomeScreen.MostPoPularItem categoryItems = categoryItemsList.get(position);

        holder.item_name.setText(categoryItems.getName());
        holder.item_desc.setText(categoryItems.getDescription1());
        holder.item_price.setText(categoryItems.getPriceCurrency() + "." + categoryItems.getPriceValue());
        holder.item_price_title.setText(categoryItems.getPriceTitle());
        holder.item_last_price.setText(categoryItems.getDescription2());
        // setting up image using GLIDE
        Glide.with(context).load(categoryItems.getImage()).placeholder(R.drawable.ic_landiing_logo).into(holder.item_image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onItemClickListener.onItemClicked(categoryItems.getItemId() , categoryID);
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
        TextView item_name , item_desc, item_price , item_price_title ,  item_last_price;
        private RatingBar itemRating;
        private CountdownView mCvCountdownView;
        public MostPopularViewHolder(@NonNull View itemView) {
            super(itemView);
            item_image = itemView.findViewById(R.id.item_image);
            item_name = itemView.findViewById(R.id.item_name);
            item_desc = itemView.findViewById(R.id.item_desc);
            item_price = itemView.findViewById(R.id.item_price);
            item_price_title = itemView.findViewById(R.id.item_price_title);
            item_last_price = itemView.findViewById(R.id.item_last_price);
            itemRating = itemView.findViewById(R.id.itemRating);
            mCvCountdownView = (CountdownView) itemView.findViewById(R.id.cv_countdownView);
        }
    }




}
