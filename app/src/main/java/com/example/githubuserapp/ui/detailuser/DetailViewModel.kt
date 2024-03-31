package com.example.githubuserapp.ui.detailuser

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.data.database.FavouriteUser
import com.example.githubuserapp.data.repository.FavouriteUserRepository
import com.example.githubuserapp.data.response.DetailUserResponse
import com.example.githubuserapp.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application): ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listDetail = MutableLiveData<DetailUserResponse>()
    val listDetail: LiveData<DetailUserResponse> = _listDetail

    private val mFavoriteUserRepository: FavouriteUserRepository = FavouriteUserRepository(application)

    fun getUser(user: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getDetailUser(user)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        _listDetail.value = response.body()
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = true
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun insertFavUser(user: FavouriteUser) {
        mFavoriteUserRepository.insertFavouriteUser(user)
    }

    fun deleteFavUser(id: Int) {
        mFavoriteUserRepository.deleteFavouriteUser(id)
    }

    fun getAllFavorites(): LiveData<List<FavouriteUser>> = mFavoriteUserRepository.getAllFavouriteUser()

    companion object {
        private const val TAG = "DetailViewModel"
    }
}