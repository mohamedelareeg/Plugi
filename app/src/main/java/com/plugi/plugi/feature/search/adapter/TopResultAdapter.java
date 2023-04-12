package com.plugi.plugi.feature.search.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.libraries.places.widget.Autocomplete;
import com.plugi.plugi.R;
import com.plugi.plugi.models.AutoComplete;
import com.plugi.plugi.models.PojoOnComplete;
import com.plugi.plugi.models.SearchItems;

import java.util.List;

public class TopResultAdapter extends RecyclerView.Adapter<TopResultAdapter.MostPopularViewHolder> {

    Context context;
    List<AutoComplete.AutoComplateItemList> searchList;

    public TopResultAdapter(Context context  , List<AutoComplete.AutoComplateItemList> searchList) {
        this.context = context;
        this.searchList = searchList;
    }
    @NonNull
    @Override
    public MostPopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_top_result_search, parent, false);

        //here we need to create a row item layout file
        return new MostPopularViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MostPopularViewHolder holder, int position) {

        //here we will bind data to our layout
        final AutoComplete.AutoComplateItemList searchItems = searchList.get(position);

        holder.item_Brand.setText(searchItems.getDescription1());//TODO
        holder.item_Name.setText(searchItems.getName());//TODO

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return searchList.size();

    }

    public  static class MostPopularViewHolder extends RecyclerView.ViewHolder{


        TextView item_Brand ,item_Name;

        public MostPopularViewHolder(@NonNull View itemView) {
            super(itemView);
            item_Brand = itemView.findViewById(R.id.item_Brand);
            item_Name = itemView.findViewById(R.id.item_Name);

        }
    }




}
