package com.plugi.plugi.feature.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.plugi.domain.core.casesHandler.Results
import com.plugi.domain.useCases.authorize.UseCaseImpl
import com.plugi.plugi.R
import com.plugi.plugi.core.base.BaseFragment
import com.plugi.plugi.core.extentions.sStartActivity
import com.plugi.plugi.core.extentions.sStartActivityWithClear
import com.plugi.plugi.core.extentions.toasting
import com.plugi.plugi.core.extentions.value
import com.plugi.plugi.core.utilities.ValidationLayer
import com.plugi.plugi.feature.forgetPassword.ForgetPasswordActivity
import com.plugi.plugi.feature.splash.SplashActivity
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.util.*

class ControllerLoginFragment : BaseFragment() {
    private val faceBookID: Int = 0;
    private val twitterID: Int = 0;
    private val device_ID: Int = 0;
    var callbackManager: CallbackManager? = null
    var mGoogleSignInClient: GoogleSignInClient? = null

    override fun layoutRes(): Int = R.layout.fragment_login
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseInit()
        clickers()
    }
    private fun firebaseInit() {
        //fb login
        callbackManager = CallbackManager.Factory.create()
        facebookLogin()
        // google login
        mGoogleSignInClient = GoogleSignIn.getClient(
            requireActivity(),
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        )
    }
    private fun clickers() {
        viewForgetPassword.setOnClickListener {
            requireActivity().sStartActivity<ForgetPasswordActivity>()
        }
        viewFaceBook.setOnClickListener {
            btn_fb_login.performClick()
        }
        viewGoogle.setOnClickListener {
            loginWithGoogle()
        }
        btnLogin.setOnClickListener {
            if (validation()){
                doLogin()
            }
        }
    }

    private fun doLogin() {
        scopeIO.launch {
            scopeMain.launch { showLoadingDialog() }
            val data = UseCaseImpl().login(etEmail.value(),etPassword.value())
            scopeMain.launch {
                dismissLoadingDialog()
                when(data){
                    is Results.Success -> {
                        if (data.value?.statusCode==1){
                            requireActivity().sStartActivityWithClear<SplashActivity>()
                        }else{
                            requireActivity().toasting(data.value?.statusMessage)
                        }
                    }
                }
            }
        }

    }

    private fun validation(): Boolean {
        if (!ValidationLayer.validateEmail(etEmail)) return false;
        if (!ValidationLayer.validateLength(etPassword)) return false;
        return true

    }
    private fun loginWithGoogle() {

        val signInIntent = mGoogleSignInClient!!.signInIntent
        startActivityForResult(
            signInIntent,
            500
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager!!.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)

        // google login
        if (requestCode == 500) {
            val task =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            if (task.isSuccessful) {
                Log.e("task", "isSuccessful")
            } else {
                Log.e("task", "Not Successful")
            }
            handleSignInResult(task)
        }
    }
    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        val id: String?
        val name: String?
        val email: String?
        var image: String?
        try {
            val account =
                completedTask.getResult(ApiException::class.java)
            if (account != null) {
                id = account.id
                name = account.displayName
                email = account.email
                image = "https://itgeeks.com/images/logo.png"
                if (account.photoUrl != null) {
                    image = account.photoUrl.toString()
                }
                socialLogin(
                    id,
                    name,
                    email,
                    ""

                )
            }
        } catch (e: ApiException) {
        } catch (e: Exception) {
        }
    }

    private fun socialLogin(id: String?, name: String?, email: String?, s: String) {


    }

    private fun facebookLogin() {
        btn_fb_login.setReadPermissions(
            Arrays.asList(
                "public_profile",
                "email"
            )
        )
        btn_fb_login.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                val mGraphRequest = GraphRequest.newMeRequest(
                    loginResult.accessToken
                ) { `object`, response -> getData(`object`) }
                val parameters = Bundle()
                parameters.putString("fields", "id,name,email,gender, birthday")
                mGraphRequest.parameters = parameters
                mGraphRequest.executeAsync()
            }

            override fun onCancel() {

            }

            override fun onError(exception: FacebookException) {

            }
        })
        if (AccessToken.getCurrentAccessToken() != null) {
            LoginManager.getInstance()
                .logInWithReadPermissions(this, Arrays.asList("public_profile"))
        }
    }

    private fun getData(jsonObject: JSONObject?) {


/*
        // Facebook Id
        if (jsonObject?.has("id")!!) {
            val facebookId = jsonObject.getString("id")
            Log.i("Facebook Id: ", facebookId.toString())
        } else {
            Log.i("Facebook Id: ", "Not exists")
        }


        // Facebook First Name
        if (jsonObject.has("first_name")) {
            val facebookFirstName = jsonObject.getString("first_name")
            Log.i("Facebook First Name: ", facebookFirstName)
        } else {
            Log.i("Facebook First Name: ", "Not exists")
        }


        // Facebook Middle Name
        if (jsonObject.has("middle_name")) {
                val facebookMiddleName = jsonObject.getString("middle_name")
                Log.i("Facebook Middle Name: ", facebookMiddleName)
            } else {
                Log.i("Facebook Middle Name: ", "Not exists")
            }


        // Facebook Last Name
        if (jsonObject.has("last_name")) {
                val facebookLastName = jsonObject.getString("last_name")
                Log.i("Facebook Last Name: ", facebookLastName)
            } else {
                Log.i("Facebook Last Name: ", "Not exists")
            }


        // Facebook Name
        if (jsonObject.has("name")) {
            val facebookName = jsonObject.getString("name")
            Log.i("Facebook Name: ", facebookName)
        } else {
            Log.i("Facebook Name: ", "Not exists")
        }


        // Facebook Profile Pic URL
        if (jsonObject.has("picture")) {
            val facebookPictureObject = jsonObject.getJSONObject("picture")
            if (facebookPictureObject.has("data")) {
                val facebookDataObject = facebookPictureObject.getJSONObject("data")
                if (facebookDataObject.has("url")) {
                    val facebookProfilePicURL = facebookDataObject.getString("url")
                    //Log.i("Facebook Profile Pic URL: ", facebookProfilePicURL)
                }
            }
        } else {
            //Log.i("Facebook Profile Pic URL: ", "Not exists")
        }

        // Facebook Email
        if (jsonObject.has("email")) {
            val facebookEmail = jsonObject.getString("email")
            Log.i("Facebook Email: ", facebookEmail)
        } else {
            Log.i("Facebook Email: ", "Not exists")
        }


 */
    }


}
