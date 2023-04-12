package com.plugi.domain.useCases.authorize

import com.plugi.domain.aamodels.BaseModel
import com.plugi.domain.aamodels.LandingModel
import com.plugi.domain.aamodels.UserModel
import com.plugi.domain.core.casesHandler.Results
import com.plugi.domain.repositories.authorize.Repository
import com.plugi.domain.repositories.authorize.RepositoryImpl


class UseCaseImpl(
    private val repository: Repository = RepositoryImpl()
) : UseCase {
    var map = mutableMapOf<String,Any>()
    init {
        map["language_ID"] =1
        map["device_ID"] ="device_id"
    }
    override fun changeSystemLanguage(language: String) {
        repository.changeSystemLanguage(language)
    }

    override fun getSystemLanguage(): String {
        return repository.getSystemLanguage()
    }

    override suspend fun getLandingInfo(): Results<LandingModel?> {
        return ServicesTransform.transform(repository.getLandingInfo(map))

    }

    override suspend fun register(
        firstName: String,
        lastName: String,
        emailAddress: String,
        mobileCode: String,
        mobileNumber: String,
        password: String,
        faceBookID: Int,
        twitterID: Int,
        device_ID: Int
    ): Results<BaseModel?> {
        map["firstName"] = firstName
        map["lastName"] = lastName
        map["emailAddress"] = emailAddress
        map["mobileCode"] = mobileCode
        map["mobileNumber"] = mobileNumber
        map["password"] = password
        map["faceBookID"] = faceBookID
        map["twitterID"] = twitterID
        map["device_ID"] = device_ID
        return ServicesTransform.transform(repository.register(map))
    }

    override suspend fun login(email: String, password: String): Results<UserModel?> {
        map["email"] = email
        map["password"] = password
        val data  = ServicesTransform.transform(repository.login(map))
        when(data){
            is Results.Success -> {
                if (data.value?.statusCode ==1){
                    repository.saveUserData(data.value)
                }
            }
        }

        return data

    }

    override fun isUserLogin(): Boolean {
        return  repository.isUserLogin()
    }

    override suspend fun forgetPassword(value: String): Results<BaseModel?> {
        map["emailAddress"] = value
        return  ServicesTransform.transform(repository.forgetPassword(map))
    }

    override suspend fun resetPassword(
        email: String?,
        password: String,
        code: String
    ): Results<BaseModel?> {
        map["email"] = email!!
        map["password"] = password
        map["code"] = code

        return  ServicesTransform.transform(repository.resetPassword(map))
    }
}