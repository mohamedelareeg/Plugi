package com.plugi.plugi.feature.profile.buyingselling.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.plugi.plugi.R;
import com.plugi.plugi.feature.profile.buyingselling.interfaces.OnHistoryClickListener;
import com.plugi.plugi.models.orderDetails.HistoryList;

import java.util.List;

public class HistoryBuyingAdapter extends RecyclerView.Adapter<HistoryBuyingAdapter.MostPopularViewHolder> {

    Context context;
    List<HistoryList> itemList;
    // Start with first item selected
    private int focusedItem = 0;


    OnHistoryClickListener onHistoryClickListener;
    public HistoryBuyingAdapter(Context context  , List<HistoryList> itemList  , OnHistoryClickListener onHistoryClickListener ) {
        this.context = context;
        this.itemList = itemList;
        this.onHistoryClickListener = onHistoryClickListener;

    }
    @NonNull
    @Override
    public MostPopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_history_buying, parent, false);

        //here we need to create a row item layout file
        return new MostPopularViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MostPopularViewHolder holder, int position) {

        //here we will bind data to our layout
        final HistoryList item = itemList.get(position);
        holder.item_title.setText(item.getItemName());
        holder.item_desc.setText(item.getStatusName());
        holder.item_size.setText(item.getPriceCurrency() + "." + item.getSizeValue());
        holder.item_date.setText(item.getPriceDate());
        holder.item_price.setText(String.valueOf(item.getPriceValue()));
        // Handle item click and set the selection
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onHistoryClickListener.onHistoryClicked(item , position);
                // Redraw the old selection and the new

            }
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();

    }

    public  static class MostPopularViewHolder extends RecyclerView.ViewHolder{

        TextView item_title , item_desc , item_size  , item_date , item_price;
        public MostPopularViewHolder(@NonNull View itemView) {
            super(itemView);

            item_title = itemView.findViewById(R.id.item_title);
            item_desc = itemView.findViewById(R.id.item_desc);
            item_size = itemView.findViewById(R.id.item_size);
            item_date = itemView.findViewById(R.id.item_date);
            item_price = itemView.findViewById(R.id.item_price);
        }

    }




}
