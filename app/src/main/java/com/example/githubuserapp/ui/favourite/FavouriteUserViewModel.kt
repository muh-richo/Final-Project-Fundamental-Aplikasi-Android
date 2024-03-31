package com.example.githubuserapp.ui.favourite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.data.database.FavouriteUser
import com.example.githubuserapp.data.repository.FavouriteUserRepository

class FavouriteUserViewModel(application: Application): ViewModel() {
    private val mFavoriteUserRepository: FavouriteUserRepository = FavouriteUserRepository(application)

    private val _listFavUser = MutableLiveData<List<FavouriteUser>>()
    val listFavUser: LiveData<List<FavouriteUser>> = _listFavUser

    fun getAllFavUser(): LiveData<List<FavouriteUser>> = mFavoriteUserRepository.getAllFavouriteUser()
}