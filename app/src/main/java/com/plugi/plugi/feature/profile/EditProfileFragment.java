package com.plugi.plugi.feature.profile;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.plugi.plugi.core.utilities.Utilites;
import com.plugi.plugi.feature.main.MainActivity;
import com.plugi.plugi.feature.main.SideMenuFragment;
import com.plugi.plugi.models.User;
import com.plugi.plugi.models.customerBids;
import com.plugi.plugi.retrofit.ApiInterface;
import com.plugi.plugi.retrofit.RetrofitClient;
import com.plugi.plugi.retrofit.SharedPrefManager;
import com.plugi.plugi.retrofit.response.UserResponse;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileFragment extends BaseFragment {
    User currentUser;
    private Button btnSubmit;
    private EditText etFullName , etEmail , etPhoneCode , etShoseSize , etClothesSize , etPhone , etcurrentPassword , etPassword , etRePassword;
    private String check;
    private TextInputLayout etpasswordPanel , etrepasswordPanel;
    ImageView ivSide , ivBack;
    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        if(SharedPrefManager.getInstance(getActivity()).isLoggedIn())
        {
            currentUser = SharedPrefManager.getInstance(getActivity()).getUser();
        }
        else
        {
            ((MainActivity) getActivity()).forceHiddenPopUp(false);
        }

        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).forceHiddenPopUp(false);
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


        //etFullName , etEmail , etPhone , etcurrentPassword , etPassword , etRePassword
        etFullName = view.findViewById(R.id.etFullName);
        etEmail = view.findViewById(R.id.etEmail);
        etPhoneCode = view.findViewById(R.id.etPhoneCode);
        etPhone = view.findViewById(R.id.etPhone);
        etShoseSize = view.findViewById(R.id.etShoseSize);
        etClothesSize = view.findViewById(R.id.etClothesSize);
        etcurrentPassword = view.findViewById(R.id.etcurrentPassword);
        etPassword = view.findViewById(R.id.etPassword);
        etRePassword = view.findViewById(R.id.etRePassword);
        etpasswordPanel = view.findViewById(R.id.etpasswordPanel);
        etrepasswordPanel = view.findViewById(R.id.etrepasswordPanel);

        etFullName.setText(currentUser.getCustomer().getFirstName());
        etEmail.setText(currentUser.getCustomer().getEmailAddress());
        etPhoneCode.setText(currentUser.getCustomer().getMobileCode().toString());
        etPhone.setText(currentUser.getCustomer().getMobileNumber().toString());
        etShoseSize.setText(currentUser.getCustomer().getShoesSizeValue().toString());
        etClothesSize.setText(currentUser.getCustomer().getClothSizeValue().toString());

        btnSubmit = view.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = etFullName.getText().toString();
                final String email = etEmail.getText().toString();
                final String phonecode = etPhoneCode.getText().toString();
                final String phone = etPhone.getText().toString();
                final String shoes = etShoseSize.getText().toString();
                final String clothes = etClothesSize.getText().toString();
                final String currentpassword = etcurrentPassword.getText().toString();
                final String password = etPassword.getText().toString();
                final String repassword = etRePassword.getText().toString();

                if (isEmailValid(etEmail.getText().toString())) {

                    if(getRating(etPassword.getText().toString())) {
                        if (etPassword.getText().length() >= 8) {

                            if (etPassword.getText().toString().equals(etRePassword.getText().toString())) {

                                if (etPhone.getText().length() == 11) {

                                    if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(currentpassword) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(phone) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(repassword)) {

                                        UpdateData(name , email ,phonecode , phone , shoes , clothes , currentpassword , password , view);
                                    }
                                } else
                                    etrepasswordPanel.setError(getResources().getString(R.string.error_phone));

                            } else
                                etpasswordPanel.setError(getResources().getString(R.string.password_doesnt_match));

                        } else {
                            etrepasswordPanel.setError(getResources().getString(R.string.error_short));
                        }
                    }


                } else {
                    etEmail.setError(getResources().getString(R.string.enter_valid_email));
                }


            }
        });

        return view;
    }
    private void UpdateData(String name , String email , String phonecode , String phonenumber , String shoes , String clothes , String passwordCurrent , String passwordNew , View view) {
        User currentUser = SharedPrefManager.getInstance(getActivity()).getUser();
        ApiInterface service = RetrofitClient.retrofitBearerTokenWrite(currentUser).create(ApiInterface.class);
        Log.d("REG", "attemptLogin: " + email);
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {

                //task.getResult().getToken()
                //CheckLoginStatus
                Map<String, Object> jsonTokenParams = new ArrayMap<>();

                //put something inside the map, could be null
                jsonTokenParams.put("Email", email);
                // jsonParams.put("address_1", selected_address.getAddress());
                // jsonParams.put("state", state);
                //jsonParams.put("phone", selected_address.getMobile());


                RequestBody bodyToken = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonTokenParams)).toString());
                //defining the call
                Call<UserResponse> call = service.GetLoginToken(
                        bodyToken
                );
                call.enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {

                        Log.d("REG", "onResponse: " + response.body().getStatusMessage());
                        if(response.body().getStatusMessage().equals("Success"))
                        {

                            Map<String, Object> jsonParams = new ArrayMap<>();
                            jsonParams.put("id", currentUser.getCustomer().getID());
                            jsonParams.put("firstName", name);//TODO
                            jsonParams.put("lastName", name);//TODO
                            jsonParams.put("emailAddress", email);//TODO
                            jsonParams.put("mobileCode", phonecode);//TODO
                            jsonParams.put("mobileNumber", phonenumber);//TODO
                            jsonParams.put("password", passwordNew);//TODO
                            jsonParams.put("ShoesSizeID", shoes);//TODO
                            jsonParams.put("ClothSizeID", clothes);//TODO
                            //jsonParams.put("enableTouchID", 1);//TODO
                            jsonParams.put("device_ID", task.getResult().getToken());//TODO
                            RequestBody bodyToken = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
                            //defining the call
                            Call<User> call_z = service.UpdateProfileInfo(
                                    bodyToken
                            );
                            call_z.enqueue(new Callback<User>() {
                                @Override
                                public void onResponse(Call<User> call_z, Response<User> response) {

                                    if(response.body().getStatusMessage().equals("Success"))
                                    {
                                        ((MainActivity) getActivity()).forceHiddenPopUp(false);
                                         showResetAlert(view);
                                        //TODO
                                    }
                                }

                                @Override
                                public void onFailure(Call<User> call_z, Throwable t) {

                                }
                            });
                        }
                        else if(response.body().getStatusMessage().equals("Email Not Exist"))
                        {
                            etEmail.setError(getResources().getString(R.string.email_not_exist));
                        }
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {

                    }
                });



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("REG", "onFailure: " + e.getLocalizedMessage());
            }
        });
    }
    private void showResetAlert(View view){

        try {

            Snackbar snackbar = Snackbar
                    .make(view, getResources().getString(R.string.your_profile_has_been_updated_successfully), Snackbar.LENGTH_LONG)
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
    @Override
    protected int layoutRes() {
        return R.layout.fragment_edit_profile;
    }

    private boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
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
    private boolean validatePass() {


        check = etPassword.getText().toString();

        if (check.length() < 6 || check.length() > 20) {
            return false;
        } else if (!check.matches("^[A-za-z0-9@]+")) {
            return false;
        }
        return true;
    }

    private boolean validateEmail() {

        check = etEmail.getText().toString();

        if (check.length() < 8 || check.length() > 40) {
            return false;
        } else if (!check.matches("^[A-za-z0-9.@]+")) {
            return false;
        } else if (!check.contains("@") || !check.contains(".")) {
            return false;
        }

        return true;
    }

    public void UpdateProfileInfo(String name , String email , String phonecode , String phonenumber , String shoes , String clothes , String passwordCurrent , String passwordNew)
    {

        ApiInterface service = RetrofitClient.retrofitWrite().create(ApiInterface.class);

        Log.d("REG", "attemptLogin: " + email);
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                User currentUser = SharedPrefManager.getInstance(getActivity()).getUser();
                ApiInterface service = RetrofitClient.retrofitBearerTokenWrite(currentUser).create(ApiInterface.class);

                Map<String, Object> jsonParams = new ArrayMap<>();
                jsonParams.put("id", currentUser.getCustomer().getID());
                jsonParams.put("firstName", name);//TODO
                jsonParams.put("lastName", name);//TODO
               // jsonParams.put("userName", name);//TODO
                //jsonParams.put("countryCode", currentUser.getCustomer().getCountryCode());//TODO
                jsonParams.put("emailAddress", email);//TODO
                jsonParams.put("mobileCode", phonecode);//TODO
                jsonParams.put("mobileNumber", phonenumber);//TODO
                jsonParams.put("password", passwordNew);//TODO
                //jsonParams.put("enableTouchID", 1);//TODO
                jsonParams.put("ShoesSizeID", shoes);//TODO
                jsonParams.put("ClothSizeID", clothes);//TODO
                jsonParams.put("device_ID", task.getResult().getToken());//TODO
                RequestBody bodyToken = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
                //defining the call
                Call<customerBids> call = service.GetCustomer_Bids(
                        bodyToken
                );
                call.enqueue(new Callback<customerBids>() {
                    @Override
                    public void onResponse(Call<customerBids> call, Response<customerBids> response) {

                        if (response.body().getStatusMessage().equals("Success")) {
                            ((MainActivity) getActivity()).forceHiddenPopUp(false);

                        }
                    }

                    @Override
                    public void onFailure(Call<customerBids> call, Throwable t) {
                        Log.d("ItemDetails", "onFailure: " + t.getLocalizedMessage());
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("REG", "onFailure 6: " + e.getLocalizedMessage());
            }
        });
    }
}
