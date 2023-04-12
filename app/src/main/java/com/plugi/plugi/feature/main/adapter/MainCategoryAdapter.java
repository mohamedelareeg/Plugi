package com.plugi.plugi.feature.main.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.plugi.plugi.R;
import com.plugi.plugi.feature.main.MainActivity;
import com.plugi.plugi.feature.main.interfaces.OnCategoryClickListener;
import com.plugi.plugi.feature.main.interfaces.OnSocialClickListener;
import com.plugi.plugi.models.MainCategory;
import com.plugi.plugi.models.SocialMedia;

import java.util.List;

public class MainCategoryAdapter extends RecyclerView.Adapter<MainCategoryAdapter.ViewHolder>   {
    private List<MainCategory.Category> categoryList;
    public Context context;
    // Start with first item selected
    private int focusedItem = 0;
    private boolean sorted = false;


    private OnCategoryClickListener onCategoryClickListener;
    public MainCategoryAdapter(List<MainCategory.Category> categoryList , OnCategoryClickListener onCategoryClickListener )
    {
        this.categoryList = categoryList;
        this.sorted = false;
        this.onCategoryClickListener = onCategoryClickListener;
    }

    public void setPosition(int focusedItem , boolean sorted)
    {
        this.focusedItem = focusedItem;
        this.sorted = sorted;
    }


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
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_main_category , viewGroup , false);
        context = viewGroup.getContext();

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        final MainCategory.Category category = categoryList.get(i);

        viewHolder.item_mCategory.setText(category.getName());
        // Set selected state; use a state list drawable to style the view
        viewHolder.itemView.setSelected(focusedItem == i);
        if(i == focusedItem)
        {
            viewHolder.item_mCategory.setTextColor(Color.parseColor("#000000"));//#999999
            if(sorted) {
                onCategoryClickListener.onCategorySortedClicked(category, i);
                sorted = false;
            }
            else
            {
                onCategoryClickListener.onCategoryClicked(category, i);
            }
            viewHolder.categoryLine.setVisibility(View.VISIBLE);
        }
        else
        {
            viewHolder.item_mCategory.setTextColor(Color.parseColor("#999999"));//#999999
            viewHolder.categoryLine.setVisibility(View.GONE);
        }
        // Handle item click and set the selection
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redraw the old selection and the new
                onCategoryClickListener.onCategoryClicked(category, i);
                notifyItemChanged(focusedItem);
                focusedItem = viewHolder.getLayoutPosition();
                notifyItemChanged(focusedItem);
            }
        });

        //viewHolder.price.setText(price);//cant cast to float

    }


    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        private View mView;
        private ImageView categoryLine;
        private TextView item_mCategory;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            categoryLine = mView.findViewById(R.id.categoryLine);
            item_mCategory = mView.findViewById(R.id.item_mCategory);

        }

    }

}
