package com.plugi.plugi.feature.onBoarding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.plugi.domain.aamodels.LandingModel
import com.plugi.domain.core.casesHandler.Results
import com.plugi.domain.useCases.authorize.UseCaseImpl
import com.plugi.plugi.R
import com.plugi.plugi.core.base.BaseActivity
import com.plugi.plugi.core.extentions.controlViews
import com.plugi.plugi.core.extentions.controlViewsResponse
import com.plugi.plugi.core.extentions.sStartActivityWithClear
import com.plugi.plugi.feature.login.LoginActivity
import com.plugi.plugi.feature.main.MainActivity
import com.plugi.plugi.retrofit.SharedPrefManager
import kotlinx.android.synthetic.main.activity_on_boarding.*
import kotlinx.coroutines.launch

class OnBoardingActivity : BaseActivity() {
     var model: LandingModel? = null

    override fun layoutResource(): Int =R.layout.activity_on_boarding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        scopeIO.launch {
            scopeMain.launch { controlViews() }
            val data = UseCaseImpl().getLandingInfo()
            scopeMain.launch {
                when(data){
                    is Results.Success -> {
                        updateUi(data.value)
                    }
                }
                controlViewsResponse(data)
            }
        }

    }

    private fun updateUi(value: LandingModel?) {
        model = value
        var adapter = TabWithPagerAdapter(supportFragmentManager)
        adapter.addFragment(FragmentLandingStepOne(),"")
        adapter.addFragment(FragmentLandingStepTwo(),"")
        adapter.addFragment(FragmentLandingStepThree(),"")
        vpLanding.adapter = adapter

    }

    fun goToNext() {

        sStartActivityWithClear<MainActivity>()
        SharedPrefManager.getInstance(applicationContext).GuestOnBoarding();
    }
}