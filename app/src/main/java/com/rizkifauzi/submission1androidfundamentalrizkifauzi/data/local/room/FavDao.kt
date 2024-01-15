package com.rizkifauzi.submission1androidfundamentalrizkifauzi.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.rizkifauzi.submission1androidfundamentalrizkifauzi.data.local.entity.FavEntity

@Dao
interface FavDao {
    @Query("SELECT * FROM favorite where bookmarked = 1")
    fun getFavUser(): LiveData<List<FavEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFav(fav: List<FavEntity>)

    @Update
    fun updateFav(fav: FavEntity)

    @Query("DELETE FROM favorite WHERE bookmarked = 0")
    fun deleteAll()

    @Query("SELECT EXISTS(SELECT * FROM favorite WHERE username = :username AND bookmarked = 1)")
    fun isUserFav(username: String): Boolean
}
