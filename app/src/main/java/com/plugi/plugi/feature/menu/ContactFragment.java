package com.plugi.plugi.feature.menu;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.plugi.plugi.R;
import com.plugi.plugi.core.base.BaseFragment;
import com.plugi.plugi.feature.main.MainActivity;
import com.plugi.plugi.feature.main.SideMenuFragment;
import com.plugi.plugi.models.Contact;
import com.plugi.plugi.models.User;
import com.plugi.plugi.retrofit.ApiInterface;
import com.plugi.plugi.retrofit.RetrofitClient;
import com.plugi.plugi.retrofit.SharedPrefManager;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactFragment extends BaseFragment {

    private Button btnSubmit;
    private EditText contactFullName ,contactEmailID , contactPhone , contactMessage;
    ImageView ivSide , ivBack;
    TextView toolbarTitle;
    User user;
    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((MainActivity) getActivity()).sidemenu.getVisibility()!= View.VISIBLE)
                {
                    ((MainActivity) getActivity()).forceHiddenPopUp(false);
                }
                else {
                    ((MainActivity) getActivity()).forceHiddenSideMenu();
                }
            }
        });
        ivSide = view.findViewById(R.id.ivSide);
        ivSide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).sidemenu.setVisibility(View.VISIBLE);
                Fragment fragment = null;
                fragment = new SideMenuFragment();
                ((MainActivity) getActivity()).replaceFragments(fragment);

            }
        });
        toolbarTitle  = view.findViewById(R.id.toolbarTitle);
        toolbarTitle.setText(getResources().getString(R.string.contact_us));

        contactFullName = view.findViewById(R.id.contactFullName);
        contactEmailID = view.findViewById(R.id.contactEmailID);
        contactPhone = view.findViewById(R.id.contactPhone);
        contactMessage = view.findViewById(R.id.contactMessage);

        //contactFullName.setTransformationMethod(new AsteriskPasswordTransformationMethod());


        if(SharedPrefManager.getInstance(getActivity()).isLoggedIn())
        {
            user = SharedPrefManager.getInstance(getActivity()).getUser();
            contactFullName.setText(user.getCustomer().getFirstName());
            contactEmailID.setText(user.getCustomer().getEmailAddress());
            if(user.getCustomer().getMobileNumber() != null && !user.getCustomer().getMobileNumber().equals("0"))
            {
                contactPhone.setText(user.getCustomer().getMobileNumber());
            }
        }
        btnSubmit = view.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = contactFullName.getText().toString();
                final String email = contactEmailID.getText().toString();
                final String phone = contactPhone.getText().toString();
                final String message = contactMessage.getText().toString();


                if (!TextUtils.isEmpty(name)) {
                    if(!TextUtils.isEmpty(email)) {
                        if(!TextUtils.isEmpty(phone)) {
                            if(!TextUtils.isEmpty(message)) {
                                if (isEmailValid(contactEmailID.getText().toString())) {
                                    if (contactPhone.getText().length() == 11) {
                                        sendData(name, email, phone, message , view);
                                    }
                                    else
                                    {
                                        contactPhone.setError(getResources().getString(R.string.error_phone));
                                    }
                                }
                                else
                                {
                                    contactEmailID.setError(getResources().getString(R.string.enter_valid_email));
                                }
                            }
                            else
                            {
                                contactMessage.setError(getResources().getString(R.string.required));
                            }
                        }
                        else
                        {
                            contactPhone.setError(getResources().getString(R.string.required));
                        }
                    }
                    else
                    {
                        contactEmailID.setError(getResources().getString(R.string.required));
                    }
                }
                else
                {
                    contactFullName.setError(getResources().getString(R.string.required));
                }
            }
        });

        return view;
    }
    public void sendData(String fullName , String email , String phone , String message , View view)
    {
        ApiInterface service = RetrofitClient.retrofitWrite().create(ApiInterface.class);

        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("fullName", fullName);
        jsonParams.put("phone", phone);
        jsonParams.put("email", email);
        jsonParams.put("message", message);
        jsonParams.put("language_ID", 1);
        RequestBody bodyToken = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
        //defining the call
        Call<Contact> call = service.Contactus(
                bodyToken
        );
        call.enqueue(new Callback<Contact>() {
            @Override
            public void onResponse(Call<Contact> call, Response<Contact> response) {

                if(response.body().getStatusMessage().equals("Success"))
                {

                    Fragment fragment = null;
                    fragment = new SideMenuFragment();
                    ((MainActivity) getActivity()).replaceFragments(fragment);
                    showConfirmedAlert(view);
                    //TSnackbar.make(findViewById(android.R.id.content),"Message sent Successfuly",TSnackbar.LENGTH_LONG).show(); TODO USING BASE ACTIVITY TO STORE SNACKBAR MESSAGES
                }
            }

            @Override
            public void onFailure(Call<Contact> call, Throwable t) {

            }
        });
    }
    @Override
    protected int layoutRes() {
        return R.layout.fragment_contact;
    }
    private boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private void showConfirmedAlert(View view){

        try {
            Snackbar snackbar = Snackbar
                    .make(view, getResources().getString(R.string.message_sended_successfuly), Snackbar.LENGTH_LONG);

            // Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);

            snackbar.show();
        }
        catch (Exception e)
        {
            Toast.makeText(getActivity(), getResources().getString(R.string.message_sended_successfuly), Toast.LENGTH_SHORT).show();
        }

    }
    private void showInternetAlert(View view){

        Snackbar snackbar = Snackbar
                .make(view, "No internet connection!", Snackbar.LENGTH_LONG)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });

        // Changing message text color
        snackbar.setActionTextColor(Color.RED);

        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);

        snackbar.show();

    }

}
