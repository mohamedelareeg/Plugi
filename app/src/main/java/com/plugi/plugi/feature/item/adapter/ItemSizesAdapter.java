package com.plugi.plugi.feature.item.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.plugi.plugi.R;
import com.plugi.plugi.feature.item.interfaces.OnItemSizeClickListener;
import com.plugi.plugi.models.itemDetails.AllItemSize;

import java.util.List;

public class ItemSizesAdapter extends RecyclerView.Adapter<ItemSizesAdapter.MostPopularViewHolder> {

    Context context;
    List<AllItemSize> categoryItemsList;
    // Start with first item selected
    private int focusedItem = 0;
    OnItemSizeClickListener onItemSizeClickListener;
    @Override
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        // Handle key up and key down and attempt to move selection
        recyclerView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                RecyclerView.LayoutManager lm = recyclerView.getLayoutManager();

                // Return false if scrolled to the bounds and allow focus to move off the list
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                        return tryMoveSelection(lm, 1);
                    } else if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
                        return tryMoveSelection(lm, -1);
                    }
                }

                return false;
            }
        });
    }

    private boolean tryMoveSelection(RecyclerView.LayoutManager lm, int direction) {
        int tryFocusItem = focusedItem + direction;

        // If still within valid bounds, move the selection, notify to redraw, and scroll
        if (tryFocusItem >= 0 && tryFocusItem < getItemCount()) {
            notifyItemChanged(focusedItem);
            focusedItem = tryFocusItem;
            notifyItemChanged(focusedItem);
            lm.scrollToPosition(focusedItem);
            return true;
        }

        return false;
    }

    public ItemSizesAdapter(Context context  , List<AllItemSize> categoryItemsList , int focusedItem, OnItemSizeClickListener onItemSizeClickListener  ) {
        this.context = context;
        this.categoryItemsList = categoryItemsList;
        this.focusedItem = focusedItem;
        this.onItemSizeClickListener = onItemSizeClickListener;

    }
    @NonNull
    @Override
    public MostPopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_sizes, parent, false);

        //here we need to create a row item layout file
        return new MostPopularViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MostPopularViewHolder holder, int position) {

        //here we will bind data to our layout
        final AllItemSize categoryItems = categoryItemsList.get(position);
        if(categoryItems.getSizeValue() != null) {
            holder.item_size_title.setText(categoryItems.getSizeValue().toString());
        }
        else
        {
            holder.item_size_title.setText(context.getResources().getString(R.string.all));
        }
        holder.item_size.setText(categoryItems.getPriceCurrency() + "." + categoryItems.getPriceValue());

        holder.itemView.setSelected(focusedItem == position);
        if(position == focusedItem)
        {
            holder.SizePanel.setCardBackgroundColor(Color.parseColor("#1976D2"));//#999999
            holder.item_size.setTextColor(Color.parseColor("#FFFFFF"));
            holder.item_size_title.setTextColor(Color.parseColor("#FFFFFF"));

        }
        else
        {
            holder.SizePanel.setCardBackgroundColor(Color.parseColor("#FFFFFF"));//#999999
            holder.item_size.setTextColor(Color.parseColor("#000000"));
            holder.item_size_title.setTextColor(Color.parseColor("#000000"));
        }
        // Handle item click and set the selection
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redraw the old selection and the new
                notifyItemChanged(focusedItem);
                focusedItem = holder.getLayoutPosition();
                notifyItemChanged(focusedItem);
                onItemSizeClickListener.onItemSizeClicked(categoryItems , position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryItemsList.size();

    }

    public  static class MostPopularViewHolder extends RecyclerView.ViewHolder{
        MaterialCardView SizePanel;
        TextView item_size_title , item_size;
        public MostPopularViewHolder(@NonNull View itemView) {
            super(itemView);
            SizePanel = itemView.findViewById(R.id.SizePanel);
            item_size_title = itemView.findViewById(R.id.item_size_title);
            item_size = itemView.findViewById(R.id.item_size);

        }

    }




}
