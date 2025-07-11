package com.katorabian.terminal.data.net

import com.katorabian.terminal.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object ApiFactory {

    private const val KEY_PARAM = "apiKey"
    private const val BASE_URL = "https://api.polygon.io/"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val origRequest = chain.request()
            val newUrl = origRequest.url().newBuilder()
                .addQueryParameter(KEY_PARAM, BuildConfig.TERMINAL_API_KEY)
                .build()
            val newRequest = origRequest.newBuilder()
                .url(newUrl)
                .build()
            chain.proceed(newRequest)
        }.build()

    private val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService = retrofit.create()
}