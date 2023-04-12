package com.plugi.plugi.feature.search.adapter;

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
import com.google.android.libraries.places.widget.Autocomplete;
import com.plugi.plugi.R;
import com.plugi.plugi.core.views.countdownview.CountdownView;
import com.plugi.plugi.feature.search.interfaces.OnSuggestClickListener;
import com.plugi.plugi.models.AutoComplete;
import com.plugi.plugi.models.SearchHistory;
import com.plugi.plugi.models.SearchItems;

import java.util.List;

public class SearchHistoryAdapter extends RecyclerView.Adapter<SearchHistoryAdapter.MostPopularViewHolder> {

    Context context;
    List<SearchHistory.RecentSearchWord> searchList;
    OnSuggestClickListener onSuggestClickListener;
    public SearchHistoryAdapter(Context context  , List<SearchHistory.RecentSearchWord> searchList , OnSuggestClickListener onSuggestClickListener) {
        this.context = context;
        this.searchList = searchList;
        this.onSuggestClickListener = onSuggestClickListener;
    }
    @NonNull
    @Override
    public MostPopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_suggest_search, parent, false);

        //here we need to create a row item layout file
        return new MostPopularViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MostPopularViewHolder holder, int position) {

        //here we will bind data to our layout
        final SearchHistory.RecentSearchWord searchItems = searchList.get(position);

        holder.item_suggest.setText(searchItems.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSuggestClickListener.onSuggestClicked(searchItems.getName() ,position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return searchList.size();

    }

    public  static class MostPopularViewHolder extends RecyclerView.ViewHolder{


        TextView item_suggest;

        public MostPopularViewHolder(@NonNull View itemView) {
            super(itemView);

            item_suggest = itemView.findViewById(R.id.item_suggest);

        }
    }




}
