package com.akhil.mt_webncrafts.models.room_db

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import com.akhil.mt_webncrafts.models.data.AllData


class ApiResponseRepository(applicationContext: Context) {

    private lateinit var apiResponseDao: ApiResponseDao
    private val database = ApiResponseDb.getInstance(applicationContext)

    init {
        apiResponseDao = database.apiResponseDao()
    }

    suspend fun insert(apiResponseData: ApiResponseData) {
        apiResponseDao.insertApiResponse(apiResponseData)
    }

    fun getApiResponse(apiName: String): LiveData<List<ApiResponseData>> {
        return apiResponseDao.getApiResponse(apiName)
    }

}