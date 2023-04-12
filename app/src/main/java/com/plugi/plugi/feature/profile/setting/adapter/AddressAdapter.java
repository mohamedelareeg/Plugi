package com.plugi.plugi.feature.profile.setting.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.plugi.plugi.R;
import com.plugi.plugi.feature.profile.setting.interfaces.OnAddressClickListener;
import com.plugi.plugi.models.CustomerAddress;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.MostPopularViewHolder> {

    Context context;
    List<CustomerAddress> itemList;
    OnAddressClickListener onAddressClickListener;
    public AddressAdapter(Context context  , List<CustomerAddress> itemList , OnAddressClickListener onAddressClickListener) {
        this.context = context;
        this.itemList = itemList;
        this.onAddressClickListener = onAddressClickListener;
    }
    @NonNull
    @Override
    public MostPopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_address, parent, false);

        //here we need to create a row item layout file
        return new MostPopularViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MostPopularViewHolder holder, int position) {

        //here we will bind data to our layout
        final CustomerAddress item = itemList.get(position);

        holder.itemHome.setText(item.getAddress1());
        holder.itemStreet.setText(item.getCityName());
        holder.itemCity.setText(item.getCountryName());
        // setting up image using GLIDE
        //Glide.with(context).load(categoryItems.getImage()).placeholder(R.drawable.ic_landiing_logo).into(holder.item_image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddressClickListener.onAddressClicked(item , position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();

    }

    public  static class MostPopularViewHolder extends RecyclerView.ViewHolder{
        ImageView ivDelete , ivEdit;
        TextView itemHome , itemStreet, itemCity ;
        public MostPopularViewHolder(@NonNull View itemView) {
            super(itemView);
            ivDelete = itemView.findViewById(R.id.ivDelete);
            ivEdit = itemView.findViewById(R.id.ivEdit);
            itemHome = itemView.findViewById(R.id.itemHome);
            itemStreet = itemView.findViewById(R.id.itemStreet);
            itemCity = itemView.findViewById(R.id.itemCity);
        }
    }




}
