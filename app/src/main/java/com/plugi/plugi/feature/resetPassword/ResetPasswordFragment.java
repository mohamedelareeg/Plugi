package com.plugi.plugi.feature.resetPassword;

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
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.plugi.plugi.R;
import com.plugi.plugi.core.base.BaseFragment;
import com.plugi.plugi.core.utilities.Constants;
import com.plugi.plugi.core.utilities.Utilites;
import com.plugi.plugi.feature.forgetPassword.ForgetPasswordFragment;
import com.plugi.plugi.feature.login.AuthFragment;
import com.plugi.plugi.feature.main.MainActivity;
import com.plugi.plugi.feature.main.SideMenuFragment;
import com.plugi.plugi.models.ForgetPassword;
import com.plugi.plugi.models.ResetPassword;
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

public class ResetPasswordFragment extends BaseFragment {

    private EditText etOtp  , etPassword , etRePassword;
    private Button btnResetPassword , btnResendPassword;
    private String email;
    private TextInputLayout etpasswordPanel , etrepasswordPanel;
    ImageView ivSide , ivBack;
    private TextView toolbarTitle;
    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reset_password, container, false);
        toolbarTitle = view.findViewById(R.id.toolbarTitle);
        toolbarTitle.setText(getResources().getString(R.string.enter_new_password));
        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                fragment = new ForgetPasswordFragment();
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


        email = getArguments().getString(Constants.BUNDLE_FORGET_EMAIL);
        etOtp = view.findViewById(R.id.etOtp);
        etPassword = view.findViewById(R.id.etPassword);
        etRePassword = view.findViewById(R.id.etRePassword);
        btnResetPassword = view.findViewById(R.id.btnResetPassword);
        btnResendPassword = view.findViewById(R.id.btnResendPassword);

        etpasswordPanel = view.findViewById(R.id.etpasswordPanel);
        etrepasswordPanel = view.findViewById(R.id.etrepasswordPanel);
        etOtp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                etOtp.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                etPassword.setError(null);
                etrepasswordPanel.setError(null);
                etpasswordPanel.setErrorEnabled(false); // disable error
                etrepasswordPanel.setErrorEnabled(false); // disable error
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etRePassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                etRePassword.setError(null);
                etrepasswordPanel.setError(null);
                etpasswordPanel.setErrorEnabled(false); // disable error
                etrepasswordPanel.setErrorEnabled(false); // disable error
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validateEmpty(etOtp)){
                    if(validateEmpty(etPassword)) {
                        if(validateEmpty(etRePassword)) {
                            if (getRating(etPassword.getText().toString())) {
                                if (etPassword.getText().length() >= 8) {
                                    if (etPassword.getText().toString().equals(etRePassword.getText().toString())) {
                                        showLoadingDialog();
                                        attemptReset(etOtp.getText().toString(), etPassword.getText().toString(), etRePassword.getText().toString() , view);
                                    } else
                                        etrepasswordPanel.setError(getResources().getString(R.string.password_doesnt_match));
                                } else
                                    etrepasswordPanel.setError(getResources().getString(R.string.error_short));
                              }
                                else
                                {
                                    etrepasswordPanel.setError(getResources().getString(R.string.please_enter_valid_password));
                                }

                            } else
                                etrepasswordPanel.setError(getResources().getString(R.string.required));
                    }else
                        etpasswordPanel.setError(getResources().getString(R.string.required));
                }else
                    etOtp.setError(getResources().getString(R.string.required));
            }
        });
        btnResendPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingDialog();
                attemptResnd(email , view);

            }
        });
        return view;
    }
    private void attemptReset(String otp , String password, String repassword , View view){

        ApiInterface service = RetrofitClient.retrofitWrite().create(ApiInterface.class);

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {


                //GET USER INFO
                Map<String, Object> jsonLoginParams = new ArrayMap<>();

                //put something inside the map, could be null
                jsonLoginParams.put("email", email);
                jsonLoginParams.put("password", password);
                jsonLoginParams.put("code", otp);

                RequestBody bodylogin = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonLoginParams)).toString());
                //defining the call
                Call<ResetPassword> call_login = service.ResetPassword(
                        bodylogin
                );
                call_login.enqueue(new Callback<ResetPassword>() {
                    @Override
                    public void onResponse(Call<ResetPassword> call, Response<ResetPassword> response) {

                       if(response.body().getStatusMessage().equals("Invalid OTP Code"))
                        {
dismissLoadingDialog();
                            etOtp.setError(getResources().getString(R.string.invild_otp_code));
                            Log.d("FORGET", "onResponse: " + response.body().getStatusMessage());
                        }
                        else  if(response.body().getStatusMessage().equals("Success"))
                        {
                            Log.d("FORGET", "onResponse: " + response.body().getStatusMessage());
                            Fragment fragment = null;
                            fragment = new AuthFragment();
                            ((MainActivity) getActivity()).replaceFragments(fragment);
                            dismissLoadingDialog();
                            showResetAlert(view);
                            //Toast.makeText(getActivity(),  getResources().getString(R.string.password_changed_succ), Toast.LENGTH_SHORT).show();
                        }
                        else
                       {
                           dismissLoadingDialog();
                       }
                    }

                    @Override
                    public void onFailure(Call<ResetPassword> call, Throwable t) {
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
    private void attemptResnd(String email , View view){

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
dismissLoadingDialog();
                            Log.d("FORGET", "onResponse: " + response.body().getStatusMessage());
                        }
                        else  if(response.body().getStatusMessage().equals("Success"))
                        {
                            Log.d("FORGET", "onResponse: " + response.body().getEmailAddress());
                            dismissLoadingDialog();
                            showConfirmedAlert(view);
                            Toast.makeText(getActivity(), getResources().getString(R.string.code_resened_successfuly_please_check_your_inbox), Toast.LENGTH_SHORT).show();
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
    private boolean validateEmpty(EditText editText)
    {
        if(editText.getText().toString().isEmpty())
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    private boolean getRating(String password) throws IllegalArgumentException {
        if (password == null) {throw new IllegalArgumentException();}
        if (password.length() < 8) {
            etrepasswordPanel.setError(getResources().getString(R.string.error_short));
            return false;
        } // minimal pw length of 6
        if (password.toLowerCase().equals(password)) {
            etrepasswordPanel.setError(getResources().getString(R.string.please_use_atleast_one_uppercase_letter));
            return false;
        } // lower and upper case
        int numDigits= Utilites.getNumberDigits(password);
        if (numDigits > 0 && numDigits == password.length()) {
            etrepasswordPanel.setError(getResources().getString(R.string.please_enter_valid_password));
            return false;
        } // contains digits and non-digits
        return true;
    }
    @Override
    protected int layoutRes() {
        return R.layout.fragment_reset_password;
    }
    private void showConfirmedAlert(View view){

        try {

            Snackbar snackbar = Snackbar
                    .make(view, getResources().getString(R.string.code_resened_successfuly_please_check_your_inbox), Snackbar.LENGTH_LONG)
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
            Toast.makeText(getActivity(), getResources().getString(R.string.code_resened_successfuly_please_check_your_inbox), Toast.LENGTH_SHORT).show();
        }


    }
    private void showResetAlert(View view){

        try {

            Snackbar snackbar = Snackbar
                    .make(view, getResources().getString(R.string.password_changed_succ), Snackbar.LENGTH_LONG)
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
            Toast.makeText(getActivity(), getResources().getString(R.string.password_changed_succ), Toast.LENGTH_SHORT).show();
        }

    }

}
