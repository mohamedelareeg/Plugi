package com.plugi.plugi.feature.profile.setting.adapter;

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
import com.plugi.plugi.feature.profile.setting.interfaces.OnPaymentMethodClickListener;
import com.plugi.plugi.models.CustomerCard;

import java.util.List;

public class PaymentMethodAdapter extends RecyclerView.Adapter<PaymentMethodAdapter.MostPopularViewHolder> {

    Context context;
    List<CustomerCard> itemList;
    OnPaymentMethodClickListener onPaymentMethodClickListener;
    public PaymentMethodAdapter(Context context  , List<CustomerCard> itemList , OnPaymentMethodClickListener onPaymentMethodClickListener) {
        this.context = context;
        this.itemList = itemList;
        this.onPaymentMethodClickListener = onPaymentMethodClickListener;
    }
    @NonNull
    @Override
    public MostPopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_payment, parent, false);

        //here we need to create a row item layout file
        return new MostPopularViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MostPopularViewHolder holder, int position) {

        //here we will bind data to our layout
        final CustomerCard item = itemList.get(position);

        holder.itemType.setText(item.getCardType().toString());
        holder.itemCardNo.setText(item.getCardNo().toString());
        holder.itemValidity.setText(item.getExpirationDate());
        // setting up image using GLIDE
        String pojoImage = "https://cdn.iconscout.com/icon/free/png-512/visa-3-226460.png";
        Glide.with(context).load(pojoImage).placeholder(R.drawable.ic_landiing_logo).into(holder.item_image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPaymentMethodClickListener.onPaymentMethodClicked(item , position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();

    }

    public  static class MostPopularViewHolder extends RecyclerView.ViewHolder{
        ImageView item_image ,  ivDelete , ivEdit;
        TextView itemType , itemCardNo, itemValidity ;
        public MostPopularViewHolder(@NonNull View itemView) {
            super(itemView);
            item_image = itemView.findViewById(R.id.item_image);
            ivDelete = itemView.findViewById(R.id.ivDelete);
            ivEdit = itemView.findViewById(R.id.ivEdit);
            itemType = itemView.findViewById(R.id.itemType);
            itemCardNo = itemView.findViewById(R.id.itemCardNo);
            itemValidity = itemView.findViewById(R.id.itemValidity);
        }
    }




}
