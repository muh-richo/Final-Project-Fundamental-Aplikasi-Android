package com.example.githubuserapp.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.githubuserapp.data.database.FavouriteUser
import com.example.githubuserapp.data.database.FavouriteUserDao
import com.example.githubuserapp.data.database.FavouriteUserRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavouriteUserRepository(application: Application) {
    private val mFavoriteUserDao: FavouriteUserDao
    private val executoreService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavouriteUserRoomDatabase.getDatabase(application)
        mFavoriteUserDao = db.favoriteUserDao()
    }

    fun getAllFavouriteUser(): LiveData<List<FavouriteUser>> = mFavoriteUserDao.getAllFavUser()

    fun insertFavouriteUser(user: FavouriteUser) {
        executoreService.execute {
            mFavoriteUserDao.insertFavUser(user)
        }
    }

    fun deleteFavouriteUser(id: Int) {
        executoreService.execute {
            mFavoriteUserDao.removeFavUser(id)
        }
    }
}