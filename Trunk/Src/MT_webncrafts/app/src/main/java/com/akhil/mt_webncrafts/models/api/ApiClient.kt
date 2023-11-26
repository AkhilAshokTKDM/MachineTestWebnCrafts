package com.akhil.mt_webncrafts.models.api

import com.akhil.mt_webncrafts.models.data.AllData
import retrofit2.Call
import retrofit2.http.GET

interface ApiClient {
    @GET(ApiConstants.get_home_data)
    fun getHomeData(): Call<List<AllData>>
}