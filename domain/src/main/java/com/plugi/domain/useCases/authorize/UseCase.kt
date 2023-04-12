package com.plugi.domain.useCases.authorize

import com.plugi.domain.aamodels.BaseModel
import com.plugi.domain.aamodels.LandingModel
import com.plugi.domain.aamodels.UserModel
import com.plugi.domain.core.casesHandler.Results

interface UseCase{
    fun changeSystemLanguage(language: String)
    fun getSystemLanguage():String
    suspend fun getLandingInfo(): Results<LandingModel?>
    suspend fun register(
        firstName: String,
        lastName: String,
        emailAddress: String,
        mobileCode: String,
        mobileNumber: String,
        password: String,
        faceBookID: Int,
        twitterID: Int,
        device_ID: Int
    ): Results<BaseModel?>

   suspend fun login(email: String, password: String): Results<UserModel?>
    fun isUserLogin():Boolean
   suspend fun forgetPassword(value: String):  Results<BaseModel?>
    suspend fun resetPassword(email: String?, password: String, code: String):  Results<BaseModel?>
}