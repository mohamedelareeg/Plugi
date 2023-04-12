package com.plugi.plugi.feature.profile.setting;

import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.plugi.plugi.R;
import com.plugi.plugi.core.base.BaseFragment;
import com.plugi.plugi.feature.main.MainActivity;
import com.plugi.plugi.feature.main.SideMenuFragment;
import com.plugi.plugi.feature.main.interfaces.IOnBackPressed;
import com.plugi.plugi.feature.profile.setting.interfaces.OnAddressClickListener;
import com.plugi.plugi.feature.profile.setting.interfaces.OnPaymentMethodClickListener;
import com.plugi.plugi.models.CustomerAddress;
import com.plugi.plugi.models.CustomerCard;
import com.plugi.plugi.models.ItemDetails;
import com.plugi.plugi.retrofit.ApiInterface;
import com.plugi.plugi.retrofit.RetrofitClient;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAddressFragment extends BaseFragment implements IOnBackPressed , OnAddressClickListener , OnPaymentMethodClickListener {


    ImageView ivSide , ivBack;
    TextView toolbarTitle;

    private Button btnSubmit;
    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_address, container, false);

        toolbarTitle  = view.findViewById(R.id.toolbarTitle);
        toolbarTitle.setText(getResources().getString(R.string.add_address));
        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = null;
                fragment = new SettingFragment();
                ((MainActivity) getActivity()).replacePopUpFragments(fragment);
            }
        });
        ivSide = view.findViewById(R.id.ivSide);
        ivSide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) requireActivity()).sidemenu.setVisibility(View.VISIBLE);
                Fragment fragment = null;
                fragment = new SideMenuFragment();
                ((MainActivity) getActivity()).replaceFragments(fragment);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        btnSubmit = view.findViewById(R.id.btnSubmit);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateData();
            }
        });
        //LoadData();

    }

    public void updateData()
    {
        ApiInterface service = RetrofitClient.retrofitWrite().create(ApiInterface.class);

        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("ItemID", 0);
        jsonParams.put("language_ID", 1);
        RequestBody bodyToken = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
        //defining the call
        Call<ItemDetails> call = service.GetItemDetailbyID(
                bodyToken
        );
        call.enqueue(new Callback<ItemDetails>() {
            @Override
            public void onResponse(Call<ItemDetails> call, Response<ItemDetails> response) {

                if(response.body().getStatusMessage().equals("Success"))
                {

                }
            }

            @Override
            public void onFailure(Call<ItemDetails> call, Throwable t) {
                Log.d("ItemDetails", "onFailure: " + t.getLocalizedMessage());
            }
        });
    }


    @Override
    protected int layoutRes() {
        return R.layout.fragment_add_address;
    }



    @Override
    public boolean onBackPressed() {
        return false;
        /*
        if (myCondition) {
            //action not popBackStack
            return true;
        } else {
            return false;
        }

         */
    }


    @Override
    public void onAddressClicked(CustomerAddress contact, int position) {

    }

    @Override
    public void onPaymentMethodClicked(CustomerCard contact, int position) {

    }
}
