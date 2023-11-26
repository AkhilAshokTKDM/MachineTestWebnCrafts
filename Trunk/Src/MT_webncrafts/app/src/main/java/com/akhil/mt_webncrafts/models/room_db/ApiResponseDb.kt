package com.akhil.mt_webncrafts.models.room_db
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase



@Database(entities = [ApiResponseData::class], version = 1)
abstract class ApiResponseDb : RoomDatabase() {

    abstract fun apiResponseDao(): ApiResponseDao

    companion object {
        private var instance: ApiResponseDb? = null

        @Synchronized
        fun getInstance(ctx: Context): ApiResponseDb {
            if (instance == null) {
                instance = Room.databaseBuilder(ctx,
                    ApiResponseDb::class.java,
                    "apiresponse").fallbackToDestructiveMigration().build()
            }
            return instance!!
        }
    }
}


