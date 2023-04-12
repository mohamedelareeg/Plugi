package com.plugi.plugi.feature.search.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.plugi.plugi.R;
import com.plugi.plugi.feature.search.interfaces.OnSuggestClickListener;
import com.plugi.plugi.models.PojoOnComplete;

import java.util.List;

public class OnCompleteAdapter extends RecyclerView.Adapter<OnCompleteAdapter.MostPopularViewHolder> {

    Context context;
    List<PojoOnComplete> searchList;
    int txtLength;
    OnSuggestClickListener onSuggestClickListener;
    public OnCompleteAdapter(Context context  , List<PojoOnComplete> searchList , int txtLength , OnSuggestClickListener onSuggestClickListener) {
        this.context = context;
        this.searchList = searchList;
        this.txtLength = txtLength;
        this.onSuggestClickListener = onSuggestClickListener;
    }
    @NonNull
    @Override
    public MostPopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_on_complete_search, parent, false);

        //here we need to create a row item layout file
        return new MostPopularViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MostPopularViewHolder holder, int position) {

        //here we will bind data to our layout
        final PojoOnComplete searchItems = searchList.get(position);

        Spannable WordtoSpan = new SpannableString(searchItems.getSearchKey());
        WordtoSpan.setSpan(new ForegroundColorSpan(Color.GRAY), txtLength, searchItems.getSearchKey().length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        holder.searchKey.setText(WordtoSpan);
        holder.searchCount.setText(searchItems.getResultCount().toString());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSuggestClickListener.onSuggestClicked(searchItems.getSearchKey() , position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return searchList.size();

    }

    public  static class MostPopularViewHolder extends RecyclerView.ViewHolder{


        TextView searchKey ,searchCount;

        public MostPopularViewHolder(@NonNull View itemView) {
            super(itemView);
            searchKey = itemView.findViewById(R.id.searchKey);
            searchCount = itemView.findViewById(R.id.searchCount);

        }
    }




}
