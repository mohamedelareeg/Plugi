package com.plugi.plugi.feature.profile.buyingselling.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.plugi.plugi.R;
import com.plugi.plugi.feature.profile.buyingselling.interfaces.OnCurrentClickListener;
import com.plugi.plugi.feature.profile.buyingselling.interfaces.OnCurrentEditClickListener;
import com.plugi.plugi.models.orderDetails.CurrentList;

import java.util.List;

public class CurrentBuyingAdapter extends RecyclerView.Adapter<CurrentBuyingAdapter.MostPopularViewHolder> {

    Context context;
    List<CurrentList> itemList;
    // Start with first item selected
    private int focusedItem = 0;


    OnCurrentClickListener onCurrentClickListener;
    OnCurrentEditClickListener onCurrentEditClickListener;
    public CurrentBuyingAdapter(Context context  , List<CurrentList> itemList  , OnCurrentClickListener onCurrentClickListener , OnCurrentEditClickListener onCurrentEditClickListener) {
        this.context = context;
        this.itemList = itemList;
        this.onCurrentClickListener = onCurrentClickListener;
        this.onCurrentEditClickListener = onCurrentEditClickListener;

    }
    @NonNull
    @Override
    public MostPopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_current_buying, parent, false);

        //here we need to create a row item layout file
        return new MostPopularViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MostPopularViewHolder holder, int position) {

        //here we will bind data to our layout
        final CurrentList item = itemList.get(position);
        holder.item_title.setText(item.getItemName());
        holder.item_desc.setText(item.getStatusName());
        holder.item_size.setText(item.getPriceCurrency() + "." + item.getSizeValue());
        holder.item_amount.setText(String.valueOf(item.getPriceValue()));
        holder.item_expires.setText(item.getPriceDate());
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCurrentEditClickListener.onCurrentEditClicked(item , position);
                // Redraw the old selection and the new

            }
        });
        // Handle item click and set the selection
        holder.InfoPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCurrentClickListener.onCurrentClicked(item , position);
                // Redraw the old selection and the new

            }
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();

    }

    public  static class MostPopularViewHolder extends RecyclerView.ViewHolder{

        TextView item_title , item_desc , item_size , item_amount , item_expires;
        ImageView btnEdit;
        LinearLayout InfoPanel;
        public MostPopularViewHolder(@NonNull View itemView) {
            super(itemView);

            item_title = itemView.findViewById(R.id.item_title);
            item_desc = itemView.findViewById(R.id.item_desc);
            item_size = itemView.findViewById(R.id.item_size);
            item_amount = itemView.findViewById(R.id.item_amount);
            item_expires = itemView.findViewById(R.id.item_expires);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            InfoPanel = itemView.findViewById(R.id.InfoPanel);

        }

    }




}
