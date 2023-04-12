package com.plugi.plugi.feature.profile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.plugi.plugi.R;
import com.plugi.plugi.feature.profile.interfaces.OnBlogClickListener;
import com.plugi.plugi.models.Blog;

import java.util.List;

public class BlogsAdapter extends RecyclerView.Adapter<BlogsAdapter.MostPopularViewHolder> {

    Context context;
    List<Blog> blogList;
    OnBlogClickListener onBlogClickListener;
    public BlogsAdapter(Context context  , List<Blog> blogList , OnBlogClickListener onBlogClickListener) {
        this.context = context;
        this.blogList = blogList;
        this.onBlogClickListener = onBlogClickListener;
    }
    @NonNull
    @Override
    public MostPopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_blog, parent, false);

        //here we need to create a row item layout file
        return new MostPopularViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MostPopularViewHolder holder, int position) {

        //here we will bind data to our layout
        final Blog blog = blogList.get(position);

        holder.blogTitle.setText(blog.getTitle());
        holder.blogOwner.setText(blog.getCreatedBy());
        holder.blogDate.setText(blog.getDate());
        // setting up image using GLIDE
        Glide.with(context).load(blog.getImage()).into(holder.blogIMG);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBlogClickListener.onBlogClicked(blog , position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return blogList.size();

    }

    public  static class MostPopularViewHolder extends RecyclerView.ViewHolder{

        ImageView blogIMG;
        TextView blogTitle , blogOwner, blogDate;

        public MostPopularViewHolder(@NonNull View itemView) {
            super(itemView);
            blogIMG = itemView.findViewById(R.id.blogIMG);
            blogTitle = itemView.findViewById(R.id.blogTitle);
            blogOwner = itemView.findViewById(R.id.blogOwner);
            blogDate = itemView.findViewById(R.id.blogDate);

        }
    }




}
