package com.plugi.plugi.feature.forgetPassword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.plugi.domain.core.casesHandler.Results
import com.plugi.domain.useCases.authorize.UseCaseImpl
import com.plugi.plugi.R
import com.plugi.plugi.core.base.BaseActivity
import com.plugi.plugi.core.extentions.sStartActivity
import com.plugi.plugi.core.extentions.toasting
import com.plugi.plugi.core.extentions.value
import com.plugi.plugi.core.utilities.ValidationLayer
import com.plugi.plugi.feature.resetPassword.ResetPasswordActivity
import kotlinx.android.synthetic.main.activity_forget_password.*
import kotlinx.coroutines.launch

class ForgetPasswordActivity : BaseActivity() {
    override fun layoutResource(): Int =R.layout.activity_forget_password

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        attachContextToLoadingDialog(this)
        clickers()
    }

    private fun clickers() {

        btnResetPassword.setOnClickListener {
            if (ValidationLayer.validateEmail(etEmail)){
                doResetPassword()
            }
        }
    }

    private fun doResetPassword() {

        scopeIO.launch {
            scopeMain.launch { showLoadingDialog() }
            val data = UseCaseImpl().forgetPassword(etEmail.value())
            scopeMain.launch {
                dismissLoadingDialog()
                when(data){
                    is Results.Success -> {
                        if (data.value?.statusCode==1){
                        sStartActivity<ResetPasswordActivity>(
                            "email" to etEmail.value()
                        )
                        }else{
                            toasting(data.value?.statusMessage)
                        }
                    }
                }
            }
        }
    }
}