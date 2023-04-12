package com.plugi.domain.repositories.authorize

import com.plugi.domain.aamodels.BaseModel
import com.plugi.domain.aamodels.CustomerModel
import com.plugi.domain.aamodels.LandingModel
import com.plugi.domain.aamodels.UserModel
import retrofit2.Call


interface Repository {
    fun getSystemLanguage(): String
    fun changeSystemLanguage(language: String)
    suspend fun getLandingInfo(map: MutableMap<String, Any>): Call<LandingModel>
    suspend fun register(map: MutableMap<String, Any>): Call<BaseModel>
    suspend fun login(map: MutableMap<String, Any>): Call<UserModel>
    fun saveUserData(customer: UserModel)
    fun isUserLogin(): Boolean
    suspend fun forgetPassword(map: MutableMap<String, Any>): Call<BaseModel>
    suspend fun resetPassword(map: MutableMap<String, Any>): Call<BaseModel>

}