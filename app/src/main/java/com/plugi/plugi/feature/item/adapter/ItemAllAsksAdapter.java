package com.plugi.plugi.feature.item.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.plugi.plugi.R;
import com.plugi.plugi.models.itemDetails.AllitemASK;

import java.util.List;

public class ItemAllAsksAdapter extends RecyclerView.Adapter<ItemAllAsksAdapter.MostPopularViewHolder> {

    Context context;
    List<AllitemASK> categoryItemsList;
    // Start with first item selected
    private int focusedItem = 0;



    public ItemAllAsksAdapter(Context context  , List<AllitemASK> categoryItemsList ) {
        this.context = context;
        this.categoryItemsList = categoryItemsList;

    }
    @NonNull
    @Override
    public MostPopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_last_ask, parent, false);

        //here we need to create a row item layout file
        return new MostPopularViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MostPopularViewHolder holder, int position) {

        //here we will bind data to our layout
        final AllitemASK categoryItems = categoryItemsList.get(position);
        if(categoryItems.getSizeValue() != null) {
            holder.item_size_title.setText(categoryItems.getSizeValue().toString());
        }
        holder.item_ask_price.setText(categoryItems.getPriceCurrency() + "." + categoryItems.getPriceValue());

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

        TextView item_size_title , item_ask_price ;
        public MostPopularViewHolder(@NonNull View itemView) {
            super(itemView);

            item_size_title = itemView.findViewById(R.id.item_size_title);
            item_ask_price = itemView.findViewById(R.id.item_ask_price);


        }

    }




}
