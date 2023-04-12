package com.plugi.plugi.feature.item.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.plugi.plugi.R;
import com.plugi.plugi.models.itemDetails.Proptety;

import java.util.List;

public class ItemPropertiesAdapter extends RecyclerView.Adapter<ItemPropertiesAdapter.MostPopularViewHolder> {

    Context context;
    List<Proptety> categoryItemsList;


    public ItemPropertiesAdapter(Context context  , List<Proptety> categoryItemsList ) {
        this.context = context;
        this.categoryItemsList = categoryItemsList;

    }
    @NonNull
    @Override
    public MostPopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_properties, parent, false);

        //here we need to create a row item layout file
        return new MostPopularViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MostPopularViewHolder holder, int position) {

        //here we will bind data to our layout
        final Proptety categoryItems = categoryItemsList.get(position);
        holder.prop_name.setText(categoryItems.getPropName());
        holder.prop_value.setText(categoryItems.getPropValue());



    }

    @Override
    public int getItemCount() {
        return categoryItemsList.size();

    }

    public  static class MostPopularViewHolder extends RecyclerView.ViewHolder{

        TextView prop_name , prop_value;
        public MostPopularViewHolder(@NonNull View itemView) {
            super(itemView);

            prop_name = itemView.findViewById(R.id.prop_name);
            prop_value = itemView.findViewById(R.id.prop_value);

        }
    }




}
