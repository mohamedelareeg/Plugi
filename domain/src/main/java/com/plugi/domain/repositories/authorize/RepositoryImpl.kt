package com.plugi.domain.repositories.authorize

import com.plugi.domain.aamodels.BaseModel
import com.plugi.domain.aamodels.CustomerModel
import com.plugi.domain.aamodels.LandingModel
import com.plugi.domain.aamodels.UserModel
import com.plugi.domain.gateways.cacheGateway.ICacheGateWay
import com.plugi.domain.gateways.cacheGateway.cacheGateway
import com.plugi.domain.gateways.preferencesGateways.PrefMangerImplementer
import com.plugi.domain.gateways.preferencesGateways.preferencesGateway
import com.plugi.domain.gateways.serverGateways.AuthorizeApi
import com.plugi.domain.gateways.serverGateways.authorizeApi
import retrofit2.Call


class RepositoryImpl(
    private val cache: ICacheGateWay = cacheGateway,
    private val prefManger: PrefMangerImplementer = preferencesGateway,
    private val server: AuthorizeApi = authorizeApi
) : Repository {
    override fun getSystemLanguage(): String {
       return prefManger.getSystemLanguage()
    }

    override fun changeSystemLanguage(language: String) {
        prefManger.setSystemLanguage(language)
        prefManger.setHasSelectLanguage(true)
    }

    override suspend fun getLandingInfo(map: MutableMap<String, Any>): Call<LandingModel> {
        return server.getLandingInfo(map)
    }

    override suspend fun register(map: MutableMap<String, Any>): Call<BaseModel> {
        return server.register(map)
    }

    override suspend fun login(map: MutableMap<String, Any>): Call<UserModel> {
        return server.login(map)
    }

    override fun saveUserData(customer: UserModel) {
        prefManger.setUserData(customer)
    }

    override fun isUserLogin(): Boolean {
        return prefManger.getUserLogged()
    }

    override suspend fun forgetPassword(map: MutableMap<String, Any>): Call<BaseModel> {
        return server.forgetPassword(map)
    }

    override suspend fun resetPassword(map: MutableMap<String, Any>): Call<BaseModel> {
        return server.resetPassword(map)
    }
}