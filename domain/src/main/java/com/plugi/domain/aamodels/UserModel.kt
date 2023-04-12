package com.plugi.domain.aamodels
import com.google.gson.annotations.SerializedName


data class UserModel(
    @SerializedName("\$id")
    val id: String,
    @SerializedName("Email")
    val email: String,
    @SerializedName("Password")
    val password: String,
    @SerializedName("Device_ID")
    val deviceID: String,
    @SerializedName("Token")
    val token: String,
    @SerializedName("Customer")
    val customer: CustomerModel,
    @SerializedName("Language_ID")
    val languageID: Int,
    @SerializedName("Currency_ID")
    val currencyID: Int,
    @SerializedName("Status_Code")
    val statusCode: Int,
    @SerializedName("Status_Message")
    val statusMessage: String
)

data class CustomerModel(
    @SerializedName("\$id")
    val id: String,
    @SerializedName("ID")
    val iD: Int,
    @SerializedName("FirstName")
    val firstName: String,
    @SerializedName("LastName")
    val lastName: String,
    @SerializedName("CountryCode")
    val countryCode: Int,
    @SerializedName("EmailAddress")
    val emailAddress: String,
    @SerializedName("Password")
    val password: String,
    @SerializedName("MobileCode")
    val mobileCode: String,
    @SerializedName("MobileNumber")
    val mobileNumber: String,
    @SerializedName("EnableTouchID")
    val enableTouchID: Boolean,
    @SerializedName("FaceBookID")
    val faceBookID: Int,
    @SerializedName("TwitterID")
    val twitterID: Int,
    @SerializedName("Device_ID")
    val deviceID: String,
    @SerializedName("Language_ID")
    val languageID: Int,
    @SerializedName("Currency_ID")
    val currencyID: Int,
    @SerializedName("Status_Code")
    val statusCode: Int,
    @SerializedName("Status_Message")
    val statusMessage: Any?
)