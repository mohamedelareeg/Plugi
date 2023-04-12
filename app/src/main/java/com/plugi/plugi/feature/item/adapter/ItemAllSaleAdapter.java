package com.plugi.plugi.feature.item.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.plugi.plugi.R;
import com.plugi.plugi.models.itemDetails.AllSale;

import java.util.List;

public class ItemAllSaleAdapter extends RecyclerView.Adapter<ItemAllSaleAdapter.MostPopularViewHolder> {

    Context context;
    List<AllSale> categoryItemsList;
    // Start with first item selected
    private int focusedItem = 0;



    public ItemAllSaleAdapter(Context context  , List<AllSale> categoryItemsList ) {
        this.context = context;
        this.categoryItemsList = categoryItemsList;

    }
    @NonNull
    @Override
    public MostPopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_all_sale, parent, false);

        //here we need to create a row item layout file
        return new MostPopularViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MostPopularViewHolder holder, int position) {

        //here we will bind data to our layout
        final AllSale categoryItems = categoryItemsList.get(position);
        if(categoryItems.getSizeValue() != null) {
            holder.item_size_title.setText(categoryItems.getSizeValue().toString());
        }
        holder.item_sale_price.setText(categoryItems.getPriceCurrency() + "." + categoryItems.getPriceValue());
        holder.item_sale_date.setText(categoryItems.getPriceDate());

        // Handle item click and set the selection
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redraw the old selection and the new

            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryItemsList.size();

    }

    public  static class MostPopularViewHolder extends RecyclerView.ViewHolder{

        TextView item_size_title , item_sale_price , item_sale_date;
        public MostPopularViewHolder(@NonNull View itemView) {
            super(itemView);

            item_size_title = itemView.findViewById(R.id.item_size_title);
            item_sale_price = itemView.findViewById(R.id.item_sale_price);
            item_sale_date = itemView.findViewById(R.id.item_sale_date);

        }

    }




}
