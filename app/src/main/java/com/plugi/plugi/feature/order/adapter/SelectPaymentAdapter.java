package com.plugi.plugi.feature.order.adapter;

import android.content.Context;
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
import com.plugi.plugi.feature.order.interfaces.OnPaymentMethodClickListener;
import com.plugi.plugi.models.profile.CustomerPaymentCard;

import java.util.List;

public class SelectPaymentAdapter extends RecyclerView.Adapter<SelectPaymentAdapter.MostPopularViewHolder> {

    Context context;
    List<CustomerPaymentCard> items;
    // Start with first item selected
    private int focusedItem = 0;
    OnPaymentMethodClickListener onPaymentMethodClickListener;
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

    public SelectPaymentAdapter(Context context  , List<CustomerPaymentCard> items  , int focusedItem , OnPaymentMethodClickListener onPaymentMethodClickListener) {
        this.context = context;
        this.items = items;
        this.focusedItem = focusedItem;
        this.onPaymentMethodClickListener = onPaymentMethodClickListener;

    }
    @NonNull
    @Override
    public MostPopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_select_payment, parent, false);

        //here we need to create a row item layout file
        return new MostPopularViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MostPopularViewHolder holder, int position) {

        //here we will bind data to our layout
        final CustomerPaymentCard item = items.get(position);
        holder.itemType.setText(item.getCardTypeName().toString());
        holder.itemCardNo.setText(item.getCardNo().toString());
        holder.itemValidity.setText(item.getExpirationDate());
        holder.itemView.setSelected(focusedItem == position);
        // setting up image using GLIDE
        String pojoImage = "https://cdn.iconscout.com/icon/free/png-512/visa-3-226460.png";
        Glide.with(context).load(pojoImage).placeholder(R.drawable.ic_landiing_logo).into(holder.item_image);

        if(position == focusedItem)
        {
            holder.item_Selected.setVisibility(View.VISIBLE);

        }
        else
        {
            holder.item_Selected.setVisibility(View.INVISIBLE);
        }
        // Handle item click and set the selection
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redraw the old selection and the new
                notifyItemChanged(focusedItem);
                focusedItem = holder.getLayoutPosition();
                notifyItemChanged(focusedItem);
                onPaymentMethodClickListener.onPaymentMethodClicked(item , position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();

    }

    public  static class MostPopularViewHolder extends RecyclerView.ViewHolder{
        ImageView item_Selected;
        ImageView item_image ;
        TextView itemType , itemCardNo, itemValidity ;
        public MostPopularViewHolder(@NonNull View itemView) {
            super(itemView);
            item_Selected = itemView.findViewById(R.id.item_Selected);
            item_image = itemView.findViewById(R.id.item_image);
            itemType = itemView.findViewById(R.id.itemType);
            itemCardNo = itemView.findViewById(R.id.itemCardNo);
            itemValidity = itemView.findViewById(R.id.itemValidity);

        }

    }




}
