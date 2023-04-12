package com.plugi.plugi.feature.search.filter.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.plugi.plugi.R;
import com.plugi.plugi.feature.search.filter.interfaces.OnSortClickListener;
import com.plugi.plugi.models.SortIDs;

import java.util.List;

public class SortItemAdapter extends RecyclerView.Adapter<SortItemAdapter.MostPopularViewHolder> {

    Context context;
    List<SortIDs.SortListID> itemList;
    OnSortClickListener onSortClickListener;
    // Start with first item selected
    private int focusedItem = 0;
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
    public SortItemAdapter(Context context  , List<SortIDs.SortListID> itemList  , int focusedItem , OnSortClickListener onSortClickListener ) {
        this.context = context;
        this.itemList = itemList;
        this.focusedItem = focusedItem;
        this.onSortClickListener = onSortClickListener;

    }

    @NonNull
    @Override
    public MostPopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_sort, parent, false);

        //here we need to create a row item layout file
        return new MostPopularViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MostPopularViewHolder holder, int position) {

        //here we will bind data to our layout
        final SortIDs.SortListID item = itemList.get(position);
        holder.sortName.setText(item.getName());
        holder.sortDesc.setText(item.getDesc());
        holder.itemView.setSelected(focusedItem == position);
        if(position == focusedItem)
        {
            holder.sortDesc.setTextColor(Color.parseColor("#000000"));
            holder.sortIMG.setVisibility(View.VISIBLE);

        }
        else
        {
            holder.sortDesc.setTextColor(Color.parseColor("#999999"));
            holder.sortIMG.setVisibility(View.GONE);
        }
        // Handle item click and set the selection
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redraw the old selection and the new
                notifyItemChanged(focusedItem);
                focusedItem = holder.getLayoutPosition();
                notifyItemChanged(focusedItem);
                onSortClickListener.onSortClicked(item , position);
            }
        });



    }

    @Override
    public int getItemCount() {
        return itemList.size();

    }

    public  static class MostPopularViewHolder extends RecyclerView.ViewHolder{

        TextView sortName , sortDesc;
        ImageView sortIMG ;
        public MostPopularViewHolder(@NonNull View itemView) {
            super(itemView);

            sortName = itemView.findViewById(R.id.sortName);
            sortDesc = itemView.findViewById(R.id.sortDesc);
            sortIMG = itemView.findViewById(R.id.sortIMG);

        }
    }




}
