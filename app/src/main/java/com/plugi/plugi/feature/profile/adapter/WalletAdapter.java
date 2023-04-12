package com.plugi.plugi.feature.profile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.plugi.plugi.R;
import com.plugi.plugi.feature.profile.interfaces.OnWalletClickListener;
import com.plugi.plugi.models.GetCustomerWallet;

import java.util.List;

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.MostPopularViewHolder> {

    Context context;
    List<GetCustomerWallet.Detail> itemList;
    // Start with first item selected
    private int focusedItem = 0;


    OnWalletClickListener onWalletClickListener;
    public WalletAdapter(Context context  , List<GetCustomerWallet.Detail> itemList  , OnWalletClickListener onWalletClickListener ) {
        this.context = context;
        this.itemList = itemList;
        this.onWalletClickListener = onWalletClickListener;

    }
    @NonNull
    @Override
    public MostPopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_wallet, parent, false);

        //here we need to create a row item layout file
        return new MostPopularViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MostPopularViewHolder holder, int position) {

        //here we will bind data to our layout
        final GetCustomerWallet.Detail item = itemList.get(position);
        holder.item_t_id.setText(item.getTransactionID().toString());
        holder.item_date.setText(item.getTransactionDate());
        holder.item_price.setText(String.valueOf(item.getPriceCurrency()) + "." + item.getOrderTotalCost().toString());
        // Handle item click and set the selection
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onWalletClickListener.onWalletClicked(item , position);
                // Redraw the old selection and the new

            }
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();

    }

    public  static class MostPopularViewHolder extends RecyclerView.ViewHolder{

        TextView item_t_id , item_price , item_date ;
        public MostPopularViewHolder(@NonNull View itemView) {
            super(itemView);

            item_t_id = itemView.findViewById(R.id.item_t_id);
            item_price = itemView.findViewById(R.id.item_price);
            item_date = itemView.findViewById(R.id.item_date);
        }

    }




}
