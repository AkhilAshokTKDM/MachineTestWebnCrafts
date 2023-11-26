package com.akhil.mt_webncrafts.models.room_db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//@Entity(tableName = "api_response_table")
//data class ApiResponseData(
//
//    @PrimaryKey
//    var apiName: String,
//
//    @ColumnInfo(name = "api_response")
//    var response: String
//)
@Entity(tableName = "api_response_table")
data class ApiResponseData(val apiName: String,
                val apiResponse: String,
                @PrimaryKey(autoGenerate = false) val id: Int? = null)

