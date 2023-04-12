package com.plugi.plugi.feature.main.adapter;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.plugi.plugi.R;
import com.plugi.plugi.feature.main.interfaces.OnSocialClickListener;
import com.plugi.plugi.models.SocialMedia;


import java.util.List;

public class SocialAdapter extends RecyclerView.Adapter<SocialAdapter.ViewHolder>   {
    private List<SocialMedia.Link> linkList;
    public Context context;


    private OnSocialClickListener onSocialClickListener;
    public SocialAdapter(List<SocialMedia.Link> linkList , OnSocialClickListener onSocialClickListener )
    {
        this.linkList = linkList;

        this.onSocialClickListener = onSocialClickListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_social_view , viewGroup , false);
        context = viewGroup.getContext();

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        final SocialMedia.Link link = linkList.get(i);

        String img = link.getIcon();
        Glide.with(context).load(img).into(viewHolder.refLink);
        viewHolder.refLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSocialClickListener.onSocialClicked(link, i);

            }
        });

        //viewHolder.price.setText(price);//cant cast to float

    }


    @Override
    public int getItemCount() {
        return linkList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        private View mView;

        private ImageView refLink;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;

            refLink = mView.findViewById(R.id.refLink);

        }

    }

}
