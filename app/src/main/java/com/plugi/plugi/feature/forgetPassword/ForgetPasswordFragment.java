package com.plugi.plugi.feature.forgetPassword;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.plugi.plugi.R;
import com.plugi.plugi.core.base.BaseFragment;
import com.plugi.plugi.core.utilities.Constants;
import com.plugi.plugi.feature.login.AuthFragment;
import com.plugi.plugi.feature.main.MainActivity;
import com.plugi.plugi.feature.main.SideMenuFragment;
import com.plugi.plugi.feature.resetPassword.ResetPasswordFragment;
import com.plugi.plugi.models.ForgetPassword;
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

public class ForgetPasswordFragment extends BaseFragment {
    private TextView toolbarTitle;
    private EditText etEmail;
    private Button btnResetPassword;
    ImageView ivSide , ivBack;
    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forget_password, container, false);
        toolbarTitle = view.findViewById(R.id.toolbarTitle);
        toolbarTitle.setText(getResources().getString(R.string.reset_password));
        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = null;
                fragment = new AuthFragment();
                ((MainActivity) getActivity()).replaceFragments(fragment);
            }
        });
        ivSide = view.findViewById(R.id.ivSide);
        ivSide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //((MainActivity) requireActivity()).toolbarTitle.setVisibility(View.GONE);
                ((MainActivity) requireActivity()).sidemenu.setVisibility(View.VISIBLE);
                Fragment fragment = null;
                fragment = new SideMenuFragment();
                ((MainActivity) getActivity()).replaceFragments(fragment);
            }
        });

        etEmail = view.findViewById(R.id.etEmail);
        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                etEmail.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btnResetPassword = view.findViewById(R.id.btnResetPassword);
        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isEmailValid(etEmail.getText().toString())){
                    showLoadingDialog();
                    attemptReset(etEmail.getText().toString() , view);

                }else {
                    etEmail.setError(getResources().getString(R.string.enter_valid_email));
                }
            }
        });
        return view;
    }
    private void attemptReset(String email , View view){

        ApiInterface service = RetrofitClient.retrofitWrite().create(ApiInterface.class);

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {


                //GET USER INFO
                Map<String, Object> jsonLoginParams = new ArrayMap<>();

                //put something inside the map, could be null
                jsonLoginParams.put("emailAddress", email);

                RequestBody bodylogin = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonLoginParams)).toString());
                //defining the call
                Call<ForgetPassword> call_login = service.ForgetPassword(
                        bodylogin
                );
                call_login.enqueue(new Callback<ForgetPassword>() {
                    @Override
                    public void onResponse(Call<ForgetPassword> call, Response<ForgetPassword> response) {

                       if(response.body().getStatusMessage().equals("Email Not Registered"))
                        {

                            etEmail.setError(getResources().getString(R.string.the_email_address_doesnt_exist));
                            dismissLoadingDialog();
                            Log.d("FORGET", "onResponse: " + response.body().getStatusMessage());
                        }
                        else  if(response.body().getStatusMessage().equals("Success"))
                        {
                            Fragment fragment = null;
                            fragment = new ResetPasswordFragment();
                            ((MainActivity) getActivity()).replaceFragments(fragment , Constants.BUNDLE_FORGET_EMAIL , response.body().getEmailAddress());
                            Log.d("FORGET", "onResponse: " + response.body().getEmailAddress());
                            dismissLoadingDialog();
                            showConfirmedAlert(view);
                        }
                        else
                       {
                           dismissLoadingDialog();
                       }
                    }

                    @Override
                    public void onFailure(Call<ForgetPassword> call, Throwable t) {
                          dismissLoadingDialog();
                    }
                });



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dismissLoadingDialog();
                Log.d("REG", "onFailure: " + e.getLocalizedMessage());
            }
        });


    }
    private boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_forget_password;
    }

    private void showConfirmedAlert(View view){

        try {

            Snackbar snackbar = Snackbar
                    .make(view, getResources().getString(R.string.check_your_email_address), Snackbar.LENGTH_LONG)
                    .setAction(getResources().getString(R.string.ok), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                        }
                    });

            // Changing message text color
            snackbar.setActionTextColor(Color.WHITE);

            // Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(R.id.snackbar_text);
            textView.setTextSize(16);
            textView.setTextColor(Color.WHITE);

            snackbar.show();
        }
        catch (Exception e)
        {
            Toast.makeText(getActivity(), getResources().getString(R.string.check_your_email_address), Toast.LENGTH_SHORT).show();
        }

    }
}
