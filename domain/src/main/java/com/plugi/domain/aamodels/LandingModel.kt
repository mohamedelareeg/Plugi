package com.plugi.domain.aamodels
import com.google.gson.annotations.SerializedName


data class LandingModel(
    @SerializedName("\$id")
    val id: String,
    @SerializedName("Step1")
    val step1: List<StepModel>,
    @SerializedName("Step2")
    val step2: List<StepModel>,
    @SerializedName("Step3")
    val step3: List<StepModel>,
    @SerializedName("Language_ID")
    val languageID: Int,
    @SerializedName("Currency_ID")
    val currencyID: Int,
    @SerializedName("Status_Code")
    val statusCode: Int,
    @SerializedName("Status_Message")
    val statusMessage: String
)

data class StepModel(
    @SerializedName("\$id")
    val id: String,
    @SerializedName("Title")
    val title: String,
    @SerializedName("Description")
    val description: String,
    @SerializedName("Icon")
    val icon: String
)
