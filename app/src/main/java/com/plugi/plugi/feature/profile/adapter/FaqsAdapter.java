package com.plugi.plugi.feature.profile.adapter;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.plugi.plugi.R;
import com.plugi.plugi.core.views.expandablePanel.Expandable;
import com.plugi.plugi.core.views.expandablePanel.ExpandableIconStyles;
import com.plugi.plugi.feature.profile.interfaces.OnBlogClickListener;
import com.plugi.plugi.models.Blogs;
import com.plugi.plugi.models.Faqs;

import java.util.List;

public class FaqsAdapter extends RecyclerView.Adapter<FaqsAdapter.MostPopularViewHolder> {

    Context context;
    List<Faqs.FAQItem> items;

    public FaqsAdapter(Context context  , List<Faqs.FAQItem> items ) {
        this.context = context;
        this.items = items;

    }
    @NonNull
    @Override
    public MostPopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_faqs, parent, false);

        //here we need to create a row item layout file
        return new MostPopularViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MostPopularViewHolder holder, int position) {

        //here we will bind data to our layout
        final Faqs.FAQItem item = items.get(position);

        holder.faqsQuestion.setText(item.getQuestion());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.faqsAnswer.setText(Html.fromHtml(String.valueOf(item.getAnswer()), Html.FROM_HTML_MODE_COMPACT));
            holder.faqsAnswer.setMovementMethod(LinkMovementMethod.getInstance());
        }
        else
        {
            holder.faqsAnswer.setText(item.getAnswer());
            holder.faqsAnswer.setMovementMethod(LinkMovementMethod.getInstance());
        }
        holder.expandable.setIcon(context.getResources().getDrawable(R.drawable.ic_plus));
        holder.expandable.setExpandingListener(new Expandable.ExpandingListener() {
            @Override
            public void onExpanded() {
                //some stuff on expand

                holder.expandable.setIcon(context.getResources().getDrawable(R.drawable.ic_minus));
            }

            @Override
            public void onCollapsed() {
                //some stuff on collapse
                holder.expandable.setIcon(context.getResources().getDrawable(R.drawable.ic_plus));
            }
        });



    }

    @Override
    public int getItemCount() {
        return items.size();

    }

    public  static class MostPopularViewHolder extends RecyclerView.ViewHolder{

        TextView faqsQuestion , faqsAnswer;
        Expandable expandable;

        public MostPopularViewHolder(@NonNull View itemView) {
            super(itemView);
            expandable = itemView.findViewById(R.id.expandable);
            faqsQuestion = itemView.findViewById(R.id.faqsQuestion);
            faqsAnswer = itemView.findViewById(R.id.faqsAnswer);

        }
    }




}
