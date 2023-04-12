package com.plugi.domain.gateways.serverGateways

import android.util.Log
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.plugi.domain.gateways.preferencesGateways.PrefManger
import com.plugi.domain.gateways.preferencesGateways.preferencesGateway
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import okhttp3.HttpUrl


val myRetrofit by lazy { ClientApi.retrofit }

object ClientApi {
    private val pref: PrefManger = preferencesGateway
    val retrofit: Retrofit by lazy {

        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    private val baseUrl by lazy {
        //https://therose-app.com/api
        //http://plugi.mawaqaademos.com/api/Plugi/
        HttpUrl.Builder()
            .scheme("http")
            .host("plugi.mawaqaademos.com")
            .addPathSegment("api")
            .addPathSegment("Plugi")
            .addPathSegment("")
            .build()
    }
    private val client by lazy {

        Log.e("TRY", "-->" + pref.loadCountryId().toString())
        Log.e("TRY", "-->" + pref.getAuthorization())
        OkHttpClient.Builder()
            .addInterceptor(
                LoggingInterceptor.Builder()
                    .setLevel(Level.BASIC)
                    .log(Log.VERBOSE)
                    .build()
            )
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .connectTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val request = chain.request()
                    .newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .build()
                chain.proceed(request)
            }
            .build()
    }

}