package com.plugi.plugi.feature.profile.buyingselling.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.plugi.plugi.R;
import com.plugi.plugi.feature.profile.buyingselling.interfaces.OnPendingClickListener;
import com.plugi.plugi.models.orderDetails.PendingList;

import java.util.List;

public class PendingBuyingAdapter extends RecyclerView.Adapter<PendingBuyingAdapter.MostPopularViewHolder> {

    Context context;
    List<PendingList> itemList;
    // Start with first item selected
    private int focusedItem = 0;


    OnPendingClickListener onPendingClickListener;
    public PendingBuyingAdapter(Context context  , List<PendingList> itemList , OnPendingClickListener onPendingClickListener ) {
        this.context = context;
        this.itemList = itemList;
        this.onPendingClickListener = onPendingClickListener;

    }
    @NonNull
    @Override
    public MostPopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_pending_buying, parent, false);

        //here we need to create a row item layout file
        return new MostPopularViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MostPopularViewHolder holder, int position) {

        //here we will bind data to our layout
        final PendingList item = itemList.get(position);
        holder.item_title.setText(item.getItemName());
        holder.item_status.setText(item.getStatusName());
        // Handle item click and set the selection
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPendingClickListener.onPendingClicked(item , position);
                // Redraw the old selection and the new

            }
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();

    }

    public  static class MostPopularViewHolder extends RecyclerView.ViewHolder{

        TextView item_title  , item_status;
        public MostPopularViewHolder(@NonNull View itemView) {
            super(itemView);

            item_title = itemView.findViewById(R.id.item_title);
            item_status = itemView.findViewById(R.id.item_status);
        }

    }




}
