package com.example.githubuserapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavouriteUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavUser(user: FavouriteUser)

    @Query("SELECT * FROM FavouriteUser ORDER BY login ASC")
    fun getAllFavUser(): LiveData<List<FavouriteUser>>

    @Query("SELECT * FROM FavouriteUser WHERE FavouriteUser.id = :id")
    fun getFavUserById(id: Int): LiveData<FavouriteUser>

    @Query("DELETE FROM FavouriteUser WHERE FavouriteUser.id = :id")
    fun removeFavUser(id: Int)
}