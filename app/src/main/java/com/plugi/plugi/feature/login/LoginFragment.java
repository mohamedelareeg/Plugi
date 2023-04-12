package com.plugi.plugi.feature.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.ArrayMap;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.plugi.plugi.R;
import com.plugi.plugi.core.base.BaseFragment;
import com.plugi.plugi.feature.forgetPassword.ForgetPasswordFragment;
import com.plugi.plugi.feature.main.MainActivity;
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

public class LoginFragment extends BaseFragment {

    public ProgressDialog mProgressDialog;
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private TextView viewForgetPassword;
    private EditText etEmail , etPassword;
    private TextInputLayout etpasswordPanel;
    private Button btnLogin;
    private String check;

    LinearLayout viewGoogle;

    private static final int SIGN_IN = 1;
    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

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


        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
        etpasswordPanel = view.findViewById(R.id.etpasswordPanel);
        etPassword.setTransformationMethod(new PasswordTransformationMethod());
        btnLogin = view.findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmailValid(etEmail.getText().toString())){
                    if (etPassword.getText().length() > 6){
                        attemptLogin(etEmail.getText().toString(), etPassword.getText().toString());
                    }else {
                        etPassword.setError(getResources().getString(R.string.password_should_longer_than_six));
                    }
                }else {
                    etEmail.setError(getResources().getString(R.string.enter_valid_email));
                }
            }
        });
        viewForgetPassword = view.findViewById(R.id.viewForgetPassword);
        viewForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                fragment = new ForgetPasswordFragment();
                ((MainActivity) getActivity()).replaceFragments(fragment);
            }
        });
        loginButton = (LoginButton)view.findViewById(R.id.login_button);
        loginButton.setFragment(this);
        loginButton.setReadPermissions("email" , "user_location" , "user_gender" , "user_photos" , "user_birthday" , "user_friends");
        loginButton.setLoginText(getResources().getString(R.string.login_with_facebook));

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signIn();
            }
        });
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                etPassword.setError(null);
                etpasswordPanel.setError(null);
                etpasswordPanel.setErrorEnabled(false); // disable error

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }
    private void attemptLogin(String email, String password){

        ApiInterface service = RetrofitClient.retrofitWrite().create(ApiInterface.class);

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {


                //GET USER INFO
                Map<String, Object> jsonLoginParams = new ArrayMap<>();

                //put something inside the map, could be null
                jsonLoginParams.put("email", email);
                jsonLoginParams.put("password", password);
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

                            etpasswordPanel.setError(getResources().getString(R.string.invild_password));
                            Log.d("REG", "onResponse: " + response.body().getStatusMessage());
                        }
                        else if(response.body().getStatusMessage().equals("Email Not Exist"))
                        {

                            etEmail.setError(getResources().getString(R.string.email_not_exist));
                            Log.d("REG", "onResponse: " + response.body().getStatusMessage());
                        }
                        else  if(response.body().getStatusMessage().equals("Success"))
                        {
                            Log.d("REG", "onResponse: " + response.body().getStatusMessage());
                            SharedPrefManager.getInstance(getApplicationContext()).UserLogin(response.body());
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.d("REG", "onFailure: " + t.getLocalizedMessage());
                        etEmail.setError(getResources().getString(R.string.invild_email_or_password));
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

                                        etPassword.setError(getResources().getString(R.string.invild_password));
                                        Log.d("REG", "onResponse: " + response.body().getStatusMessage());
                                    }
                                    else if(response.body().getStatusMessage().equals("Email Not Exist"))
                                    {

                                        etEmail.setError(getResources().getString(R.string.email_not_exist));
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
                                    Log.d("REG", "onFailure: " + t.getLocalizedMessage());
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
                Log.d("REG", "onFailure: " + e.getLocalizedMessage());
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
        return R.layout.fragment_login;
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

        if (check.length() < 4 || check.length() > 40) {
            return false;
        } else if (!check.matches("^[A-za-z0-9.@]+")) {
            return false;
        } else if (!check.contains("@") || !check.contains(".")) {
            return false;
        }

        return true;
    }



}
