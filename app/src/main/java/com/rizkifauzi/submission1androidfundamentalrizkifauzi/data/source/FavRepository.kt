package com.rizkifauzi.submission1androidfundamentalrizkifauzi.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.rizkifauzi.submission1androidfundamentalrizkifauzi.BuildConfig
import com.rizkifauzi.submission1androidfundamentalrizkifauzi.data.local.entity.FavEntity
import com.rizkifauzi.submission1androidfundamentalrizkifauzi.data.local.room.FavDao
import com.rizkifauzi.submission1androidfundamentalrizkifauzi.data.remote.response.GthubResponse
import com.rizkifauzi.submission1androidfundamentalrizkifauzi.data.remote.retrofit.ApiService
import com.rizkifauzi.submission1androidfundamentalrizkifauzi.utils.AppExecutors

class FavRepository private constructor(
    private val apiService: ApiService,
    private val favDao: FavDao,
    private val appExecutors: AppExecutors
) {
    private val result = MediatorLiveData<Result<List<FavEntity>>>()

    fun getFavUsers(): LiveData<Result<List<FavEntity>>> {
        result.value = Result.Loading
        val client = apiService.searchUsers(BuildConfig.KEY)
        client.enqueue(object : retrofit2.Callback<GthubResponse> {
            override fun onResponse(call: retrofit2.Call<GthubResponse>, response: retrofit2.Response<GthubResponse>) {
                if (response.isSuccessful) {
                    val users = response.body()?.items
                    val usersList = ArrayList<FavEntity>()
                    appExecutors.diskIO.execute {
                        users?.forEach { item ->
                            val isBookmarked = favDao.isUserFav(item.login)
                            val user = FavEntity(
                                item.login,
                                item.avatarUrl,
                                isBookmarked
                            )
                            usersList.add(user)
                        }
                        favDao.deleteAll()
                        favDao.insertFav(usersList)
                    }
                }
            }

            override fun onFailure(call: retrofit2.Call<GthubResponse>, t: Throwable) {
                result.value = Result.Error(t.message.toString())
            }
        })
        val localData = favDao.getFavUser()
        result.addSource(localData) { newData: List<FavEntity> ->
            result.value = Result.Success(newData)
        }
        return result
    }

    fun setFavUser(fav: FavEntity, bookmarkState: Boolean) {
        appExecutors.diskIO.execute {
            fav.isBookmarked = bookmarkState
            favDao.updateFav(fav)
        }
    }

    companion object {
        @Volatile
        private var instance: FavRepository? = null
        fun getInstance(
            apiService: ApiService,
            favDao: FavDao,
            appExecutors: AppExecutors
        ): FavRepository =
            instance ?: synchronized(this) {
                instance ?: FavRepository(apiService, favDao, appExecutors)
            }.also { instance = it }
    }
}
