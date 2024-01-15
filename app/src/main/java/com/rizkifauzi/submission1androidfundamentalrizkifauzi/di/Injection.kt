package com.rizkifauzi.submission1androidfundamentalrizkifauzi.di

import android.content.Context
import com.rizkifauzi.submission1androidfundamentalrizkifauzi.data.local.room.FavDatabase
import com.rizkifauzi.submission1androidfundamentalrizkifauzi.data.remote.retrofit.ApiConfig
import com.rizkifauzi.submission1androidfundamentalrizkifauzi.data.source.FavRepository
import com.rizkifauzi.submission1androidfundamentalrizkifauzi.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): FavRepository {
        val apiService = ApiConfig.getApiService()
        val database = FavDatabase.getInstance(context)
        val dao = database.favDao()
        val appExecutors = AppExecutors()
        return FavRepository.getInstance(apiService, dao, appExecutors)
    }
}