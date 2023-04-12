package com.plugi.domain.aamodels
import com.google.gson.annotations.SerializedName


data class BaseModel(

    @SerializedName("Language_ID")
    val languageID: Int,
    @SerializedName("Currency_ID")
    val currencyID: Int,
    @SerializedName("Status_Code")
    val statusCode: Int,
    @SerializedName("Status_Message")
    val statusMessage: String
)