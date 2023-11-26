package com.akhil.mt_webncrafts.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.akhil.mt_webncrafts.MyApp
import com.akhil.mt_webncrafts.models.data.AllData
import com.akhil.mt_webncrafts.models.api.RetrofitClient
import com.akhil.mt_webncrafts.models.room_db.ApiResponseData
import com.akhil.mt_webncrafts.models.room_db.ApiResponseRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    var onSuccessMutableLiveData: MutableLiveData<List<AllData>?> = MutableLiveData<List<AllData>?>()
    var onErrorMutableLiveData: MutableLiveData<String?>? = null
    val loading = MutableLiveData<Boolean>()
    private val repository = ApiResponseRepository(MyApp.applicationContext())

    fun onSuccessHandler(): MutableLiveData<List<AllData>?> {
        return onSuccessMutableLiveData
    }

    fun onErrorHandler(): MutableLiveData<String?>? {
        if (onErrorMutableLiveData == null) {
            onErrorMutableLiveData = MutableLiveData<String?>()
        }
        return onErrorMutableLiveData
    }

    fun getHomeData() {
        val call = RetrofitClient.apiInterface.getHomeData()
        call.enqueue(object : Callback<List<AllData>> {
            override fun onResponse(
                call: Call<List<AllData>?>, response: Response<List<AllData>?>
            ) {

                if (response.isSuccessful && response.code() == 200) {
                    loading.value = false
                    onSuccessMutableLiveData.postValue(response.body())
                } else {
                    loading.value = false

                    onErrorMutableLiveData?.postValue(response.errorBody()?.string())
                }
            }

            override fun onFailure(call: Call<List<AllData>>, t: Throwable) {
                onErrorMutableLiveData?.postValue(t.message)
            }


        })
    }

    suspend fun insertHomeData(apiResponseData: ApiResponseData) {
        repository.insert(apiResponseData)
    }

    fun getApiResponseFromDb(apiName: String): LiveData<List<ApiResponseData>> {
        return repository.getApiResponse(apiName)
    }
}