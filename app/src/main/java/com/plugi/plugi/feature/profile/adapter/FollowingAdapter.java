package com.plugi.plugi.feature.profile.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.plugi.plugi.R;
import com.plugi.plugi.core.utilities.SparseBooleanArrayParcelable;
import com.plugi.plugi.models.getMyFavouriteItems;

import java.util.ArrayList;
import java.util.List;

public class FollowingAdapter extends RecyclerView.Adapter<FollowingAdapter. AgentViewHolder> {
    //DefaultCategory = 1
    //InnerSubCategory = 2
    private List<getMyFavouriteItems.Item> followingList;
    Context context;
    private ServiesAdapterListener listener;
    public final SparseBooleanArrayParcelable selectedItems = new SparseBooleanArrayParcelable();
    private int currentSelectedPos;

    public FollowingAdapter(Context context, List<getMyFavouriteItems.Item> followingList) {
        this.context = context;
        this.followingList = followingList;
    }

    public void setListener(ServiesAdapterListener listener) {
        this.listener = listener;
    }

    public List<getMyFavouriteItems.Item> getAgentsList() {
        return followingList;
    }

    @NonNull
    @Override
    public AgentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_following, parent, false);

        return new  AgentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  AgentViewHolder holder, final int position) {
        final getMyFavouriteItems.Item items = followingList.get(position);

        // setting up image using GLIDE
        holder.itemName.setText(items.getName());
        holder.itemColor.setText(items.getDescription1());
        holder.itemPrice.setText(items.getPriceCurrency() + " " + items.getPriceValue());
        holder.setFollowingImage(items.getImage());

        holder.bind(items);
       // holder.agentSelected.setEnabled(false);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedItems.size() > 0 && listener != null) {
                    listener.onItemClick(position, items);
                }
                else
                {
                    listener.onItemClick(position, items);
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (listener != null)
                    listener.onItemLongClick(position , items);
                return true;
            }
        });

        if (currentSelectedPos == position) currentSelectedPos = -1;

        //holder.setCategoryImage(categories.getImage());


    }

    @Override
    public int getItemCount() {
        return followingList.size();
    }

    public void deleteEmails() {
        List<getMyFavouriteItems.Item> followings = new ArrayList<>();
        for (getMyFavouriteItems.Item following : this.followingList) {
            if (following.isSelected())
                followings.add(following);
        }

        this.followingList.removeAll(followings);
        notifyDataSetChanged();
        currentSelectedPos = -1;
    }

    public void toggleSelection(int position) {
        currentSelectedPos = position;
        if (selectedItems.get(position)) {
            selectedItems.delete(position);
            followingList.get(position).setSelected(false);
        } else {
            selectedItems.put(position, true);
            followingList.get(position).setSelected(true);
        }
        notifyItemChanged(position);
    }
    class AgentViewHolder extends RecyclerView.ViewHolder {
        TextView itemName ,itemColor , itemPrice;
        ImageView itemImage;

        private  AgentViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.itemImage);
            itemName = itemView.findViewById(R.id.itemName);
            itemColor = itemView.findViewById(R.id.itemColor);
            itemPrice = itemView.findViewById(R.id.itemPrice);
        }
        private void setFollowingImage(String url) {
            Glide.with(context).load(url).into(itemImage);

        }
        void bind(getMyFavouriteItems.Item agents)
        {
            if (agents.isSelected()) {
                /*
                agentSelectNotify.setBackground(oval(Color.rgb(26, 115, 233), agentSelectNotify));
                GradientDrawable gradientDrawable = new GradientDrawable();
                gradientDrawable.setShape(GradientDrawable.RECTANGLE);
                gradientDrawable.setCornerRadius(32f);
                gradientDrawable.setColor(Color.rgb(232, 240, 253));
                itemView.setBackground(gradientDrawable);

                 */
                //agentSelectNotify.setVisibility(View.VISIBLE);
                //agentSelected.setChecked(true);
            } else {
                /*
                GradientDrawable gradientDrawable = new GradientDrawable();
                gradientDrawable.setShape(GradientDrawable.RECTANGLE);
                gradientDrawable.setCornerRadius(32f);
                gradientDrawable.setColor(Color.WHITE);
                itemView.setBackground(gradientDrawable);

                 */
                //agentSelectNotify.setVisibility(View.GONE);
                //agentSelected.setChecked(false);
            }

            // animation
            if (selectedItems.size() > 0) {
                //animate(agentSelectNotify, agents);
            }
        }
        private void animate(final TextView view, final getMyFavouriteItems.Item agents) {
            ObjectAnimator oa1 = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0f);
            final ObjectAnimator oa2 = ObjectAnimator.ofFloat(view, "scaleX", 0f, 1f);
            oa1.setInterpolator(new DecelerateInterpolator());
            oa2.setInterpolator(new AccelerateDecelerateInterpolator());
            oa1.setDuration(200);
            oa2.setDuration(200);

            oa1.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    if (agents.isSelected())
                        view.setText("\u2713");
                    oa2.start();
                }
            });
            oa1.start();
        }

    }
    public interface ServiesAdapterListener {
        void onItemClick(int position , getMyFavouriteItems.Item item);
        void onItemLongClick(int position , getMyFavouriteItems.Item item);
    }

    private static ShapeDrawable oval(@ColorInt int color, View view) {
        ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShape());
        shapeDrawable.setIntrinsicHeight(view.getHeight());
        shapeDrawable.setIntrinsicWidth(view.getWidth());
        shapeDrawable.getPaint().setColor(color);
        return shapeDrawable;
    }

}
