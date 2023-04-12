package com.plugi.plugi.feature.splash

import android.os.Bundle
import android.util.Log
import com.plugi.domain.useCases.authorize.UseCaseImpl
import com.plugi.plugi.R
import com.plugi.plugi.core.base.BaseActivity
import com.plugi.plugi.core.extentions.sStartActivityWithClear
import com.plugi.plugi.feature.language.SelectLanguageActivity
import com.plugi.plugi.feature.main.MainActivity
import com.plugi.plugi.retrofit.SharedPrefManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : BaseActivity() {
    override fun layoutResource(): Int =R.layout.activity_splash
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        whereToGo()
    }

    private fun whereToGo() {
        val userLogin = UseCaseImpl().isUserLogin()
        val userCLogin = SharedPrefManager.getInstance(this).isLoggedIn()
        val guestBoarding = SharedPrefManager.getInstance(this).guestOnBoarding
        Log.d("REG", "whereToGo: " + userCLogin)
        if (!userCLogin){
            if(guestBoarding != 0)
            {
                scopeMain.launch {
                    delay(1000*2)
                    sStartActivityWithClear<MainActivity>()//SelectLanguageActivity //MainActivity
                }
            }
            else
            {
                scopeMain.launch {
                    delay(1000*2)
                    sStartActivityWithClear<SelectLanguageActivity>()//SelectLanguageActivity //MainActivity
                }
            }

        }else{
            scopeMain.launch {
                delay(1000*2)
                sStartActivityWithClear<MainActivity>()
            }
        }


    }
}