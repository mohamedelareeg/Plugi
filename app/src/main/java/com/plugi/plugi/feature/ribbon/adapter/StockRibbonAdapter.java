package com.plugi.plugi.feature.ribbon.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.plugi.plugi.R;
import com.plugi.plugi.models.HomeRibbon;
import com.plugi.plugi.models.ItemDetails;

import java.util.List;

public class StockRibbonAdapter extends RecyclerView.Adapter<StockRibbonAdapter.MostPopularViewHolder> {

    Context context;
    List<HomeRibbon.Item> itemList;


    public StockRibbonAdapter(Context context  , List<HomeRibbon.Item> itemList ) {
        this.context = context;
        this.itemList = itemList;

    }
    @NonNull
    @Override
    public MostPopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_ribbon, parent, false);

        //here we need to create a row item layout file
        return new MostPopularViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MostPopularViewHolder holder, int position) {

        //here we will bind data to our layout
        final HomeRibbon.Item item = itemList.get(position);
        holder.prop_name.setText(item.getItemName());
        holder.prop_value.setText(item.getItemValue().toString());
        if(item.getUpDown() == 1)
        {
            holder.prop_value.setTextColor(Color.parseColor("#8DCF5F"));
            holder.itemUp.setVisibility(View.VISIBLE);
            holder.itemDown.setVisibility(View.GONE);
        }else if(item.getUpDown() ==2)
        {
            holder.prop_value.setTextColor(Color.parseColor("#EC3030"));
            holder.itemUp.setVisibility(View.GONE);
            holder.itemDown.setVisibility(View.VISIBLE);
        }



    }

    @Override
    public int getItemCount() {
        return itemList.size();

    }

    public  static class MostPopularViewHolder extends RecyclerView.ViewHolder{

        TextView prop_name , prop_value;
        ImageView itemUp , itemDown;
        public MostPopularViewHolder(@NonNull View itemView) {
            super(itemView);

            prop_name = itemView.findViewById(R.id.prop_name);
            prop_value = itemView.findViewById(R.id.prop_value);
            itemUp = itemView.findViewById(R.id.itemUp);
            itemDown = itemView.findViewById(R.id.itemDown);

        }
    }




}
