package com.example.githubuserapp.ui.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubuserapp.ui.detailuser.DetailViewModel
import com.example.githubuserapp.ui.favourite.FavouriteUserViewModel

class FavouriteViewModelFactory(private val application: Application): ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: FavouriteViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): FavouriteViewModelFactory {
            if (INSTANCE == null) {
                synchronized(FavouriteViewModelFactory::class.java) {
                    INSTANCE = FavouriteViewModelFactory(application)
                }
            }
            return INSTANCE as FavouriteViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(application) as T
        } else if (modelClass.isAssignableFrom(FavouriteUserViewModel::class.java)) {
            return FavouriteUserViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown viewModel class: " + modelClass.name)
    }
}