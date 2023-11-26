package com.akhil.mt_webncrafts.models.api

import com.google.gson.GsonBuilder
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private const val READ_TIME_OUT: Long = 25
    private const val CONNECTION_TIME_OUT: Long = 25
    private const val WRITE_TIME_OUT: Long = 300

    val apiInterface: ApiClient by lazy()
    { retrofit(true).create(ApiClient::class.java) }

    fun retrofit(isRequiredInterceptor: Boolean): Retrofit {
        var baseUrl: String = "https://64bfc2a60d8e251fd111630f.mockapi.io/api/"

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(createClientAuth(isRequiredInterceptor))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    private fun createClientAuth(isRequiredInterceptor: Boolean): OkHttpClient {
        //ADD DISPATCHER WITH MAX REQUEST TO 1
        val dispatcher = Dispatcher()
        dispatcher.maxRequests = 1
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        okHttpClientBuilder.dispatcher(dispatcher)
        okHttpClientBuilder.readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
        okHttpClientBuilder.connectTimeout(CONNECTION_TIME_OUT, TimeUnit.SECONDS)
        okHttpClientBuilder.writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
        return okHttpClientBuilder.build()
    }

    private val gson = GsonBuilder().setLenient().create()
}