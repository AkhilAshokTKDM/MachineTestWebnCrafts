package com.akhil.mt_webncrafts.models.room_db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ApiResponseDao {
    @Insert
    suspend fun insertApiResponse(apiResponseData: ApiResponseData)

    @Query("SELECT * FROM api_response_table WHERE apiName = :apiNameValue")
    fun getApiResponse(apiNameValue : String): LiveData<List<ApiResponseData>>

    @Update
    suspend fun updateApiResponse(book: ApiResponseData)

    @Delete
    suspend fun deleteApiResponse(book: ApiResponseData)
}
