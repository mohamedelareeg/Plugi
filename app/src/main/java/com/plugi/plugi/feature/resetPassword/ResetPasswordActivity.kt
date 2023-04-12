package com.plugi.plugi.feature.resetPassword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.plugi.domain.core.casesHandler.Results
import com.plugi.domain.useCases.authorize.UseCaseImpl
import com.plugi.plugi.R
import com.plugi.plugi.core.base.BaseActivity
import com.plugi.plugi.core.extentions.sStartActivity
import com.plugi.plugi.core.extentions.toasting
import com.plugi.plugi.core.extentions.value
import com.plugi.plugi.core.utilities.ValidationLayer
import kotlinx.android.synthetic.main.activity_forget_password.*
import kotlinx.android.synthetic.main.activity_reset_password.*
import kotlinx.android.synthetic.main.activity_reset_password.btnResetPassword
import kotlinx.coroutines.launch

class ResetPasswordActivity : BaseActivity() {
    private var email: String? =""

    override fun layoutResource(): Int =R.layout.activity_reset_password

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        attachContextToLoadingDialog(this)
       email = intent.getStringExtra("email")
        clickers()
    }

    private fun clickers() {
        btnResetPassword.setOnClickListener {
            if (validates()){
                doResetPassword()
            }
        }
        btnResendPassword.setOnClickListener {
            scopeIO.launch {
                scopeMain.launch { showLoadingDialog() }
                val data = UseCaseImpl().forgetPassword(etEmail.value())
                scopeMain.launch {
                    dismissLoadingDialog()
                    when(data){
                        is Results.Success -> {
                            if (data.value?.statusCode==1){
                                toasting(data.value?.statusMessage)
                            }else{
                                toasting(data.value?.statusMessage)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun doResetPassword() {

        scopeIO.launch {
            scopeMain.launch { showLoadingDialog() }
            val data = UseCaseImpl().resetPassword(email,etPassword.value(),etOtp.value())
            scopeMain.launch {
                dismissLoadingDialog()
                when(data){
                    is Results.Success -> {
                        if (data.value?.statusCode==1){
                            toasting("Your password has been changed successfully!")
                        }else{
                            toasting("Error Opt")
                            toasting(data.value?.statusMessage)
                        }
                    }
                }
            }
        }
    }

    private fun validates(): Boolean {
        if (!ValidationLayer.validateEmpty(etOtp)) return false
        if (!ValidationLayer.validateLength(etPassword)) return false
        if (!ValidationLayer.validateMatch(etRePassword,etPassword)) return false
        return true
    }
}