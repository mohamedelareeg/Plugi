package com.plugi.plugi.feature.login;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.ArrayMap;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.plugi.plugi.R;
import com.plugi.plugi.core.base.BaseFragment;
import com.plugi.plugi.core.utilities.Utilites;
import com.plugi.plugi.feature.main.MainActivity;
import com.plugi.plugi.models.Terms;
import com.plugi.plugi.models.User;
import com.plugi.plugi.retrofit.ApiInterface;
import com.plugi.plugi.retrofit.RetrofitClient;
import com.plugi.plugi.retrofit.SharedPrefManager;
import com.plugi.plugi.retrofit.response.UserResponse;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

public class RegisterFragment extends BaseFragment implements GoogleApiClient.OnConnectionFailedListener {

    public ProgressDialog mProgressDialog;
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private EditText etFirstName , etLastName , etEmail , etPhone , etPassword , etRePassword;
    private CheckBox cbTerms;
    private Button btnRegister;
    private String check;
    private TextInputLayout etpasswordPanel , etrepasswordPanel;
    private static Dialog termsDialog;
    private TextView viewTerms;
    LinearLayout viewGoogle;

    private static final int SIGN_IN = 1;
    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        FacebookSdk.sdkInitialize(getActivity());

        viewGoogle = view.findViewById(R.id.viewGoogle);
        viewGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent( ((MainActivity) getActivity()).googleApiClient);
                startActivityForResult(intent , SIGN_IN);
            }
        });
        callbackManager = CallbackManager.Factory.create();
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        if(isLoggedIn)
        {
            AccessTokenTracker tokenTracker  = new AccessTokenTracker() {
                @Override
                protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                    if(currentAccessToken == null)
                    {
                        Log.d("REG", "onCurrentAccessTokenChanged: ");
                    }
                }
            };

            //onBackPressed();

        }
        viewTerms = view.findViewById(R.id.viewTerms);
        etFirstName = view.findViewById(R.id.etFirstName);
        etLastName = view.findViewById(R.id.etLastName);
        etEmail = view.findViewById(R.id.etEmail);
        etPhone = view.findViewById(R.id.etPhone);
        etPassword = view.findViewById(R.id.etPassword);
        etRePassword = view.findViewById(R.id.etRePassword);
        etpasswordPanel = view.findViewById(R.id.etpasswordPanel);
        etrepasswordPanel = view.findViewById(R.id.etrepasswordPanel);
        cbTerms = view.findViewById(R.id.cbTerms);
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

        btnRegister = view.findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String firstName = etFirstName.getText().toString();
                final String lastName = etLastName.getText().toString();
                final String email = etEmail.getText().toString();
                final String phone = etPhone.getText().toString();
                final String password = etPassword.getText().toString();
                final String repassword = etRePassword.getText().toString();


                if(cbTerms.isChecked()) {
                    Log.d("REG", "onClick: " + cbTerms.toString());
                    if (isEmailValid(etEmail.getText().toString())) {
                        Log.d("REG", "onClick: " + etEmail.getText().toString());
                        if(getRating(etPassword.getText().toString())) {
                            if (etPassword.getText().length() >= 8) {
                                Log.d("REG", "onClick: " + etPassword.getText().toString());
                                if (etPassword.getText().toString().equals(etRePassword.getText().toString())) {

                                    if (etPhone.getText().length() == 9 || etPhone.getText().length() == 11) {

                                        if (!TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(lastName) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(phone) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(repassword)) {
                                            Log.d("REG", "onClick: isEmpty");
                                            attemptLogin(firstName, lastName, email, phone, password, repassword);
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
                else
                {
                    cbTerms.setError(getResources().getString(R.string.plase_agree_terms_conditions_first));
                }
            }
        });

        loginButton = (LoginButton)view.findViewById(R.id.login_button);
        loginButton.setFragment(this);
        loginButton.setReadPermissions("email" , "user_location" , "user_gender" , "user_photos" , "user_birthday" , "user_friends");
        loginButton.setLoginText(getResources().getString(R.string.signup_with_facebook));

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                signIn();
            }
        });
        viewTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               loadData();

            }
        });
        return view;
    }

    private void attemptLogin(String firstName, String lastName, String email, String phone, String password, String repassword) {
        ApiInterface service = RetrofitClient.retrofitWrite().create(ApiInterface.class);

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
                            etEmail.setError(getResources().getString(R.string.email_already_exist));
                        }
                        else if(response.body().getStatusMessage().equals("Email Not Exist"))
                        {

                            Map<String, Object> jsonParams = new ArrayMap<>();

                            //put something inside the map, could be null
                            jsonParams.put("firstName", firstName);
                            jsonParams.put("lastName", lastName);
                            jsonParams.put("emailAddress", email);
                            jsonParams.put("mobileCode", "+20");
                            jsonParams.put("mobileNumber", phone);
                            jsonParams.put("password", password);
                            jsonParams.put("faceBookID", 0);
                            jsonParams.put("twitterID", 0);
                            jsonParams.put("device_ID", task.getResult().getToken());
                            // jsonParams.put("address_1", selected_address.getAddress());
                            // jsonParams.put("state", state);
                            //jsonParams.put("phone", selected_address.getMobile());

                            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
                            //defining the call
                            Call<UserResponse> call_register = service.createUserBody(
                                    body
                            );

                            //calling the api
                            call_register.enqueue(new Callback<UserResponse>() {
                                @Override
                                public void onResponse(Call<UserResponse> call_register, Response<UserResponse> response) {
                                    //hiding progress dialog
                                    hideProgressDialog();

                                    if(response.body() != null) {

                                        if(response.body().getStatusMessage().equals("Email already registered"))
                                        {

                                            Log.d("REG", "onResponse: " + response.body().getStatusMessage());
                                        }
                                        else  if(response.body().getStatusMessage().equals("Success"))
                                        {
                                            //GET USER INFO
                                            Map<String, Object> jsonLoginParams = new ArrayMap<>();

                                            //put something inside the map, could be null
                                            jsonLoginParams.put("Email", email);
                                            jsonLoginParams.put("password", password);
                                            jsonLoginParams.put("device_ID", task.getResult().getToken());
                                            jsonLoginParams.put("Language_ID", 1);
                                            // jsonParams.put("address_1", selected_address.getAddress());
                                            // jsonParams.put("state", state);
                                            //jsonParams.put("phone", selected_address.getMobile());

                                            RequestBody bodylogin = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonLoginParams)).toString());
                                            //defining the call
                                            Call<User> call_login = service.Login(
                                                    bodylogin
                                            );
                                            call_login.enqueue(new Callback<User>() {
                                                @Override
                                                public void onResponse(Call<User> call, Response<User> response) {

                                                    if(response.body().getStatusMessage().equals("Invalid Password"))
                                                    {

                                                        Log.d("REG", "onResponse: " + response.body().getStatusMessage());
                                                    }
                                                    else  if(response.body().getStatusMessage().equals("Success"))
                                                    {
                                                        SharedPrefManager.getInstance(getApplicationContext()).UserLogin(response.body());
                                                        Intent intent = new Intent(getActivity(), MainActivity.class);
                                                        startActivity(intent);
                                                        getActivity().finish();
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<User> call, Throwable t) {

                                                }
                                            });


                                        }
                                        else
                                        {
                                            Log.d("REG", "onResponse: " + response.body().getStatusMessage());
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<UserResponse> call_register, Throwable t) {
                                    hideProgressDialog();
                                    Log.d("REG", "onFailure 5: " + t.getLocalizedMessage());
                                    //Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call_register, Throwable t) {

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

    private void signIn() {
        Log.d("REG", "signIn: ");
        printKeyHash();
        showProgressDialog();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {

                //handleFacebookAccessToken(loginResult.getAccessToken());
                // Toast.makeText(FacebookLoginActivity.this, "User ID :"  + loginResult.getAccessToken().getUserId() + "ImageURL" + loginResult.getAccessToken().ge, Toast.LENGTH_SHORT).show();
                printKeyHash();
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.e("response: ", response + "");
                                try {
                                    String id = object.getString("id").toString();
                                    String email = object.getString("email").toString();
                                    String name = object.getString("name").toString();
                                    String profilePicUrl = "https://graph.facebook.com/" + loginResult.getAccessToken().getUserId() + "/picture?type=large";
                                    Log.d("imageFB", profilePicUrl);
                                    Log.d("FB_ID:", id);
                                    Log.d("name:", name);
                                    Log.d("email:", email);
                                    //  hideProgressDialog();
                                    final Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                                    //checkFBLogin(id, email, name, profilePicUrl);
                                    signUp(email , id, name , profilePicUrl , timestamp.getTime() , 2);
                                    // handleFacebookAccessToken(loginResult.getAccessToken() , id, email, name, profilePicUrl);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    hideProgressDialog();
                                }
                                //  finish();
                            }



                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday,friends,likes,hometown,education,work");
                request.setParameters(parameters);
                request.executeAsync();




            }

            @Override
            public void onCancel() {
                hideProgressDialog();
            }

            @Override
            public void onError(FacebookException error) {
                hideProgressDialog();
            }
        });
    }
    private void signUp(final String email , final String id , final String name , final String profilePic , final long  created_AT , int type) {



        ApiInterface service = RetrofitClient.retrofitWrite().create(ApiInterface.class);

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

                        if(response.body().getStatusMessage().equals("Success"))
                        {
                            //GET USER INFO
                            Map<String, Object> jsonLoginParams = new ArrayMap<>();

                            //put something inside the map, could be null
                            jsonLoginParams.put("Email", email);
                            jsonLoginParams.put("password", email);
                            jsonLoginParams.put("device_ID", task.getResult().getToken());
                            jsonLoginParams.put("Language_ID", 1);

                            RequestBody bodylogin = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonLoginParams)).toString());
                            //defining the call
                            Call<User> call_login = service.Login(
                                    bodylogin
                            );
                            call_login.enqueue(new Callback<User>() {
                                @Override
                                public void onResponse(Call<User> call, Response<User> response) {

                                    if(response.body().getStatusMessage().equals("Invalid Password"))
                                    {

                                        hideProgressDialog();
                                        etpasswordPanel.setError(getResources().getString(R.string.invild_password));
                                        Log.d("REG", "onResponse: " + response.body().getStatusMessage());
                                    }
                                    else if(response.body().getStatusMessage().equals("Email Not Exist"))
                                    {

                                        hideProgressDialog();
                                        etEmail.setError(getResources().getString(R.string.email_not_exist));
                                        Log.d("REG", "onResponse: " + response.body().getStatusMessage());
                                    }
                                    else  if(response.body().getStatusMessage().equals("Success"))
                                    {
                                        hideProgressDialog();
                                        SharedPrefManager.getInstance(getApplicationContext()).UserLogin(response.body());
                                        Intent intent = new Intent(getActivity(), MainActivity.class);
                                        startActivity(intent);
                                        getActivity().finish();
                                    }
                                }

                                @Override
                                public void onFailure(Call<User> call, Throwable t) {
                                    hideProgressDialog();
                                    Log.d("REG", "onFailure 7: " + t.getLocalizedMessage());

                                }
                            });

                            /*
                            SharedPrefManager.getInstance(getApplicationContext()).UserLogin(response.body());
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                            getActivity().finish();

                             */

                            //TODO LOGIN
                        }
                        else if(response.body().getStatusMessage().equals("Email Not Exist"))
                        {
                            Map<String, Object> jsonParams = new ArrayMap<>();

                            //put something inside the map, could be null
                            jsonParams.put("firstName", name);
                            jsonParams.put("lastName", name);
                            jsonParams.put("emailAddress", email);
                            jsonParams.put("mobileCode", "+20");
                            jsonParams.put("mobileNumber", "0");
                            jsonParams.put("password", email);
                            if(type == 1) {
                                jsonParams.put("faceBookID", 0);
                                jsonParams.put("twitterID", id);
                            }
                            else if(type == 2) {
                                jsonParams.put("faceBookID", id);
                                jsonParams.put("twitterID", 0);
                            }
                            else if(type == 3) {
                                jsonParams.put("faceBookID", id);
                                jsonParams.put("twitterID", 0);
                            }
                            jsonParams.put("device_ID", task.getResult().getToken());
                            // jsonParams.put("address_1", selected_address.getAddress());
                            // jsonParams.put("state", state);
                            //jsonParams.put("phone", selected_address.getMobile());

                            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
                            //defining the call
                            Call<UserResponse> call_register = service.createUserBody(
                                    body
                            );

                            //calling the api
                            call_register.enqueue(new Callback<UserResponse>() {
                                @Override
                                public void onResponse(Call<UserResponse> call_register, Response<UserResponse> response) {
                                    //hiding progress dialog
                                    hideProgressDialog();

                                    if(response.body() != null) {

                                        if(response.body().getStatusMessage().equals("Email already registered"))
                                        {

                                            hideProgressDialog();
                                            Log.d("REG", "onResponse: " + response.body().getStatusMessage());
                                        }
                                        else  if(response.body().getStatusMessage().equals("Success"))
                                        {
                                            //GET USER INFO
                                            Map<String, Object> jsonLoginParams = new ArrayMap<>();

                                            //put something inside the map, could be null
                                            jsonLoginParams.put("Email", email);
                                            jsonLoginParams.put("password", email);
                                            jsonLoginParams.put("device_ID", task.getResult().getToken());
                                            jsonLoginParams.put("Language_ID", 1);
                                            // jsonParams.put("address_1", selected_address.getAddress());
                                            // jsonParams.put("state", state);
                                            //jsonParams.put("phone", selected_address.getMobile());

                                            RequestBody bodylogin = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonLoginParams)).toString());
                                            //defining the call
                                            Call<User> call_login = service.Login(
                                                    bodylogin
                                            );
                                            call_login.enqueue(new Callback<User>() {
                                                @Override
                                                public void onResponse(Call<User> call, Response<User> response) {

                                                    if(response.body().getStatusMessage().equals("Invalid Password"))
                                                    {
                                                        hideProgressDialog();
                                                        Log.d("REG", "onResponse: " + response.body().getStatusMessage());
                                                    }
                                                    else  if(response.body().getStatusMessage().equals("Success"))
                                                    {
                                                        hideProgressDialog();
                                                        SharedPrefManager.getInstance(getApplicationContext()).UserLogin(response.body());
                                                        Intent intent = new Intent(getActivity(), MainActivity.class);
                                                        startActivity(intent);
                                                        getActivity().finish();
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<User> call, Throwable t) {
                                                    Log.d("REG", "onFailure 1: " + t.getLocalizedMessage());
                                                    hideProgressDialog();
                                                }
                                            });


                                        }
                                        else
                                        {
                                            hideProgressDialog();
                                            Log.d("REG", "onResponse: " + response.body().getStatusMessage());
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<UserResponse> call_register, Throwable t) {
                                    hideProgressDialog();
                                    Log.d("REG", "onFailure 2: " + t.getLocalizedMessage());
                                    //Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call_register, Throwable t) {
                        Log.d("REG", "onFailure 3: " + t.getLocalizedMessage());
                        hideProgressDialog();
                    }
                });



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("REG", "onFailure 4: " + e.getLocalizedMessage());
                hideProgressDialog();
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SIGN_IN)
        {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess())
            {
                String profilePicUrl = "";
                showProgressDialog();
                try {
                    final Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    //checkFBLogin(id, email, name, profilePicUrl);
                    String id = result.getSignInAccount().getId().toString();
                    String email = result.getSignInAccount().getEmail().toString();
                    String name =result.getSignInAccount().getDisplayName().toString();
                    if(result.getSignInAccount().getDisplayName() != null) {
                        profilePicUrl = result.getSignInAccount().getDisplayName().toString();
                    }

                    signUp(email , id, name , profilePicUrl , timestamp.getTime() , 3);
                }
                catch (Exception e)
                {
                    Log.d("GOOGLE", "onActivityResult: " + e.getLocalizedMessage());
                    hideProgressDialog();
                }
                Log.d("GOOGLE", "onActivityResult: " + result.getSignInAccount().getEmail());
                Log.d("GOOGLE", "onActivityResult: " + result.getSignInAccount().getDisplayName());
                Log.d("GOOGLE", "onActivityResult: " + profilePicUrl);
                Log.d("GOOGLE", "onActivityResult: " + result.getSignInAccount().getId());
            }
        }

    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_register;
    }
    private void printKeyHash() {
        try {
            PackageInfo packageInfo =getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature  : packageInfo.signatures)
            {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA");
                messageDigest.update(signature.toByteArray());
                Log.e("KEYHASH", Base64.encodeToString(messageDigest.digest(), Base64.DEFAULT));
            }
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage("Loading....");
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
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

    public void showtermsDialog(Activity activity ,Terms terms){

        termsDialog = new Dialog(activity);
        // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        termsDialog.setCancelable(false);
        termsDialog.setContentView(R.layout.dialog_terms);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(termsDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        ImageView ivClose = termsDialog.findViewById(R.id.ivClose);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                termsDialog.dismiss();
                viewTerms.setEnabled(true);
            }
        });
        LinearLayout contentPanel = termsDialog.findViewById(R.id.contentPanel);
        TextView termsTitle = termsDialog.findViewById(R.id.termsTitle);
        TextView termsContent = termsDialog.findViewById(R.id.termsContent);
        ShimmerFrameLayout shimmerFrameLayout  = termsDialog.findViewById(R.id.parentShimmerLayout);
        shimmerFrameLayout.startShimmer();

        termsTitle.setText(terms.getTitle());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            termsContent.setText(Html.fromHtml(String.valueOf(terms.getContent()), Html.FROM_HTML_MODE_COMPACT));
        }
        else
        {
            termsContent.setText(terms.getContent());
        }
        shimmerFrameLayout.setVisibility(View.GONE);
        shimmerFrameLayout.stopShimmer();
        contentPanel.setVisibility(View.VISIBLE);

        termsDialog.show();
        termsDialog.getWindow().setAttributes(lp);
        viewTerms.setEnabled(false);


    }
    public void loadData()
    {
        ApiInterface service = RetrofitClient.retrofitWrite().create(ApiInterface.class);

        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("language_ID", 1);
        RequestBody bodyToken = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
        //defining the call
        Call<Terms> call = service.TermsConditions(
                bodyToken
        );
        call.enqueue(new Callback<Terms>() {
            @Override
            public void onResponse(Call<Terms> call, Response<Terms> response) {

                if(response.body().getStatusMessage().equals("Success"))
                {

                    showtermsDialog(getActivity() , response.body());

                }
            }

            @Override
            public void onFailure(Call<Terms> call, Throwable t) {

            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
