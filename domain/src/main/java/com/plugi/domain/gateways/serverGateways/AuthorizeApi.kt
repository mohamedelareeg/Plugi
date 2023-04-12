package com.plugi.domain.gateways.serverGateways

import com.plugi.domain.aamodels.BaseModel
import com.plugi.domain.aamodels.LandingModel
import com.plugi.domain.aamodels.UserModel
import com.plugi.domain.core.ApiUrl
import retrofit2.Call
import retrofit2.http.*

internal val authorizeApi: AuthorizeApi by lazy { myRetrofit.create(AuthorizeApi::class.java) }

interface AuthorizeApi {
    @POST(ApiUrl.LANDING)
    fun getLandingInfo(@Body map: MutableMap<String, Any>): Call<LandingModel>

    @POST(ApiUrl.REGISTER)
    fun register(@Body map: MutableMap<String, Any>): Call<BaseModel>

    @POST(ApiUrl.LOGIN)
    fun login(@Body map: MutableMap<String, Any>): Call<UserModel>

    @POST(ApiUrl.FORGET_PASSWORD)
    fun forgetPassword(@Body map: MutableMap<String, Any>): Call<BaseModel>

    @POST(ApiUrl.RESET_PASSWORD)
    fun resetPassword(@Body map: MutableMap<String, Any>): Call<BaseModel>

//
//    @FormUrlEncoded
//    @POST(ApiUrl.CHECK_PHONE)
//    fun checkPhone(
//        @Field("country_code") countryCode: String,
//        @Field("phone") phone: String
//    ): Call<Calling<Any>>
//
//    @FormUrlEncoded
//    @POST(ApiUrl.FORGET_PASSWORD)
//    fun forgetPassword(
//        @Field("country_code") countryCode: String,
//        @Field("phone") phone: String,
//        @Field("user_type") userType: String
//    ): Call<Calling<Any>>
//
//    @FormUrlEncoded
//    @POST(ApiUrl.RESEND_SMS)
//    fun resendConfirmationCode(
//        @Field("country_code") countryCode: String,
//        @Field("phone") phone: String
//    ): Call<Calling<Any>>
//
//
//    @FormUrlEncoded
//    @POST(ApiUrl.CONFIRM_PHONE)
//    fun confirmCode(
//        @Field("country_code") countryCode: String,
//        @Field("phone") phone: String,
//        @Field("code") code: String
//    ): Call<Calling<Any>>
//
//
//    @JvmSuppressWildcards
//    @Multipart
//    @POST(ApiUrl.REGISTER_STEP_BASIC)
//    fun registerStepBasic(@PartMap map: Map<String, RequestBody>): Call<Calling<RegisterStepsModel>>
//
//
//    @FormUrlEncoded
//    @POST(ApiUrl.LOGIN)
//    fun login(
//        @Field("country_code") countryCode: String,
//        @Field("phone") phone: String,
//        @Field("password") password: String,
//        @Field("device_token") fireBaseToken: String,
//        @Field("device_identifier") deviceIndentifer: String,
//        @Field("user_type") type: String
//    ): Call<Calling<UserModel>>
//
//
//    @JvmSuppressWildcards
//    @POST(ApiUrl.REGISTER_STEP_INFO)
//    fun registerStepInfo(@Body map: LinkedHashMap<String, Any>): Call<Calling<RegisterStepsModel>>
//
//
//    @JvmSuppressWildcards
//    @Multipart
//    @POST(ApiUrl.UPLOAD_FILE_REGISTER)
//    fun uploadFile(@PartMap map: Map<String, RequestBody>): Call<Calling<FilePathModel>>
//
//    @JvmSuppressWildcards
//    @POST(ApiUrl.REGISTER_STEP_PROFILE)
//    fun registerStepProfileInfo(@Body map: LinkedHashMap<String, Any>): Call<Calling<RegisterStepsModel>>
//
//    @JvmSuppressWildcards
//    @POST(ApiUrl.REGISTER_STEP_SECTIONS)
//    fun registerStepSections(@Body map: LinkedHashMap<String, Any>): Call<Calling<UserModel>>
//
//    @FormUrlEncoded
//    @POST(ApiUrl.CONFIRM_PHONE_FORGET_PASSWORD)
//    fun confirmCodeForForgetPassword(
//      @Field("country_code") countryCode: String,
//      @Field("phone") phone: String,
//      @Field("reset_code") code: String,
//      @Field("user_type") userType: String
//    ): Call<Calling<Any>>
//
//    @FormUrlEncoded
//    @POST(ApiUrl.RESEND_SMS_FORGET_PASSWORD)
//    fun resendConfirmationCodeForForgetPassword(
//        @Field("country_code") countryCode: String,
//        @Field("phone") phone: String,
//        @Field("user_type") userType: String
//    ): Call<Calling<Any>>
//
//
//    @FormUrlEncoded
//    @POST(ApiUrl.RESET_PASSWORD)
//    fun resetPassword(
//      @Field("country_code") countryCode: String,
//      @Field("phone") phone: String,
//      @Field("password") password: String,
//      @Field("password_confirmation") rePassword: String,
//      @Field("user_type") userType: String
//    ): Call<Calling<Any>>
//
//    @FormUrlEncoded
//    @POST(ApiUrl.REMOVE_FILE_REGISTER)
//    fun removeUploadedFile(@Field("name") mFile: String): Call<Calling<Any>>

}