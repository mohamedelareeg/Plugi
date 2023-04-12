package com.plugi.plugi.feature.profile.setting.adapter;

import android.content.Context;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kyleduo.switchbutton.SwitchButton;
import com.plugi.plugi.R;
import com.plugi.plugi.core.views.expandablePanel.Expandable;
import com.plugi.plugi.models.GetNotificationsetting;
import com.plugi.plugi.models.User;
import com.plugi.plugi.retrofit.ApiInterface;
import com.plugi.plugi.retrofit.RetrofitClient;
import com.plugi.plugi.retrofit.SharedPrefManager;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MostPopularViewHolder> {

    Context context;
    List<GetNotificationsetting.NotificationSetting> itemList;

    public NotificationAdapter(Context context  , List<GetNotificationsetting.NotificationSetting> itemList ) {
        this.context = context;
        this.itemList = itemList;

    }
    @NonNull
    @Override
    public MostPopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_notification, parent, false);

        //here we need to create a row item layout file
        return new MostPopularViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MostPopularViewHolder holder, int position) {

        //here we will bind data to our layout
        final GetNotificationsetting.NotificationSetting item = itemList.get(position);

        holder.itemTitle.setText(item.getName());
        //holder.itemStatus.setText(item.getStatus());
        holder.itemDesc.setText(item.getNotes());
        holder.sbProgress.setProgress(75);//ToDO
        holder.itemProgressTXT.setText(75 + "%");
        holder.sbProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                holder.itemProgressTXT.setText(String.valueOf(progress) + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        //holder.emailSB.setChecked(item.getSendEmail());
        //holder.notifySB.setChecked(item.getSendPush());
        if(item.getSendEmail())
        {

            holder.emailSB.setChecked(true);
        }
        else
        {

            holder.emailSB.setChecked(false);
        }

        if(item.getSendPush())
        {

            holder.notifySB.setChecked(true);
        }
        else
        {

            holder.notifySB.setChecked(false);
        }

        holder.emailSB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(SharedPrefManager.getInstance(context).isLoggedIn()) {
                    User user = SharedPrefManager.getInstance(context).getUser();
                    ApiInterface service = RetrofitClient.retrofitBearerTokenWrite(user).create(ApiInterface.class);

                    Map<String, Object> jsonParams = new ArrayMap<>();
                    jsonParams.put("NotificationID", item.getID());
                    jsonParams.put("CustomerId", user.getCustomer().getID());
                    jsonParams.put("SendEmail", holder.emailSB.isChecked());
                    jsonParams.put("SendPush", holder.emailSB.isChecked());
                    jsonParams.put("Language_ID", 1);
                    RequestBody bodyToken = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
                    //defining the call
                    Call<GetNotificationsetting> call = service.GetNotificationsetting(
                            bodyToken
                    );
                    call.enqueue(new Callback<GetNotificationsetting>() {
                        @Override
                        public void onResponse(Call<GetNotificationsetting> call, Response<GetNotificationsetting> response) {
                            Log.d("NOTIFY", "onResponse: " + response.body().getStatusMessage());
                            if (response.body().getStatusMessage().equals("Success")) {


                            } else {

                            }
                        }

                        @Override
                        public void onFailure(Call<GetNotificationsetting> call, Throwable t) {
                            Log.d("NOTIFY", "onFailure: " + t.getMessage());
                            Log.d("NOTIFY", "onFailure: " + t.getLocalizedMessage());

                        }
                    });
                }
            }
        });
        holder.notifySB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(SharedPrefManager.getInstance(context).isLoggedIn()) {
                    User user = SharedPrefManager.getInstance(context).getUser();
                    ApiInterface service = RetrofitClient.retrofitBearerTokenWrite(user).create(ApiInterface.class);


                    Map<String, Object> jsonParams = new ArrayMap<>();
                    jsonParams.put("NotificationID", item.getID());
                    jsonParams.put("CustomerId", user.getCustomer().getID());
                    jsonParams.put("SendEmail", holder.emailSB.isChecked());
                    jsonParams.put("SendPush", holder.emailSB.isChecked());
                    jsonParams.put("Language_ID", 1);
                    RequestBody bodyToken = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
                    //defining the call
                    Call<GetNotificationsetting> call = service.GetNotificationsetting(
                            bodyToken
                    );
                    call.enqueue(new Callback<GetNotificationsetting>() {
                        @Override
                        public void onResponse(Call<GetNotificationsetting> call, Response<GetNotificationsetting> response) {

                            Log.d("NOTIFY", "onResponse: " + response.body().getStatusMessage());
                            if (response.body().getStatusMessage().equals("Success")) {

                            } else {

                            }
                        }

                        @Override
                        public void onFailure(Call<GetNotificationsetting> call, Throwable t) {
                            Log.d("NOTIFY", "onFailure: " + t.getMessage());
                            Log.d("NOTIFY", "onFailure: " + t.getLocalizedMessage());

                        }
                    });
                }
            }
        });




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
        return itemList.size();

    }

    public  static class MostPopularViewHolder extends RecyclerView.ViewHolder{

        TextView itemTitle , itemStatus , itemDesc , itemProgressTXT;
        Expandable expandable;
        SeekBar sbProgress;
        SwitchButton emailSB , notifySB;

        public MostPopularViewHolder(@NonNull View itemView) {
            super(itemView);
            expandable = itemView.findViewById(R.id.expandable);
            itemTitle = itemView.findViewById(R.id.itemTitle);
            itemStatus = itemView.findViewById(R.id.itemStatus);
            itemDesc = itemView.findViewById(R.id.itemDesc);
            itemProgressTXT = itemView.findViewById(R.id.itemProgressTXT);
            sbProgress = itemView.findViewById(R.id.sbProgress);
            emailSB = itemView.findViewById(R.id.emailSB);
            notifySB = itemView.findViewById(R.id.notifySB);

        }

    }




}
