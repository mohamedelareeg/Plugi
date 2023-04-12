package com.plugi.plugi.feature.language

import android.os.Bundle
import com.plugi.domain.useCases.authorize.UseCaseImpl
import com.plugi.plugi.R
import com.plugi.plugi.core.base.BaseActivity
import com.plugi.plugi.core.extentions.sStartActivityWithClear
import com.plugi.plugi.feature.onBoarding.OnBoardingActivity
import kotlinx.android.synthetic.main.activity_select_language.*

class SelectLanguageActivity : BaseActivity() {
    override fun layoutResource(): Int =R.layout.activity_select_language
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        clickers()
    }

    private fun clickers() {

        tvEnglish.setOnClickListener { changeLanguage("en") }
        tvArabic.setOnClickListener { changeLanguage("ar") }
    }

    private fun changeLanguage(language: String) {
        UseCaseImpl().changeSystemLanguage("en")
        sStartActivityWithClear<OnBoardingActivity>()
    }
}